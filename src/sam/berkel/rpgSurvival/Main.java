package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public void onEnable() {
        Bukkit.getLogger().info("Starting RPG Survival...");
        loadConfig();

        NamespacedKey nsk = new NamespacedKey(Main.getPlugin(Main.class), "combat/level5");
        System.out.println(nsk.getKey());
        System.out.println(nsk.getNamespace());

        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
