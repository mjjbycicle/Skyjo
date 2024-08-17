package core.behaviors;

import core.UIBehavior;
import core.math.Vec2;
import core.input.Input;

/**
 * An abstract Button class.
 */
public class ButtonBehavior extends UIBehavior {

    public ButtonBehavior () {}

    /**
     * @param v The position in screen space of the mouse
     * @return Whether the given position is over the GameObject
     */
    public boolean contains(Vec2 v) {
        if(!gameObject.isEnabled()) return false;

        Vec2 difference = v.minus(gameObject.getAbsolutePosition()).transform(Math::abs);
        Vec2 size = gameObject.size();
        return difference.x * 2 <= size.x && difference.y * 2 <= size.y;
    }

    /**
     * @return Whether the current mouse position is over the GameObject
     */
    public boolean isHovered(){
        return contains(Input.getMousePosition());
    }
}