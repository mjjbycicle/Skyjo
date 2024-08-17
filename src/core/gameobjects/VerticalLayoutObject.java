package core.gameobjects;

import core.GameObject;
import core.math.Vec2;

public class VerticalLayoutObject extends GameObject {
    private Vec2 padding = new Vec2(0);

    @Override
    public final GameObject update() {
        super.update();

        var w = children.stream().map(GameObject::size).mapToDouble(Vec2::x).max().orElse(0);
        var h = children.stream().map(GameObject::size).mapToDouble(Vec2::y).sum();

        setSize(padding.times(2).plus(w, h));

        double y = 0;
        for (GameObject child : children) {
            child.setTopLeft(getTopLeftOffset().plus(padding).plus(0, y));
            y += child.size().y;
        }
        return this;
    }

    public VerticalLayoutObject setPadding(Vec2 padding){
        this.padding = padding;
        return this;
    }
}
