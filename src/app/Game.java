package app;

import app.objects.Deck;
import app.objects.Player;

import java.util.List;

public class Game {
    private List<Player> players;
    private Deck drawDeck, discardDeck;

    public Game(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
        this.discardDeck = new Deck(false);
    }
}
