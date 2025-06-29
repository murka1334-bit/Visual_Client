package me.onimula.helpers.common;

public class MathUtil {
    public static float lerp(float start, float end, float t) {
        return start + (end - start) * clamp(t, 0.0f, 1.0f);
    }
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}