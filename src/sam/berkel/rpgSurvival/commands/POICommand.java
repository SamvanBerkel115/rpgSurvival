package sam.berkel.rpgSurvival.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.PointOfInterest;
import sam.berkel.rpgSurvival.model.Server;

public class POICommand implements CommandExecutor {
    public static String command = "poi";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "add":
                return addPOI(commandSender, args);
            case "delete":
                return deletePOI(commandSender, args);
            default:
                break;
        }

        return false;
    }

    // Args: add, name x, y, z, radius
    public boolean addPOI(CommandSender commandSender, String[] args) {
        String name = args[1];
        Integer x = Integer.parseInt(args[2]);
        Integer y = Integer.parseInt(args[3]);
        Integer z = Integer.parseInt(args[4]);
        Integer radius = Integer.parseInt(args[5]);

        PointOfInterest poi = new PointOfInterest(name, x, y, z, radius);

        Server.getInstance().addPOI(poi);

        System.out.println("Added poi: " + name + "(" + x + ", " + y + ", " + z + ") " + radius);

        Plugin plugin = Main.getPlugin(Main.class);

        plugin.getConfig().set("POIs." + name +".x", x);
        plugin.getConfig().set("POIs." + name +".y", y);
        plugin.getConfig().set("POIs." + name +".z", z);
        plugin.getConfig().set("POIs." + name +".radius", radius);

        return true;
    }

    // Args: delete, name
    public boolean deletePOI(CommandSender commandSender, String[] args) {
        Server.getInstance().removePOI(args[1]);

        Plugin plugin = Main.getPlugin(Main.class);
        plugin.getConfig().set("POIs." + args[1], null);

        return true;
    }
}
