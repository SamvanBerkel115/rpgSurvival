package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;
import sam.berkel.rpgSurvival.skills.*;

import java.util.Arrays;
import java.util.List;

public class Events implements Listener {
    private Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Server server = Server.getInstance();
        Block brokenBlock = event.getBlock();
        Player player = event.getPlayer();
        User user = server.getUser(player.getUniqueId());

        Mining.brokeBlock(user, brokenBlock);
        Woodcutting.brokeBlock(user, brokenBlock);
        Excavation.brokeBlock(user, brokenBlock);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            User user = Server.getInstance().getUser(event.getPlayer().getUniqueId());

            String lockedBy = user.toolIsLockedBy(event.getItem());
            if (lockedBy.equals("none")) {
                System.out.println("Tool is unlocked");
            } else {
                System.out.println("Tool " + event.getItem().toString() + " is locked");
                user.getPlayer().sendMessage("Your " + lockedBy + " level is too low to use this item");
                event.setCancelled(true);
            }
        }

        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Wand")) {
                InventoryMenu.openSpellMenu(player);
            }
        }

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (player.getInventory().getItemInMainHand() != null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Wand")) {
                    Magic.castSpell(player);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        System.out.println(event.getCause().toString());
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Entity target = event.getEntity();

            int xp = (int) event.getDamage() * Combat.getXpMultiplier(target);

            User damagerUser = Server.getInstance().getUser(damager.getUniqueId());

            if (damagerUser.hasDamageSpellHit()) {
                damagerUser.addXp(xp, Main.Skill.MAGIC);
            } else {
                damagerUser.addXp(xp, Main.Skill.COMBAT);
            }


            damager.sendMessage(damager.getDisplayName() + " did " + event.getDamage() + "to " + target.getName());
        }
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        System.out.println(event.getState());

        if(event.getCaught() instanceof Item){
            System.out.println("Caught item");
            Item caughtItem = (Item) event.getCaught();
            User user = Server.getInstance().getUser(event.getPlayer().getUniqueId());
            Fishing.handleCaughtItem(caughtItem, user);
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        User user = Server.getInstance().getUser(player.getUniqueId());

        user.addXp(1000, Main.Skill.CRAFTING);
    }

    @EventHandler
    public void onSmeltItem(FurnaceSmeltEvent event) {

    }

    // Inventory events
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        System.out.println(event.getCurrentItem().getType().toString());

        if (event.getCurrentItem() != null) {
            // Open the main menu.
            if (event.getRawSlot() == 36 && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Menu")) {
                event.setCancelled(true);

                InventoryMenu.openMainMenu(player);
            }

            // Check if a menu is open and handle the click if true.
            if (player.getOpenInventory().getTitle().substring(0, 2).equals(ChatColor.DARK_RED.toString())) {
                event.setCancelled(true);

                InventoryMenu.handleClick(event);
            }
        }

        if (player.getOpenInventory().getTitle().equals("Furnace") && event.getAction().toString().split("_")[0].equals("PICKUP_ALL")) {
            User user = Server.getInstance().getUser(player.getUniqueId());
            user.addXp(1000, Main.Skill.CRAFTING
            );
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        String droppedItemName = event.getItemDrop().getItemStack().getItemMeta().getDisplayName();

        // Prevent the menu item from being dropped.
        if (droppedItemName.equals(ChatColor.GOLD + "Menu")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

    }

    // Event that handle players joining and leaving.
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Server.getInstance().userJoined(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Server.getInstance().getUser(player.getUniqueId());

        plugin.getConfig().set("Users." + player.getUniqueId() +".combatLvl", user.getCombatLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".combatXp", user.getCombatXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".craftingLvl", user.getCraftingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".craftingXp", user.getCraftingXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", user.getExcavationLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", user.getExcavationXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", user.getFarmingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", user.getFarmingXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", user.getFishingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", user.getFishingXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".magicLvl", user.getMagicLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".magicXp", user.getMagicXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", user.getMiningLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", user.getMiningXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", user.getWoodcuttingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", user.getWoodcuttingXp());

        plugin.saveConfig();
    }
}
