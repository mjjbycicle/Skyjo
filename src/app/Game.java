package app;

import app.objects.Card;
import app.objects.Deck;
import app.objects.Player;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.ButtonBehavior;

import java.util.List;

public class Game extends GameObject {
    private List<Player> players;
    private Deck drawDeck, discardDeck;
    private int activePlayerIndex = 0;

    public Game(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
        this.discardDeck = new Deck(false, Constants.DISCARD_DECK_X, Constants.DISCARD_DECK_Y);
        players.get(activePlayerIndex).updateActive(true);
    }

    public void advanceActivePlayer() {
        activePlayerIndex++;
        activePlayerIndex %= players.size();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).updateActive(i == activePlayerIndex);
        }
    }

    @Override
    public void updateAndDraw(GameCanvas canvas) {
        for (Player player : players) {
            player.updateAndDraw(canvas);
        }
        drawDeck.updateAndDraw(canvas);
        discardDeck.updateAndDraw(canvas);
    }

    public void matrixFlipCard() {
        if (players.get(activePlayerIndex).matrixMouseClicked()) {
            players.get(activePlayerIndex).matrixFlipCard();
        }
    }

    public Card matrixReplaceCard(Card replacement) {
        return players.get(activePlayerIndex).matrixReplaceCard(replacement);
    }

    public boolean matrixClicked() {
        return players.get(activePlayerIndex).matrixMouseClicked();
    }

    public boolean drawDeckClicked() {
        System.out.println("drawDeck");
        return drawDeck.clicked();
    }

    public Card drawDeckCard() {
        Card drawnCard = drawDeck.drawCard();
        drawnCard.setFaceDown(false);
        drawnCard.setPosition(Constants.DRAWN_CARD_X, Constants.DRAWN_CARD_Y);
        return drawnCard;
    }

    public Card drawFromDiscard() {
        Card drawnCard = discardDeck.drawCard();
        drawnCard.setFaceDown(false);
        drawnCard.setPosition(Constants.DRAWN_CARD_X, Constants.DRAWN_CARD_Y);
        return drawnCard;
    }

    public boolean discardDeckEmpty() {
        return discardDeck.empty();
    }

    public boolean discardDeckClicked() {
        return discardDeck.clicked();
    }

    public void discardCard(Card card) {
        discardDeck.push(card);
    }
}
