package sam.berkel.rpgSurvival.skills;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Mining {
    // Checks if a given tool should give mining xp.
    public static boolean isMiningTool(ItemStack item) {
        Plugin plugin = Main.getPlugin(Main.class);

        String tool = item.getType().toString();
        List<String> miningTools = plugin.getConfig().getStringList("Leveling.tools.mining");

        return miningTools.contains(tool);
    }

    public static void brokeBlock(User user, Block block) {
        Server server = Server.getInstance();
        ItemStack tool = user.getPlayer().getInventory().getItemInMainHand();

        if (isMiningTool(tool)) {
            String blockType = block.getType().toString();

            int gainedXp = getBlockXp(block);
            user.addXp(gainedXp, Main.Skill.MINING);

            System.out.println("Gained " + gainedXp + " mining xp.");
        }
    }

    // Returns the amount of xp that is gained when a player destroys this block with a mining tool.
    public static int getBlockXp(Block block) {
        Plugin plugin = Main.getPlugin(Main.class);

        String type = block.getType().toString();
         plugin.getConfig().getConfigurationSection("Leveling.xp.mining").contains(type);

        if ( plugin.getConfig().getConfigurationSection("Leveling.xp.mining").contains(type) ) {
            System.out.println("The block " + type + "Gives: " + plugin.getConfig().getInt("Leveling.xp.mining." + type));
            return plugin.getConfig().getInt("Leveling.xp.mining." + type);
        } else {
            System.out.println("The block " + type + "Gives no mining xp ");
            return 0;
        }
    }

    public static ArrayList<String> getBlockedItems (int level) {
        ArrayList<String> blockedItems = new ArrayList<>();

        if (level < 5) {
            blockedItems.add("STONE_PICKAXE");
        }
        if (level < 10) {
            blockedItems.add("IRON_PICKAXE");
        }
        if (level < 15) {
            blockedItems.add("GOLD_PICKAXE");
        }
        if (level < 20) {
            blockedItems.add("DIAMOND_PICKAXE");
        }
        if (level < 30) {
            blockedItems.add("NETHERITE_PICKAXE");
        }

        return blockedItems;
    }
}
