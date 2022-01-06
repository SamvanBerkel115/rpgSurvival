package sam.berkel.rpgSurvival.model.activities.spleef;

import org.bukkit.Location;

import java.util.ArrayList;

public class RoundSpleefArena extends SpleefArena {
    Location center;
    int radius;

    public RoundSpleefArena(String name, ArrayList<Location> playerSpawns, int rounds, Location center, int radius) {
        super(name, playerSpawns, rounds);
        this.center = center;
        this.radius = radius;
    }
}
