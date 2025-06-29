package me.onimula.features;

import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private final String name;
    private int binding;
    private boolean enable;
    private final Category category;
    private final String desc;
    private final String icon;
    public List<Settings> settingList = new ArrayList<>();

    public void addSetting(Settings c) {
        settingList.add(c);
    }

    public Feature(String name, int binding, String desc, Category category, String icon) {
        this.name = name;
        this.binding = binding;
        this.category = category;
        this.desc = desc;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return binding;
    }

    public String getDesc() {
        return desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setBinding(int binding) {
        this.binding = binding;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isToggled() {
        return isEnable();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public Category getCategory() {
        return category;
    }

    public List<Settings> getSettings() {
        return new ArrayList<>();
    }

    public boolean onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        return false;
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void toggle() {
        setEnable(!enable);
    }

    public enum Category {
        Interface, Visuals, Other, Helper
    }
}