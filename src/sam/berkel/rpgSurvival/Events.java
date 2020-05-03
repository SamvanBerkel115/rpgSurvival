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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

        ItemStack tool = player.getInventory().getItemInMainHand();
        player.sendMessage("You used" + tool.getType().toString());
        player.sendMessage("You broke" + brokenBlock.getType().toString() + "using " + tool.getType().toString());
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
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Entity target = event.getEntity();

            int xp = (int) event.getDamage() * Combat.getXpMultiplier(target);

            User damagerUser = Server.getInstance().getUser(damager.getUniqueId());
            damagerUser.addXp(xp, "Combat");

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
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user;

        if ( plugin.getConfig().contains("Users." + player.getUniqueId()) ) {
            System.out.println("An existing user joined");

            int miningLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningLvl").toString());
            int miningXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningXp").toString());
            int woodcuttingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingLvl").toString());
            int woodcuttingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingXp").toString());
            int excavationLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationLvl").toString());
            int excavationXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationXp").toString());
            int fishingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingLvl").toString());
            int fishingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingXp").toString());
            int combatLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatLvl").toString());
            int combatXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatXp").toString());

            user = new User(player, combatXp, combatLvl, excavationXp, excavationLvl, fishingXp, fishingLvl, miningXp, miningLvl, woodcuttingXp, woodcuttingLvl);
        } else {
            System.out.println("A new user joined");
            plugin.getConfig().getStringList("Users").add(player.getUniqueId().toString());

            plugin.getConfig().set("Users." + player.getUniqueId() +".combatLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".combatXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", "0");

            plugin.saveConfig();

            user = new User(player, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1);
        }

        Server.getInstance().userJoined(user);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Server.getInstance().getUser(player.getUniqueId());

        plugin.getConfig().set("Users." + player.getUniqueId() +".combatLvl", user.getCombatLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".combatXp", user.getCombatXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", user.getExcavationLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", user.getExcavationXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", user.getFishingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", user.getFishingXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", user.getMiningLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", user.getMiningXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", user.getWoodcuttingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", user.getWoodcuttingXp());

        plugin.saveConfig();
    }
}
