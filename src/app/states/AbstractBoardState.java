package app.states;

import app.Game;
import core.states.AbstractGameState;

public class AbstractBoardState extends AbstractGameState {
    private Game game;

    public AbstractBoardState(Game g) {
        this.game = g;
    }
}
