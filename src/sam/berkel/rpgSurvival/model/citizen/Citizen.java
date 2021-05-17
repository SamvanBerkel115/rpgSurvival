package sam.berkel.rpgSurvival.model.citizen;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.Plugin;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.User;

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

    public static Map<UUID, Citizen> getCitizensFromConfig() {
        Plugin plugin = Main.getPlugin(Main.class);
        Map<UUID, Citizen> citizens = new HashMap<>();

        ConfigurationSection citizensSection = plugin.getConfig().getConfigurationSection("Citizens.");
        Set<String> uuids = citizensSection.getKeys(false);

        // Each citizen has a uuid
        for (String uuid : uuids) {
            System.out.println("Adding citizen: " + uuid);
            ConfigurationSection citizenStatesConfig = citizensSection.getConfigurationSection(uuid + ".states");

            HashMap<String, State> states = new HashMap<>();
            Set<String> stateNames = citizenStatesConfig.getKeys(false);

            State firstState = null;
            // Create all states first, since we need them as references in the dialogue.
            for (String stateName : stateNames) {
                System.out.println("Creating state: " + stateName);
                ConfigurationSection stateSection = citizenStatesConfig.getConfigurationSection(stateName);

                String dialogue = stateSection.getString("dialogue");
                State state = new State(stateName, dialogue);
                states.put(stateName, state);
                System.out.println("Created state: " + stateName);

                if (firstState == null) firstState = state;
            }

            // Add all dialogue options to the states
            for (String stateName : stateNames) {
                State state = states.get(stateName);
                System.out.println("Adding responses to state: " + state.getName());

                ConfigurationSection responsesSection = citizenStatesConfig.getConfigurationSection(stateName + ".responses");

                if (responsesSection != null) {
                    Set<String> responses = responsesSection.getKeys(false);

                    for (String responseName : responses) {
                        System.out.println("Creating response: " + responseName);
                        String responseText = responsesSection.getString(responseName + ".text");
                        String responseSateName = responsesSection.getString(responseName + ".state");

                        System.out.println("Text: " + responseText);
                        System.out.println("Statename: " + responseSateName);

                        State responseState = states.get(responseSateName);
                        System.out.println("Found response state");

                        Response response = new Response(responseText, responseState);

                        System.out.println("Created response");

                        state.addResponse(response);
                        System.out.println("Created response: " + responseName);
                    }
                }
            }

            String returnStateName = citizensSection.getString(uuid + ".returnState");
            State returnState = states.get(returnStateName);

            UUID citizenUUID = UUID.fromString(uuid);
            Citizen cit = new Citizen(citizenUUID, states, firstState, returnState);
            citizens.put(citizenUUID, cit);
        }

        return citizens;
    }
}
