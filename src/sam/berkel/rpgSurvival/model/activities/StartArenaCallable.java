package sam.berkel.rpgSurvival.model.activities;

import net.minecraft.server.v1_15_R1.Activity;

import java.util.concurrent.Callable;

public class StartArenaCallable implements Callable {
    private ActivityArena arena;

    public StartArenaCallable(ActivityArena arena) {
        this.arena = arena;
    }

    @Override
    public Object call() {
        arena.start();

        return null;
    }
}
