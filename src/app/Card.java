package app;

import core.GameObject;
import core.behaviors.TextStyle;
import core.gameobjects.ImageObject;
import core.gameobjects.TextObject;
import core.util.FontLoader;

import javax.print.attribute.TextSyntax;
import java.awt.*;

import static java.awt.Color.WHITE;

public class Card extends TextObject {
    public final int num;

    public Card(int num) {
        super(
                num + "",
                FontLoader.load("font/JetBrainsMono-Regular.ttf"),
                switch (num) {
                    case -1, -2 -> new Color(0x70379e);
                    case 0 -> new Color(0x5ad7db);
                    case 1, 2, 3, 4 -> new Color(0x3ef705);
                    case 5, 6, 7, 8 -> new Color(0xfcf635);
                    case 9, 10, 11, 12 -> new Color(0xf73434);
                    default -> WHITE;
                },
                TextStyle.TextAlign.ALIGN_CENTER
        );
        this.num = num;
    }
}
