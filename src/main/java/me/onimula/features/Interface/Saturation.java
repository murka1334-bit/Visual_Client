package me.onimula.features.Interface;

import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.onimula.features.FeatureManager.mc;

@Mod.EventBusSubscriber
public class Saturation extends Feature {

    public Saturation() {
        super("Saturation", 0, "Отображение насыщенния", Category.Interface, "o");
    }


    @SubscribeEvent
    public void Shkebede(RenderGameOverlayEvent.Post event) {
        if (!this.isToggled()) return;
        if (event.getType() != RenderGameOverlayEvent.ElementType.FOOD) {
            return;
        }

        PlayerEntity player = mc.player;

        if (player != null) {
            int foodLevel = player.getFoodData().getFoodLevel();
            float saturationLevel = player.getFoodData().getSaturationLevel();

            int iconXPos = mc.getWindow().getGuiScaledWidth() / 2 + 80;
            int iconYPos = mc.getWindow().getGuiScaledHeight() - 40;

            RenderSystem.enableBlend();
            mc.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
            for (int i = 0; i < 10; i++) {
                if (i * 2 + 1 < saturationLevel) {
                    mc.gui.blit(event.getMatrixStack(), iconXPos - i * 8, iconYPos - 10, 16, 27, 9, 9);
                } else if (i * 2 + 1 == saturationLevel) {
                    mc.gui.blit(event.getMatrixStack(), iconXPos - i * 8, iconYPos - 10, 25, 27, 9, 9);
                }
            }
            RenderSystem.disableBlend();
        }
    }
}