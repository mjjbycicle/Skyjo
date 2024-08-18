package app;

import java.awt.*;

public class Styles {
    public static Color setAlpha(Color color, int i) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), i);
    }
}
