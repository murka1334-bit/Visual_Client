package me.onimula.assistants;

public class Waypoint {
    private String name;
    private double x;
    private double y;
    private double z;

    public Waypoint(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
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

    public double[] getPosition() {
        return new double[]{x, y, z};
    }
}