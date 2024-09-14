package app.objects;

import app.Constants;
import app.Styles;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextRendererBehavior;
import core.behaviors.TextStyle;
import core.gameobjects.TextObject;
import core.math.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private final Matrix mat;
    private final TextObject text;
    private final TextObject activeText;
    private final TextObject scoreText;
    private final String name;
    private final int id;
    private final List<Integer> scores = new ArrayList<>();
    private int round = 0;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.mat = new Matrix();
        this.scores.add(0);
        text = (TextObject) new TextObject(
                name + ": " + scores.get(round),
                Styles.textFont.deriveFont(40f),
                Color.WHITE,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(Constants.ZERO_X + 100 + id * Constants.INACTIVE_MATRIX_WIDTH_WITH_PADDING, Constants.ZERO_Y);
        activeText = (TextObject) new TextObject(
                name + ": " + scores.get(round),
                Styles.textFont,
                Color.WHITE,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(-100, -180);
        scoreText = (TextObject) new TextObject(
                name,
                Styles.textFont.deriveFont(40f),
                Color.WHITE,
                TextStyle.TextAlign.ALIGN_CENTER
        ).setPosition(Constants.ZERO_X + 140 + id * 250, Constants.ZERO_Y + 100);
    }

    public void updateAndDrawScore(GameCanvas canvas, int score) {
        scoreText.findBehavior(TextRendererBehavior.class).enable();
        scoreText.findBehavior(TextRendererBehavior.class).setText(name + ": " + score);
        scoreText.updateAndDraw(canvas);
    }

    public void updateAndDraw(GameCanvas canvas, boolean active) {
        scoreText.findBehavior(TextRendererBehavior.class).disable();
        scores.set(round, mat.getScore());
        text.findBehavior(TextRendererBehavior.class).setText(name + ": " + scores.get(round));
        activeText.findBehavior(TextRendererBehavior.class).setText(name + ": " + scores.get(round));
        if (active) {
            updateActive();
        } else {
            updateInactive();
        }
        text.updateAndDraw(canvas);
        activeText.updateAndDraw(canvas);
        mat.updateAndDraw(canvas, active, id);
    }

    private void updateActive() {
        text.findBehavior(TextRendererBehavior.class).disable();
        activeText.findBehavior(TextRendererBehavior.class).enable();
    }

    private void updateInactive() {
        text.findBehavior(TextRendererBehavior.class).enable();
        activeText.findBehavior(TextRendererBehavior.class).disable();
    }

    public void deal(int[] cards) {
        mat.deal(cards);
    }

    public boolean matrixMouseClicked() {
        return mat.mouseClicked();
    }

    public Card matrixReplaceCard(Card replacement) {
        return mat.replaceCard(replacement);
    }

    public void matrixReplaceCard(Card replacement, Vec2 position) {
        mat.replaceCard(replacement, position);
    }

    public Vec2 getClickedIndex() {
        return mat.getClicked();
    }

    public boolean matrixFlipCard() {
        return mat.flipCard();
    }

    public void advanceRound() {
        round++;
        scores.add(0);
    }

    public boolean roundFinished() {
        return mat.finished();
    }

    public int getID() {
        return id;
    }

    public int getRoundScore() {
        return scores.get(round);
    }
}
