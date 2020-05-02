package sam.berkel.rpgSurvival.skills;

import java.util.HashMap;

public class SkillData {
    private HashMap<String, Boolean> tools;
    public HashMap<String, Integer> xpValues;

    public SkillData(HashMap<String, Boolean> tools, HashMap<String, Integer> xpValues) {
        this.tools = tools;
        this.xpValues = xpValues;
    }

    public HashMap<String, Boolean> getTools() {
        return tools;
    }

    public HashMap<String, Integer> getXpValues() {
        return xpValues;
    }
}
