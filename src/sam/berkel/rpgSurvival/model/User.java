package sam.berkel.rpgSurvival.model;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.*;

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

    public User(Player player, int miningXp, int miningLvl, int woodcuttingXp, int woodcuttingLvl, int excavationXp, int excavationLvl) {
        System.out.println("Added new user" + player.getDisplayName());
        this.player = player;
        this.miningXp = miningXp;
        this.miningLvl = miningLvl;
        this.woodcuttingXp = miningXp;
        this.woodcuttingLvl = miningLvl;
        this.excavationXp = miningXp;
        this.excavationLvl = miningLvl;

        initScoreboard();
        updateScoreboard(miningLvl, woodcuttingLvl, excavationLvl);
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
                    break;
                case "Woodcutting":
                    woodcuttingLvl++;
                    break;
                case "Excavation":
                    excavationLvl++;
                    break;
                default:
                    System.out.println("This skill does not exist");
                    return;
            }

            Score skillScore = scoreboard.getObjective("levels").getScore(skill);
            skillScore.setScore(currentLvl);

            showLevelUpNotification(skill);
        }

        System.out.println("Total xp: " + miningXp);
        System.out.println("Xp for next level: " + xpForNextLevel);
    }

    public int getMiningLvl() {
        return miningLvl;
    }

    public int getMiningXp() {
        return miningXp;
    }

    public void showLevelUpNotification(String skill) {
        player.sendTitle("", "Your " + skill + " level is now: " + ChatColor.DARK_AQUA + miningLvl, 10, 70, 20);

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
        updateScoreboard(miningLvl, woodcuttingLvl, excavationLvl);
    }

    public void updateScoreboard(int miningLvl, int fishingLvl, int excavationLvl) {
        scoreboard.getObjective("levels").getScore("Mining").setScore(miningLvl);
        scoreboard.getObjective("levels").getScore("Woodcutting").setScore(fishingLvl);
        scoreboard.getObjective("levels").getScore("Excavation").setScore(excavationLvl);

        Score questPoints = scoreboard.getObjective("levels").getScore(ChatColor.AQUA + "Quest Points: " + ChatColor.WHITE + "0");
        questPoints.setScore(0);
    }
}
