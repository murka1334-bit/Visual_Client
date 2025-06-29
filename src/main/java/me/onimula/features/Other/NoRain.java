package me.onimula.features.Other;

import me.onimula.features.Feature;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.onimula.features.FeatureManager.mc;

public class NoRain extends Feature {
    public NoRain() {
        super("NoRain", 0, "Сегодня без дождика", Category.Other, "v");
    }

    float lastRain;

    @SubscribeEvent
    public void RenderWorldLastEvent(RenderWorldLastEvent e) {
        if (mc.level != null & mc.player != null) return;
        if (isToggled())
        {
            mc.level.setRainLevel(0.0f);
        }
    }
}
//Rain ON = 1.0f
//Rain OFF = 0.0f
