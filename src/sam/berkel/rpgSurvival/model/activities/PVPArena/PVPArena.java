package sam.berkel.rpgSurvival.model.activities.PVPArena;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import sam.berkel.rpgSurvival.model.activities.ActivityArena;
import sam.berkel.rpgSurvival.model.activities.ActivityPlayer;

import java.util.ArrayList;

public class PVPArena extends ActivityArena {
    public PVPArena(String name, Location lobbySpawn, int lobbyTimer, ArrayList<Location> playerSpawns) {
        super(name, lobbySpawn, lobbyTimer, playerSpawns);
    }

    @Override
    public void beforeStart() {

    }

    @Override
    public void afterStart() {

    }

    @Override
    public void onPlayerDeath(ActivityPlayer player, PlayerDeathEvent event) {

    }

    @Override
    public void onPlayerRespawn(ActivityPlayer player, PlayerRespawnEvent event) {

    }
}
