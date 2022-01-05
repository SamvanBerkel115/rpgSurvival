package sam.berkel.rpgSurvival.model.spleef;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpleefArena {
    String name;
    Location startLocation;
    boolean inProgress;
    int round;
    ArrayList<User> users;

    public void start(ArrayList<User> users) {
        inProgress = true;

        for (User user : users) {
            Player player = user.getPlayer();

            player.teleport(startLocation);
        }

        List<Player> playersList = users.stream().map(user -> user.getPlayer()).collect(Collectors.toList());
        ArrayList<Player> playersArrList = new ArrayList<>(playersList);

        CountDownRunnable cdr = new CountDownRunnable(playersArrList, 10);
        cdr.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 20);
    }
}
