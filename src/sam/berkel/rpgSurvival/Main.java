package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import sam.berkel.rpgSurvival.enchantments.Test;

import java.lang.reflect.Field;

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

    private void loadEnchantments() {
        Test ench = new Test(NamespacedKey.minecraft("TestEnchantment"));

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        try {
            Enchantment.registerEnchantment(ench);
        } catch (Exception e) {

        }
    }
}
