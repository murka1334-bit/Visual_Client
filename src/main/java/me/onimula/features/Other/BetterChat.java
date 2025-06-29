package me.onimula.features.Other;

import me.onimula.features.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BetterChat extends Feature {
    public BetterChat() {
        super("BetterChat", 0, "Улучшенный чат", Category.Other, "v");
    }

    private String lastMessage = "";
    private int amount = 1;

    @SubscribeEvent
    public void onReceivePacket(ClientChatReceivedEvent event) {
        if (event.getType() == ChatType.CHAT) {
            ITextComponent message = event.getMessage();
            String rawMessage = message.getString();
            NewChatGui chatGui = Minecraft.getInstance().gui.getChat();

            if (lastMessage.equals(rawMessage)) {
                amount++;
                chatGui.clearMessages(false);
                message.getSiblings().add(new StringTextComponent(TextFormatting.GRAY + " [x" + amount + "]"));
            } else {
                amount = 1;
            }

            chatGui.addMessage(message);
            lastMessage = rawMessage;

            event.setCanceled(true);
        }
    }
}