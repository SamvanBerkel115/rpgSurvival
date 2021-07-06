package sam.berkel.rpgSurvival.commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;

public class BossCommand implements CommandExecutor {
    public static String command = "boss";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "link":
                return linkBoss(commandSender, args);
            case "teleport":
                return teleportPlayerToBossWorld(commandSender, args);
            default:
                break;
        }

        return false;
    }

    private static boolean linkBoss(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            Plugin plugin = Main.getPlugin(Main.class);
            Block targetBlock = player.getTargetBlock(null, 10);
            Location targetBlockLocation = targetBlock.getLocation();

            String bossName = args[1];

            switch (bossName) {
                case "skeleton_king":
                    plugin.getConfig().set("Bosses." + bossName + ".blockX", targetBlockLocation.getBlockX());
                    plugin.getConfig().set("Bosses." + bossName + ".blockY", targetBlockLocation.getBlockY());
                    plugin.getConfig().set("Bosses." + bossName + ".blockZ", targetBlockLocation.getBlockZ());

                    plugin.saveConfig();

                    System.out.println("Linked skeleton king to" + targetBlockLocation.getBlockX() + ", " + targetBlockLocation.getBlockY() + ", " + targetBlockLocation.getBlockZ());

                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    private static boolean teleportPlayerToBossWorld(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {

        }

        return true;
    }
}
