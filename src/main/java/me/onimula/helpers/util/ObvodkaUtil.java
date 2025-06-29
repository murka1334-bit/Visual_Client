package me.onimula.helpers.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IngameGui;
import org.lwjgl.opengl.GL11;

public class ObvodkaUtil {


    public static void drawRoundedOutline(MatrixStack m, int x, int y, int width, int height, int radius, int color) {
        drawArcOutline(m, x + radius, y + radius, radius, 90, 180, color);
        drawArcOutline(m, x + width - radius, y + radius, radius, 0, 90, color);
        drawArcOutline(m, x + radius, y + height - radius, radius, 180, 270, color);
        drawArcOutline(m, x + width - radius, y + height - radius, radius, 270, 360, color);

        IngameGui.fill(m, x + radius, y, x + width - radius, y + 1, color);
        IngameGui.fill(m, x + radius, y + height - 1, x + width - radius, y + height, color);
        IngameGui.fill(m, x, y + radius, x + 1, y + height - radius, color);
        IngameGui.fill(m, x + width - 1, y + radius, x + width, y + height - radius, color);
    }

    private static void drawArcOutline(MatrixStack m, float centerX, float centerY, float radius, int startAngle, int endAngle, int color) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); // Установка МЕГА КРУТОГО ЦВЕТА 3D БРАВЛ СТАРС МАЙНКРАФТ РОБЛАКС
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = startAngle; i <= endAngle; i++) {
            double angle = Math.toRadians(i);
            float x = (float) (centerX + radius * Math.cos(angle));
            float y = (float) (centerY + radius * Math.sin(angle));
            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}