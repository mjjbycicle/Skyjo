package app.states;

import app.Game;
import core.GameCanvas;

public class TurnState extends AbstractBoardState{
    public TurnState(Game g) {
        super(g);
    }

    @Override
    public void draw(GameCanvas canvas) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
