package app.objects;

import app.Constants;
import core.GameCanvas;
import core.behaviors.ButtonBehavior;
import core.behaviors.ImageRendererBehavior;
import core.behaviors.PositionAnimationBehavior;
import core.gameobjects.ImageObject;
import core.math.Vec2;
import core.util.ImageLoader;

public class Card extends ImageObject {
    public final int num;
    private boolean faceDown;

    public Card(int num, boolean faceDown) {
        super(num + ".png");
        this.num = num;
        this.faceDown = faceDown;
        this.addBehaviors(
                new ButtonBehavior(),
                new PositionAnimationBehavior()
        );
        if (!faceDown) this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        else this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get("back.png"));
    }

    public void updateAndDrawActive(GameCanvas canvas, int i, int j, int startX, int startY) {
        this.setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
        this.setPosition(startX + j * Constants.ACTIVE_CARD_WIDTH_WITH_PADDING, startY + i * Constants.ACTIVE_CARD_HEIGHT_WITH_PADDING);
        this.updateAndDraw(canvas);
    }

    public void updateAndDrawInactive(GameCanvas canvas, int i, int j, int startX, int startY) {
        this.setSize(Constants.INACTIVE_CARD_WIDTH, Constants.INACTIVE_CARD_HEIGHT);
        this.setPosition(startX + j * Constants.INACTIVE_CARD_WIDTH_WITH_PADDING, startY + i * Constants.INACTIVE_CARD_HEIGHT_WITH_PADDING);
        this.updateAndDraw(canvas);
    }

    public void updateAndDrawDeck(GameCanvas canvas, int x, int y) {
        this.findBehavior(PositionAnimationBehavior.class).update();
        this.setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
        this.setPosition(x, y);
        this.updateAndDraw(canvas);
    }

    public boolean clicked() {
        return this.findBehavior(ButtonBehavior.class).isHovered();
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
        if (!faceDown) this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        else this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get("back.png"));
    }

    public int getNum() {
        return num;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void moveTo(int newX, int newY) {
        this.findBehavior(PositionAnimationBehavior.class).moveTo(new Vec2(newX, newY), 30);
    }

    public void moveTo(Vec2 newPosition) {
        this.findBehavior(PositionAnimationBehavior.class).moveTo(newPosition, 30);
    }

    public Vec2 getPositionVec2() {
        return new Vec2(this.getPosition().x, this.getPosition().y);
    }

    public boolean finishedMoving() {
        return this.findBehavior(PositionAnimationBehavior.class).finishedMoving();
    }
}
