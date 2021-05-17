package sam.berkel.rpgSurvival.model;

import java.util.ArrayList;
import java.util.UUID;

public class Citizen {
    UUID uuid;
    String name;
    String[] dialogue;

    public Citizen(UUID uuid, String name, String[] dialogue) {
        this.uuid = uuid;
        this.name = name;
        this.dialogue = dialogue;
    }
}
