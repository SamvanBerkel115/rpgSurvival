package sam.berkel.rpgSurvival.model.activities.spleef;

import org.bukkit.Location;
import sam.berkel.rpgSurvival.model.activities.ActivityArena;
import java.util.ArrayList;

public abstract class SpleefArena extends ActivityArena {
    int currentRound;
    int rounds;

    public SpleefArena(String name, ArrayList<Location> playerSpawns, int rounds) {
        super(name, playerSpawns);
        this.currentRound = 1;
        this.rounds = rounds;
    }
}
