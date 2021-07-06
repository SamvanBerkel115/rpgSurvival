package sam.berkel.rpgSurvival.model.bosses;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;
import java.util.Set;

public class Boss {
    Location startBlock;
    Location startTeleport;
    String name;
    ArrayList<User> users;

    public Boss(Location startLocation, String name, ArrayList<User> users) {
        this.startBlock = startLocation;
        this.name = name;
        this.users = users;

        teleportUsers(users, startLocation);
    }

    private void teleportUsers(ArrayList<User> users, Location location) {
        for (User user : users) {
            Player player = user.getPlayer();

            player.teleport(location);
        }
    }

    private static ArrayList<Boss> loadBossesFromConfig() {
        ArrayList<Boss> bosses = new ArrayList<>();

        Plugin plugin = Main.getPlugin(Main.class);
        ConfigurationSection bossSection = plugin.getConfig().getConfigurationSection("Bosses");
        Set<String> bossNames = bossSection.getKeys(false);

        World bossWorld = plugin.getServer().getWorld("Bosses");

        for (String bossName : bossNames) {
            BossType type = BossType.valueOf(bossName);

            int blockX = bossSection.getInt("blockX");
            int blockY = bossSection.getInt("blockY");
            int blockZ = bossSection.getInt("blockZ");

            Location blockLocation = new Location(bossWorld, blockX, blockY, blockZ);

            int startX = bossSection.getInt("startX");
            int startY = bossSection.getInt("startY");
            int startZ = bossSection.getInt("startZ");
            int startYaw = bossSection.getInt("startYaw");
            int startPitch = bossSection.getInt("startPitch");

            Location teleportLocation = new Location(bossWorld, startX, startY, startZ, startYaw, startPitch);

            switch (type) {
                case SKELETON_KING:

            }
        }



        return bosses;
    }
}
