package sam.berkel.rpgSurvival.skills;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

import java.util.ArrayList;
import java.util.List;

public class Woodcutting {
    // Checks if a given tool should give mining xp.
    public static boolean isWoodcuttingTool(ItemStack item) {
        Plugin plugin = Main.getPlugin(Main.class);

        String tool = item.getType().toString();
        List<String> woodcuttingTools = plugin.getConfig().getStringList("Leveling.tools.woodcutting");
        return woodcuttingTools.contains(tool);
    }

    public static void brokeBlock(User user, Block block) {
        Server server = Server.getInstance();
        ItemStack tool = user.getPlayer().getInventory().getItemInMainHand();
        user.getPlayer().sendMessage("You used" + tool.getType().toString());

        if (isWoodcuttingTool(tool)) {
            String blockType = block.getType().toString();

            int gainedXp = getBlockXp(block);
            user.addXp(gainedXp, "Woodcutting");
        }
    }

    // Returns the amount of xp that is gained when a player destroys this block with a mining tool.
    public static int getBlockXp(Block block) {
        Plugin plugin = Main.getPlugin(Main.class);

        String type = block.getType().toString();
        plugin.getConfig().getConfigurationSection("Leveling.xp.woodcutting").contains(type);

        if ( plugin.getConfig().getConfigurationSection("Leveling.xp.woodcutting").contains(type) ) {
            System.out.println("The block " + type + "Gives: " + plugin.getConfig().getInt("Leveling.xp.woodcutting." + type));
            return plugin.getConfig().getInt("Leveling.xp.woodcutting." + type);
        } else {
            System.out.println("The block " + type + "Gives no woodcutting xp ");
            return 0;
        }
    }

    public static ArrayList<String> getBlockedItems (int level) {
        ArrayList<String> blockedItems = new ArrayList<>();

        if (level < 5) {
            blockedItems.add("STONE_AXE");
        }
        if (level < 10) {
            blockedItems.add("IRON_AXE");
        }

        if (level < 20) {
            blockedItems.add("GOLD_AXE");
        }

        if (level < 30) {
            blockedItems.add("DIAMOND_AXE");
        }

        return blockedItems;
    }
}
