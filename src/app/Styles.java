package app;

import core.behaviors.TextStyle;
import core.util.FontLoader;

import java.awt.*;

public class Styles {
    public static Color setAlpha(Color color, int i) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
    }

    public static Font buttonFont = FontLoader.load("font/TitilliumWeb-SemiBold.ttf").deriveFont(40f);
    public static Color buttonBGColor = new Color(40, 160, 160, 50);
    public static Color buttonBorderColor = new Color(18, 150, 255);
    public static Color buttonTextColor = Color.WHITE;

    public static Font textFont = FontLoader.load("font/TitilliumWeb-ExtraLight.ttf").deriveFont(60f);
}
