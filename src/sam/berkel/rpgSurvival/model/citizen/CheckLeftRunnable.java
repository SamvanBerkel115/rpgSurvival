package sam.berkel.rpgSurvival.model.citizen;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sam.berkel.rpgSurvival.model.Server;
import sam.berkel.rpgSurvival.model.User;

public class CheckLeftRunnable extends BukkitRunnable {
    private User user;
    private Entity citizenEntity;

    public CheckLeftRunnable(User user, Entity citizenEntity) {
        this.user = user;
        this.citizenEntity = citizenEntity;
    }

    @Override
    public void run() {
        Player player = user.getPlayer();

        double playerX = player.getLocation().getX();
        double playerY = player.getLocation().getY();
        double playerZ = player.getLocation().getZ();

        double citizenX = citizenEntity.getLocation().getX();
        double citizenY = citizenEntity.getLocation().getY();
        double citizenZ = citizenEntity.getLocation().getZ();

        double distance = Math.pow(Math.pow(playerX - citizenX, 2) + Math.pow(playerY - citizenY, 2) + Math.pow(playerZ - citizenZ, 2), 0.5);

        if (distance > 7) {
            user.setDialogueCitizen(null);

            Citizen citizen = Server.getInstance().getCitizen(citizenEntity.getUniqueId());
            citizen.setToReturnState(player);

            this.cancel();
        }
    }
}
