package me.onimula.features.Visuals;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.onimula.features.Feature;
import me.onimula.helpers.common.TimerUtil;
import me.onimula.helpers.util.ColorUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static me.onimula.features.FeatureManager.mc;

public class TargetESP extends Feature {
    private final ResourceLocation skins = new ResourceLocation("examplemod", "skins/esp.png");
    private final TimerUtil timerUtil = new TimerUtil();
    private LivingEntity target;
    private final double maxDistance = 10.0;

    public TargetESP() {
        super("TargetESP", 0, "Отображает призраков вокруг цели", Category.Visuals, "r");
    }

    private float rotationAngle = 0.0f;
    private float rotationSpeed = 0.0f;
    private boolean isReversing = false;
    private final float maxRotationSpeed = 1.75f;
    private final float acceleration = 0.0025f;

    @SubscribeEvent
    public void onTick(TickEvent tickEvent) {
        if (mc.player != null && mc.level != null) {
            try {
                if (mc.hitResult != null && mc.hitResult instanceof EntityRayTraceResult) {
                    EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) mc.hitResult;
                    if (entityRayTraceResult.getEntity() instanceof LivingEntity) {
                        LivingEntity newTarget = (LivingEntity) entityRayTraceResult.getEntity();
                        if (target == null || !target.equals(newTarget)) {
                            target = newTarget;
                        }
                    }
                }
            } catch (Exception ignored) {
                target = null;
            }

            if (target != null && (mc.player.distanceTo(target) > maxDistance || target.isDeadOrDying() || target.getHealth() <= 0)) {
                target = null;
            }
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent renderWorldLastEvent) {
        if (target != null && !target.isInvisible() && mc.player.canSee(target) && mc.player.distanceTo(target) <= maxDistance && target.getHealth() > 0 && !target.isDeadOrDying()) {
            MatrixStack matrixStack = renderWorldLastEvent.getMatrixStack();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuilder();
            matrixStack.pushPose();
            Vector3d vector3d = mc.getEntityRenderDispatcher().camera.getPosition();
            matrixStack.translate(
                    target.xOld + (target.getX() - target.xOld) * renderWorldLastEvent.getPartialTicks() - vector3d.x,
                    (target.yOld + (target.getY() - target.yOld) * renderWorldLastEvent.getPartialTicks() - vector3d.y + target.getBbHeight()) - 0.6f,
                    target.zOld + (target.getZ() - target.zOld) * renderWorldLastEvent.getPartialTicks() - vector3d.z
            );
            matrixStack.mulPose(mc.gameRenderer.getMainCamera().rotation());
            matrixStack.scale(-0.15f, -0.15f, -0.15f);

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            mc.getTextureManager().bind(skins);

            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
            Color color1 = new Color(0, 0, 255); // Синий цвет
            Color color2 = new Color(0, 255, 255); // Голубой цвет
            Color color = new Color(ColorUtil.twoColorEffectTh(color1, color2, 255).getRGB());

            updateRotation();

            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(rotationAngle));

            bufferBuilder.vertex(matrixStack.last().pose(), 5, 5, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 0).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), 5, -5, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(0, 1).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), -5, -5, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 1).endVertex();
            bufferBuilder.vertex(matrixStack.last().pose(), -5, 5, 0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).uv(1, 0).endVertex();
            tessellator.end();

            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();

            matrixStack.popPose();
        }
    }

    public void updateRotation() {
        if (!isReversing) {
            rotationSpeed += acceleration;
            if (rotationSpeed > maxRotationSpeed) {
                rotationSpeed = maxRotationSpeed;
                isReversing = true;
            }
        } else {
            rotationSpeed -= acceleration;
            if (rotationSpeed < -maxRotationSpeed) {
                rotationSpeed = -maxRotationSpeed;
                isReversing = false;
            }
        }
        rotationAngle += rotationSpeed;
        rotationAngle = (rotationAngle + 360) % 360;
    }
}