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

import java.util.HashMap;
import java.util.UUID;

public class User {
    private org.bukkit.entity.Player player;
    private Scoreboard scoreboard;
    private int miningXp;
    private int miningLvl;
    private int woodcuttingXp;
    private int woodcuttingLvl;
    private int excavationXp;
    private int excavationLvl;
    private int combatXp;
    private int combatLvl;
    private HashMap<String, String> lockedItems;

    public User(Player player, int miningXp, int miningLvl, int woodcuttingXp, int woodcuttingLvl, int excavationXp, int excavationLvl, int combatXp, int combatLvl) {
        System.out.println("Added new user" + player.getDisplayName());
        this.player = player;
        this.miningXp = miningXp;
        this.miningLvl = miningLvl;
        this.woodcuttingXp = woodcuttingXp;
        this.woodcuttingLvl = woodcuttingLvl;
        this.excavationXp = excavationXp;
        this.excavationLvl = excavationLvl;
        this.combatXp = combatXp;
        this.combatLvl = combatLvl;

        initScoreboard();
        updateScoreboard(miningLvl, woodcuttingLvl, excavationLvl, combatLvl);

        lockedItems = new HashMap<>();
        lockedItems.put("IRON_PICKAXE", "mining");
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
            case "Excavation":
                excavationXp += xp;
                currentXp = excavationXp;
                currentLvl = excavationLvl;
                break;
            case "Combat":
                combatXp += xp;
                currentXp = combatXp;
                currentLvl = combatLvl;
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
                case "Mining":
                    miningLvl++;
                    grantAdvancement("mining/level" + miningLvl);
                    break;
                case "Woodcutting":
                    woodcuttingLvl++;
                    grantAdvancement("woodcutting/level" + woodcuttingLvl);
                    break;
                case "Excavation":
                    excavationLvl++;
                    grantAdvancement("excavation/level" + excavationLvl);
                    break;
                case "Combat":
                    combatLvl++;
                    grantAdvancement("combat/level" + combatLvl);
                    break;
                default:
                    System.out.println("This skill does not exist");
                    return;
            }
            currentLvl++;

            Score skillScore = scoreboard.getObjective("levels").getScore(skill);
            skillScore.setScore(currentLvl);

            showLevelUpNotification(skill, currentLvl);
        }

        System.out.println("Total xp: " + currentXp);
        System.out.println("Xp for next level: " + xpForNextLevel);
    }

    public int getMiningLvl() {
        return miningLvl;
    }

    public int getMiningXp() {
        return miningXp;
    }

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
        updateScoreboard(miningLvl, woodcuttingLvl, excavationLvl, combatLvl);
    }

    public void updateScoreboard(int miningLvl, int fishingLvl, int excavationLvl, int combatLvl) {
        scoreboard.getObjective("levels").getScore("Mining").setScore(miningLvl);
        scoreboard.getObjective("levels").getScore("Woodcutting").setScore(fishingLvl);
        scoreboard.getObjective("levels").getScore("Excavation").setScore(excavationLvl);
        scoreboard.getObjective("levels").getScore("Combat").setScore(combatLvl);

        Score questPoints = scoreboard.getObjective("levels").getScore(ChatColor.AQUA + "Quest Points: " + ChatColor.WHITE + "0");
        questPoints.setScore(0);
    }
}
