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

    public static boolean isTeleportBlock(Block block) {
        Server server = Server.getInstance();

        Location blockLoc = new Location(null, block.getX(), block.getY(), block.getZ());

        return server.getTeleportBlock(blockLoc) != null;
    }
}
