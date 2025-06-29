package me.onimula.features.Other;

import me.onimula.features.Feature;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.onimula.features.FeatureManager.mc;

public class FullBright  extends Feature {
    public FullBright () {
        super("FullBright ", 0, "" +
                "", Category.Other, "v");
    }

    double old;
    @Override
    public boolean onEnable() {
        mc.player.addEffect(new EffectInstance(Effect.byId(16),999999999));
        return false;
    }
    @Override
    public void onDisable() {
        mc.player.removeEffect(Effect.byId(16));
    }

    @SubscribeEvent
    public void onLeave(LivingEvent.LivingUpdateEvent e){
        this.onDisable();
    }

}
