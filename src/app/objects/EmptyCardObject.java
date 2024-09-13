package app.objects;

import app.Constants;
import core.behaviors.ImageRendererBehavior;
import core.behaviors.RoundedRectRendererBehavior;

import java.awt.*;

public class EmptyCardObject extends Card{
    public EmptyCardObject(int num, boolean faceDown) {
        super(num, faceDown);
        super.findBehavior(ImageRendererBehavior.class).disable();
        setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
    }
}
