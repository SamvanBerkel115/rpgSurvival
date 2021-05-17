package sam.berkel.rpgSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "name":
                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;
                    User user = Server.getInstance().getUser(player.getUniqueId());
                }
                break;
            default:
                break;
        }

        return true;
    }
}
