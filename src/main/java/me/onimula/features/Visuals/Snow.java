package me.onimula.features.Visuals;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import me.onimula.helpers.common.TimerUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static me.onimula.features.FeatureManager.mc;

public class Snow extends Feature {
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final ResourceLocation snowTexture = new ResourceLocation("examplemod", "skins/heart.png");
    private final float particleSize = (0.3f * 1.5f) / 1.75f; // Уменьшение размера в 1.75 раза относительно текущего
    private final Random random = new Random();
    private final long spawnInterval = 100;
    private long lastSpawnTime = 0;

    public Snow() {
        super("Snow", 0, "Красивый снег в виде партиклов [FPS]", Category.Visuals, "r");
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent renderWorldLastEvent) {
        particles.removeIf(particle -> particle.timerUtil.hasReached(particle.lifetime) || particle.isDead);
        particles.forEach(particle -> {
            particle.startY -= 0.02;
            if (particle.startY < -5 || isCollidingWithBlock(particle)) {
                particle.isDead = true;
            }
        });

        MatrixStack ms = renderWorldLastEvent.getMatrixStack();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        particles.forEach(particle -> {
            Vector3d cameraPos = mc.getEntityRenderDispatcher().camera.getPosition();
            mc.getTextureManager().bind(snowTexture);

            ms.pushPose();
            ms.translate(particle.startX - cameraPos.x,
                    particle.startY - cameraPos.y,
                    particle.startZ - cameraPos.z);
            ms.mulPose(mc.gameRenderer.getMainCamera().rotation().copy());
            ms.scale(particleSize, particleSize, particleSize);

            RenderSystem.pushMatrix();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
            Color color = particle.color;

            float life = (float) particle.timerUtil.getMc() / particle.lifetime;
            int alpha = (int) (MathHelper.clamp(1 - life, 0, 1) * 255);
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);

            ms.mulPose(Vector3f.ZP.rotationDegrees(life * 360));
            bufferBuilder.vertex(ms.last().pose(), 2, 2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 0).endVertex();
            bufferBuilder.vertex(ms.last().pose(), 2, -2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 1).endVertex();
            bufferBuilder.vertex(ms.last().pose(), -2, -2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 1).endVertex();
            bufferBuilder.vertex(ms.last().pose(), -2, 2, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 0).endVertex();
            tessellator.end();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.popMatrix();
            ms.popPose();
        });

        if (System.currentTimeMillis() - lastSpawnTime > spawnInterval) {
            spawnParticles();
            lastSpawnTime = System.currentTimeMillis();
        }
    }

    private void spawnParticles() {
        PlayerEntity player = mc.player;
        if (player != null) {
            int particleCount = 12 / 2;
            for (int i = 0; i < particleCount; i++) {
                double offsetX = (random.nextDouble() - 0.5) * 100;
                double offsetZ = (random.nextDouble() - 0.5) * 100;
                double spawnX = player.getX() + offsetX;
                double spawnZ = player.getZ() + offsetZ;
                double spawnY = player.getY() + 10;

                particles.add(new Particle(spawnX, spawnY, spawnZ, 5000));
            }
        }
    }

    private boolean isCollidingWithBlock(Particle particle) {
        World world = mc.level;
        if (world != null) {
            BlockPos pos = new BlockPos(particle.startX, particle.startY, particle.startZ);
            return !world.getBlockState(pos).isAir();
        }
        return false;
    }

    static class Particle {
        double startX, startY, startZ;
        TimerUtil timerUtil = new TimerUtil();
        long lifetime;
        Color color = Color.decode("#8187FF");
        boolean isDead = false;

        public Particle(double startX, double startY, double startZ, long lifetime) {
            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.lifetime = lifetime;
            timerUtil.reset();
        }
    }
}