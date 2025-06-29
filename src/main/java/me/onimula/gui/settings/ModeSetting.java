package me.onimula.gui.settings;

import me.onimula.features.Settings;
import net.minecraftforge.client.model.animation.Animation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModeSetting extends Settings {
    private List<String> modes;
    private int index;
    public String mode;
    public Animation animation;
    public String previousMode;
    private int previousIndex;

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.mode = modes.get(index);
    }

    public String getMode() {
        return mode;
    }


    public ModeSetting(String name, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.mode = this.modes.get(0);
        this.index = 0;
        this.previousMode = null;
        this.previousIndex = 0;
    }

    public void cycle() {
        this.previousMode = this.mode;
        this.previousIndex = this.index;
        if (index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
        this.mode = modes.get(index);
    }

    public void setMode(String mode) {
        if (!this.mode.equals(mode)) {
            this.previousMode = this.mode;
            this.previousIndex = this.index;
            this.mode = mode;
            this.index = modes.indexOf(mode);
        }
    }

    public int getPreviousIndex() {
        return previousIndex;
    }

    public boolean isMode(String mode) {
        return Objects.equals(this.mode, mode);
    }

    public String getValue() {
        return this.mode;
    }
}