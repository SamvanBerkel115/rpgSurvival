package sam.berkel.rpgSurvival.model;


import org.bukkit.util.Vector;

public class Frame {
    double x;
    double y;
    double z;
    float yaw;
    float pitch;
    int tick;

    public Frame(double x, double y, double z, float yaw, float pitch, int tick) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.tick = tick;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public int getTick() {
        return tick;
    }
}
