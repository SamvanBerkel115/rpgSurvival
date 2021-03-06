package sam.berkel.rpgSurvival.model;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.bosses.Boss;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.teleport.TeleportBlock;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.*;

public final class Server {
    // static variable single_instance of type Singleton
    private static Server instance = null;

    private Map<String, User> users;
    private Map<UUID, Citizen> citizens;
    private HashMap<Location, TeleportBlock> teleportBlocks;
    private HashMap<String, Location> teleportLocations;
    private HashMap<Location, Boss> bossLocations;
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
        citizens = Citizen.getCitizensFromConfig();
        teleportBlocks = TeleportBlock.getTeleportBlocksFromConfig();
        teleportLocations = TeleportBlock.getTeleportLocationsFromConfig();
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

        ConfigurationSection POISection = plugin.getConfig().getConfigurationSection("POIs.");

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

            //Magic.giveWand(player);
            //InventoryMenu.placeMenuButton(player);
        }

        users.put(user.getUniqueId().toString(), user);
    }

    public User getUser(UUID uuid) {
        return users.get(uuid.toString());
    }

    public  Citizen getCitizen(UUID uuid) {
        return citizens.get(uuid);
    }

    public TeleportBlock getTeleportBlock(Location location) {
        return teleportBlocks.get(location);
    }

    public ArrayList<TeleportBlock> getTeleportBlockList() {
        ArrayList<TeleportBlock> teleportBlockList = new ArrayList<>();

        for (Location loc : teleportBlocks.keySet()) {
            teleportBlockList.add(teleportBlocks.get(loc));
        }

        return teleportBlockList;
    }

    public Location getTeleportLocation(String teleportName) {
        return teleportLocations.get(teleportName);
    }

    public void addTeleportBlock(TeleportBlock tpBlock) {
        String blockName = tpBlock.getName();
        Location blockLocation = tpBlock.getBlockLocation();

        teleportBlocks.put(blockLocation, tpBlock);
        teleportLocations.put(blockName, blockLocation);

        // Write the teleport to config
        plugin.getConfig().getStringList("TeleportBlocks").add(blockName);

        plugin.getConfig().set("TeleportBlocks." + blockName + ".blockX", blockLocation.getBlockX());
        plugin.getConfig().set("TeleportBlocks." + blockName + ".blockY", blockLocation.getBlockY());
        plugin.getConfig().set("TeleportBlocks." + blockName + ".blockZ", blockLocation.getBlockZ());

        plugin.getConfig().set("TeleportBlocks." + blockName + ".targetX", tpBlock.getTarget().getX());
        plugin.getConfig().set("TeleportBlocks." + blockName + ".targetY", tpBlock.getTarget().getY());
        plugin.getConfig().set("TeleportBlocks." + blockName + ".targetZ", tpBlock.getTarget().getZ());

        plugin.getConfig().set("TeleportBlocks." + blockName + ".material", tpBlock.getMaterial().toString());
        plugin.saveConfig();

        System.out.println("Finished adding the block to the config");
    }

    public void removeTeleportBlock(TeleportBlock tpBlock) {
        teleportBlocks.remove(tpBlock.getBlockLocation());
        teleportLocations.remove(tpBlock.getName());

        plugin.getConfig().set(tpBlock.getName(), null);
    }

    public ArrayList<PointOfInterest> getPOIs() {
        return POIs;
    }

    public void addPOI(PointOfInterest poi) {
        this.POIs.add(poi);
    }

    public void removePOI(String name) {
        for (int i = 0; i < POIs.size(); i++) {
            if (POIs.get(i).getName().equals(name)) {
                POIs.remove(i);
                break;
            }
        }
    }

    public Set<String> getUserKeys() {
        return users.keySet();
    }
}
