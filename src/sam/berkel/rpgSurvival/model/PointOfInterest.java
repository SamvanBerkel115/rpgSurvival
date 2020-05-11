package sam.berkel.rpgSurvival.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PointOfInterest {
    private String name;
    private int x;
    private int y;
    private int z;
    private int radius;

    public PointOfInterest(String name, int x, int y, int z, int radius) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public double calculateDistance(Player player) {
        Location loc = player.getLocation();
        double locX = loc.getX();
        double locY = loc.getY();
        double locZ = loc.getZ();

        double distance = Math.sqrt(Math.pow(locX - x, 2) + Math.pow(locY - y, 2) + Math.pow(locZ - z, 2));
        return distance;
    }

    // Check if a player is within the radius of a poi.
    public boolean isWithinRadius(Player player) {
        if (this.calculateDistance(player) <= radius) {
            return true;
        } else {
            return false;
        }
    }
}
