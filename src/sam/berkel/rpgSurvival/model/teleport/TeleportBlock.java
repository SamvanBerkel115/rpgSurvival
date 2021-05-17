package sam.berkel.rpgSurvival.model;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;

import java.util.HashMap;
import java.util.Set;

public class TeleportBlock {
    private String name;
    private Material material;
    private Location blockLocation;
    private Location teleportLocation;

    public TeleportBlock(String name, Material material, Location blockLocation, Location teleportLocation) {
        this.name = name;
        this.material = material;
        this.blockLocation = blockLocation;
        this.teleportLocation = teleportLocation;
    }

    public HashMap<String, TeleportBlock> getTeleportsFromConfig() {
        Plugin plugin = Main.getPlugin(Main.class);
        HashMap<String, TeleportBlock> teleports = new HashMap<>();

        ConfigurationSection teleportConfig = plugin.getConfig().getConfigurationSection("Teleports");

        Set<String> teleportNames = teleportConfig.getKeys(false);
        for (String teleportName : teleportNames) {
            double blockX = teleportConfig.getString(teleportName + ".blockX");
            double blockY = teleportConfig.getString(teleportName + ".blockY");
            double blockZ = teleportConfig.getString(teleportName + ".blockZ");

            double teleportX = teleportConfig.getString(teleportName + ".teleportX");
            double teleportY = teleportConfig.getString(teleportName + ".teleportY");
            double teleportZ = teleportConfig.getString(teleportName + ".teleportZ");

            String materialName = teleportConfig.getString(teleportName + ".material");
            Material material = Material.getMaterial(materialName);
        }

        return teleports;
    }
}
