package sam.berkel.rpgSurvival.skills;

import java.util.ArrayList;

public class Crafting {
    public static boolean canCraft(int level, int customModelData) {
        // Wand
        if (customModelData == 938123 && level >= 2) {
            return true;
        }

        // Teleport block
        if (customModelData == 938124 && level >= 1) {
            return true;
        }

        return false;
    }
}
