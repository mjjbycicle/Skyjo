package app.objects;

import app.Constants;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextRendererBehavior;
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
    private boolean active;

    private enum Actions {
        DISCARD,
        REPLACE
    }

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.mat = new Matrix();
        x = id * Constants.INACTIVE_MATRIX_WIDTH_WITH_PADDING + Constants.INACTIVE_MATRIX_Y;
        text = (TextObject) new TextObject(
                name,
                FontLoader.load("font/JetBrainsMono-Regular.ttf").deriveFont(40f),
                Color.WHITE,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(Constants.ZERO_X + 120 + id * Constants.INACTIVE_MATRIX_WIDTH_WITH_PADDING, Constants.ZERO_Y + 20);
    }

    public void updateAndDraw(GameCanvas canvas) {
        if (active) {
            drawActive(canvas);
        } else {
            drawInactive(canvas);
        }
        text.draw(canvas);
    }

    private void drawActive(GameCanvas canvas) {
        text.findBehavior(TextRendererBehavior.class).disable();
        mat.updateAndDrawActive(canvas);
    }

    private void drawInactive(GameCanvas canvas) {
        text.findBehavior(TextRendererBehavior.class).enable();
        text.updateAndDraw(canvas);
        mat.updateAndDrawInactive(canvas, id);
    }

    public void deal(Card[] cards) {
        mat.deal(cards);
    }

    public void onMouseClick() {
        mat.onMouseClick();
    }

    public void updateActive(boolean b) {
        active = b;
        mat.active = b;
    }
}
