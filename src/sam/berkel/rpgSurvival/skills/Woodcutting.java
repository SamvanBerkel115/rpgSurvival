package sam.berkel.rpgSurvival.skills;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

import java.util.ArrayList;
import java.util.List;

public class Woodcutting {
    // Checks if a given tool should give woodcutting xp.
    public static boolean isWoodcuttingTool(ItemStack item) {
        Plugin plugin = Main.getPlugin(Main.class);

        String tool = item.getType().toString();
        List<String> woodcuttingTools = plugin.getConfig().getStringList("Leveling.tools.woodcutting");
        return woodcuttingTools.contains(tool);
    }

    public static void brokeBlock(User user, Block block) {
        Server server = Server.getInstance();
        ItemStack tool = user.getPlayer().getInventory().getItemInMainHand();

        if (isWoodcuttingTool(tool)) {
            String blockType = block.getType().toString();

            int gainedXp = getBlockXp(block);
            user.addXp(gainedXp, Main.Skill.WOODCUTTING);

            System.out.println("Gained " + gainedXp + " woodcutting xp.");
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

        if (level < 15) {
            blockedItems.add("GOLD_AXE");
        }

        if (level < 20) {
            blockedItems.add("DIAMOND_AXE");
        }

        if (level < 30) {
            blockedItems.add("NETHERITE_AXE");
        }

        return blockedItems;
    }

    public static boolean isWood(Material material) {
        switch(material) {
            case ACACIA_LOG:
            case BIRCH_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case OAK_LOG:
            case SPRUCE_LOG:
            case STRIPPED_ACACIA_LOG:
            case STRIPPED_BIRCH_LOG:
            case STRIPPED_DARK_OAK_LOG:
            case STRIPPED_JUNGLE_LOG:
            case STRIPPED_OAK_LOG:
            case STRIPPED_SPRUCE_LOG:
            case ACACIA_WOOD:
            case BIRCH_WOOD:
            case DARK_OAK_WOOD:
            case JUNGLE_WOOD:
            case OAK_WOOD:
            case SPRUCE_WOOD:
            case STRIPPED_ACACIA_WOOD:
            case STRIPPED_BIRCH_WOOD:
            case STRIPPED_DARK_OAK_WOOD:
            case STRIPPED_JUNGLE_WOOD:
            case STRIPPED_OAK_WOOD:
            case STRIPPED_SPRUCE_WOOD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLeaves(Material material) {
        switch(material) {
            case ACACIA_LEAVES:
            case BIRCH_LEAVES:
            case DARK_OAK_LEAVES:
            case JUNGLE_LEAVES:
            case OAK_LEAVES:
            case SPRUCE_LEAVES:
                return true;
            default:
                return false;
        }
    }
}
