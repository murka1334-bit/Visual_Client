package me.onimula.features.Interface;

import me.onimula.features.Feature;
import me.onimula.helpers.util.ColorUtil;
import me.onimula.helpers.util.render.DrawHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber
public class Crosshair extends Feature {

    public Crosshair() {
        super("Crosshair", 0, "Кастомный прицел", Category.Interface, "o");
    }


    private float cooled;
    Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            event.setCanceled(true);
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.getCameraType() == PointOfView.FIRST_PERSON) {
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            float x = width / 2.0F;
            float y = height / 2.0F;

            if (mc.player == null) return;

            float cooledAtt = MathHelper.clamp(mc.player.getAttackStrengthScale(0.5F) * 105.0F, 0.0F, 720.0F);
            this.cooled = MathHelper.lerp(0.1F, this.cooled, cooledAtt);

            int progress = (int) this.cooled;
            int thickness = 3;
            Color color1 = new Color(0, 0, 255);
            Color color2 = new Color(0, 255, 255);
            Color color = new Color(ColorUtil.twoColorEffectTh(color1, color2, 255).getRGB());

            DrawHelper.drawCircleOutline(x, y, 4, thickness, 360, 1, new Color(30,30,30, 125));

            DrawHelper.drawCircleOutline(x, y, 4, thickness, progress, 1, color);

        }
    }
}