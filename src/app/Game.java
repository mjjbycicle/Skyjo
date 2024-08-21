package app;

import app.objects.Deck;
import app.objects.Player;

import java.util.List;

public class Game {
    private List<Player> players;
    private Deck drawDeck, discardDeck;
    private int activePlayerIndex = 0;

    public Game(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
        this.discardDeck = new Deck(false);
    }
}
