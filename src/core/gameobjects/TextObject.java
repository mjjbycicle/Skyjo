package core.gameobjects;

import core.GameObject;
import core.behaviors.TextRendererBehavior;
import core.behaviors.TextStyle;

import java.awt.*;

public class TextObject extends GameObject {
    private final TextRendererBehavior textRenderer;

    public TextObject(String text, Font font, Color textColor, int alignment) {
        addBehavior(textRenderer = new TextRendererBehavior(text, new TextStyle(font, textColor, alignment)));
    }

    public TextObject resizeToFitH(double padding) {
        textRenderer.resizeToFitH(padding);
        return this;
    }

    public TextObject resizeToFitV(double padding) {
        textRenderer.resizeToFitV(padding);
        return this;
    }

    public TextObject resizeToFit(int padding) {
        textRenderer.resizeToFit(padding);
        return this;
    }

    public TextObject setUnderlined(boolean underline) {
        textRenderer.setUnderlined(underline);
        return this;
    }

    public TextObject setText(String text) {
        textRenderer.setText(text);
        return this;
    }
}
