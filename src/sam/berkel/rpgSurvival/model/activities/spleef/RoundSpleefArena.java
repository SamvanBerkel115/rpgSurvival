package sam.berkel.rpgSurvival.model.activities.spleef;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import sam.berkel.rpgSurvival.Main;

import java.util.ArrayList;

public class RoundSpleefArena extends SpleefArena {
    Location center;
    int radius;

    public RoundSpleefArena(String name, Location lobbySpawn, int lobbyTimer, ArrayList<Location> playerSpawns, int rounds, Location center, int radius) {
        super(name, lobbySpawn, lobbyTimer, playerSpawns, rounds);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void beforeStart() {
        fillCircle();
    }

    private void fillCircle() {
        World world = center.getWorld();
        int y = center.getBlockY();

        int radiusSquared = radius * radius;

        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();

        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++) {
                if( (x * x) + (z * z) <= radiusSquared) {
                    Location loc = new Location(world, centerX + x, y, centerZ + z);

                    world.getBlockAt(loc).setType(Material.SNOW_BLOCK);
                }
            }
        }
    }
}
