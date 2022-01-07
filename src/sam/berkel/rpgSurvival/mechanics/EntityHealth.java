package sam.berkel.rpgSurvival.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntityHealth {

    public static void setMonsterName(Entity entity) {
        if (entity instanceof Monster && !(entity instanceof Player)) {
            LivingEntity livingEntity = (LivingEntity) entity;

            double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            double health = livingEntity.getHealth();

            double healthPercentage = health / maxHealth;
            ChatColor color = null;
            if (healthPercentage > 0.7) color = ChatColor.DARK_GREEN;
            else if (healthPercentage > 0.3) color = ChatColor.GOLD;
            else color = ChatColor.RED;

            String healthString = "LVL 1 " + color + "";
            for (int i = 0; i < health; i+=4) {
                healthString += "⬛";
            }

            livingEntity.set

            livingEntity.setCustomName(healthString);
            livingEntity.setCustomNameVisible(true);
        }
    }
}
