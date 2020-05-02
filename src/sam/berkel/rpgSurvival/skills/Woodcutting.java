package sam.berkel.rpgSurvival.skills;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

public class Woodcutting {
    // Checks if a given tool should give mining xp.
    public static boolean isWoodcuttingTool(ItemStack item) {
        Plugin plugin = Main.getPlugin(Main.class);

        String tool = item.getType().toString();
        return plugin.getConfig().getConfigurationSection("Leveling.tools.woodcutting").contains(tool);
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
}
