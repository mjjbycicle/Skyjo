package core;

import core.math.Vec2;
import core.math.Vec2i;
import core.states.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Image;

/**
 * A double-buffered canvas on which to draw stuff.
 *
 * @noinspection UnusedReturnValue, unused
 */
public class GameCanvas extends JPanel {
    public Graphics2D graphics = null;
    private AbstractGameState currScene = null;

    public final Object synchronizer = new Object();

    private final Vec2i referenceSize;
    private double scale = 1;

    public GameCanvas(Vec2i referenceSize) {
        setBackground(Color.BLACK);
        this.referenceSize = referenceSize;
    }

    /**
     * Repaints the canvas, using the {@link AbstractGameState#draw} method of the {@link AbstractGameState} supplied
     *
     * @param a The function to call to paint the canvas
     */
    public void repaint(AbstractGameState a) {
        currScene = a;
        super.repaint();
    }

    /**
     * Paints the canvas with the given graphics context, should never be called directly
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = (Graphics2D) g.create();

        scale =
           referenceSize == null
               ? 1
           : referenceSize.y < getAspectRatio() * referenceSize.x
                ? 1.0 * getWidth() / referenceSize.x
                : 1.0 * getHeight() / referenceSize.y;

        graphics.transform(new AffineTransform(scale, 0, 0, scale, getWidth()/2., getHeight()/2.));

        if (currScene != null) {
            // Prevent jagged text (slower)
            graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            // Prevent jagged shapes (slower)
            graphics.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Use highest image quality
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            // Use highest render quality
            graphics.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );

            // Use 2 px wide stroke for better legibility
            graphics.setStroke(new BasicStroke(2));

            synchronized(synchronizer) {
                currScene.draw(this);
            }
        }
    }

    public double getScale() {
        return scale;
    }


    public Vec2 getMousePos() {
        return toWorldSpace(getMousePosition());
    }

    public Vec2 toWorldSpace(Point mousePosition){
        return mousePosition == null ? null : new Vec2(mousePosition.x, mousePosition.y).minus(get_size().times(.5)).times(1 / getScale());
    }

    public Vec2 get_size() {
        return new Vec2(getWidth(), getHeight());
    }

    public double getAspectRatio() {
        return get_size().y / get_size().x;
    }

    /**
     * Calls {@link GameObject#updateAndDraw(GameCanvas)}
     * @param object The {@link GameObject} to be drawn
     */
    public void updateAndDraw(GameObject object){
        object.updateAndDraw(this);
    }

    // Canvas functions

    public GameCanvas setColor(int r, int g, int b) {
        return setColor(r / 255., g / 255., b / 255., 1.0);
    }

    public GameCanvas setColor(int r, int g, int b, float a) {
        return setColor(new Color(r, g, b, a * 255));
    }

    public GameCanvas setColor(float r, float g, float b) {
        return setColor(r, g, b, 1.0);
    }

    public GameCanvas setColor(double r, double g, double b, double a) {
        return setColor(new Color((float) r, (float) g, (float) b, (float) a));
    }

    public GameCanvas setColor(Color color) {
        graphics.setColor(color);
        return this;
    }

    public GameCanvas drawImage(Image img, Vec2 position, Vec2 size) {
        return drawImage(img, position.x, position.y, size.x, size.y);
    }

    public GameCanvas drawImage(Image img, Vec2 position, Vec2 size, double rot) {
        return drawImage(img, position.x, position.y, size.x, size.y);
    }

    public GameCanvas drawImage(Image img, double x, double y, double width, double height) {
        // Need this chunk of weird code to achieve sub-pixel accuracy by
        // transforming the graphics context to the right position
        AffineTransform originalTransform = graphics.getTransform();

        graphics.translate(x, y);
        graphics.scale(width/img.getWidth(null), height/img.getHeight(null));

        graphics.drawImage(img, 0, 0, null);

        graphics.setTransform(originalTransform);

        return this;
    }

    public GameCanvas drawRect(Vec2 position, Vec2 size) {
        return drawRect(position, size.x(), size.y());
    }

    public GameCanvas drawRect(Vec2 position, double width, double height) {
        return drawRect(position.x(), position.y(), width, height);
    }

    public GameCanvas drawRect(double x, double y, Vec2 size) {
        return drawRect(x, y, size.x(), size.y());
    }

    public GameCanvas drawRect(double x, double y, double width, double height) {
        graphics.draw(new Rectangle2D.Double(x, y, width, height));
        return this;
    }

    public GameCanvas clear() {
        return fillRect(new Vec2(referenceSize).times(-0.5), new Vec2(referenceSize));
    }

    public GameCanvas fillRect(Vec2 position, Vec2 size) {
        return fillRect(position, size.x(), size.y());
    }

    public GameCanvas fillRect(Vec2 position, double width, double height) {
        return fillRect(position.x(), position.y(), width, height);
    }

    public GameCanvas fillRect(double x, double y, Vec2 size) {
        return fillRect(x, y, size.x(), size.y());
    }

    public GameCanvas fillRect(double x, double y, double width, double height) {
        graphics.fill(new Rectangle2D.Double(x, y, width, height));
        return this;
    }

    public GameCanvas setDrawFont(Font font) {
        graphics.setFont(font);
        return this;
    }

    public GameCanvas drawText(String text, Vec2 position) {
        return drawText(text, position.x(), position.y());
    }

    public GameCanvas drawText(String text, double x, double y) {
        graphics.drawString(text, (float) x, (float) y);
        return this;
    }
}