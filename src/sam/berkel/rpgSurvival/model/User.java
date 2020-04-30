package sam.berkel.rpgSurvival.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class User {
    private org.bukkit.entity.Player player;
    private Scoreboard scoreboard;
    private int miningXp;
    private int miningLvl;

    public User(Player player) {
        this.player = player;
        miningXp = 0;
        miningLvl = 1;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        Objective o = scoreboard.registerNewObjective("Levels", "Test");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score miningLevel = o.getScore("Mining");
        miningLevel.setScore(1);

        Score fishingLevel = o.getScore("Fishing");
        miningLevel.setScore(1);
    }


}
