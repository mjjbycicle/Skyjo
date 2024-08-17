package core.behaviors;

import core.GameCanvas;
import core.math.Vec2;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RoundedRectRendererBehavior extends RectRendererBehavior {
    // Cache images to improve performance :)
    private static class ImageCache {
        private final Map<Vec2, Map<Long, Map<Double, Image>>> cache = new HashMap<>();

        public Image get(Color background, Color border, double radius, Vec2 size) {

            long colorKey = (long) background.getRGB() << 32 | border.getRGB();

            if(!cache.containsKey(size)) cache.put(size, new HashMap<>());
            var _cache = cache.get(size);
            if(!_cache.containsKey(colorKey)) _cache.put(colorKey, new HashMap<>());
            var __cache = _cache.get(colorKey);
            return __cache.containsKey(radius) ? __cache.get(radius) : generateImage(background, border, radius, size, colorKey);
        }

        private Image generateImage(Color fill_color, Color border_color, double radius, Vec2 size, long colorKey) {
            // Image is 2x size to make borders accurate
            Image image = new BufferedImage((int) (size.x * 2), (int) (size.y * 2), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setStroke(new BasicStroke(2));

            var shape = new RoundRectangle2D.Double(size.x * .5, size.y * .5, size.x, size.y, radius * 2, radius * 2);

            if(fill_color.getAlpha() != 0) {
                graphics.setColor(fill_color);
                graphics.fill(shape);
            }
            if(border_color.getAlpha() != 0) {
                graphics.setColor(border_color);
                graphics.draw(shape);
            }

            cache.get(size).get(colorKey).put(radius, image);
            return image;
        }
    };
    private static final ImageCache cache = new ImageCache();


    protected double radius;
    public RoundedRectRendererBehavior(double radius, Color border_color, Color fill_color) {
        super(border_color, fill_color);
        this.radius = radius;
    }

    @Override
    public void draw(GameCanvas canvas) {
        Vec2 tl = gameObject.getAbsoluteTopLeft();
        Vec2 size = gameObject.size();

        if(size.x <= 1 || size.y <= 1) return;

        var img = cache.get(fill_color, border_color, radius, gameObject.size());
        canvas.drawImage(img, gameObject.getAbsolutePosition().minus(gameObject.size()), gameObject.size().times(2));
    }

    public RoundedRectRendererBehavior setRadius(double radius) {
        this.radius = radius;
        return this;
    }
}