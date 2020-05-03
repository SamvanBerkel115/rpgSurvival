package sam.berkel.rpgSurvival.skills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import sam.berkel.rpgSurvival.model.User;

import java.util.Arrays;

public class Fishing {
    // Returns a random fish weight, getting higher as the level increases.
    public static double getFishWeight(int level) {
        double baseWeight = 2;
        double multiplier = 0.25;
        double randomRange = 2;

        double randomFactor = Math.random() * randomRange - randomRange / 2;
        double weight = baseWeight + randomFactor + Math.sqrt(level) * multiplier;
        double roundedWeight = Math.round(weight * 10) / 10.0;

        return roundedWeight;
    }

    public static boolean isLarge(int level) {
        if ( Math.random() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void handleCaughtItem(Item caughtItem, User user) {
        Material caughtMat = caughtItem.getItemStack().getType();

        // Check if the item that the player has caught a fish.
        if(caughtMat.equals(Material.COD) || caughtMat.equals(Material.SALMON) || caughtMat.equals(Material.PUFFERFISH) || caughtMat.equals(Material.TROPICAL_FISH)){
            System.out.println("Caught fish");

            // Add the weight of the fish
            ItemMeta itemMeta = caughtItem.getItemStack().getItemMeta();
            double fishWeight = Fishing.getFishWeight(user.getFishingLvl());

            // Give the fish a different name and weight if the fish is large.
            if (Fishing.isLarge(user.getFishingLvl())) {
                String fishName = caughtItem.getItemStack().getItemMeta().getDisplayName();
                itemMeta.setDisplayName(ChatColor.GOLD + "Large " + fishName);

                fishWeight =  2 * fishWeight;
            }

            user.addXp((int) (700 * fishWeight), "Fishing");

            itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Weight: " + fishWeight));
            caughtItem.getItemStack().setItemMeta(itemMeta);

            System.out.println(caughtItem.getItemStack().getItemMeta().getDisplayName());
        }
    }
}