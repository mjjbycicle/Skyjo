package app.states;

import app.Game;
import core.states.AbstractGameState;
import core.states.InstantaneousGameState;

import java.util.Iterator;

public class DealCardsState extends InstantaneousGameState {
    private final Game game;

    public DealCardsState(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.dealCards();
    }

    @Override
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        AbstractGameState theNextState = new TurnStartedState(game);
        return makeIterator(
                new FadeInScene(theNextState),
                theNextState
        );
    }
}
