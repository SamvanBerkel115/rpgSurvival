package sam.berkel.rpgSurvival;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CustomRecipes {
    public static void addAll() {
        CustomRecipes.wand();
        CustomRecipes.teleportBlock();
    }

    public static void wand() {
        ItemStack wand = new ItemStack(Material.STICK);

        ItemMeta wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.RED + "Wand");
        wandMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        wandMeta.setCustomModelData(938123);
        wand.setItemMeta(wandMeta);

        NamespacedKey nsk = new NamespacedKey(Main.getPlugin(Main.class), "wandRecipe");
        ShapedRecipe recipe = new ShapedRecipe(nsk, wand);

        recipe.shape("  *", " / ", "/  ");
        recipe.setIngredient('/', Material.STICK);
        recipe.setIngredient('*', Material.REDSTONE);

        Main.getPlugin(Main.class).getServer().addRecipe(recipe);
    }

    public static void teleportBlock() {
        ItemStack block = new ItemStack(Material.CYAN_GLAZED_TERRACOTTA);

        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.RED + "Teleport block");
        blockMeta.addEnchant(Enchantment.CHANNELING, 1, true);
        blockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        blockMeta.setCustomModelData(938124);
        block.setItemMeta(blockMeta);

        NamespacedKey nsk = new NamespacedKey(Main.getPlugin(Main.class), "teleportBlockRecipe");
        ShapedRecipe recipe = new ShapedRecipe(nsk, block);

        recipe.shape("000", "B*B", "BBB");
        recipe.setIngredient('0', Material.DIAMOND);
        recipe.setIngredient('*', Material.EMERALD);
        recipe.setIngredient('B', Material.OBSIDIAN);

        Main.getPlugin(Main.class).getServer().addRecipe(recipe);
    }
}
