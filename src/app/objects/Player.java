package app.objects;

import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextStyle;
import core.gameobjects.TextObject;
import core.util.FontLoader;

import java.awt.*;

public class Player extends GameObject {
    private Matrix mat;
    private TextObject text;
    private final String name;
    private final int id;
    private int x;
    public boolean active;

    private enum Actions {
        DISCARD,
        REPLACE
    }

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.mat = new Matrix();
        x = id * 240 + 120;
        text = (TextObject) new TextObject(
                name,
                FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
                Color.BLACK,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(x, 400);
    }

    public void updateAndDraw(GameCanvas canvas) {

    }

    public void deal(Card[] cards) {
        mat.deal(cards);
    }
}
