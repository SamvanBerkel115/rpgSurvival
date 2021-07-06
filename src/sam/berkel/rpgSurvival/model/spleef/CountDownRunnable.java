package sam.berkel.rpgSurvival.model.spleef;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;

public class CountDownRunnable extends BukkitRunnable {
    private ArrayList<Player> players;
    private int startCount;

    public CountDownRunnable(ArrayList<Player> players, int startCount) {
        this.players = players;
        this.startCount = startCount;
    }

    @Override
    public void run() {
        for (Player player : players) {
            if (startCount == 1) player.sendTitle("", ChatColor.AQUA + "GO!", 0, 20, 10);
            else player.sendTitle("", ChatColor.AQUA + String.valueOf(startCount), 0, 10, 10);
        }

        startCount--;
        if (startCount <= 0) this.cancel();
    }
}

