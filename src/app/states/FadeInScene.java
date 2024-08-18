package app.states;

import core.GameCanvas;
import core.states.AbstractGameState;

import static app.Styles.*;
import static java.awt.Color.BLACK;

class FadeInScene extends AbstractGameState {
    private final AbstractGameState s;
    double t = 0;

    public FadeInScene(AbstractGameState s) {
        this.s = s;
    }

    public void onUpdate() {
        t += 0.03;
    }

    public void draw(GameCanvas canvas) {
        s.draw(canvas);
        canvas.setColor(setAlpha(BLACK, (int) (Math.max(1 - t, 0) * 255))).clear();
    }

    public boolean isFinished() {
        return t >= 1;
    }
}
