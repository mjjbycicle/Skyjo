package core.input;

import core.math.Vec2;

import java.util.Set;
import java.util.TreeSet;

/**
 * @noinspection unused
 */
public class Input {
    public static final Set<Character> __keysDown = new TreeSet<>();
    public static Vec2 __mousePosition = Vec2.zero;
    public static boolean __mouseDownR = false, __mouseDownL = false;

    public static Vec2 getMousePosition() {
        return __mousePosition;
    }

    public static boolean isMouseLeftDown() {
        return __mouseDownL;
    }

    public static boolean isMouseRightDown() {
        return __mouseDownR;
    }

    public static boolean isKeyDown(char key) {
        return __keysDown.contains(key);
    }

    public static double getTimeInSeconds(){
        return System.currentTimeMillis() / 1000.;
    }

    public static void __setMousePosition(Vec2 v) {
        if(v != null) __mousePosition = v;
    }
}