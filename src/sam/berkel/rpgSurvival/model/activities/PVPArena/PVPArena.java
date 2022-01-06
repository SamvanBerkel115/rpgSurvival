package sam.berkel.rpgSurvival.model.activities.PVPArena;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.activities.ActivityArena;

import java.util.ArrayList;

public class PVPArena extends ActivityArena {
    public PVPArena(String name, ArrayList<Location> playerSpawns) {
        super(name, playerSpawns);
    }
}
