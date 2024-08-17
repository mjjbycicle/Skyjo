package core.behaviors;

import core.UIBehavior;
import core.GameCanvas;

import java.awt.*;

/**
 * @noinspection UnusedReturnValue, unused
 */
@SuppressWarnings("unused")
public class RectRendererBehavior extends UIBehavior {
    protected final static Color DEFAULT_BORDER_COLOR = Color.BLACK;
    protected final static Color DEFAULT_FILL_COLOR = new Color(0, 0, 0, 0);
    protected Color border_color;
    protected Color fill_color;
    protected boolean dashed = false;
    protected static BasicStroke defaultStroke = new BasicStroke(2);
    protected static BasicStroke dashedStroke = new BasicStroke(
        2,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_ROUND,
        0.0f,
        new float[]{2f, 0f, 2f},
        2f
    );

    public RectRendererBehavior() {
        this(DEFAULT_BORDER_COLOR);
    }

    public RectRendererBehavior(Color border_color) {
        this(border_color, DEFAULT_FILL_COLOR);
    }

    public RectRendererBehavior(Color border_color, Color fill_color) {
        this.border_color = border_color;
        this.fill_color = fill_color;
    }

    @Override
    public void draw(GameCanvas canvas) {
        canvas
                .setColor(fill_color)
                .fillRect(
                        gameObject.getAbsoluteTopLeft(),
                        gameObject.size()
                )
                .setColor(border_color)
                .drawRect(
                        gameObject.getAbsoluteTopLeft(),
                        gameObject.size()
                );
    }

    public RectRendererBehavior setBorderColor(Color color) {
        border_color = color;
        return this;
    }

    public RectRendererBehavior setFillColor(Color color) {
        fill_color = color;
        return this;
    }

    public RectRendererBehavior setDashed(boolean dashed) {
        this.dashed = dashed;
        return this;
    }
}