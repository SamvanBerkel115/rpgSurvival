package sam.berkel.rpgSurvival.model.citizen;

public class Response {
    private String text;
    private State state;

    public Response(String text, State state) {
        this.text = text;
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public State getState() {
        return state;
    }
}
