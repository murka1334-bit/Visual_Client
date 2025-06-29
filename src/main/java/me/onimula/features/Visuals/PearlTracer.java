package me.onimula.features.Visuals;

import me.onimula.features.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.onimula.features.FeatureManager.mc;

public class PearlTracer extends Feature  {
    public PearlTracer() {
        super("PearlTracer", 0, "Показывает линию твоего перла", Feature.Category.Other, "v");
    }
    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        for (final Entity entity : mc.level.entitiesForRendering()){
            if (entity instanceof EnderPearlEntity){
                entity.setGlowing(true);
            }
        }

    }
}