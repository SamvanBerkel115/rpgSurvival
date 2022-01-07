package sam.berkel.rpgSurvival.model.activities;

import java.util.concurrent.Callable;

public class EndArenaCallable implements Callable {
    private ActivityArena arena;
    private ActivityPlayer winner;

    public EndArenaCallable(ActivityArena arena, ActivityPlayer winner) {
        this.arena = arena;
        this.winner = winner;
    }

    @Override
    public Object call() {
        arena.end(winner);

        return null;
    }
}
