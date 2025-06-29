package me.onimula.assistants;

public class HoverUtil {

    public static boolean Hovering(float mouseX, float mouseY, float x, float y, int width, int height) {
        return mouseX > x && mouseY > y && mouseX < width && mouseY < height;
    }
}

