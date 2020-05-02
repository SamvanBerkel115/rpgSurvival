package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;
import sam.berkel.rpgSurvival.skills.Mining;
import sam.berkel.rpgSurvival.skills.Woodcutting;

public class Events implements Listener {
    private Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Server server = Server.getInstance();
        Block brokenBlock = event.getBlock();
        Player player = event.getPlayer();
        User user = server.getUser(player.getUniqueId());

        Mining.brokeBlock(user, brokenBlock);
        Woodcutting.brokeBlock(user, brokenBlock);

        ItemStack tool = player.getInventory().getItemInMainHand();
        player.sendMessage("You used" + tool.getType().toString());
        player.sendMessage("You broke" + brokenBlock.getType().toString() + "using " + tool.getType().toString());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user;

        String[] skills = new String[] {"mining", "woodcutting", "excavation"};

        if ( plugin.getConfig().contains("Users." + player.getUniqueId()) ) {
            System.out.println("An existing user joined");

            int miningLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningLvl").toString());
            int miningXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".miningXp").toString());
            int woodcuttingLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingLvl").toString());
            int woodcuttingXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".woodcuttingXp").toString());
            int excavationLvl = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationLvl").toString());
            int excavationXp = Integer.parseInt(plugin.getConfig().get("Users." + player.getUniqueId() +".excavationXp").toString());

            user = new User(player, miningXp, miningLvl, woodcuttingXp, woodcuttingLvl, excavationXp, excavationLvl);
        } else {
            System.out.println("A new user joined");
            plugin.getConfig().getStringList("Users").add(player.getUniqueId().toString());

            plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".woodcuttingXp", "0");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationLvl", "1");
            plugin.getConfig().set("Users." + player.getUniqueId() +".excavationXp", "0");

            plugin.saveConfig();

            user = new User(player, 0, 1, 0, 1, 0, 1);
        }

        Server.getInstance().userJoined(user);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Server.getInstance().getUser(player.getUniqueId());


        plugin.getConfig().set("Users." + player.getUniqueId() +".miningLvl", user.getMiningLvl());
        plugin.getConfig().set("Users." + player.getUniqueId() +".miningXp", user.getMiningXp());
        plugin.saveConfig();
    }
}
