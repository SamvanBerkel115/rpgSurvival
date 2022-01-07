package sam.berkel.rpgSurvival.model.activities.spleef;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.activities.ActivityPlayer;
import sam.berkel.rpgSurvival.model.activities.EndArenaCallable;
import sam.berkel.rpgSurvival.model.activities.StartArenaCallable;

import java.util.ArrayList;

public class SpleefCheckWinnerRunnable extends BukkitRunnable {
    private SpleefArena arena;

    public SpleefCheckWinnerRunnable(SpleefArena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {
        ActivityPlayer winner = arena.getWinner();

        if (winner != null) {
            Bukkit.getScheduler().callSyncMethod(Main.getPlugin(Main.class), new EndArenaCallable(arena, winner));
            this.cancel();
        }
    }
}
