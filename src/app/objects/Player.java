package app.objects;

import app.Constants;
import app.Styles;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.TextRendererBehavior;
import core.behaviors.TextStyle;
import core.gameobjects.TextObject;
import core.math.Vec2;
import core.util.FontLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private Matrix mat;
    private Matrix clickMatrix;
    private TextObject text;
    private TextObject activeText;
    private TextObject scoreText;
    private final String name;
    private final int id;
    private int x;
    private boolean active;
    private List<Integer> scores = new ArrayList<>();
    private int round = 0;

    private enum Actions {
        DISCARD,
        REPLACE
    }

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.mat = new Matrix();
        this.clickMatrix = new Matrix();
        this.scores.add(0);
        x = id * Constants.INACTIVE_MATRIX_WIDTH_WITH_PADDING + Constants.INACTIVE_MATRIX_Y;
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
        clickMatrix.dealEmpty();
    }

    public void updateAndDrawScore(GameCanvas canvas, int score) {
        scoreText.findBehavior(TextRendererBehavior.class).enable();
        scoreText.findBehavior(TextRendererBehavior.class).setText(name + ": " + score);
        scoreText.updateAndDraw(canvas);
    }

    public void updateAndDraw(GameCanvas canvas) {
        scoreText.findBehavior(TextRendererBehavior.class).disable();
        scores.set(round, mat.getScore());
        text.findBehavior(TextRendererBehavior.class).setText(name + ": " + scores.get(round));
        activeText.findBehavior(TextRendererBehavior.class).setText(name + ": " + scores.get(round));
        if (active) {
            drawActive(canvas);
        } else {
            drawInactive(canvas);
        }
        text.updateAndDraw(canvas);
        activeText.updateAndDraw(canvas);
    }

    private void drawActive(GameCanvas canvas) {
        text.findBehavior(TextRendererBehavior.class).disable();
        activeText.findBehavior(TextRendererBehavior.class).enable();
        mat.updateAndDrawActive(canvas);
        clickMatrix.updateAndDrawActive(canvas);
    }

    private void drawInactive(GameCanvas canvas) {
        text.findBehavior(TextRendererBehavior.class).enable();
        activeText.findBehavior(TextRendererBehavior.class).disable();
        text.updateAndDraw(canvas);
        mat.updateAndDrawInactive(canvas, id);
        clickMatrix.updateAndDrawActive(canvas);
    }

    public void deal(Card[] cards) {
        mat.deal(cards);
    }

    public boolean matrixMouseClicked() {
        return clickMatrix.mouseClicked();
    }

    public Card matrixReplaceCard(Card replacement) {
        return mat.replaceCard(replacement);
    }

    public void matrixReplaceCard(Card replacement, Vec2 position) {
        mat.replaceCard(replacement, position);
    }

    public Vec2 getClickedIndex() {
        return clickMatrix.getClicked();
    }

    public boolean matrixFlipCard() {
        return mat.flipCard();
    }

    public void updateActive(boolean b) {
        active = b;
        mat.active = b;
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
