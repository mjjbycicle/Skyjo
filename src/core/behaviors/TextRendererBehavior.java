package core.behaviors;

import core.UIBehavior;
import core.GameCanvas;
import core.math.Vec2;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class TextRendererBehavior extends UIBehavior {
    protected static final TextStyle DEFAULT_STYLE = new TextStyle();
    protected TextStyle style;
    protected String text;
    protected String[] lines;
    protected double[] lineY;
    protected TextLayout[] lineLayouts;
    private Vec2 textOffset;
    private Vec2 renderedSize;
    private boolean useUnderline = false;

    // Dummy graphics object we use for string size evaluation
    private static final Graphics2D _graphics = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();

    public TextRendererBehavior(String text) {
        this(text, DEFAULT_STYLE);
    }

    public TextRendererBehavior(String text, Font font) {
        this(text, new TextStyle(font));
    }

    public TextRendererBehavior(String text, TextStyle style) {
        this.style = style;
        setText(text);
        recalculate_bounds();
    }

    public Vec2 getRenderedSize() {
        return renderedSize;
    }

    public String getText() {
        return text;
    }

    public TextRendererBehavior setText(String text) {
        if(text.equals(this.text)) return this;
        this.text = text;
        this.lines = text.isEmpty() ? new String[]{} : text.split("\n");
        recalculate_bounds();
        return this;
    }

    public TextRendererBehavior resizeToFit(double padding){
        gameObject.setSize(getRenderedSize().plus(new Vec2(padding * 2)));
        return this;
    }

    public TextRendererBehavior resizeToFitH(double padding) {
        gameObject.setSize(getRenderedSize().x + 2 * padding, gameObject.size().y);
        return this;
    }

    public TextRendererBehavior resizeToFitV(double padding) {
        gameObject.setSize(gameObject.size().x, getRenderedSize().y + 2 * padding);
        return this;
    }

    public Font getFont() {
        return style.font;
    }

    public void setStyle(TextStyle style) {
        this.style = style;
        recalculate_bounds();
    }
    public TextStyle getStyle() {
        return style;
    }

    private void recalculate_bounds() {

        FontRenderContext context = _graphics.getFontRenderContext();

        lineLayouts = Arrays.stream(lines).map(i -> new TextLayout(i, style.font, context)).toArray(TextLayout[]::new);

        double width = 0, height = 0;
        lineY = new double[lines.length];
        for (int i = 0; i < lines.length; i++) {
            TextLayout txt = new TextLayout(lines[i], style.font, context);
            Rectangle2D size = txt.getBounds();

            width = Math.max(width, size.getWidth());
            height += size.getHeight() * (i == 0 ? 1 : 1.2);
            lineY[i] = height;
        }
        renderedSize = new Vec2(width, height);

        textOffset = new Vec2(
            switch (style.getHorizontalAlignment()) {
                case START -> 0;
                case CENTER -> .5;
                case END -> 1;
            },
            switch (style.getVerticalAlignment()) {
                case START -> 0;
                case CENTER -> .5;
                case END -> 1;
            }
        ).times(getRenderedSize());
    }

    @Override
    public void draw(GameCanvas canvas) {
        Vec2 rect_start = gameObject.getAbsoluteTopLeft();
        Vec2 text_start = rect_start.minus(textOffset).plus(new Vec2(
                switch (style.getHorizontalAlignment()) {
                    case START -> 0;
                    case CENTER -> gameObject.size().x() * 0.5;
                    case END -> gameObject.size().x();
                },
                switch (style.getVerticalAlignment()) {
                    case START -> 0;
                    case CENTER -> gameObject.size().y() * 0.5;
                    case END -> gameObject.size().y();
                })
        );

        canvas.setColor(style.fg_color).setDrawFont(style.font);
        for(int i = 0; i < lines.length; i++) {
            lineLayouts[i].draw(canvas.graphics, (float) text_start.x, (float) (text_start.y + lineY[i]));
            if(useUnderline) {
                canvas.drawRect(text_start.plus(new Vec2(0, lineY[i])), new Vec2(getRenderedSize().x, 0));
            }
        }
    }

    public void setUnderlined(boolean underline) {
        this.useUnderline = underline;
    }
}