package core.input;

import core.math.Vec2;

public class MouseEvent {
    public final Vec2 position;
    public final MouseButton button;

    public MouseEvent(Vec2 position, MouseButton button) {
        this.position = position;
        this.button = button;
    }

    public enum MouseButton {
        LEFT, RIGHT, MIDDLE, NONE
    }
}