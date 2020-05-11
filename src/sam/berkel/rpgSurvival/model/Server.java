package sam.berkel.rpgSurvival.model;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sam.berkel.rpgSurvival.InventoryMenu;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.skills.Magic;
import sam.berkel.rpgSurvival.skills.Mining;

import java.util.*;

public final class Server {
    // static variable single_instance of type Singleton
    private static Server instance = null;

    private Map<String, User> users;
    private ArrayList<PointOfInterest> POIs;
    private Plugin plugin = Main.getPlugin(Main.class);
    private double base;
    private double exponent;

    // private constructor restricted to this class itself
    private Server() {
        users = new HashMap<>();

        base = plugin.getConfig().getDouble("Leveling.formula.base");
        exponent = plugin.getConfig().getDouble("Leveling.formula.exponent");

        POIs = initLocations();
    };

    // static method to create instance of Singleton class
    public static Server getInstance()
    {
        if (instance == null)
            instance = new Server();

        return instance;
    }

    public ArrayList<PointOfInterest> initLocations() {
        ArrayList<PointOfInterest> POIs = new ArrayList<>();

        ConfigurationSection POISection = plugin.getConfig().getConfigurationSection("Locations.");

        Set<String> locationKeys = POISection.getKeys(false);

        for (String locKey : locationKeys) {
            int x = POISection.getInt(locKey + ".x");
            int y = POISection.getInt(locKey + ".y");
            int z = POISection.getInt(locKey + ".z");
            int radius = POISection.getInt(locKey + ".radius");

            PointOfInterest poi = new PointOfInterest(locKey, x, y, z, radius);
            POIs.add(poi);
            System.out.println("Added POI: " + locKey + " X: " + x + " Y: " + y + " Z: " + z + " R: " + radius);
        }

        return POIs;
    }

    public double getBase() {
        return base;
    }

    public double getExponent() {
        return exponent;
    }

    public void userJoined(Player player) {
        User user;

        if ( plugin.getConfig().contains("Users." + player.getUniqueId()) ) {
            System.out.println("An existing user joined");

            int combatLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatLvl").toString());
            int combatXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatXp").toString());
            int craftingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatLvl").toString());
            int craftingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".combatXp").toString());
            int excavationLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationLvl").toString());
            int excavationXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationXp").toString());
            int farmingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingLvl").toString());
            int farmingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingXp").toString());
            int fishingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingLvl").toString());
            int fishingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".fishingXp").toString());
            int magicLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".magicLvl").toString());
            int magicXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".magicXp").toString());
            int miningLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningLvl").toString());
            int miningXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningXp").toString());
            int woodcuttingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingLvl").toString());
            int woodcuttingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingXp").toString());

            user = new User(player, combatXp, combatLvl, craftingXp, craftingLvl, excavationXp, excavationLvl, farmingXp, farmingLvl, fishingXp, fishingLvl, magicXp, magicLvl, miningXp, miningLvl, woodcuttingXp, woodcuttingLvl);
        }
        else {
            System.out.println("A new user joined");
            plugin.getConfig().getStringList("Users").add(player.getUniqueId().toString());

            plugin.getConfig().set("Users." + player.getUniqueId() +".combatLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".combatXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".craftingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".craftingXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".farmingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".farmingXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".fishingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".fishingXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".magicLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".magicXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", "0");

            plugin.saveConfig();

            user = new User(player, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1);

            Magic.giveWand(player);
            InventoryMenu.placeMenuButton(player);
        }

        users.put(user.getUniqueId().toString(), user);
    }

    public User getUser(UUID uuid) {
        return users.get(uuid.toString());
    }

    public ArrayList<PointOfInterest> getPOIs() {
        return POIs;
    }

    public Set<String> getUserKeys() {
        return users.keySet();
    }
}
