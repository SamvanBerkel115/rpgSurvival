package sam.berkel.rpgSurvival.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.config.ConfigManager;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.activities.Activities;
import sam.berkel.rpgSurvival.model.bosses.Boss;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.teleport.TeleportBlock;
import sam.berkel.rpgSurvival.model.user.User;
import sam.berkel.rpgSurvival.model.user.UserLevels;

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
    private Activities activities;

    private Plugin plugin = Main.getPlugin(Main.class);
    private ConfigManager cfgManager;

    private double base;
    private double exponent;

    // private constructor restricted to this class itself
    private Server() {
        users = new HashMap<>();

        cfgManager = new ConfigManager();

        base = plugin.getConfig().getDouble("Leveling.formula.base");
        exponent = plugin.getConfig().getDouble("Leveling.formula.exponent");

        POIs = cfgManager.getPOIs();
        citizens = cfgManager.getCitizens();
        teleportBlocks = cfgManager.getTeleportBlocks();
        teleportLocations = cfgManager.getTeleportLocations();
        activities = cfgManager.getActivities();
    };

    // static method to create instance of Singleton class
    public static Server getInstance()
    {
        if (instance == null)
            instance = new Server();

        return instance;
    }

    public double getBase() {
        return base;
    }

    public double getExponent() {
        return exponent;
    }

    public Activities getActivities() {
        return activities;
    }

    /**
     * Creates a user object for the player that joined the server.
     * @param player
     */
    public void userJoined(Player player) {
        User user;

        UserLevels userLvls = cfgManager.getUserLevels(player);
        if ( userLvls != null ) {
            System.out.println("An existing user joined");

            user = new User(player, userLvls);
        }
        else {
            System.out.println("A new user joined");

            UserLevels newUserLevels = new UserLevels(0, 1, 0, 1,
                    0, 1, 0, 1, 0, 1, 0,
                    1, 0, 1, 0, 1);

            user = new User(player, newUserLevels);

            //Magic.giveWand(player);
            //InventoryMenu.placeMenuButton(player);
        }

        users.put(user.getUniqueId().toString(), user);
    }

    public ConfigManager getConfigManger() {
        return cfgManager;
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

        // Write the teleport to config.
        cfgManager.saveTeleportBlock(tpBlock);

        System.out.println("Finished adding the block to the config");
    }

    public void removeTeleportBlock(TeleportBlock tpBlock) {
        teleportBlocks.remove(tpBlock.getBlockLocation());
        teleportLocations.remove(tpBlock.getName());

        // Remove the teleport from the config.
        cfgManager.removeTeleportBlock(tpBlock);
    }

    public ArrayList<PointOfInterest> getPOIs() {
        return POIs;
    }

    public void addPOI(PointOfInterest poi) {
        this.POIs.add(poi);

        cfgManager.savePOI(poi);
    }

    public void removePOI(String name) {
        for (int i = 0; i < POIs.size(); i++) {
            if (POIs.get(i).getName().equals(name)) {
                POIs.remove(i);
                break;
            }
        }

        cfgManager.removePOI(name);
    }

    public Set<String> getUserKeys() {
        return users.keySet();
    }
}
