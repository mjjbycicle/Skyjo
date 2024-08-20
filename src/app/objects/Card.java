package app.objects;

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

    public void updateAndDraw(GameCanvas canvas, int height, int width, int x, int y) {
        this.findBehavior(ImageRendererBehavior.class).setImage(ImageLoader.get(num + ".png"));
        this.setSize(width, height);
        this.setPosition(x, y);
        this.updateAndDraw(canvas);
    }

    public void onMouseClick() {
        if (this.findBehavior(ButtonBehavior.class).isHovered()) {
            this.faceDown = !this.faceDown;
        }
    }
}
