package core.gameobjects;

import core.GameObject;
import core.math.Vec2;

public class SpacingObject extends GameObject {
    public SpacingObject(Vec2 size) {
        this.size = size;
    }
    public SpacingObject(double size) {
        this(new Vec2(size));
    }
}