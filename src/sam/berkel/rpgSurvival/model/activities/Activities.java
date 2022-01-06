package sam.berkel.rpgSurvival.model.activities;

import net.minecraft.server.v1_15_R1.Activity;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.activities.PVPArena.PVPArena;
import sam.berkel.rpgSurvival.model.activities.spleef.SpleefArena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Activities {
    private HashMap<String, SpleefArena> spleefArenas;
    private HashMap<String, PVPArena> pvpArenas;
    private HashMap<UUID, ActivityPlayer> players;

    public Activities(HashMap<String, SpleefArena> spleefArenas, HashMap<String, PVPArena> pvpArenas) {
        this.spleefArenas = spleefArenas;
        this.pvpArenas = pvpArenas;
        this.players = new HashMap<>();
    }

    public void join(ActivityType type, String name, Player player) {
        ActivityArena foundArena = null;

        switch (type) {
            case SPLEEF:
                foundArena = spleefArenas.get(name);
                break;
            case PVP_ARENA:
                foundArena = pvpArenas.get(name);
                break;
        }

        if (foundArena == null) {
            player.sendMessage(ChatColor.DARK_RED + "This arena could not be found!");
            return;
        }

        boolean playerCanJoin = foundArena.canPlayerJoin(player);
        if (!playerCanJoin) return;

        ActivityPlayer joinedPlayer = foundArena.playerJoins(player);
        players.put(player.getUniqueId(), joinedPlayer);
    }

    public void leave(Player player) {
        ActivityPlayer actPlayer = players.get(player.getUniqueId());
        ActivityArena arena = actPlayer.getArena();
        arena.playerLeaves(player);

        players.remove(player.getUniqueId());
    }
}
