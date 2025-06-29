package me.onimula.features.Other;

import me.onimula.features.Feature;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoFireOverlay extends Feature {
    public NoFireOverlay() {
        super("NoFireOverlay", 0, "Уберает огонь с экрана", Category.Other, "v");
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    }
}
