package sam.berkel.rpgSurvival.model.citizen;

import java.util.ArrayList;

public class State {
    private String name;
    private String dialogue;
    private ArrayList<Response> responses;

    public State(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
        this.responses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public  ArrayList<Response> getResponses() {
        return responses;
    }

    public void addResponse(Response response) {
        System.out.println("reponse size: " + responses.size());
        responses.add(response);
    }
}
