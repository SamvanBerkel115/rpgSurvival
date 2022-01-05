package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.model.PointOfInterest;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.citizen.Response;
import sam.berkel.rpgSurvival.model.citizen.State;
import sam.berkel.rpgSurvival.model.teleport.TeleportBlock;
import sam.berkel.rpgSurvival.model.user.User;
import sam.berkel.rpgSurvival.model.user.UserLevels;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigManager {
    private Main plugin;

    // Files & file configs
    private FileConfiguration bossesCfg;
    private File bossesFile;
    private FileConfiguration citizensCfg;
    private File citizensFile;
    private FileConfiguration POIsCfg;
    private File POIsFile;
    private FileConfiguration teleportsCfg;
    private File teleportsFile;
    private FileConfiguration usersCfg;
    private File usersFile;

    public ConfigManager() {
        this.plugin = Main.getPlugin(Main.class);

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        initBosses();
        initCitizens();
        initPOIs();
        initTeleports();
        initUsers();
    }

    private void initBosses () {
        bossesFile = new File(plugin.getDataFolder(), "bosses.yml");
        if (!bossesFile.exists()) {
            try {
                bossesFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create bosses.yml file.");
            }
        }
        bossesCfg = YamlConfiguration.loadConfiguration(bossesFile);
    }

    private void initCitizens () {
        citizensFile = new File(plugin.getDataFolder(), "citizens.yml");
        if (!citizensFile.exists()) {
            try {
                citizensFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create citizens.yml file.");
            }
        }
        citizensCfg = YamlConfiguration.loadConfiguration(citizensFile);
    }

    private void initPOIs () {
        POIsFile = new File(plugin.getDataFolder(), "pointsOfInterest.yml");
        if (!POIsFile.exists()) {
            try {
                POIsFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create pointsOfInterest.yml file.");
            }
        }
        POIsCfg = YamlConfiguration.loadConfiguration(POIsFile);
    }

    private void initTeleports() {
        teleportsFile = new File(plugin.getDataFolder(), "teleports.yml");
        if (!teleportsFile.exists()) {
            try {
                teleportsFile.createNewFile();
                teleportsCfg = YamlConfiguration.loadConfiguration(teleportsFile);
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create teleports.yml file.");
            }
        } else {
            teleportsCfg = YamlConfiguration.loadConfiguration(teleportsFile);
        }
    }

    private void initUsers() {
        usersFile = new File(plugin.getDataFolder(), "users.yml");
        if (!usersFile.exists()) {
            try {
                usersFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create users.yml file.");
            }
        }
        usersCfg = YamlConfiguration.loadConfiguration(usersFile);
    }

    public UserLevels getUserLevels(Player player) {
        if (usersCfg.contains(player.getUniqueId().toString())) {
            int combatLvl = (int) usersCfg.get(player.getUniqueId() +".combatLvl");
            int combatXp = (int) usersCfg.get(player.getUniqueId() +".combatXp");
            int craftingLvl = (int) usersCfg.get(player.getUniqueId() +".combatLvl");
            int craftingXp = (int) usersCfg.get(player.getUniqueId() +".combatXp");
            int excavationLvl = (int) usersCfg.get(player.getUniqueId() +".excavationLvl");
            int excavationXp = (int) usersCfg.get(player.getUniqueId() +".excavationXp");
            int farmingLvl = (int) usersCfg.get(player.getUniqueId() +".fishingLvl");
            int farmingXp = (int) usersCfg.get(player.getUniqueId() +".fishingXp");
            int fishingLvl = (int) usersCfg.get(player.getUniqueId() +".fishingLvl");
            int fishingXp = (int) usersCfg.get(player.getUniqueId() +".fishingXp");
            int magicLvl = (int) usersCfg.get(player.getUniqueId() +".magicLvl");
            int magicXp = (int) usersCfg.get(player.getUniqueId() +".magicXp");
            int miningLvl = (int) usersCfg.get(player.getUniqueId() +".miningLvl");
            int miningXp = (int) usersCfg.get(player.getUniqueId() +".miningXp");
            int woodcuttingLvl = (int) usersCfg.get(player.getUniqueId() +".woodcuttingLvl");
            int woodcuttingXp = (int) usersCfg.get(player.getUniqueId() +".woodcuttingXp");

            return new UserLevels(combatXp, combatLvl, craftingXp, craftingLvl, excavationXp,
                    excavationLvl, farmingXp, farmingLvl, fishingXp, fishingLvl, magicXp, magicLvl,
                    miningXp, miningLvl, woodcuttingXp, woodcuttingLvl);
        } else {
            return null;
        }
    }

    public void saveUserLevels(Player player) {
        User user = Server.getInstance().getUser(player.getUniqueId());
        UserLevels userLvls = user.getLevels();

        usersCfg.set(player.getUniqueId() +".combatLvl", userLvls.getCombatLvl());
        usersCfg.set(player.getUniqueId() +".combatXp", userLvls.getCombatXp());
        usersCfg.set(player.getUniqueId() +".craftingLvl", userLvls.getCraftingLvl());
        usersCfg.set(player.getUniqueId() +".craftingXp", userLvls.getCraftingXp());

        usersCfg.set(player.getUniqueId() +".excavationLvl", userLvls.getExcavationLvl());
        usersCfg.set(player.getUniqueId() +".excavationXp", userLvls.getExcavationXp());
        usersCfg.set(player.getUniqueId() +".fishingLvl", userLvls.getFarmingLvl());
        usersCfg.set(player.getUniqueId() +".fishingXp", userLvls.getFarmingXp());

        usersCfg.set(player.getUniqueId() +".fishingLvl", userLvls.getFishingLvl());
        usersCfg.set(player.getUniqueId() +".fishingXp", userLvls.getFishingXp());
        usersCfg.set(player.getUniqueId() +".magicLvl", userLvls.getMagicLvl());
        usersCfg.set(player.getUniqueId() +".magicXp", userLvls.getMagicXp());

        usersCfg.set(player.getUniqueId() +".miningLvl", userLvls.getMiningLvl());
        usersCfg.set(player.getUniqueId() +".miningXp", userLvls.getMiningXp());
        usersCfg.set(player.getUniqueId() +".woodcuttingLvl", userLvls.getWoodcuttingLvl());
        usersCfg.set(player.getUniqueId() +".woodcuttingXp", userLvls.getWoodcuttingXp());

        try {
            usersCfg.save(usersFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                    ChatColor.RED + "Could not save user data for player: " + player.getUniqueId() + " to config.");
        }
    }

    public Map<UUID, Citizen> getCitizens() {
        Plugin plugin = Main.getPlugin(Main.class);
        Map<UUID, Citizen> citizens = new HashMap<>();

        Set<String> uuids = citizensCfg.getKeys(false);

        // Each citizen has a uuid
        for (String uuid : uuids) {
            System.out.println("Adding citizen: " + uuid);
            ConfigurationSection citizenStatesConfig = citizensCfg.getConfigurationSection(uuid + ".states");

            HashMap<String, State> states = new HashMap<>();
            Set<String> stateNames = citizenStatesConfig.getKeys(false);

            State firstState = null;
            // Create all states first, since we need them as references in the dialogue.
            for (String stateName : stateNames) {
                System.out.println("Creating state: " + stateName);
                ConfigurationSection stateSection = citizenStatesConfig.getConfigurationSection(stateName);

                String dialogue = stateSection.getString("dialogue");
                State state = new State(stateName, dialogue);
                states.put(stateName, state);
                System.out.println("Created state: " + stateName);

                if (firstState == null) firstState = state;
            }

            // Add all dialogue options to the states
            for (String stateName : stateNames) {
                State state = states.get(stateName);
                System.out.println("Adding responses to state: " + state.getName());

                ConfigurationSection responsesSection = citizenStatesConfig.getConfigurationSection(stateName + ".responses");

                if (responsesSection != null) {
                    Set<String> responses = responsesSection.getKeys(false);

                    for (String responseName : responses) {
                        System.out.println("Creating response: " + responseName);
                        String responseText = responsesSection.getString(responseName + ".text");
                        String responseSateName = responsesSection.getString(responseName + ".state");

                        System.out.println("Text: " + responseText);
                        System.out.println("Statename: " + responseSateName);

                        State responseState = states.get(responseSateName);
                        System.out.println("Found response state");

                        Response response = new Response(responseText, responseState);

                        System.out.println("Created response");

                        state.addResponse(response);
                        System.out.println("Created response: " + responseName);
                    }
                }
            }

            String returnStateName = citizensCfg.getString(uuid + ".returnState");
            State returnState = states.get(returnStateName);

            UUID citizenUUID = UUID.fromString(uuid);
            Citizen cit = new Citizen(citizenUUID, states, firstState, returnState);
            citizens.put(citizenUUID, cit);
        }

        return citizens;
    }

    /**
     * Create a list of POI objects from the POI config file.
     * @return list of POI objects.
     */
    public ArrayList<PointOfInterest> getPOIs() {
        ArrayList<PointOfInterest> POIs = new ArrayList<>();
        Set<String> locationKeys = POIsCfg.getKeys(false);

        for (String locKey : locationKeys) {
            int x = POIsCfg.getInt(locKey + ".x");
            int y = POIsCfg.getInt(locKey + ".y");
            int z = POIsCfg.getInt(locKey + ".z");
            int radius = POIsCfg.getInt(locKey + ".radius");

            PointOfInterest poi = new PointOfInterest(locKey, x, y, z, radius);
            POIs.add(poi);
            System.out.println("Added POI: " + locKey + " X: " + x + " Y: " + y + " Z: " + z + " R: " + radius);
        }

        return POIs;
    }

    /**
     * Save a POI object to the POI config file.
     * @param poi POI object to be saved to the config file.
     */
    public void savePOI(PointOfInterest poi) {
        POIsCfg.set("POIs." + poi.getName() +".x", poi.getX());
        POIsCfg.set("POIs." + poi.getName() +".y", poi.getY());
        POIsCfg.set("POIs." + poi.getName() +".z", poi.getZ());
        POIsCfg.set("POIs." + poi.getName() +".radius", poi.getRadius());

        try {
            POIsCfg.save(POIsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save POI: " + poi.getName() + " to config.");
        }
    }

    public void removePOI(String poiName) {
        POIsCfg.set("POIs." + poiName, null);

        try {
            POIsCfg.save(POIsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not remove POI: " + poiName + " from config.");
        }
    }

    /**
     * Retrieve a hashmap with all teleport blocks from the teleport config file.
     * @return
     */
    public HashMap<Location, TeleportBlock> getTeleportBlocks() {
        HashMap<Location, TeleportBlock> teleportBlocks = new HashMap<>();

        Set<String> teleportBlockNames = teleportsCfg.getKeys(false);
        for (String teleportBlockName : teleportBlockNames) {
            System.out.println("Creating teleport: " + teleportBlockName);

            String materialName = teleportsCfg.getString(teleportBlockName + ".material");
            Material material = Material.getMaterial(materialName);

            double blockX = teleportsCfg.getDouble(teleportBlockName + ".blockX");
            double blockY = teleportsCfg.getDouble(teleportBlockName + ".blockY");
            double blockZ = teleportsCfg.getDouble(teleportBlockName + ".blockZ");

            Location blockLocation = new Location(null, blockX, blockY, blockZ);

            double targetX = teleportsCfg.getDouble(teleportBlockName + ".targetX");
            double targetY = teleportsCfg.getDouble(teleportBlockName + ".targetY");
            double targetZ = teleportsCfg.getDouble(teleportBlockName + ".targetZ");

            Location targetLocation = new Location(null, targetX, targetY, targetZ);

            TeleportBlock teleportBlock = new TeleportBlock(teleportBlockName, material, blockLocation, targetLocation);

            teleportBlocks.put(blockLocation, teleportBlock);

            System.out.println(blockX + " " + blockY + " " + blockZ);
            System.out.println("Finished teleport: " + teleportBlockName);
        }

        return teleportBlocks;
    }

    public HashMap<String, Location> getTeleportLocations() {
        HashMap<String, Location> teleLocs = new HashMap<>();

        Set<String> teleportBlockNames = teleportsCfg.getKeys(false);
        for (String teleportBlockName : teleportBlockNames) {
            double targetX = teleportsCfg.getDouble(teleportBlockName + ".targetX");
            double targetY = teleportsCfg.getDouble(teleportBlockName + ".targetY");
            double targetZ = teleportsCfg.getDouble(teleportBlockName + ".targetZ");

            Location targetLocation = new Location(null, targetX, targetY, targetZ);
            teleLocs.put(teleportBlockName, targetLocation);
        }

        return teleLocs;
    }

    public void saveTeleportBlock(TeleportBlock tpBlock) {
        String blockName = tpBlock.getName();
        Location blockLocation = tpBlock.getBlockLocation();

        teleportsCfg.createSection(blockName);

        teleportsCfg.set("TeleportBlocks." + blockName + ".blockX", blockLocation.getBlockX());
        teleportsCfg.set("TeleportBlocks." + blockName + ".blockY", blockLocation.getBlockY());
        teleportsCfg.set("TeleportBlocks." + blockName + ".blockZ", blockLocation.getBlockZ());

        teleportsCfg.set("TeleportBlocks." + blockName + ".targetX", tpBlock.getTarget().getX());
        teleportsCfg.set("TeleportBlocks." + blockName + ".targetY", tpBlock.getTarget().getY());
        teleportsCfg.set("TeleportBlocks." + blockName + ".targetZ", tpBlock.getTarget().getZ());

        teleportsCfg.set("TeleportBlocks." + blockName + ".material", tpBlock.getMaterial().toString());

        try {
            teleportsCfg.save(teleportsFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save teleport block to config: " + blockName);
        }
    }

    public void removeTeleportBlock(TeleportBlock tpBlock) {
        teleportsCfg.set(tpBlock.getName(), null);
    }

}
