package sam.berkel.rpgSurvival;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.model.*;
import sam.berkel.rpgSurvival.model.citizen.CheckLeftRunnable;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.citizen.Response;
import sam.berkel.rpgSurvival.model.citizen.State;
import sam.berkel.rpgSurvival.model.teleport.TeleportBlock;
import sam.berkel.rpgSurvival.model.user.User;
import sam.berkel.rpgSurvival.model.user.UserLevels;
import sam.berkel.rpgSurvival.model.user.UserState;
import sam.berkel.rpgSurvival.skills.*;

import java.util.ArrayList;
import java.util.UUID;

public class Events implements Listener {
    private Plugin plugin = Main.getPlugin(Main.class);
    private Server server = Server.getInstance();

    // Block events

    /**
     *
     * @param event
     */
    @EventHandler
    public  void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.WHEAT) {
            Player player = event.getPlayer();
            User user = server.getUser(player.getUniqueId());

            user.addXp(20, Main.Skill.FARMING);
        }

        ItemStack itemInHand = event.getItemInHand();
        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasCustomModelData()) {
            ItemMeta itemInHandMeta = itemInHand.getItemMeta();
            if (itemInHandMeta.getCustomModelData() == 938124) {
                Player player = event.getPlayer();
                User user = server.getUser(player.getUniqueId());

                user.setPlacedTeleBlock(event.getBlock());
                player.sendTitle("" , "Type the name of the teleport in the chat.", 10, 80, 20);

                user.setState(UserState.CREATING_TELEPORT);
            }
        }

        System.out.println("Player placed block: " + event.getItemInHand().getType());
    }

    /**
     * Add the right mining, woodcutting or excavation xp whenever the player breaks a block
     * @param event
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Server server = Server.getInstance();
        Block brokenBlock = event.getBlock();
        Player player = event.getPlayer();
        User user = server.getUser(player.getUniqueId());

        Mining.brokeBlock(user, brokenBlock);
        Woodcutting.brokeBlock(user, brokenBlock);
        Excavation.brokeBlock(user, brokenBlock);
        Farming.brokeBlock(user, brokenBlock);

        if (brokenBlock.getType() == Material.CYAN_GLAZED_TERRACOTTA) {
            TeleportBlock tpBlock = server.getTeleportBlock(brokenBlock.getLocation());

            if (tpBlock != null) {
                server.removeTeleportBlock(tpBlock);
            }
        }
    }

    // Player events

    /**
     * If the player is interacting with a block, check if the player is allowed to use the tool that he is interacting with.
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.hasBlock()) {
            User user = server.getUser(player.getUniqueId());

            ItemStack tool = event.getItem();

            if (tool != null) {
                String lockedBy = user.toolIsLockedBy(tool);
                if (!lockedBy.equals("none")) {
                    player.sendMessage("Your " + lockedBy + " level is too low to use this item");
                    event.setCancelled(true);
                }
            }

            if (TeleportBlock.isTeleportBlock(event.getClickedBlock())) {
                InventoryMenu.openTeleportMenu(player);

                event.setCancelled(true);
            }
        }

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand != null) {
                ItemMeta itemMeta = itemInHand.getItemMeta();

                if (itemMeta != null && itemMeta.hasCustomModelData()) {
                    if (itemMeta.getCustomModelData() == 938123) {
                        InventoryMenu.openSpellMenu(player);
                    }
                }
            }
        }

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand != null) {
                ItemMeta itemMeta = itemInHand.getItemMeta();

                if (itemMeta != null && itemMeta.hasCustomModelData()) {
                    if (itemMeta.getCustomModelData() == 938123) {
                        Magic.castSpell(player);
                    }
                }
            }
        }
    }

    /**
     * Let the player talk to a citizen when he right clicks it
     * @param event
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();

        if (clickedEntity instanceof Villager) {
            Citizen citizen = server.getCitizen(clickedEntity.getUniqueId());

            if (citizen != null) {
                User user = server.getUser(player.getUniqueId());
                user.setDialogueCitizen(clickedEntity);

                State citizenState = citizen.getState(player.getUniqueId());
                citizen.setActiveState(player, citizenState);

                new CheckLeftRunnable(user, clickedEntity).runTaskTimerAsynchronously(plugin, 0, 10);
            }

            event.setCancelled(true);
        }

        System.out.println("Clicked: " + clickedEntity.getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Server server = Server.getInstance();
        Player player = event.getPlayer();

        if (false) {
            event.setCancelled(true);
        }
    }

    /**
     * Get a random weight for a fish the user caught and add this to the metadata.
     * @param event
     */
    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if(event.getCaught() instanceof Item){
            Item caughtItem = (Item) event.getCaught();
            User user = server.getUser(event.getPlayer().getUniqueId());
            Fishing.handleCaughtItem(caughtItem, user);
        }

    }

    @EventHandler
    public  void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        String[] commandSplit = command.split(" ");

        if (commandSplit.length > 0 && commandSplit[0].equals("/citizen")) {
            Player player = event.getPlayer();
            UUID playerUUID = player.getUniqueId();
            User user = server.getUser(playerUUID);

            Entity entity = user.getDialogueCitizen();
            Citizen dialogueCitizen = server.getCitizen(entity.getUniqueId());

            if (dialogueCitizen != null) {
                State currentState = dialogueCitizen.getState(playerUUID);

                int chosenResponseIndex = Integer.parseInt(commandSplit[2]);
                Response chosenResponse = currentState.getResponses().get(chosenResponseIndex);
                State nextState = chosenResponse.getState();

                dialogueCitizen.setActiveState(player, nextState);
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Server server = Server.getInstance();
        Player player = event.getPlayer();
        User user = server.getUser(player.getUniqueId());

        if (user.getState() == UserState.CREATING_TELEPORT) {
            Block teleBlock = user.getPlacedTeleBlock();
            Location blockLoc = new Location(null, teleBlock.getX(), teleBlock.getY(), teleBlock.getZ());
            TeleportBlock tpBlock = new TeleportBlock(event.getMessage(), Material.CYAN_GLAZED_TERRACOTTA, blockLoc, blockLoc);

            server.addTeleportBlock(tpBlock);
            user.setState(null);

            event.setCancelled(true);
        }
    }

    //Entity events

    /**
     * Add combat xp if a player kills an entity.
     * @param event
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity killedEntity = event.getEntity();
        Player killer = killedEntity.getKiller();

        if (killer != null) {
            User killerUser = server.getUser(killer.getUniqueId());

            int xp = Combat.getEntityXp(killedEntity);
            killerUser.addXp(xp, Main.Skill.COMBAT);
        }
    }

    /**
     * Check if the player is allowed to use this weapon whenever he damages an entity.
     * @param event
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (damager instanceof  Player) {
            Player player = (Player) damager;

            ItemStack weapon = player.getInventory().getItemInMainHand();

            if (weapon != null) {
                User user = server.getUser(player.getUniqueId());

                String lockedBy = user.toolIsLockedBy(weapon);
                if (!lockedBy.equals("none")) {
                    player.sendMessage("Your " + lockedBy + " level is too low to use this item");
                    event.setCancelled(true);
                }
            }
        }

        Citizen citizen = server.getCitizen(victim.getUniqueId());

        if (citizen != null) {
            event.setCancelled(true);
        }
    }

    /**
     * Check if the player is allowed to fire this bow.
     * @param event
     */
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        Entity shooter = event.getEntity();

        if (shooter instanceof Player) {
            Player player = (Player) shooter;
            User user = server.getUser(shooter.getUniqueId());

            ItemStack bow = event.getBow();

            if (bow != null) {
                String lockedBy = user.toolIsLockedBy(bow);

                if (!lockedBy.equals("none")) {
                    player.sendMessage("Your " + lockedBy + " level is too low to use this item");
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * Prevent entities from spawning within points of interest.
     * @param event
     */
    @EventHandler
    public  void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        ArrayList<PointOfInterest> pois = server.getPOIs();

        for (int i = 0; i < pois.size(); i++) {
            PointOfInterest poi = pois.get(i);
            if (poi.isWithinRadius(entity)) {
                event.setCancelled(true);
                break;
            }
        }
    }

    // Other events
    /**
     * Add crafting xp whenever the player crafts an item
     * @param event
     */
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        User user = server.getUser(player.getUniqueId());

        ItemStack craftedItem = event.getCurrentItem();
        ItemMeta craftedItemMeta = craftedItem.getItemMeta();

        if (craftedItemMeta.hasCustomModelData()) {
            int modelData = craftedItemMeta.getCustomModelData();

            if (!Crafting.canCraft(user.getLevels().getCraftingLvl(), modelData)) {
                player.sendMessage("Your crafting level is too low to craft this item");
                event.setCancelled(true);

                return;
            }
        }

        user.addXp(50, Main.Skill.CRAFTING);
    }

    @EventHandler
    public void onSmeltItem(FurnaceSmeltEvent event) {

    }

    // Inventory events
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null) {
            // Open the main menu.
            if (event.getRawSlot() == 36 && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Menu")) {
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
            User user = server.getUser(player.getUniqueId());
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
        server.userJoined(event.getPlayer());
        CutScene.intro(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = server.getUser(player.getUniqueId());
        UserLevels userLvls = user.getLevels();

        plugin.getConfig().set("Users." + player.getUniqueId() +".combatLvl", userLvls.getCombatLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".combatXp", userLvls.getCombatXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".craftingLvl", userLvls.getCraftingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".craftingXp", userLvls.getCraftingXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", userLvls.getExcavationLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", userLvls.getExcavationXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", userLvls.getFarmingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", userLvls.getFarmingXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", userLvls.getFishingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", userLvls.getFishingXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".magicLvl", userLvls.getMagicLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".magicXp", userLvls.getMagicXp());

        plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", userLvls.getMiningLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", userLvls.getMiningXp());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", userLvls.getWoodcuttingLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", userLvls.getWoodcuttingXp());

        plugin.saveConfig();
    }
}
