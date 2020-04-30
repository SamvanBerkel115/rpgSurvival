package sam.berkel.rpgSurvival.model;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class Server {
    // static variable single_instance of type Singleton
    private static Server instance = null;

    private Map<String, User> users;

    // private constructor restricted to this class itself
    private Server() {
        users = new HashMap<>();
    };

    // static method to create instance of Singleton class
    public static Server getInstance()
    {
        if (instance == null)
            instance = new Server();

        return instance;
    }

    public void playerJoined(Player player) {
        users.put(player.getDisplayName(), new User(player));
    }
}
