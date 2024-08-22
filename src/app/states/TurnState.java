package app.states;

import app.Game;
import core.GameCanvas;
import core.states.AbstractGameState;

public class TurnState extends AbstractGameState {
    private Game game;

    public TurnState(Game g) {
        this.game = g;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.updateAndDraw(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
