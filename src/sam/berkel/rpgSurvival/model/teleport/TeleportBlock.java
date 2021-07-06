package sam.berkel.rpgSurvival.model.teleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;

import java.util.HashMap;
import java.util.Set;

public class TeleportBlock {
    private String name;
    private Material material;
    private Location target;
    private Location blockLocation;

    public TeleportBlock(String name, Material material, Location target, Location blockLocation) {
        this.name = name;
        this.material = material;
        this.target = target;
        this.blockLocation = blockLocation;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public Location getTarget() {
        return target;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public static HashMap<Location, TeleportBlock> getTeleportBlocksFromConfig() {
        Plugin plugin = Main.getPlugin(Main.class);

        HashMap<Location, TeleportBlock> teleportBlocks = new HashMap<>();

        ConfigurationSection teleportConfig = plugin.getConfig().getConfigurationSection("TeleportBlocks");

        Set<String> teleportBlockNames = teleportConfig.getKeys(false);
        for (String teleportBlockName : teleportBlockNames) {
            System.out.println("Creating teleport: " + teleportBlockName);

            String materialName = teleportConfig.getString(teleportBlockName + ".material");
            Material material = Material.getMaterial(materialName);

            double blockX = teleportConfig.getDouble(teleportBlockName + ".blockX");
            double blockY = teleportConfig.getDouble(teleportBlockName + ".blockY");
            double blockZ = teleportConfig.getDouble(teleportBlockName + ".blockZ");

            Location blockLocation = new Location(null, blockX, blockY, blockZ);

            double targetX = teleportConfig.getDouble(teleportBlockName + ".targetX");
            double targetY = teleportConfig.getDouble(teleportBlockName + ".targetY");
            double targetZ = teleportConfig.getDouble(teleportBlockName + ".targetZ");

            Location targetLocation = new Location(null, targetX, targetY, targetZ);

            TeleportBlock teleportBlock = new TeleportBlock(teleportBlockName, material, blockLocation, targetLocation);

            teleportBlocks.put(blockLocation, teleportBlock);

            System.out.println(blockX + " " + blockY + " " + blockZ);
            System.out.println("Finished teleport: " + teleportBlockName);
        }

        return teleportBlocks;
    }

    public static HashMap<String, Location> getTeleportLocationsFromConfig() {
        Plugin plugin = Main.getPlugin(Main.class);

        HashMap<String, Location> teleLocs = new HashMap<>();

        ConfigurationSection teleportConfig = plugin.getConfig().getConfigurationSection("TeleportBlocks");

        Set<String> teleportBlockNames = teleportConfig.getKeys(false);
        for (String teleportBlockName : teleportBlockNames) {


            double targetX = teleportConfig.getDouble(teleportBlockName + ".targetX");
            double targetY = teleportConfig.getDouble(teleportBlockName + ".targetY");
            double targetZ = teleportConfig.getDouble(teleportBlockName + ".targetZ");

            Location targetLocation = new Location(null, targetX, targetY, targetZ);


            teleLocs.put(teleportBlockName, targetLocation);
        }

        return teleLocs;
    }

    public static boolean isTeleportBlock(Block block) {
        Server server = Server.getInstance();

        Location blockLoc = new Location(null, block.getX(), block.getY(), block.getZ());

        return server.getTeleportBlock(blockLoc) != null;
    }
}
