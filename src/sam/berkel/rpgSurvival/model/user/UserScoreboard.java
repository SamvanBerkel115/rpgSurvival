package sam.berkel.rpgSurvival.model.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;
import sam.berkel.rpgSurvival.Main;

public class UserScoreboard {
    User user;
    private Scoreboard scoreboard;

    public UserScoreboard(User user) {
        this.user = user;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective levelObjective = scoreboard.registerNewObjective("levels", "Level", ChatColor.GOLD + "Levels");
        levelObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        user.getPlayer().setScoreboard(scoreboard);

        updateScoreboard();
    }

    public void updateScoreboard() {
        System.out.println("Updating score");
        UserLevels userLvls = user.getLevels();
        Objective levelObjective = scoreboard.getObjective("levels");

        levelObjective.getScore(ChatColor.WHITE + "Combat").setScore(userLvls.getCombatLvl());
        levelObjective.getScore(ChatColor.WHITE + "Crafting").setScore(userLvls.getCraftingLvl());
        levelObjective.getScore(ChatColor.WHITE + "Excavation").setScore(userLvls.getExcavationLvl());
        levelObjective.getScore(ChatColor.WHITE + "Farming").setScore(userLvls.getFarmingLvl());
        levelObjective.getScore(ChatColor.WHITE + "Fishing").setScore(userLvls.getFishingLvl());
        levelObjective.getScore(ChatColor.WHITE + "Magic").setScore(userLvls.getMagicLvl());
        levelObjective.getScore(ChatColor.WHITE + "Mining").setScore(userLvls.getMiningLvl());
        levelObjective.getScore(ChatColor.WHITE + "Woodcutting").setScore(userLvls.getWoodcuttingLvl());
    }

    public void setLevel(Main.Skill skill, int level) {
        Score skillScore = scoreboard.getObjective("levels").getScore(ChatColor.WHITE + skill.toString());
        skillScore.setScore(level);
    }
}
