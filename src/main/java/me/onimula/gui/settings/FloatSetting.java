package me.onimula.gui.settings;

import me.onimula.features.Settings;

public class FloatSetting extends Settings {
    private double min, max, inc;
    public double value;
    double defaultvalue;

    public void setInc(double inc) {
        this.inc = inc;
    }

    public double getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(double defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public FloatSetting(String name, double min, double max, double defaultvalue, double inc) {
        super(name);
        this.max = max;
        this.min = min;
        this.defaultvalue = defaultvalue;
        this.inc = inc;
        this.value = clamp((float) defaultvalue, (float) min, (float) max);
    }

    public double getValue() {
        return (value != 0) ? value : defaultvalue;
    }

    public double getValueFloat() {
        return (float) value;
    }

    public void setValDouble(double in) {
        this.value = in;
    }

    public void setValue(double value) {
        value = Math.round(value / inc) * inc;
        this.value = clamp(value, min, max);
    }

    public static double clamp(double num, double min, double max) {
        return Math.max(min, Math.min(max, num));
    }

    public void reset() {
        setValue(defaultvalue);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getInc() {
        return inc;
    }

    public static float clamp(float num, float min, float max) {
        return num < min ? min : num > max ? max : num;
    }

    public int getValueInt() {
        return (int) value;
    }

    public void inc(boolean isPositive) {
        if (isPositive) setValue(getValue() + getInc());
        else setValue(getValue() - getInc());
    }
}