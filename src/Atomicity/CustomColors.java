package Atomicity;

import java.awt.Color;

public enum CustomColors {
    COLOR_0(12),
    COLOR_1(34),
    COLOR_2(56),
    COLOR_3(78),
    COLOR_4(100),
    COLOR_5(122),
    COLOR_6(144),
    COLOR_7(166),
    COLOR_8(188),
    COLOR_9(210),
    COLOR_10(232),
    COLOR_11(254),
    COLOR_12(276),
    COLOR_13(298),
    COLOR_14(320),
    COLOR_15(342);

    private final int hue;
    private final float saturation = 1;
    private final float value = 1;

    CustomColors(int hue) {
        this.hue = hue;
    }


    public static CustomColors findClosestColor(Color inputColor) {
        float hue = Color.RGBtoHSB(inputColor.getRed(), inputColor.getGreen(), inputColor.getBlue(), null)[0];
        hue *= 360;


        CustomColors closestColor = null;
        float minDiff = Float.MAX_VALUE;

        for (CustomColors color : CustomColors.values()) {
            float diff = Math.abs(hue - color.hue);
            if (diff < minDiff) {
                minDiff = diff;
                closestColor = color;
            }
        }

        return closestColor;
    }


    public Color getColor() {
        return new Color(Color.HSBtoRGB(hue/360f, saturation, value));
    }
}
