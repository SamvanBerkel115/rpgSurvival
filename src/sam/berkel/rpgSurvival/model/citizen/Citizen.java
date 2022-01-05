package sam.berkel.rpgSurvival.model.citizen;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;

import java.util.*;

public class Citizen {
    UUID uuid;
    State startState;
    State returnState;
    HashMap<String, State> states;
    HashMap<UUID, State> activeStates;

    public Citizen(UUID uuid, HashMap<String, State> states, State startState, State returnState) {
        this.uuid = uuid;
        this.states = states;
        this.activeStates = new HashMap<>();
        this.startState = startState;
        this.returnState = returnState;
    }

    public State getState(UUID uuid) {
        State state = activeStates.get(uuid);

        if (state == null) return startState;
        else return state;
    }

    public State getStartState() {
        return startState;
    }

    public State getReturnState() {
        return returnState;
    }
    public void setToReturnState(Player player) {
        activeStates.put(player.getUniqueId(), returnState);
    }


    public void setActiveState(Player player, State state) {
        for (int i = 0; i < 20; i++) {
            player.sendMessage("");
        }

        player.sendMessage(ChatColor.GOLD + state.getDialogue());
        player.sendMessage("");

        ArrayList<Response> responses = state.getResponses();
        for (int i = 0; i < responses.size(); i++) {
            Response res = responses.get(i);

            TextComponent resMessage = new TextComponent(ChatColor.GRAY + String.valueOf(i + 1) + ": " + res.getText());

            ClickEvent clickEvt = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/citizen dialogue " + i);
            resMessage.setClickEvent(clickEvt);

            player.spigot().sendMessage(resMessage);
        }

        System.out.println("Setting active state: " + state.getName());

        activeStates.put(player.getUniqueId(), state);
    }
}
