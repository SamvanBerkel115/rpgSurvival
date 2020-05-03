package sam.berkel.rpgSurvival.skills;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

import java.util.ArrayList;
import java.util.List;

public class Excavation {
    // Checks if a given tool should give mining xp.
    public static boolean isExcavationTool(ItemStack item) {
        Plugin plugin = Main.getPlugin(Main.class);

        String tool = item.getType().toString();
        List<String> excavationTools = plugin.getConfig().getStringList("Leveling.tools.excavation");

        return excavationTools.contains(tool);
    }

    public static void brokeBlock(User user, Block block) {
        Server server = Server.getInstance();
        ItemStack tool = user.getPlayer().getInventory().getItemInMainHand();
        user.getPlayer().sendMessage("You used" + tool.getType().toString());

        if (isExcavationTool(tool)) {
            String blockType = block.getType().toString();

            int gainedXp = getBlockXp(block);
            user.addXp(gainedXp, "Excavation");
        }
    }

    // Returns the amount of xp that is gained when a player destroys this block with a mining tool.
    public static int getBlockXp(Block block) {
        Plugin plugin = Main.getPlugin(Main.class);

        String type = block.getType().toString();
        plugin.getConfig().getConfigurationSection("Leveling.xp.excavation").contains(type);

        if ( plugin.getConfig().getConfigurationSection("Leveling.xp.excavation").contains(type) ) {
            System.out.println("The block " + type + "Gives: " + plugin.getConfig().getInt("Leveling.xp.excavation." + type));
            return plugin.getConfig().getInt("Leveling.xp.excavation." + type);
        } else {
            System.out.println("The block " + type + "Gives no excavation xp ");
            return 0;
        }
    }

    public static ArrayList<String> getBlockedItems (int level) {
        ArrayList<String> blockedItems = new ArrayList<>();

        if (level < 5) {
            blockedItems.add("STONE_SPADE");
        }
        if (level < 10) {
            blockedItems.add("IRON_SPADE");
        }

        return blockedItems;
    }
}
