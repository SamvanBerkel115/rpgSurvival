package sam.berkel.rpgSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sam.berkel.rpgSurvival.Main;

public class RpgCommand implements CommandExecutor {
    public static String command = "rpg";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "tpworld":
                return tpWorldCommand(commandSender, args);
            default:
                break;
        }

        return false;
    }

    private static boolean tpWorldCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        String worldName = args[1];
        Location worldSpawnLoc = Bukkit.getServer().getWorld(worldName).getSpawnLocation();

        Player player = (Player) commandSender;
        player.teleport(worldSpawnLoc);

        return true;
    }
}
