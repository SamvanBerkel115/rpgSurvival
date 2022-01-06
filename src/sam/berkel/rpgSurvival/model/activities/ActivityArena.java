package sam.berkel.rpgSurvival.model.activities;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class ActivityArena {
    private String name;
    private ArrayList<Location> playerSpawns;
    private HashMap<UUID, ActivityPlayer> players;
    private boolean lobbyOpened;
    private boolean activityStarted;

    public ActivityArena(String name, ArrayList<Location> playerSpawns) {
        this.name = name;
        this.playerSpawns = playerSpawns;
        this.players = new HashMap<>();
        this.lobbyOpened = false;
        this.activityStarted = false;
    }

    public boolean canPlayerJoin(Player player) {
        // A player can no longer join if the activity has already started.
        if (activityStarted) {
            player.sendMessage(ChatColor.DARK_RED + "A game is already ongoing in this arena!");
            return false;
        }

        // Check if the new player isn't already in the lobby.
        boolean playerAlreadyJoined = players.containsKey(player.getUniqueId());
        if (playerAlreadyJoined) {
            player.sendMessage(ChatColor.DARK_RED + "You are already in this lobby");
            return false;
        }

        return true;
    }

    public ActivityPlayer playerJoins(Player newPlayer) {
        ActivityPlayer actPlayer = new ActivityPlayer(newPlayer, this);

        players.put(newPlayer.getUniqueId(), actPlayer);

        int numPlayers = players.size();
        int capacity = playerSpawns.size();

        // If this player is the first player, open the game lobby.
        if (numPlayers == 1) {
            openLobby();
        }

        TextComponent inviteText = new TextComponent("A PVP Arena game is starting! (" + numPlayers + "/" + capacity + " players)");

        TextComponent joinButtonText = new TextComponent(" " + ChatColor.DARK_AQUA + "[JOIN]");
        ClickEvent clickEvt = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/spleef join " + name);
        joinButtonText.setClickEvent(clickEvt);

        inviteText.addExtra(joinButtonText);
        Bukkit.getServer().spigot().broadcast(inviteText);

        return actPlayer;
    }

    public void playerLeaves(Player player) {
        players.remove(player.getUniqueId());
    }

    private void openLobby() {
        lobbyOpened = true;
        activityStarted = false;
    }

    private void start() {
        lobbyOpened = false;
        activityStarted = true;
    }
}
