package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        Bukkit.getLogger().info("Starting RPG Survival...");
        loadConfig();

        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
