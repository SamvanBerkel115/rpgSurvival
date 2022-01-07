package sam.berkel.rpgSurvival.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import sam.berkel.rpgSurvival.model.activities.spleef.RoundSpleefArena;
import sam.berkel.rpgSurvival.model.activities.spleef.SpleefArena;
import sam.berkel.rpgSurvival.model.activities.spleef.SpleefArenaType;

import java.util.ArrayList;
import java.util.Set;

public class ActivityConfig {
    public static SpleefArena getSpleefArena(ConfigurationSection arenaSection, String arenaName) {
        int numRounds = arenaSection.getInt("rounds");
        String worldName = arenaSection.getString("world");
        World world = Bukkit.getWorld(worldName);

        // Get the lobby data.
        Location lobbySpawnLocation = ConfigManager.getLocationFromSection(arenaSection.getConfigurationSection("lobbySpawn"), world);
        int lobbyTimer = arenaSection.getInt("lobbyTimer");

        // Create all player spawn locations
        ArrayList<Location> spawnLocations = new ArrayList<>();
        ConfigurationSection playerSpawnSection = arenaSection.getConfigurationSection("playerSpawns");
        Set<String> spawnNames = playerSpawnSection.getKeys(false);
        for (String spawnName : spawnNames) {
            ConfigurationSection spawnSection = playerSpawnSection.getConfigurationSection(spawnName);

            Location spawnLocation = ConfigManager.getLocationFromSection(spawnSection, world);
            spawnLocations.add(spawnLocation);
        }

        SpleefArenaType type = SpleefArenaType.valueOf(arenaSection.getString("type"));
        switch(type) {
            case ROUND:
                Location center = ConfigManager.getLocationFromSection(arenaSection.getConfigurationSection("center"), world);
                int radius = arenaSection.getInt("radius");

                return new RoundSpleefArena(arenaName, lobbySpawnLocation, lobbyTimer, spawnLocations, numRounds, center, radius);
            default:
                return null;
        }
    }
}
