package sam.berkel.rpgSurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.activities.Activities;
import sam.berkel.rpgSurvival.model.activities.ActivityPlayer;
import sam.berkel.rpgSurvival.model.activities.ActivityType;

import java.util.UUID;

public class SpleefCommand implements CommandExecutor {
    public static String command = "spleef";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "join":
                return joinCommand(commandSender, args);
            case "leave":
                return leaveCommand(commandSender, args);
            case "cancel":
                return cancelCommand(commandSender, args);
            default:
                break;
        }

        return false;
    }

    private boolean joinCommand(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Invalid spleef join command. Usage: /spleef join <arenaName>.");
            return false;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Invalid spleef join command. Command sender should be a player.");
            return false;
        }

        String arenaName = args[1];
        Activities activities = Server.getInstance().getActivities();
        activities.join(ActivityType.SPLEEF, arenaName, (Player) commandSender);

        return true;
    }

    private boolean leaveCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Invalid spleef leave command. Command sender should be a player.");
            return false;
        }

        Player player = (Player) commandSender;

        Server.getInstance().getActivities().leave(player);

        return true;
    }

    private boolean cancelCommand(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Invalid spleef cancel command. Usage: /spleef cancel <arenaName>.");
            return false;
        }

        String arenaName = args[1];
        Activities activities = Server.getInstance().getActivities();
        activities.cancel(ActivityType.SPLEEF, arenaName);

        return true;
    }
}
