package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import sam.berkel.rpgSurvival.commands.*;
import sam.berkel.rpgSurvival.enchantments.Test;
import sam.berkel.rpgSurvival.model.POIRunnable;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {
    public void onEnable() {
        Bukkit.getLogger().info("Starting RPG Survival...");
        loadConfig();

        // Load the extra worlds.
        getServer().createWorld(new WorldCreator("Bosses"));
        getServer().createWorld(new WorldCreator("Activities"));

        // Add all events.
        getServer().getPluginManager().registerEvents(new Events(), this);

        // Add custom items and crafting recipes.
        CustomRecipes.addAll();

        // Add commands
        this.getCommand(CutSceneCommand.command).setExecutor(new CutSceneCommand());
        this.getCommand(POICommand.command).setExecutor(new POICommand());
        this.getCommand(CitizenCommand.command).setExecutor(new CitizenCommand());
        this.getCommand(BossCommand.command).setExecutor(new BossCommand());
        this.getCommand(RpgCommand.command).setExecutor(new RpgCommand());
        this.getCommand(SpleefCommand.command).setExecutor(new SpleefCommand());

        // Performs the check if players have exited or entered the radius of a POI.
        new POIRunnable().runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0, 60);
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
