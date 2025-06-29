package me.onimula.features.Interface;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import me.onimula.helpers.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber
public class ChinaHat extends Feature {

    public ChinaHat() {
        super("ChinaHat", 0, "Китайская шляпка, почти как кепочка, но нет", Category.Interface, "o");
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        renderChinaHat(event.getMatrixStack());
    }

    private void renderChinaHat(MatrixStack matrixStack) {
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null && !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            final float partialTicks = Minecraft.getInstance().getFrameTime();
            final double x = player.xOld + (player.position().x - player.xOld) * partialTicks - Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().x;
            final double y = player.yOld + (player.position().y - player.yOld) * partialTicks - Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().y;
            final double z = player.zOld + (player.position().z - player.zOld) * partialTicks - Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition().z;
            final float[] color1 = ColorUtil.getColorComponents(new Color(123123).getRGB());
            final float[] color2 = ColorUtil.getColorComponents(new Color(323123).getRGB());

            matrixStack.pushPose();
            matrixStack.translate(x, y, z);

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-player.yRot));

            // Настройки рендеринга
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableTexture();

            final float playerHeight = player.getBbHeight() + 0.08f;
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder bufferBuilder = tessellator.getBuilder();

            bufferBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
            for (int i = 0; i <= 360; ++i) {
                final double cos = Math.cos(Math.toRadians(i)) * 0.5;
                final double sin = Math.sin(Math.toRadians(i)) * 0.5;
                bufferBuilder.vertex(matrixStack.last().pose(), 0.0f, playerHeight + 0.2f, 0.0f).color(color1[0], color1[1], color1[2], 0.5f).endVertex();
                bufferBuilder.vertex(matrixStack.last().pose(), (float) cos, playerHeight, (float) sin).color(color1[0], color1[1], color1[2], 0.5f).endVertex();
            }
            tessellator.end();

            bufferBuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
            for (int i = 0; i <= 360; ++i) {
                final double cos = Math.cos(Math.toRadians(i)) * 0.5;
                final double sin = Math.sin(Math.toRadians(i)) * 0.5;
                bufferBuilder.vertex(matrixStack.last().pose(), (float) cos, playerHeight, (float) sin).color(color2[0], color2[1], color2[2], 0.5f).endVertex();
            }
            RenderSystem.lineWidth(3.0f);
            tessellator.end();

            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);

            matrixStack.popPose();
        }
    }
}