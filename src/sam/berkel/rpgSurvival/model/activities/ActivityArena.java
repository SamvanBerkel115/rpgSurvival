package sam.berkel.rpgSurvival.model.activities;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_15_R1.Activity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.POIRunnable;
import sam.berkel.rpgSurvival.model.Server;

import java.util.*;

public abstract class ActivityArena {
    private String name;
    private ArrayList<Location> playerSpawns;
    private HashMap<UUID, ActivityPlayer> players;
    private Location lobbySpawn;
    private int lobbyTimer;
    private boolean lobbyOpened;
    private BukkitTask lobbyTask;
    private boolean activityStarted;

    public ActivityArena(String name, Location lobbySpawn, int lobbyTimer, ArrayList<Location> playerSpawns) {
        this.name = name;
        this.lobbySpawn = lobbySpawn;
        this.lobbyTimer = lobbyTimer;
        this.playerSpawns = playerSpawns;
        this.players = new HashMap<>();
        this.lobbyOpened = false;
        this.activityStarted = false;
    }

    public ArrayList<ActivityPlayer> getPlayerList() {
        ArrayList<ActivityPlayer> playerList = new ArrayList<>();
        playerList.addAll(players.values());

        return playerList;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
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

        actPlayer.toLobby();

        return actPlayer;
    }

    public void playerLeaves(Player player) {
        players.remove(player.getUniqueId());
    }

    public boolean isFull() {
        return players.size() == playerSpawns.size();
    }

    private void openLobby() {
        lobbyOpened = true;
        activityStarted = false;

        lobbyTask = new LobbyTimerRunnable(this, lobbyTimer).runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 20);
    }

    public void startCountdown() {
        // If the arena was started and the lobby counter is still running, cancel it.
        if (lobbyTask != null && !lobbyTask.isCancelled()) lobbyTask.cancel();

        new StartActivityRunnable(this).runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 20);
    }

    public void start() {
        beforeStart();

        // Randomize the spawnpoint order.
        Collections.shuffle(playerSpawns);

        ArrayList<ActivityPlayer> players = getPlayerList();
        for (int i = 0; i < players.size(); i++) {
            ActivityPlayer actPlayer = players.get(i);
            Location spawnPoint = playerSpawns.get(i);

            actPlayer.getPlayer().teleport(spawnPoint);
            actPlayer.start();
        }

        lobbyOpened = false;
        activityStarted = true;

        afterStart();
    }

    public void cancel() {
        lobbyOpened = false;
        activityStarted = false;

        ArrayList<ActivityPlayer> players = getPlayerList();
        for (ActivityPlayer actPlayer : players) {
            actPlayer.leaveActivity();
        }

        this.players = new HashMap<>();
    }

    public void end(ActivityPlayer winner) {
        UUID winnerUUID = winner.getPlayer().getUniqueId();

        ArrayList<ActivityPlayer> players = getPlayerList();
        for (ActivityPlayer actPlayer : players) {
            if (actPlayer.getPlayer().getUniqueId().equals(winnerUUID)) {
                actPlayer.getPlayer().sendTitle(ChatColor.GOLD + "You Won!", "Congratulations", 0, 40, 20);
            } else {
                actPlayer.getPlayer().sendTitle(ChatColor.DARK_RED + "You Lost!", "Winner: " + winner.getPlayer().getDisplayName(), 0, 40, 20);
            }

            actPlayer.leaveActivity();
        }

        lobbyOpened = false;
        activityStarted = false;
    }

    public abstract void beforeStart();

    public abstract void afterStart();

    public abstract void onPlayerDeath(ActivityPlayer player, PlayerDeathEvent event);

    public abstract void onPlayerRespawn(ActivityPlayer player, PlayerRespawnEvent event);
}
