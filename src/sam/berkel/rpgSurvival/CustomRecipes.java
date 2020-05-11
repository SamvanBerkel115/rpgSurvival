package sam.berkel.rpgSurvival;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CustomRecipes {
    public static void addAll() {
        CustomRecipes.wand();
    }

    public static void wand() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.RED + "Wand");
        wand.setItemMeta(wandMeta);

        NamespacedKey nsk = new NamespacedKey(Main.getPlugin(Main.class), "wandRecipe");
        ShapedRecipe recipe = new ShapedRecipe(nsk, wand);

        recipe.shape("  *", " / ", "/  ");
        recipe.setIngredient('/', Material.STICK);
        recipe.setIngredient('*', Material.REDSTONE);

        Main.getPlugin(Main.class).getServer().addRecipe(recipe);
    }

}
