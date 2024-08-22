package app.objects;

import app.Constants;
import core.GameCanvas;
import core.behaviors.ButtonBehavior;
import core.behaviors.ImageRendererBehavior;
import core.gameobjects.ImageObject;
import core.util.ImageLoader;

public class Card extends ImageObject {
    public final int num;
    public boolean faceDown;

    public Card(int num) {
        super(num + ".png");
        this.num = num;
        this.faceDown = true;
        this.addBehavior(
                new ButtonBehavior()
        );
    }

    public void updateAndDrawActive(GameCanvas canvas, int i, int j, int startX, int startY) {
        this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        this.setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
        this.setPosition(startX + j * Constants.ACTIVE_CARD_WIDTH_WITH_PADDING, startY + i * Constants.ACTIVE_CARD_HEIGHT_WITH_PADDING);
        this.updateAndDraw(canvas);
    }

    public void updateAndDrawInactive(GameCanvas canvas, int i, int j, int startX, int startY) {
        this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        this.setSize(Constants.INACTIVE_CARD_WIDTH, Constants.INACTIVE_CARD_HEIGHT);
        this.setPosition(startX + j * Constants.INACTIVE_CARD_WIDTH_WITH_PADDING, startY + i * Constants.INACTIVE_CARD_HEIGHT_WITH_PADDING);
        this.updateAndDraw(canvas);
    }

    public void updateAndDrawDeck(GameCanvas canvas, int x, int y) {
        this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        this.setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
        this.setPosition(x, y);
        this.updateAndDraw(canvas);
    }

    public void onMouseClick() {
        if (this.findBehavior(ButtonBehavior.class).isHovered()) {
            this.faceDown = !this.faceDown;
        }
    }
}
