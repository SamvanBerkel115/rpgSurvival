package sam.berkel.rpgSurvival.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import java.util.concurrent.ThreadLocalRandom;

public class RPGMonster {
    Monster monster;
    int level;

    public RPGMonster(Monster monster) {
        this.monster = monster;
        this.level = ThreadLocalRandom.current().nextInt(1, 31);

        refreshName();
    }

    public Monster getMonster() {
        return monster;
    }

    public void refreshName() {
        ChatColor levelColor = null;
        if (level <= 10) levelColor = ChatColor.DARK_GREEN;
        else if (level <= 20) levelColor = ChatColor.GOLD;
        else levelColor = ChatColor.RED;

        double maxHealth = monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        double health = monster.getHealth();

        double healthPercentage = health / maxHealth;
        ChatColor healthcolor = null;
        if (healthPercentage > 0.7) healthcolor = ChatColor.DARK_GREEN;
        else if (healthPercentage > 0.3) healthcolor = ChatColor.GOLD;
        else healthcolor = ChatColor.RED;

        String nameString = "LVL " + levelColor + level + " " + healthcolor + "";
        for (int i = 0; i < health; i+=4) {
            nameString += "⬛";
        }

        monster.setCustomName(nameString);
        monster.setCustomNameVisible(true);
    }
}
