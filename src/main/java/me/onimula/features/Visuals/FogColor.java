package me.onimula.features.Visuals;

import me.onimula.features.Feature;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FogColor extends Feature  {
    public FogColor() {
        super("FogColor", 0, "Меняет цвет неба", Feature.Category.Other, "v");
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.FogColors e) {
        e.setBlue(1);
        e.setGreen(0);
        e.setRed(0);
    }
}