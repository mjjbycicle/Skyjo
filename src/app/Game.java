package app;

import app.objects.Deck;
import app.objects.Player;
import core.GameCanvas;
import core.GameObject;

import java.util.List;

public class Game extends GameObject {
    private List<Player> players;
    private Deck drawDeck, discardDeck;
    private int activePlayerIndex = 0;

    public Game(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
        this.discardDeck = new Deck(false);
        players.get(activePlayerIndex).updateActive(true);
    }

    public void advanceActivePlayer() {
        activePlayerIndex++;
        activePlayerIndex %= players.size();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).updateActive(i == activePlayerIndex);
        }
    }

    public void updateAndDraw(GameCanvas canvas) {
        for (Player player : players) {
            player.updateAndDraw(canvas);
        }
        drawDeck.updateAndDraw(canvas, Constants.DECK_TYPE.DRAW);
        discardDeck.updateAndDraw(canvas, Constants.DECK_TYPE.DISCARD);
    }

    public void onMouseClick() {
        for (Player player : players) {
            player.onMouseClick();
        }
    }
}
