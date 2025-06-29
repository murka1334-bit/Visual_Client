package me.onimula.features;

public class Settings {
    public String name;
    public boolean isVisible = true;

    public Settings(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public String getName() {
        return this.name;
    }
}
