package app.states;

import app.Game;
import app.objects.Card;
import app.objects.Deck;
import app.objects.Player;
import core.states.AbstractGameState;
import core.states.InstantaneousGameState;

import java.util.Iterator;
import java.util.List;

public class DealCardsState extends InstantaneousGameState {
    private Game game;

    public DealCardsState(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.dealCards();
    }

    @Override
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        return makeIterator(new TurnStartedState(game));
    }
}
