package app.objects;

import app.Constants;
import core.GameCanvas;
import core.behaviors.ButtonBehavior;
import core.behaviors.ImageRendererBehavior;
import core.gameobjects.ImageObject;
import core.util.ImageLoader;

public class Card extends ImageObject {
    public final int num;
    private boolean faceDown;

    public Card(int num, boolean faceDown) {
        super(num + ".png");
        this.num = num;
        this.faceDown = faceDown;
        this.addBehavior(
                new ButtonBehavior()
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
}
