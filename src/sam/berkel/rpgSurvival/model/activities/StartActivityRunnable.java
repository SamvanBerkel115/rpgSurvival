package sam.berkel.rpgSurvival.model.activities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.Main;

import java.util.ArrayList;

public class StartActivityRunnable extends BukkitRunnable {
    ActivityArena arena;
    int timer;

    public StartActivityRunnable(ActivityArena arena) {
        this.arena = arena;
        timer = 8;
    }

    @Override
    public void run() {
        timer--;

        if (timer <= 0) {
            for (ActivityPlayer actPlayer : arena.getPlayerList()) {
                actPlayer.getPlayer().sendTitle(ChatColor.DARK_AQUA + "START!", "", 0, 0, 20);
            }

            Bukkit.getScheduler().callSyncMethod(Main.getPlugin(Main.class), new StartArenaCallable(arena));

            this.cancel();
        } else if (timer <= 5) {
            for (ActivityPlayer actPlayer : arena.getPlayerList()) {
                actPlayer.getPlayer().sendTitle(String.valueOf(timer), "", 0, 0, 20);
            }
        } else if (timer == 7) {
            for (ActivityPlayer actPlayer : arena.getPlayerList()) {
                actPlayer.getPlayer().sendTitle("Activity is starting!", "", 0, 20, 20);
            }
        }
    }
}
