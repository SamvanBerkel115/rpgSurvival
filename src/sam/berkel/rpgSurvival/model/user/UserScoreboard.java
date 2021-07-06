package sam.berkel.rpgSurvival.model.user;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;
import sam.berkel.rpgSurvival.Main;

public class UserScoreboard {
    User user;
    private Scoreboard scoreboard;

    public UserScoreboard(User user) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        Objective levelObjective = scoreboard.registerNewObjective("levels", "Level", ChatColor.GOLD + "Levels");
        levelObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        user.getPlayer().setScoreboard(scoreboard);

        updateScoreboard();
        this.user = user;
    }

    public void updateScoreboard() {
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Combat").setScore(user.getLevels().getCombatLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Crafting").setScore(user.getLevels().getCraftingLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Excavation").setScore(user.getLevels().getExcavationLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Farming").setScore(user.getLevels().getFarmingLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Fishing").setScore(user.getLevels().getFishingLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Magic").setScore(user.getLevels().getMagicLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Mining").setScore(user.getLevels().getMiningLvl());
        scoreboard.getObjective("levels").getScore(ChatColor.WHITE + "Woodcutting").setScore(user.getLevels().getWoodcuttingLvl());
    }

    public void setLevel(Main.Skill skill, int level) {
        Score skillScore = scoreboard.getObjective("levels").getScore(ChatColor.WHITE + skill.toString());
        skillScore.setScore(level);
    }
}
