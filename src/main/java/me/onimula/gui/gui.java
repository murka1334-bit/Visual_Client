package me.onimula.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import me.onimula.features.Feature;
import me.onimula.features.FeatureManager;
import me.onimula.helpers.common.Lang;
import me.onimula.helpers.styled.StyledFont;
import me.onimula.helpers.styled.StyledFontRenderer;
import me.onimula.helpers.util.render.DrawHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static me.onimula.features.FeatureManager.mc;

public class gui extends Screen {
    private final int width = 300;
    private final int height = 200;
    private int guiX;
    private int guiY;
    private static StyledFont name23 = new StyledFont("sfb.ttf", 14, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    private static StyledFont name = new StyledFont("sfb.ttf", 17, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    private static StyledFont name3 = new StyledFont("sfb.ttf", 23, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);
    private int zabil = 0;
    private static final int maxfeature = 45;
    private int indexc = 0;
    public gui() {
        super(new StringTextComponent("onimula"));
        this.guiX = (int) ((mc.getWindow().getWidth() - width) / 2.6f);
        this.guiY = (mc.getWindow().getHeight() - height) / 2;
    }// ну это типа типа гуи мая я ещё кротой лол келауыгщиоватгидол ВТГЩмлр ыиыукгвщдрпкуг9тм
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Основной фон с прозрачностью
        DrawHelper.drawRoundedRect(guiX + -1.5f, guiY + 240, 350, height + 100, 14, new Color(41, 41, 41, 150)); // Прозрачный фон
        DrawHelper.drawRoundedRect(guiX + -1.5f, guiY + 240, 100, height + 100, 14, new Color(0, 0, 0, 150)); // Прозрачный фон

        // Заголовок
        StyledFontRenderer.drawString(matrixStack, name3, "Визуалы", guiX + 20, guiY + -30, new Color(203, 195, 206));

        // Категории
        List<Feature.Category> categories = Arrays.asList(Feature.Category.values());
        String currentCategoryName = categories.get(indexc).name();
        int categoryWidth = (int) StyledFontRenderer.getWidth(name, currentCategoryName);
        int selectedCategoryY = guiY + (indexc * 22);

        // Обводка синего цвета для выбранной категории
        DrawHelper.drawRoundedOutline(guiX + 15, selectedCategoryY + 1, categoryWidth + 10, 13, 3, 2, new Color(0, 128, 255));

        // Отображение категорий
        for (int i = 0; i < categories.size(); i++) {
            StyledFontRenderer.drawString(matrixStack, name, categories.get(i).name(), guiX + 20, guiY + (i * 22), new Color(255, 255, 255));
        }

        // Отображение фичей
        int yOffset = 0;
        List<Feature> features = FeatureManager.getfeatures();
        int featureCount = features.size();
        for (int i = zabil; i < Math.min(zabil + maxfeature, featureCount); i++) {
            Feature feature = features.get(i);
            if (feature.getCategory() == categories.get(indexc)) {
                // Обводка синего цвета для фичи
                DrawHelper.drawRoundedOutline(guiX + 112, guiY + yOffset + 44 - 70, width - 75, 27, 5, 2, new Color(0, 128, 255));

                if (feature.isEnable()) {
                    DrawHelper.drawRoundedRect(guiX + 112.5f, guiY + yOffset + 44 - 70, width - 75, 27, 5, new Color(255, 255, 255, 255));
                }
                DrawHelper.drawRoundedRect(guiX + 112.5f, guiY + yOffset + 44 - 70, width - 75, 27, 3, new Color(160, 160, 160, 142)); // Прозрачный фон
                StyledFontRenderer.drawString(matrixStack, name, feature.getName(), guiX + 117, guiY + 22 + yOffset + 15 - 5 - 70, new Color(0, 0, 0));
                // Изменение цвета описания на более яркий
                StyledFontRenderer.drawString(matrixStack, name23, feature.getDesc(), guiX + 117, guiY + 22 + yOffset + 19 - 70, new Color(0, 0, 0, 255)); // Светло-серый
                yOffset += 30;
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int categoryY = guiY;
        for (int i = 0; i < Feature.Category.values().length; i++) {
            if (mouseX >= guiX && mouseX <= guiX + (width / 4) &&
                    mouseY >= categoryY + -60 && mouseY <= categoryY + 5) {
                indexc = i;
                zabil = 0;
                return true;
            }
            categoryY += 30;
        }
        if (mouseX >= guiX + 80 && mouseX <= guiX + 95 &&
                mouseY >= guiY + -30 && mouseY <= guiY - 7 + 3) {
            return true;
        }
        if (mouseX >= guiX + 340 && mouseX <= guiX + 380 &&
                mouseY >= guiY + -7 + 3 + -50 && mouseY <= guiY + -7 + 3 - 25) {
            mc.setScreen(null);
            return true;
        }
        int featureY = guiY;
        List<Feature> features = FeatureManager.getfeatures();
        for (int i = zabil; i < Math.min(zabil + maxfeature, features.size()); i++) {
            Feature feature = features.get(i);
            if (feature.getCategory() == Feature.Category.values()[indexc]) {
                if (mouseX >= guiX + 112.5f && mouseX <= guiX + width - 10 &&
                        mouseY >= featureY + -80 && mouseY <= featureY + -20) {
                    feature.toggle();
                    return true;
                }
                featureY += 30;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            zabil = Math.max(0, zabil - 1);
        } else if (delta < 0) {
            List<Feature> features = FeatureManager.getfeatures();
            if (zabil + maxfeature < features.size()) {
                zabil++;
            }
        }
        return true;
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 87) { // W
            indexc = Math.max(0, indexc - 1);
        } else if (keyCode == 83) { // S
            indexc = Math.min(Feature.Category.values().length - 1, indexc + 1);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}