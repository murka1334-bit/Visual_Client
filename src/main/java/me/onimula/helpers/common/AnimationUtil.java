package me.onimula.helpers.common;

public class AnimationUtil {
    public float anim, to, speed;
    long mc = System.currentTimeMillis();

    public AnimationUtil(float speed) {
        this.speed = speed;
    }

    public float getAnim() {
        int count = (int) ((System.currentTimeMillis() - mc) / 5);
        if (count > 0) {
            mc = System.currentTimeMillis();
        }
        for (int i = 0; i < count; i++) {
            anim = MathUtil.lerp(anim, to, speed);
        }
        return anim;
    }
}