package sam.berkel.rpgSurvival.model;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;
import java.util.Collection;

public class CutScene {
    public static void intro(Player player) {
        Server server = Server.getInstance();
        User user = server.getUser(player.getUniqueId());

        user.setInCutscene(true);
    }

    public static void writePathToConfig(ArrayList<Frame> frames, String name) {
        Plugin plugin = Main.getPlugin(Main.class);

        for (Frame frame : frames) {
            plugin.getConfig().set("Paths." + name + "." + frame.getTick() + ".x", frame.getX());
            plugin.getConfig().set("Paths." + name + "." + frame.getTick() + ".y", frame.getY());
            plugin.getConfig().set("Paths." + name + "." + frame.getTick() + ".z", frame.getZ());
            plugin.getConfig().set("Paths." + name + "." + frame.getTick() + ".pitch", frame.getPitch());
            plugin.getConfig().set("Paths." + name + "." + frame.getTick() + ".yaw", frame.getYaw());

            plugin.saveConfig();
        }
    }

    //Duration in seconds
    public static void recordPath(Player player, int duration, String name) {
        System.out.println("Recording scene");

        int tickDuration = duration * 20;

        System.out.println(tickDuration);

        new BukkitRunnable() {
            ArrayList<Frame> frames = new ArrayList<>();
            Location oldLoc = player.getLocation();

            int tick = 0;

            @Override
            public void run() {
                Location loc = player.getLocation();

                if (tick == 0) {
                    Frame frame = new Frame(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), tick);
                    frames.add(frame);
                } else {
                    double x = loc.getX() - oldLoc.getX();
                    double y = loc.getY() - oldLoc.getY();
                    double z = loc.getZ() - oldLoc.getZ();

                    System.out.println(x + ", " + y + ", " + z);

                    Frame frame = new Frame(x, y, z, loc.getYaw(), loc.getPitch(), tick);
                    frames.add(frame);

                    oldLoc = loc;

                    if (tick >= tickDuration) {
                        writePathToConfig(frames, name);
                        this.cancel();
                    }
                }
                tick++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 20, 1);
    }

    public static void playPath(Player player, String pathName) {
        Plugin plugin = Main.getPlugin(Main.class);

        ConfigurationSection pathSection = plugin.getConfig().getConfigurationSection("Paths." + pathName);

        Collection<String> ticks = pathSection.getKeys(false);

        System.out.println(ticks.size());

        player.setAllowFlight(true);
        player.setFlying(true);

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index == 0) {
                    Location loc = player.getLocation();
                    loc.setX(pathSection.getDouble(index + ".x"));
                    loc.setY(pathSection.getDouble(index + ".y"));
                    loc.setZ(pathSection.getDouble(index + ".z"));
                    loc.setPitch( Double.valueOf(pathSection.getDouble(index + ".pitch")).floatValue() );
                    loc.setYaw( Double.valueOf(pathSection.getDouble(index + ".yaw")).floatValue() );

                    player.teleport(loc);
                } else {
                    if (index > ticks.size()) {
                        System.out.println("Cutscene finished.");
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        this.cancel();
                    }

                    // Change the player look direction
                    Location loc = player.getLocation();
                    loc.setPitch( Double.valueOf(pathSection.getDouble(index + ".pitch")).floatValue() );
                    loc.setYaw( Double.valueOf(pathSection.getDouble(index + ".yaw")).floatValue() );
                    player.teleport(loc);

                    double x = pathSection.getDouble(index + ".x");
                    double y = pathSection.getDouble(index + ".y");
                    double z = pathSection.getDouble(index + ".z");
                    Vector velocity = new Vector(x, y, z);
                    player.setVelocity(velocity);
                }
                index++;
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, 1);

    }
}
