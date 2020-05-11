package sam.berkel.rpgSurvival;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;
import sam.berkel.rpgSurvival.skills.Magic;

import java.util.Arrays;

public class InventoryMenu {
    public static void placeMenuButton(Player player) {
        ItemStack menubutton = new ItemStack(Material.WRITABLE_BOOK, 1);

        ItemMeta menuMeta = menubutton.getItemMeta();
        menuMeta.setDisplayName(ChatColor.GOLD + "Menu");
        menubutton.setItemMeta(menuMeta);

        player.getInventory().setItem(0, menubutton);
    }

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

        ItemStack city  = new ItemStack(Material.STONE_BRICKS, 1);
        ItemMeta cityMeta = city.getItemMeta();
        cityMeta.setDisplayName(ChatColor.RED + "City");
        city.setItemMeta(cityMeta);

        ItemStack bosses  = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta bossesMeta = bosses.getItemMeta();
        bossesMeta.setDisplayName(ChatColor.RED + "Bosses");
        bosses.setItemMeta(bossesMeta);

        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName("'");
        empty.setItemMeta(emptyMeta);

        menu.setItem(0, empty);
        menu.setItem(1, city);
        menu.setItem(2, empty);
        menu.setItem(3, bosses);

        player.openInventory(menu);
    }

    public static void openSpellMenu(Player player) {
        Inventory menu = Main.getPlugin(Main.class).getServer().createInventory(null, 9, ChatColor.DARK_RED + "Spells");

        ItemStack fireBolt  = new ItemStack(Material.FIRE_CORAL, 1);
        ItemMeta fireBoltMeta = fireBolt.getItemMeta();
        fireBoltMeta.setDisplayName(ChatColor.RED + "Fire Bolt");
        fireBoltMeta.setLore(Arrays.asList("Damage: 7", "Cooldown: 3", "Cost: 1 Redstone"));
        fireBolt.setItemMeta(fireBoltMeta);

        ItemStack jailBolt  = new ItemStack(Material.COBWEB, 1);
        ItemMeta jailBoltMeta = jailBolt.getItemMeta();
        jailBoltMeta.setDisplayName(ChatColor.RED + "Jail Bolt");
        jailBoltMeta.setLore(Arrays.asList("Damage: 0", "Cooldown: 3", "Cost: 1 Redstone"));
        jailBolt.setItemMeta(jailBoltMeta);

        ItemStack avadaKedavra  = new ItemStack(Material.SKELETON_SKULL, 1);
        ItemMeta avadaKedavraMeta = avadaKedavra.getItemMeta();
        avadaKedavraMeta.setDisplayName(ChatColor.RED + "Avada Kedavra");
        avadaKedavraMeta.setLore(Arrays.asList("Damage: 0", "Cooldown: 3", "Cost: 1 Redstone"));
        avadaKedavra.setItemMeta(avadaKedavraMeta);

        ItemStack sigma  = new ItemStack(Material.FEATHER, 1);
        ItemMeta sigmaMeta = sigma.getItemMeta();
        sigmaMeta.setDisplayName(ChatColor.RED + "Sigma");
        sigmaMeta.setLore(Arrays.asList("Damage: 0", "Cooldown: 3", "Cost: 1 Redstone"));
        sigma.setItemMeta(sigmaMeta);

        ItemStack volley  = new ItemStack(Material.ARROW, 1);
        ItemMeta volleyMeta = volley.getItemMeta();
        volleyMeta.setDisplayName(ChatColor.RED + "Volley");
        volleyMeta.setLore(Arrays.asList("Damage: 0", "Cooldown: 3", "Cost: 1 Redstone"));
        volley.setItemMeta(volleyMeta);

        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName("'");
        empty.setItemMeta(emptyMeta);

        menu.setItem(0, empty);
        menu.setItem(1, fireBolt);
        menu.setItem(2, jailBolt);
        menu.setItem(3, avadaKedavra);
        menu.setItem(4, sigma);
        menu.setItem(5, volley);

        player.openInventory(menu);
    }

    public static void handleClick(InventoryClickEvent event) {
        String menuName = event.getView().getTitle();

        if ( menuName.equals(ChatColor.DARK_RED + "Main menu") ) {
            handleMainMenuClick(event);
        } else if ( menuName.equals(ChatColor.DARK_RED + "Teleports") ) {
            handleTeleportMenuClick(event);
        } else if ( menuName.equals(ChatColor.DARK_RED + "Spells") ) {
            handleSpellsMenuClick(event);
        }
    }

    public static void handleMainMenuClick(InventoryClickEvent event) {
        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

        if (itemName.equals(ChatColor.RED + "Teleports")) {
            InventoryMenu.openTeleportMenu((Player) event.getWhoClicked());
        }
    }

    public static void handleTeleportMenuClick(InventoryClickEvent event) {
        Plugin plugin = Main.getPlugin(Main.class);
        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
        HumanEntity player = event.getWhoClicked();

        if (itemName.equals(ChatColor.RED + "City")) {
            World bosses = plugin.getServer().getWorld("Survival");
            Location loc = new Location(bosses, 0, 70, 0);

            player.teleport(loc);
        } else if (itemName.equals(ChatColor.RED + "Bosses")) {
            World survival = plugin.getServer().getWorld("Bosses");
            Location loc = new Location(survival, 0, 70, 0);

            player.teleport(loc);
        }
    }

    public static void handleSpellsMenuClick(InventoryClickEvent event) {
        String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
        Player player = (Player) event.getWhoClicked();
        User user = Server.getInstance().getUser(player.getUniqueId());

        if ( itemName.equals(ChatColor.RED + "Fire Bolt") ) {
            user.setActiveSpell("fireBolt");
        } else if ( itemName.equals(ChatColor.RED + "Jail Bolt") ) {
            user.setActiveSpell("jailBolt");
        } else if ( itemName.equals(ChatColor.RED + "Avada Kedavra") ) {
            user.setActiveSpell("avadaKedavra");
        } else if ( itemName.equals(ChatColor.RED + "Sigma") ) {
            user.setActiveSpell("sigma");
        } else if ( itemName.equals(ChatColor.RED + "Volley") ) {
            user.setActiveSpell("volley");
        }

        player.closeInventory();
    }
}
