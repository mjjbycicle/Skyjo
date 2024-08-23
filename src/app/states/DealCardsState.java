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
    private List<Player> players;
    private Deck drawDeck;

    public DealCardsState(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
    }


    @Override
    public void execute() {
        for (Player player : players) {
            Card[] cards = new Card[12];
            for (int i = 0; i < 12; i++) {
                cards[i] = drawDeck.drawCard();
            }
            player.deal(cards);
        }
    }

    @Override
    public Iterator<? extends AbstractGameState> getStatesAfter() {
        return makeIterator(new TurnStartedState(new Game(players, drawDeck)));
    }
}
