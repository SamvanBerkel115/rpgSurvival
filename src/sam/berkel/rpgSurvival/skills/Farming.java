package sam.berkel.rpgSurvival.skills;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

public class Farming {
    public static void brokeBlock(User user, Block block) {
        if (block.getType() == Material.WHEAT) {
            user.addXp(20, Main.Skill.FARMING);
        }
    }
}
