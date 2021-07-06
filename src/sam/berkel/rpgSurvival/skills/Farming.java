package sam.berkel.rpgSurvival.skills;

import org.bukkit.Material;
import org.bukkit.block.Block;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.user.User;

public class Farming {
    public static void brokeBlock(User user, Block block) {
        if (block.getType() == Material.WHEAT) {
            user.addXp(20, Main.Skill.FARMING);
        }
    }
}
