package me.onimula.features.Visuals;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import me.onimula.helpers.common.TimerUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static me.onimula.features.FeatureManager.mc;
import static org.lwjgl.opengl.GL11C.GL_QUADS;

public class HitBubbles extends Feature {
    private final ResourceLocation skins = new ResourceLocation("examplemod", "skins/circle.png");
    private final TimerUtil timerUtil = new TimerUtil();
    private List<Particle> test = new ArrayList<>();

    public HitBubbles() {
        super("HitBubbles", 0, "Красивый эффект при ударе", Category.Visuals, "r");
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent renderWorldLastEvent) {
        MatrixStack matrixStack = renderWorldLastEvent.getMatrixStack();
        Vector3d cameraPos = mc.getEntityRenderDispatcher().camera.getPosition();

        Iterator<Particle> iterator = test.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (particle.timerUtil.getMc() >= particle.lifeTime) {
                iterator.remove();
                continue;
            }

            matrixStack.pushPose();
            matrixStack.translate(particle.startX - cameraPos.x,
                    particle.startY - cameraPos.y,
                    particle.startZ - cameraPos.z);
            matrixStack.mulPose(mc.gameRenderer.getMainCamera().rotation().copy());

            float life = (float) particle.timerUtil.getMc() / particle.lifeTime;
            float scale = MathHelper.lerp(life, 0.15f * 2, 0.45f * 2); // Увеличенный масштаб в 2 раза
            int alpha = (int) (MathHelper.clamp(1 - life, 0, 1) * 255);

            RenderSystem.pushMatrix();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha / 255.0F);
            mc.getTextureManager().bind(skins);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuilder();
            bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
            Color color = particle.clr;
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
            matrixStack.scale(scale, scale, scale);
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(life * 360));
            bufferBuilder.vertex(matrixStack.last().pose(), 1, 1, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 0).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), 1, -1, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 1).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), -1, -1, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 1).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), -1, 1, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1,  0).endVertex();
            tessellator.end();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.popMatrix();
            matrixStack.popPose();
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
        if (timerUtil.hasReached(200) && attackEntityEvent.getTarget().isAlive()) {
            test.add(new Particle(
                    attackEntityEvent.getTarget().getX(),
                    attackEntityEvent.getTarget().getY() + attackEntityEvent.getTarget().getBbHeight() / 2,
                    attackEntityEvent.getTarget().getZ(),
                    500
            ));
            timerUtil.reset();
        }
    }

    static class Particle {
        double startX, startY, startZ;
        TimerUtil timerUtil = new TimerUtil();
        long lifeTime;
        Color clr = Color.decode("#8187FF");

        public Particle(double startX, double startY, double startZ, long lifeTime) {
            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.lifeTime = lifeTime;
            timerUtil.reset();
        }
    }
}