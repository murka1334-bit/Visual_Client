package me.onimula.features.Interface;

import me.onimula.features.Feature;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.onimula.features.FeatureManager.mc;
import static net.minecraft.client.gui.screen.inventory.InventoryScreen.renderEntityInInventory;

@Mod.EventBusSubscriber
public class PlayerRender extends Feature {

    public PlayerRender() {
        super("PlayerRender", 0, "Красивый рендер игрока (с инва)", Category.Interface, "o");
    }


    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderEntityInInventory(35, 200, 50, 55, 55, mc.player);
        }
    }
}