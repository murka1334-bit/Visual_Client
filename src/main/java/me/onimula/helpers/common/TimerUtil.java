package me.onimula.helpers.common;

public class TimerUtil {
    long mc;

    public void reset() {
        this.mc = System.currentTimeMillis();
    }

    public long getMc() {
        return System.currentTimeMillis() - this.mc;
    }

    public boolean hasReached(final long n) {
        return System.currentTimeMillis() - this.mc > n;
    }

    public TimerUtil() {
        this.mc = System.currentTimeMillis();
    }
}