package sam.berkel.rpgSurvival.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import sam.berkel.rpgSurvival.model.citizen.Citizen;
import sam.berkel.rpgSurvival.model.Server;

import java.util.HashMap;

public class CitizenCommand implements CommandExecutor {
    public static String command = "citizen";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args[0]) {
            case "add":
                return addCitizen(commandSender, args);
            case "dialogue":
                return true;
            default:
                break;
        }

        return false;
    }

    public boolean addCitizen(CommandSender commandSender, String[] args) {
        String name = args[1];
        String profession = args[2];

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Location loc = player.getLocation();

            Villager.Profession villagerProfession = Villager.Profession.NONE;
            switch (profession) {
                case "armorer": villagerProfession = Villager.Profession.ARMORER;
                break;
                case "butcher": villagerProfession = Villager.Profession.BUTCHER;
                    break;
                case "cartographer": villagerProfession = Villager.Profession.CARTOGRAPHER;
                    break;
                case "cleric": villagerProfession = Villager.Profession.CLERIC;
                    break;
                case "farmer": villagerProfession = Villager.Profession.FARMER;
                    break;
                case "fisherman": villagerProfession = Villager.Profession.FISHERMAN;
                    break;
                case "fletcher": villagerProfession = Villager.Profession.FLETCHER;
                    break;
                case "leatherworker": villagerProfession = Villager.Profession.LEATHERWORKER;
                    break;
                case "librarian": villagerProfession = Villager.Profession.LIBRARIAN;
                    break;
                case "mason": villagerProfession = Villager.Profession.MASON;
                    break;
                case "shepherd": villagerProfession = Villager.Profession.SHEPHERD;
                    break;
                case "toolsmith": villagerProfession = Villager.Profession.TOOLSMITH;
                    break;
                case "weaponsmitch": villagerProfession = Villager.Profession.WEAPONSMITH;
                    break;
            }

            Villager villager = (Villager) player.getWorld().spawnEntity(loc, EntityType.VILLAGER);
            villager.setCustomName(name);
            villager.setCustomNameVisible(true);
            villager.setProfession(villagerProfession);
            villager.setAI(false);

            Server.getInstance();

            return true;
        } else {
            return false;
        }
    }
}
