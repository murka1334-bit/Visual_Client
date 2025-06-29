package me.onimula.features.Interface;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.onimula.features.Feature;
import me.onimula.helpers.common.Lang;
import me.onimula.helpers.styled.StyledFont;
import me.onimula.helpers.styled.StyledFontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

import static me.onimula.features.FeatureManager.mc;

@Mod.EventBusSubscriber
public class BlockCounter extends Feature {
    private static StyledFont sww = new StyledFont("sf_bold.ttf", 24, 0.0f, 2.0f, 0.5f, Lang.ENG_RU);

    public BlockCounter() {
        super("ExpCount", 0, "Показывает количество опыта", Category.Interface, "o");
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        PlayerEntity player = mc.player;
        if (player != null) {
            ItemStack itemponda = player.getMainHandItem();
            if (itemponda.getItem() == Items.EXPERIENCE_BOTTLE) {
                int count = itemponda.getCount();
                String text = "Кол-во: " + count;
                тестоднойзалупытипоес(event.getMatrixStack(), text); //shit code$$$
            }
        }
    }

    private void тестоднойзалупытипоес(MatrixStack matrixStack, String text) {
        StyledFontRenderer.drawCenteredXYString(matrixStack,sww,text,0,15,new Color(255, 255, 255));
    }
}