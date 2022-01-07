package sam.berkel.rpgSurvival.model.activities.spleef;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.activities.ActivityArena;
import sam.berkel.rpgSurvival.model.activities.ActivityPlayer;

import java.util.ArrayList;

public abstract class SpleefArena extends ActivityArena {
    int currentRound;
    int rounds;

    public SpleefArena(String name, Location lobbySpawn, int lobbyTimer, ArrayList<Location> playerSpawns, int rounds) {
        super(name, lobbySpawn, lobbyTimer, playerSpawns);
        this.currentRound = 1;
        this.rounds = rounds;
    }

    public ActivityPlayer getWinner() {
        int numActivePlayers = 0;

        ArrayList<ActivityPlayer> actPlayers = getPlayerList();
        for (ActivityPlayer actPlayer : actPlayers) {
            if (actPlayer.isPlaying()) numActivePlayers++;
        }

        if (numActivePlayers == 1) {
            return actPlayers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void afterStart() {
        ItemStack spadeStack = new ItemStack(Material.DIAMOND_SHOVEL, 1);

        ArrayList<ActivityPlayer> actPlayers = getPlayerList();
        for (ActivityPlayer actPlayer : actPlayers) {
            actPlayer.getPlayer().getInventory().setItemInMainHand(spadeStack);
        }

        new SpleefCheckWinnerRunnable(this).runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 20);
    }

    @Override
    public void onPlayerDeath(ActivityPlayer actPlayer, PlayerDeathEvent event) {
        actPlayer.setPlaying(false);
    }

    @Override
    public void onPlayerRespawn(ActivityPlayer actPlayer, PlayerRespawnEvent event) {
        actPlayer.getPlayer().teleport(getLobbySpawn());
    }
}
