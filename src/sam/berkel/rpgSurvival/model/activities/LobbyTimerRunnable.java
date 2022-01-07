package sam.berkel.rpgSurvival.model.activities;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.Main;

public class LobbyTimerRunnable extends BukkitRunnable {
    ActivityArena arena;
    int timer;

    public LobbyTimerRunnable(ActivityArena arena, int timer) {
        this.arena = arena;
        this.timer = timer;
    }

    @Override
    public void run() {
        timer--;

        if (timer <= 0) {
            arena.startCountdown();
        }
    }
}
