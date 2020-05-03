package sam.berkel.rpgSurvival.skills;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;

import java.util.ArrayList;

public class Combat {
    // Returns the amount of xp that is gained when a player destroys this block with a mining tool.
    public static int getXpMultiplier(Entity entity) {
        Plugin plugin = Main.getPlugin(Main.class);

        String name = entity.getName();

        if ( plugin.getConfig().getConfigurationSection("Leveling.xp.combat").contains(name) ) {
            return plugin.getConfig().getInt("Leveling.xp.combat." + name);
        } else {
            System.out.println("The block " + name + "Gives no combat xp ");
            return 0;
        }
    }

    public static ArrayList<String> getBlockedItems (int level) {
        ArrayList<String> blockedItems = new ArrayList<>();

        if (level < 5) {
            blockedItems.add("STONE_SWORD");
        }
        if (level < 10) {
            blockedItems.add("IRON_SWORD");
        }

        return blockedItems;
    }
}
