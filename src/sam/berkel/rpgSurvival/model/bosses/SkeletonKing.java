package sam.berkel.rpgSurvival.model.bosses;

import org.bukkit.Location;
import sam.berkel.rpgSurvival.model.user.User;

import java.util.ArrayList;

public class SkeletonKing extends Boss {
    public SkeletonKing(Location startLocation, String name, ArrayList<User> users) {
        super(startLocation, name, users);
    }
}
