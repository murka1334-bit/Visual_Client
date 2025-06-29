package me.onimula.features.Helper;

import me.onimula.features.Feature;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.onimula.features.FeatureManager.mc;

@Mod.EventBusSubscriber
public class AutoSprint extends Feature {

    public AutoSprint() {
        super("AutoSprint", 0, "Автоматический бег", Category.Helper, "o");
    }


    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (isToggled()) {
            mc.options.keySprint.setDown(true);
        }
    }
}