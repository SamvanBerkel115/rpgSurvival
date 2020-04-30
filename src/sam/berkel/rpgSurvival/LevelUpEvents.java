package sam.berkel.rpgSurvival;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import sam.berkel.rpgSurvival.model.Server;

public class LevelUpEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();
        String blockType = brokenBlock.getType().toString();

        switch (blockType) {
            case "STONE":
            break;
        }
        Player player = event.getPlayer();

        player.sendMessage("You broke" + brokenBlock.getType().toString());

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Server.getInstance().playerJoined(player);
    }
}
