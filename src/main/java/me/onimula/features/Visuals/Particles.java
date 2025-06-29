package me.onimula.features.Visuals;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import me.onimula.helpers.common.AnimationUtil;
import me.onimula.helpers.common.TimerUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static me.onimula.features.FeatureManager.mc;
import static org.lwjgl.opengl.GL11C.GL_QUADS;

public class Particles extends Feature {
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final ResourceLocation image = new ResourceLocation("examplemod", "skins/star.png");
    private final TimerUtil timerUtil = new TimerUtil();
    private final float sizwe = 0.1f;
    private final Random random = new Random();

    public Particles() {
        super("HitParticles", 0, "Красивый эффект при ударе", Category.Visuals, "v");
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent renderWorldLastEvent) {
        particles.removeIf(particle -> particle.timerUtil.hasReached(particle.lifeTime));
        MatrixStack ms = renderWorldLastEvent.getMatrixStack();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bb = tessellator.getBuilder();
        particles.forEach(particle -> {
            particle.animationX.to = (float) particle.endX;
            particle.animationY.to = (float) particle.endY;
            particle.animationZ.to = (float) particle.endZ;

            Vector3d cmrpos = mc.getEntityRenderDispatcher().camera.getPosition();

            mc.getTextureManager().bind(image);

            ms.pushPose();
            ms.translate((particle.startX + particle.animationX.getAnim()) - cmrpos.x,
                    (particle.startY + particle.animationY.getAnim() - cmrpos.y),
                    (particle.startZ + particle.animationZ.getAnim()) - cmrpos.z);
            ms.mulPose(mc.gameRenderer.getMainCamera().rotation().copy());
            ms.scale(sizwe, sizwe, sizwe);

            RenderSystem.pushMatrix();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            bb.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
            Color color = particle.clr;
            float life = (float) particle.timerUtil.getMc() / particle.lifeTime;
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (MathHelper.clamp(1 - life, 0, 1) * 255));
            bb.vertex(ms.last().pose(), 2, 2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 0).endVertex();
            bb.vertex(ms.last().pose(), 2, -2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 1).endVertex();
            bb.vertex(ms.last().pose(), -2, -2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 1).endVertex();
            bb.vertex(ms.last().pose(), -2, 2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 0).endVertex();
            tessellator.end();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.popMatrix();
            ms.popPose();
        });
    }

    @SubscribeEvent
    public void yaNEsosal(AttackEntityEvent attackEntityEvent) {
        if (timerUtil.hasReached(200) && attackEntityEvent.getTarget().isAlive()) {
            // Увеличиваем количество частиц в 1.5–2 раза (с 5 до 8–10)
            int particleCount = 8 + random.nextInt(3); // От 8 до 10 частиц
            for (int i = 0; i < particleCount; i++) {
                float r0 = random.nextInt(5) + random.nextFloat();
                float r1 = random.nextInt(5) + random.nextFloat();
                float verticalMovement = random.nextBoolean() ? random.nextInt(5) + random.nextFloat() : -(random.nextInt(5) + random.nextFloat());
                particles.add(new Particle(attackEntityEvent.getTarget().getX(),
                        attackEntityEvent.getTarget().getY() + attackEntityEvent.getTarget().getBbHeight() / 2,
                        attackEntityEvent.getTarget().getZ(),
                        random.nextBoolean() ? r0 : -r0,
                        verticalMovement,
                        random.nextBoolean() ? r1 : -r1,
                        random.nextInt(5000)));
                timerUtil.reset();
            }
        }
    }

    static class Particle {
        double startX, startY, startZ, endX, endY, endZ;
        TimerUtil timerUtil = new TimerUtil();
        long lifeTime;
        Color clr = Color.decode("#8187FF");
        AnimationUtil animationX = new AnimationUtil(0.003f);
        AnimationUtil animationY = new AnimationUtil(0.003f);
        AnimationUtil animationZ = new AnimationUtil(0.003f);

        public Particle(double startX, double startY, double startZ, double endX, double endY, double endZ, long lifeTime) {
            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.endX = endX;
            this.endY = endY;
            this.endZ = endZ;
            this.lifeTime = lifeTime;
        }
    }
}