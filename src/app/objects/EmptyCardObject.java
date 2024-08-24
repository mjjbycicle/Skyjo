package app.objects;

import app.Constants;
import core.behaviors.ImageRendererBehavior;
import core.behaviors.RoundedRectRendererBehavior;

import java.awt.*;

public class EmptyCardObject extends Card{
    public EmptyCardObject(int num, boolean faceDown) {
        super(num, faceDown);
        super.findBehavior(ImageRendererBehavior.class).disable();

        this.addBehavior(
                new RoundedRectRendererBehavior(
                        20,
                        Color.WHITE,
                        new Color(0, 0, 0, 20)
                )
        ).setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
    }
}
