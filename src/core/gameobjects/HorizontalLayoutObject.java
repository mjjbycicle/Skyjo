package core.gameobjects;

import core.GameObject;
import core.math.Vec2;

public class HorizontalLayoutObject extends GameObject {
    private Vec2 padding = new Vec2(0);
    private boolean resize = true;

    @Override
    public GameObject update() {
        super.update();

        if(resize) resize();

        double x = 0;
        for (GameObject child : children) {
            child.setTopLeft(new Vec2(x, 0).plus(getTopLeftOffset()).plus(padding));
            x += child.size().x;
        }

        return this;
    }

    public HorizontalLayoutObject setPadding(Vec2 padding){
        this.padding = padding;
        return this;
    }

    public HorizontalLayoutObject setAutoResize(boolean resize) {
        this.resize = resize;
        return this;
    }

    public HorizontalLayoutObject resize(){
        var w = children.stream().map(GameObject::size).mapToDouble(Vec2::x).sum();
        var h = children.stream().map(GameObject::size).mapToDouble(Vec2::y).max().orElse(0);

        setSize(padding.times(2).plus(new Vec2(w, h)));
        double x = 0;
        for (GameObject child : children) {
            child.setTopLeft(new Vec2(x, 0).plus(getTopLeftOffset()).plus(padding));
            x += child.size().x;
        }
        return this;
    }
}
