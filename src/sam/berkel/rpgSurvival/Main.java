package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import sam.berkel.rpgSurvival.enchantments.Test;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {
    public void onEnable() {
        Bukkit.getLogger().info("Starting RPG Survival...");
        loadConfig();

        // Load the bosses world.
        getServer().createWorld(new WorldCreator("Bosses"));

        // Add all events.
        getServer().getPluginManager().registerEvents(new Events(), this);

        // Add custom items and crafting recipes.
        CustomRecipes.addAll();

        // Add commands
        this.getCommand("kit").setExecutor(new Commands());
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

    public enum Skill {
        COMBAT {
            @Override
            public String toString() {
                return "Combat";
            }
        },
        CRAFTING {
            @Override
            public String toString() {
                return "Crafting";
            }
        },
        EXCAVATION {
            @Override
            public String toString() {
                return "Excavation";
            }
        },
        FARMING {
            @Override
            public String toString() {
                return "Farming";
            }
        },
        FISHING {
            @Override
            public String toString() {
                return "Fishing";
            }
        },
        MAGIC {
            @Override
            public String toString() {
                return "Magic";
            }
        },
        MINING {
            @Override
            public String toString() {
                return "Mining";
            }
        },
        WOODCUTTING {
            @Override
            public String toString() {
                return "Woodcutting";
            }
        },
    }
}
