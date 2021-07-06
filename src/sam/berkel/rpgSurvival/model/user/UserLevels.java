package sam.berkel.rpgSurvival.model.user;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import sam.berkel.rpgSurvival.Main;
import sam.berkel.rpgSurvival.model.Server;

public class UserLevels {
    private int combatXp;
    private int combatLvl;
    private int craftingXp;
    private int craftingLvl;
    private int excavationXp;
    private int excavationLvl;
    private int farmingXp;
    private int farmingLvl;
    private int fishingXp;
    private int fishingLvl;
    private int magicXp;
    private int magicLvl;
    private int miningXp;
    private int miningLvl;
    private int woodcuttingXp;
    private int woodcuttingLvl;

    public UserLevels(int combatXp, int combatLvl, int craftingXp, int craftingLvl, int excavationXp, int excavationLvl, int farmingXp, int farmingLvl, int fishingXp, int fishingLvl, int magicXp, int magicLvl, int miningXp, int miningLvl, int woodcuttingXp, int woodcuttingLvl) {
        this.combatXp = combatXp;
        this.combatLvl = combatLvl;
        this.craftingXp = craftingXp;
        this.craftingLvl = craftingLvl;
        this.excavationXp = excavationXp;
        this.excavationLvl = excavationLvl;
        this.farmingXp = farmingXp;
        this.farmingLvl = farmingLvl;
        this.fishingXp = fishingXp;
        this.fishingLvl = fishingLvl;
        this.magicXp = magicXp;
        this.magicLvl = magicLvl;
        this.miningXp = miningXp;
        this.miningLvl = miningLvl;
        this.woodcuttingXp = woodcuttingXp;
        this.woodcuttingLvl = woodcuttingLvl;
    }

    public int getCombatXp() {
        return combatXp;
    }

    public int getCombatLvl() {
        return combatLvl;
    }

    public int getCraftingXp() {
        return craftingXp;
    }

    public int getCraftingLvl() {
        return craftingLvl;
    }
    public int getExcavationXp() {
        return excavationXp;
    }

    public int getExcavationLvl() {
        return excavationLvl;
    }

    public int getFarmingXp() {
        return farmingXp;
    }

    public int getFarmingLvl() {
        return farmingLvl;
    }

    public int getFishingXp() {
        return fishingXp;
    }

    public int getFishingLvl() {
        return fishingLvl;
    }

    public int getMagicLvl() {
        return magicLvl;
    }

    public int getMagicXp() {
        return magicXp;
    }

    public int getMiningLvl() {
        return miningLvl;
    }

    public int getMiningXp() {
        return miningXp;
    }

    public int getWoodcuttingXp() {
        return woodcuttingXp;
    }

    public int getWoodcuttingLvl() {
        return woodcuttingLvl;
    }

    public void increaseCombatLevel() {
        combatLvl++;
    }
    public void increaseCraftingLevel() {
        combatLvl++;
    }
    public void increaseExcavationLevel() {
        combatLvl++;
    }

    public void increaseFarmingLevel() {
        combatLvl++;
    }

    public void increaseFishingLevel() {
        combatLvl++;
    }

    public void increaseMagicLevel() {
        combatLvl++;
    }

    public void increaseMiningLevel() {
        combatLvl++;
    }

    public void increaseWoodcuttingLevel() {
        combatLvl++;
    }

    public boolean addXp(int xp, Main.Skill skill) {
        int currentXp;
        int currentLvl;

        switch (skill) {
            case COMBAT:
                combatXp += xp;
                currentXp = craftingXp;
                currentLvl = craftingLvl;
                break;
            case CRAFTING:
                craftingXp += xp;
                currentXp = craftingXp;
                currentLvl = craftingLvl;
                break;
            case EXCAVATION:
                excavationXp += xp;
                currentXp = excavationXp;
                currentLvl = excavationLvl;
                break;
            case FARMING:
                farmingXp += xp;
                currentXp = farmingXp;
                currentLvl = farmingLvl;
                break;
            case FISHING:
                fishingXp += xp;
                currentXp = fishingXp;
                currentLvl = fishingLvl;
                break;
            case MAGIC:
                magicXp += xp;
                currentXp = magicXp;
                currentLvl = magicLvl;
                break;
            case MINING:
                miningXp += xp;
                currentXp = miningXp;
                currentLvl = miningLvl;
                break;
            case WOODCUTTING:
                woodcuttingXp += xp;
                currentXp = woodcuttingXp;
                currentLvl = woodcuttingLvl;
                break;


            default:
                System.out.println("This skill does not exist");
                return false;
        }

        sam.berkel.rpgSurvival.model.Server server = Server.getInstance();
        double base = server.getBase();
        double exponent = server.getExponent();

        int xpForNextLevel = (int) (base * (1 - Math.pow(exponent, currentLvl)) / (1 - exponent));

        if (currentXp >= xpForNextLevel) return true;
        else return false;
    }
}
