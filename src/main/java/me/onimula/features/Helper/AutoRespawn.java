package me.onimula.features.Helper;

import me.onimula.features.Feature;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.onimula.features.FeatureManager.mc;

@Mod.EventBusSubscriber
public class AutoRespawn extends Feature {

    public AutoRespawn() {
        super("AutoRespawn", 0, "Автоматически респавнит", Category.Helper, "o");
    }

@SubscribeEvent
public void onTick(TickEvent e) {
    if (isToggled()) {
        assert mc.player != null;
        if (mc.player.isDeadOrDying()) {
            try {
                mc.player.respawn();
            } catch (Exception ex) {}

        }
    }
}
}