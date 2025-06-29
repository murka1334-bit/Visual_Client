package me.onimula.gui.settings;

import me.onimula.features.Settings;

public class BooleanSetting extends Settings {
    public boolean value;

    public BooleanSetting(String name, boolean defaultVal) {
        super(name);
        this.value = defaultVal;
    }

    public void toggle() {
        this.value = !this.value;
    }

    public boolean isEnabled() {
        return value;
    }

    public void setEnabled() {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }
}