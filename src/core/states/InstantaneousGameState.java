package core.states;

import core.GameCanvas;
import core.input.MouseEvent;

import java.awt.event.KeyEvent;

/**
 * Extend this class when your intention is to make an instant scene executing a single action as its body
 */
public abstract class InstantaneousGameState extends AbstractGameState {
    @Override
    public final void onUpdate() {}
    @Override
    public final void draw(GameCanvas canvas){}
    @Override
    public final void onKeyPress(KeyEvent ke) {}
    @Override
    public final void onMouseClick(MouseEvent me) {}
    @Override
    public final void onScroll(int distance) {}
    @Override
    public final void onExecutionEnd() {}
    @Override
    public final void onExecutionStart() {
        execute();
    }
    @Override
    public final boolean isFinished() {
        return true;
    }
    public abstract void execute();
}