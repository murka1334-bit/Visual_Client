package me.onimula.features.Interface;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.onimula.features.Feature;
import me.onimula.helpers.common.Lang;
import me.onimula.helpers.styled.StyledFont;
import me.onimula.helpers.styled.StyledFontRenderer;
import me.onimula.helpers.util.render.DrawHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

import static me.onimula.features.FeatureManager.mc;

@Mod.EventBusSubscriber
public class Watermark extends Feature {
    private static StyledFont sww = new StyledFont("sf-pro-display-medium.otf", 20, 0.0f, 1.0f, 0.5f, Lang.ENG_RU);
    private static StyledFont sww2 = new StyledFont("sf-pro-display-semibold.otf", 18, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    private static StyledFont sm = new StyledFont("sf-pro-display-regular.otf", 16, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    private static StyledFont icon = new StyledFont("fullin.ttf", 18, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    int x = 5;
    int y = 10;

    public Watermark() {
        super("Watermark", 0, "Ватермарка", Category.Interface, "o");
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            MatrixStack matrices = event.getMatrixStack();

            float heavenVisualWidth = StyledFontRenderer.getWidth(sww, "Pisun" + " Visual");

            String ipText = "UID:" + mc.player.getServer();
            float ipTextWidth = StyledFontRenderer.getWidth(sww, ipText);

            float totalWidth = heavenVisualWidth + ipTextWidth + 35;

            DrawHelper.drawRoundedRect(x + 2, y + 15, totalWidth, 14, 3, Color.decode("#191A28"));

            StyledFontRenderer.drawString(matrices, icon, "m", x + 5, y + 13, Color.decode("#8187FF"));

            StyledFontRenderer.drawString(matrices, sww, "Clowed Visual", x + 19, y + 14.5f, new Color(0xFFFFFFFF, true));

            StyledFontRenderer.drawString(matrices, sww, "|", x + 13, y + 14, new Color(0x33FFFFFF, true));

            StyledFontRenderer.drawString(matrices, icon, "i", x + 19 + heavenVisualWidth + 5, y + 13.5f, Color.decode("#8187FF"));

            StyledFontRenderer.drawString(matrices, sww, ipText, x + 19 + heavenVisualWidth + 15, y + 14.5f, new Color(0xFFFFFFFF, true));
        }
    }

    @SubscribeEvent
    public void onMouseDrag(GuiScreenEvent.MouseDragEvent e) {
        if (mc.screen instanceof ChatScreen) {
            assert mc.player != null;
            if (e.getMouseX() > x + -50 && e.getMouseX() < x + 165 && e.getMouseY() > y + -50 && e.getMouseY() < y + 50) {
                x = (int) e.getMouseX() - 140 / 2;
                y = (int) e.getMouseY() - 10;
            }
        }
    }
}