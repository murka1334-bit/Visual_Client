package me.onimula.features.Other;

import me.onimula.features.Feature;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.onimula.features.FeatureManager.mc;

public class NoHurtCam extends Feature {
    public NoHurtCam() {
        super("NoHurtCam", 0, "Уберает тряску при ударе", Category.Other, "v");
    }

    @SubscribeEvent
    public void norender(final TickEvent event) {
        if (mc.player != null && mc.level != null) {
            mc.player.hurtDuration = 0;
            mc.player.hurtMarked = false;
            mc.player.hurtDir = 0;
        }
    }
}