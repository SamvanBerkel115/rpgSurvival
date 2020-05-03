package sam.berkel.rpgSurvival;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryMenu {
    public static void openMainMenu(Player player) {
        Inventory menu = Main.getPlugin(Main.class).getServer().createInventory(null, 9, ChatColor.DARK_RED + "Main menu");

        ItemStack teleports  = new ItemStack(Material.PAPER, 1);
        ItemMeta teleportsMeta = teleports.getItemMeta();
        teleportsMeta.setDisplayName(ChatColor.RED + "Teleports");
        teleports.setItemMeta(teleportsMeta);

        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName("'");
        empty.setItemMeta(emptyMeta);

        menu.setItem(0, empty);
        menu.setItem(0, empty);
        menu.setItem(2, teleports);

        player.openInventory(menu);
    }

    public static void openTeleportMenu(Player player) {
        Inventory menu = Main.getPlugin(Main.class).getServer().createInventory(null, 9, ChatColor.DARK_RED + "Teleports");

        ItemStack teleports  = new ItemStack(Material.WOODEN_AXE, 1);
        ItemMeta teleportsMeta = teleports.getItemMeta();
        teleportsMeta.setDisplayName(ChatColor.RED + "Teleport 1");
        teleports.setItemMeta(teleportsMeta);

        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName("'");
        empty.setItemMeta(emptyMeta);

        menu.setItem(0, empty);
        menu.setItem(1, teleports);
        menu.setItem(2, empty);

        player.openInventory(menu);
    }

    public static void handleClick(InventoryClickEvent event) {
        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

        if (itemName.equals(ChatColor.RED + "Teleports")) {
            openTeleportMenu((Player) event.getWhoClicked());
        }
    }
}
