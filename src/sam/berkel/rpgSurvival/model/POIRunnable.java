package sam.berkel.rpgSurvival.model;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.Main;

public class POIRunnable extends BukkitRunnable {
    @Override
    public void run() {
        Plugin plugin = Main.getPlugin(Main.class);
        Server server = Server.getInstance();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            User user = server.getUser(player.getUniqueId());

            for (PointOfInterest poi : server.getPOIs()) {
                if ( poi.isWithinRadius(player) ) {
                    String currentPoi = user.getCurrentPoi();

                    if ( currentPoi == null || !currentPoi.equals(poi.getName()) ) {
                        user.setCurrentPoi(poi.getName());

                        player.sendTitle("", "You entered " + ChatColor.AQUA + poi.getName(), 10, 30, 10);

                        Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
                            @Override
                            public void run(){
                                player.setGameMode(GameMode.ADVENTURE);
                            }
                        });
                    }
                } else {
                    String currentPoi = user.getCurrentPoi();

                    if (currentPoi == null) return;

                    if ( currentPoi.equals(poi.getName()) ) {
                        user.removeCurrentPoi();

                        player.sendTitle("", "You left " + ChatColor.AQUA + poi.getName(), 10, 30, 10);

                        Bukkit.getServer().getScheduler().runTask(plugin, new Runnable(){
                            @Override
                            public void run(){
                                player.setGameMode(GameMode.SURVIVAL);
                            }
                        });
                    }
                }
            }
        }
    }
}
