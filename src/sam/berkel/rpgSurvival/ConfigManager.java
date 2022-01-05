package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private Main plugin = Main.getPlugin(Main.class);

    // Files & file configs
    public FileConfiguration playersCfg;
    public File playersFile;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playersFile = new File(plugin.getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create players.yml file.");
            }
        }

        playersCfg = YamlConfiguration.loadConfiguration(playersFile);
    }
}
