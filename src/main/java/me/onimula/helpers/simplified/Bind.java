package me.onimula.helpers.simplified;

import me.onimula.features.FeatureManager;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.onimula.features.FeatureManager.mc;

public class Bind {
    //shitcode
    @SubscribeEvent
    public void bind(InputEvent.KeyInputEvent e) {
        if (mc.player != null && mc.level != null && mc.screen == null) {
            FeatureManager.keyPressed(e.getKey(), e.getAction());
        }
    }
}