package sam.berkel.rpgSurvival.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.CutScene;

public class CutSceneCommand implements CommandExecutor {
    public static String command = "cutScene";

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        switch (args[0]) {
            case "recordPath":
                return recordPath(commandSender, args);
            case "playPath":
                return playPath(commandSender, args);
            default:
                break;
        }

        return false;
    }


    public static boolean recordPath(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length > 3) {
                player.sendMessage("Too many arguments!");
                return false;
            } else if (args.length < 3) {
                player.sendMessage("Not enough arguments!");
                return false;
            } else {
                try {
                    int duration = Integer.parseInt(args[1]);

                    String name = args[2];

                    CutScene.recordPath(player, duration, name);
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage("Invalid duration!");
                    return false;
                }
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command.");
            return false;
        }
    }

    public static boolean playPath(CommandSender commandSender, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length > 2) {
                player.sendMessage("Too many arguments!");
                return false;
            } else if (args.length < 2) {
                player.sendMessage("Not enough arguments!");
                return false;
            } else {
                String pathName = args[1];

                CutScene.playPath(player, pathName);
                return true;
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command.");
            return false;
        }
    }
}
