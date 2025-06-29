package me.onimula;

import com.mojang.blaze3d.matrix.MatrixStack;

import me.onimula.gui.gui;
import me.onimula.helpers.common.Lang;
import me.onimula.features.FeatureManager;
import me.onimula.helpers.common.MathUtil;
import me.onimula.helpers.icon.IconFont;
import me.onimula.helpers.simplified.Bind;
import me.onimula.helpers.simplified.TextFont;
import me.onimula.helpers.styled.StyledFont;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod(TemplateMod.MOD_ID)
public class TemplateMod {
	public static gui ui;
	public static final String MOD_ID = "examplemod";
	public static final String FONT_DIR = "/assets/" + TemplateMod.MOD_ID + "/font/";
	private static StyledFont font = new StyledFont("Nunito-Medium.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static StyledFont font1 = new StyledFont("Montserrat Medium.ttf", 26, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font2 = new TextFont("Greycliff.ttf", 30, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static TextFont font3 = new TextFont("Comfortaa.ttf", 35, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
	private static IconFont font4 = new IconFont("Icons.ttf", 60, 'a', 'b', 'c');
	public TemplateMod() {
		ui = new gui();
		FeatureManager.FeatureList();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new Bind());
	}

	public static Color pizdak(Color color1, Color color2, float alpha) {
		float val = MathHelper.clamp((float) Math.sin(19 * ((Math.abs(System.currentTimeMillis() / 25) / 200.4) / 2 % 1)) / 2 + 0.5f, 0, 1);
		return new Color(MathUtil.lerp((float) color1.getRed() / 255, (float) color2.getRed() / 255, val), MathUtil.lerp((float) color1.getGreen() / 255, (float) color2.getGreen() / 255, val), MathUtil.lerp((float) color1.getBlue() / 255, (float) color2.getBlue() / 255, val), alpha / 255);
	}
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			MatrixStack matrices = event.getMatrixStack();
		}
	}

}
