package sam.berkel.rpgSurvival.model.user;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.*;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.skills.*;

import java.util.HashMap;
import java.util.UUID;

public class User {
    private org.bukkit.entity.Player player;
    private UserScoreboard scoreboard;
    private UserLevels levels;
    private HashMap<String, String> lockedItems;
    private String currentPoi;
    private String activeSpell;
    private boolean damageSpellHit;
    private boolean isInCutscene;
    private Entity dialogueCitizen;
    private UserState state;
    private Block placedTeleBlock;

    public User(Player player,
                int combatXp, int combatLvl, int craftingXp, int craftingLvl, int excavationXp, int excavationLvl, int farmingXp, int farmingLvl,
                int fishingXp, int fishingLvl, int magicXp, int magicLvl, int miningXp, int miningLvl, int woodcuttingXp, int woodcuttingLvl) {
        System.out.println("Added new user" + player.getDisplayName());
        this.player = player;

        levels = new UserLevels(combatXp, combatLvl, craftingXp, craftingLvl, excavationXp, excavationLvl, farmingXp, farmingLvl, fishingXp, fishingLvl, magicXp, magicLvl, miningXp, miningLvl, woodcuttingXp, woodcuttingLvl);

        System.out.println(player.getUniqueId());

        currentPoi = "";

        scoreboard = new UserScoreboard(this);

        lockedItems = new HashMap<>();
        initLockedItems();

        activeSpell = "fireBolt";
        damageSpellHit = false;
        isInCutscene = false;
    }

    public String getDisplayName() {
        return player.getDisplayName();
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    public UserLevels getLevels() {
        return levels;
    }

    public void addXp(int xp, Main.Skill skill) {
        int currentLvl;

        boolean hasIncreasedLevel = levels.addXp(xp, skill);

        if (hasIncreasedLevel) {
            switch (skill) {
                case COMBAT:
                    levels.increaseCombatLevel();
                    currentLvl = levels.getCombatLvl();

                    grantAdvancement("combat/level" + levels.getCombatLvl());
                    break;
                case CRAFTING:
                    levels.increaseCraftingLevel();
                    currentLvl = levels.getCraftingLvl();

                    grantAdvancement("crafting/level" + levels.getCombatLvl());
                    grantAdvancement("crafting/recipe" + levels.getCraftingLvl());
                    break;
                case EXCAVATION:
                    levels.increaseExcavationLevel();
                    currentLvl = levels.getExcavationLvl();

                    grantAdvancement("excavation/level" + levels.getExcavationLvl());
                    break;
                case FARMING:
                    levels.increaseFarmingLevel();
                    currentLvl = levels.getFarmingLvl();

                    grantAdvancement("farming/level" + levels.getFarmingLvl());
                    break;
                case FISHING:
                    levels.increaseFishingLevel();
                    currentLvl = levels.getFishingLvl();

                    grantAdvancement("fishing/level" + levels.getFishingLvl());
                    break;
                case MAGIC:
                    levels.increaseMagicLevel();
                    currentLvl = levels.getMagicLvl();

                    grantAdvancement("magic/level" + levels.getMagicLvl());
                    break;
                case MINING:
                    levels.increaseMiningLevel();
                    currentLvl = levels.getMiningLvl();

                    grantAdvancement("mining/level" + levels.getMagicLvl());
                    break;
                case WOODCUTTING:
                    levels.increaseWoodcuttingLevel();
                    currentLvl = levels.getWoodcuttingLvl();

                    grantAdvancement("woodcutting/level" + levels.getWoodcuttingLvl());
                    break;

                default:
                    System.out.println("This skill does not exist");
                    return;
            }

            scoreboard.setLevel(skill, currentLvl);
            showLevelUpNotification(skill, currentLvl);

            initLockedItems();
        }
    }

    public String getActiveSpell() {return activeSpell;}

    public String getCurrentPoi() {
        return currentPoi;
    }

    public void setCurrentPoi(String currentPoi) {
        this.currentPoi = currentPoi;
    }

    public void removeCurrentPoi() {
        currentPoi = null;
    }

    public Entity getDialogueCitizen() {
        return dialogueCitizen;
    }

    public void setDialogueCitizen(Entity citizen) {
        dialogueCitizen = citizen;
    }

    public boolean isInCutscene() {
        return isInCutscene;
    }

    public void setInCutscene(boolean inCutscene) {
        isInCutscene = inCutscene;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Block getPlacedTeleBlock() {
        return placedTeleBlock;
    }

    public void setPlacedTeleBlock(Block placedTeleBlock) {
        this.placedTeleBlock = placedTeleBlock;
    }

    public boolean hasDamageSpellHit() {
        return damageSpellHit;
    }

    public void setActiveSpell(String activeSpell) {this.activeSpell = activeSpell;}

    public String toolIsLockedBy(ItemStack tool) {
        String toolType = tool.getType().toString();

        if ( lockedItems.containsKey(toolType) ) {
            return lockedItems.get( toolType );
        } else {
            return "none";
        }
    }

    public void grantAdvancement(String advancementName) {
        NamespacedKey nsk = new NamespacedKey(Main.getPlugin(Main.class), advancementName);
        Advancement advancement = Main.getPlugin(Main.class).getServer().getAdvancement(nsk);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementProgress(advancement);
            progress.awardCriteria("LevelReached");
        }
    }

    public void showLevelUpNotification(Main.Skill skill, int level) {
        player.sendTitle("", "Your " + skill.toString() + " level is now: " + ChatColor.DARK_AQUA + level, 10, 40, 20);

        Location playerLoc = player.getLocation();
        Firework fw = (Firework) playerLoc.getWorld().spawnEntity(playerLoc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
    }


    public void initLockedItems() {
        lockedItems = new HashMap<>();

        for (String item : Combat.getBlockedItems(levels.getCombatLvl())) {
            lockedItems.put(item, "combat");
        }

        for (String item : Excavation.getBlockedItems(levels.getExcavationLvl())) {
            lockedItems.put(item, "excavation");
        }

        for (String item : Mining.getBlockedItems(levels.getMiningLvl())) {
            lockedItems.put(item, "mining");
        }

        for (String item : Woodcutting.getBlockedItems(levels.getWoodcuttingLvl())) {
            lockedItems.put(item, "woodcutting");
        }
    }
}
