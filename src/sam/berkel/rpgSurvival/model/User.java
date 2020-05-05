package sam.berkel.rpgSurvival.model;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.skills.Combat;
import sam.berkel.rpgSurvival.skills.Excavation;
import sam.berkel.rpgSurvival.skills.Mining;
import sam.berkel.rpgSurvival.skills.Woodcutting;

import java.util.HashMap;
import java.util.UUID;

public class User {
    private org.bukkit.entity.Player player;
    private Scoreboard scoreboard;
    private int combatXp;
    private int combatLvl;
    private int craftingXp;
    private int craftingLvl;
    private int magicXp;
    private int magicLvl;
    private int miningXp;
    private int miningLvl;
    private int woodcuttingXp;
    private int woodcuttingLvl;
    private int excavationXp;
    private int excavationLvl;
    private int farmingXp;
    private int farmingLvl;
    private int fishingXp;
    private int fishingLvl;
    private HashMap<String, String> lockedItems;
    private String activeSpell;

    public User(Player player,
                int combatXp, int combatLvl, int craftingXp, int craftingLvl, int excavationXp, int excavationLvl, int farmingXp, int farmingLvl,
                int fishingXp, int fishingLvl, int magicXp, int magicLvl, int miningXp, int miningLvl, int woodcuttingXp, int woodcuttingLvl) {
        System.out.println("Added new user" + player.getDisplayName());
        this.player = player;
        this.combatXp = combatXp;
        this.combatLvl = combatLvl;
        this.craftingXp = craftingXp;
        this.craftingLvl = craftingLvl;
        this.excavationXp = excavationXp;
        this.excavationLvl = excavationLvl;
        this.farmingLvl = fishingLvl;
        this.farmingXp = fishingXp;
        this.fishingLvl = fishingLvl;
        this.fishingXp = fishingXp;
        this.magicXp = magicXp;
        this.magicLvl = magicLvl;
        this.miningXp = miningXp;
        this.miningLvl = miningLvl;
        this.woodcuttingXp = woodcuttingXp;
        this.woodcuttingLvl = woodcuttingLvl;

        initScoreboard();
        updateScoreboard(combatLvl, craftingLvl, excavationLvl, farmingLvl, fishingLvl, magicLvl, miningLvl, woodcuttingLvl);

        lockedItems = new HashMap<>();
        initLockedItems();

        activeSpell = "fireBolt";
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

    public void addXp(int xp, String skill) {
        int currentXp;
        int currentLvl;

        switch (skill) {
            case "Combat":
                combatXp += xp;
                currentXp = combatXp;
                currentLvl = combatLvl;
                break;
            case "Crafting":
                craftingXp += xp;
                currentXp = craftingXp;
                currentLvl = craftingLvl;
                break;
            case "Excavation":
                excavationXp += xp;
                currentXp = excavationXp;
                currentLvl = excavationLvl;
                break;
            case "Farming":
                farmingXp += xp;
                currentXp = farmingXp;
                currentLvl = farmingLvl;
                break;
            case "Fishing":
                fishingXp += xp;
                currentXp = fishingXp;
                currentLvl = fishingLvl;
                break;
            case "Magic":
                magicXp += xp;
                currentXp = magicXp;
                currentLvl = magicLvl;
                break;
            case "Mining":
                miningXp += xp;
                currentXp = miningXp;
                currentLvl = miningLvl;
                break;
            case "Woodcutting":
                woodcuttingXp += xp;
                currentXp = woodcuttingXp;
                currentLvl = woodcuttingLvl;
                break;


            default:
                System.out.println("This skill does not exist");
                return;
        }

        Server server = Server.getInstance();
        double base = server.getBase();
        double exponent = server.getExponent();

        // If The player has enough xp for the next level, increment their level and update the scoreboard.
        int xpForNextLevel = (int) (base * (1 - Math.pow(exponent, currentLvl)) / (1 - exponent));
        if (currentXp >= xpForNextLevel) {
            switch (skill) {
                case "Combat":
                    combatLvl++;
                    grantAdvancement("combat/level" + combatLvl);
                    break;
                case "Crafting":
                    craftingLvl++;
                    grantAdvancement("crafting/level" + craftingLvl);
                    break;
                case "Excavation":
                    excavationLvl++;
                    grantAdvancement("excavation/level" + excavationLvl);
                    break;
                case "Farming":
                    farmingLvl++;
                    grantAdvancement("farming/level" + farmingLvl);
                    break;
                case "Fishing":
                    fishingLvl++;
                    grantAdvancement("fishing/level" + fishingLvl);
                    break;
                case "Magic":
                    magicLvl++;
                    grantAdvancement("magic/level" + magicLvl);
                    break;
                case "Mining":
                    miningLvl++;
                    grantAdvancement("mining/level" + miningLvl);
                    break;
                case "Woodcutting":
                    woodcuttingLvl++;
                    grantAdvancement("woodcutting/level" + woodcuttingLvl);
                    break;

                default:
                    System.out.println("This skill does not exist");
                    return;
            }
            currentLvl++;

            Score skillScore = scoreboard.getObjective("levels").getScore(ChatColor.GRAY + skill);
            skillScore.setScore(currentLvl);

            showLevelUpNotification(skill, currentLvl);
        }

        System.out.println("Total xp: " + currentXp);
        System.out.println("Xp for next level: " + xpForNextLevel);
    }

    public int getCombatXp() {
        return combatXp;
    }

    public int getCombatLvl() {
        return combatLvl;
    }

    public int getCraftingXp() {
        return craftingXp;
    }

    public int getCraftingLvl() {
        return craftingLvl;
    }
    public int getExcavationXp() {
        return excavationXp;
    }

    public int getExcavationLvl() {
        return excavationLvl;
    }

    public int getFarmingXp() {
        return farmingXp;
    }

    public int getFarmingLvl() {
        return farmingLvl;
    }

    public int getFishingXp() {
        return fishingXp;
    }

    public int getFishingLvl() {
        return fishingLvl;
    }

    public int getMagicLvl() {
        return magicLvl;
    }

    public int getMagicXp() {
        return magicXp;
    }

    public int getMiningLvl() {
        return miningLvl;
    }

    public int getMiningXp() {
        return miningXp;
    }

    public int getWoodcuttingXp() {
        return woodcuttingXp;
    }

    public int getWoodcuttingLvl() {
        return woodcuttingLvl;
    }

    public String getActiveSpell() {return activeSpell;}

    public void setActiveSpell(String activeSpell) {this.activeSpell = activeSpell;}

    public String toolIsLockedBy(ItemStack tool) {
        System.out.println(tool.getItemMeta().getDisplayName());
        System.out.println(tool.getType());
        System.out.println(lockedItems);
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

    public void showLevelUpNotification(String skill, int level) {
        player.sendTitle("", "Your " + skill + " level is now: " + ChatColor.DARK_AQUA + level, 10, 40, 20);

        Location playerLoc = player.getLocation();
        Firework fw = (Firework) playerLoc.getWorld().spawnEntity(playerLoc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();
    }

    public void initScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        Objective levelObjective = scoreboard.registerNewObjective("levels", "Level", ChatColor.DARK_AQUA + "Levels");
        levelObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
        updateScoreboard(combatLvl, craftingLvl, excavationLvl, farmingLvl, fishingLvl, magicLvl, miningLvl, woodcuttingLvl);
    }

    public void updateScoreboard(int combatLvl, int craftingLvl, int excavationLvl, int farmingLvl, int fishingLvl, int magicLvl, int miningLvl, int woodcuttingLvl) {
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Combat").setScore(combatLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Crafting").setScore(craftingLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Excavation").setScore(excavationLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Farming").setScore(farmingLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Fishing").setScore(fishingLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Magic").setScore(magicLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Mining").setScore(miningLvl);
        scoreboard.getObjective("levels").getScore(ChatColor.GRAY + "Woodcutting").setScore(woodcuttingLvl);
    }

    public void initLockedItems() {
        for (String item : Combat.getBlockedItems(combatLvl)) {
            lockedItems.put(item, "combat");
        }

        for (String item : Excavation.getBlockedItems(excavationLvl)) {
            lockedItems.put(item, "excavation");
        }

        for (String item : Mining.getBlockedItems(miningLvl)) {
            lockedItems.put(item, "mining");
        }

        for (String item : Woodcutting.getBlockedItems(woodcuttingLvl)) {
            lockedItems.put(item, "woodcutting");
        }
    }
}
