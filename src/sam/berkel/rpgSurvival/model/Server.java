package sam.berkel.rpgSurvival.model;

import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.skills.Mining;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class Server {
    // static variable single_instance of type Singleton
    private static Server instance = null;

    private Map<String, User> users;
    private Plugin plugin = Main.getPlugin(Main.class);
    private double base;
    private double exponent;

    // private constructor restricted to this class itself
    private Server() {
        users = new HashMap<>();

        base = plugin.getConfig().getDouble("Leveling.formula.base");
        exponent = plugin.getConfig().getDouble("Leveling.formula.exponent");
    };

    // static method to create instance of Singleton class
    public static Server getInstance()
    {
        if (instance == null)
            instance = new Server();

        return instance;
    }

    public double getBase() {
        return base;
    }

    public double getExponent() {
        return exponent;
    }

    public void userJoined(User user) {
        users.put(user.getUniqueId().toString(), user);
    }

    public User getUser(UUID uuid) {
        return users.get(uuid.toString());
    }

    public Set<String> getUserKeys() {
        return users.keySet();
    }
}
