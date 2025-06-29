package me.onimula.features;

import me.onimula.TemplateMod;
import me.onimula.features.Helper.AutoRespawn;
import me.onimula.features.Helper.AutoSprint;
import me.onimula.features.Interface.*;
import me.onimula.features.Other.*;
import me.onimula.features.Visuals.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class FeatureManager {
    public static ArrayList<Feature> features = new ArrayList<>();
    public static Minecraft mc = Minecraft.getInstance();

    public static void FeatureList() {

        add(new Particles());
        add(new Snow());
        add(new HitBubbles());
        add(new Watermark());
        add(new ChinaHat());
        add(new NoHurtCam());
        add(new AutoSprint());
        add(new AutoRespawn());
        add(new Crosshair());
        add(new BetterChat());
        add(new PearlTracer());
        add(new FogColor());
        add(new NoFireOverlay());
        add(new Saturation());
        add(new BlockCounter());
        add(new PlayerRender());
        add(new NoRain());
        add(new FullBright());
        add(new TargetESP());
    }

    public static void add(Feature f) {
        features.add(f);
    }

    public static ArrayList<Feature> getfeatures() {
        return features;
    }

    @SubscribeEvent
    public static void keyPressed(int key, int action) {
        if (action == 1) {
            for (Feature f : getfeatures()) {
                if (mc.screen == null) {
                    if (f.getKey() == key) {
                        f.toggle();
                    } else if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                        mc.setScreen(TemplateMod.ui);
                    }
                }
            }
        }
    }
}
