package sam.berkel.rpgSurvival.model.activities;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ActivityPlayer {
    Player player;
    ActivityArena arena;
    Location startLocation;
    ItemStack helmet;
    ItemStack chestplate;
    ItemStack leggings;
    ItemStack boots;
    ItemStack mainHand;
    ItemStack offHand;
    ItemStack[] contents;
    GameMode gameMode;

    public ActivityPlayer(Player player, ActivityArena arena) {
        this.player = player;
        this.arena = arena;
        this.startLocation = player.getLocation();
    }

    public ActivityArena getArena() {
        return arena;
    }

    public Player getPlayer() {
        return player;
    }

    public void startActivity() {
        // Save all items in the player's inventory so we can restore it once the activity is over.
        PlayerInventory playerInventory = player.getInventory();
        helmet = playerInventory.getHelmet();
        chestplate = playerInventory.getChestplate();
        leggings = playerInventory.getLeggings();
        boots = playerInventory.getBoots();
        mainHand = playerInventory.getItemInMainHand();
        offHand = playerInventory.getItemInOffHand();
        contents = playerInventory.getContents();

        playerInventory.clear();

        player.setGameMode(GameMode.ADVENTURE);
    }

    public void leaveActivity() {
        PlayerInventory playerInventory = player.getInventory();

        player.teleport(startLocation);

        playerInventory.setHelmet(helmet);
        playerInventory.setChestplate(chestplate);
        playerInventory.setLeggings(leggings);
        playerInventory.setBoots(boots);
        playerInventory.setItemInMainHand(mainHand);
        playerInventory.setItemInOffHand(offHand);
        playerInventory.setContents(contents);

        player.setGameMode(gameMode);
    }
}
