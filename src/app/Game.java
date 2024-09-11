package app;

import app.objects.Card;
import app.objects.Deck;
import app.objects.Player;
import core.GameCanvas;
import core.GameObject;
import core.math.Vec2;

import java.util.*;

public class Game extends GameObject {
    private List<Player> players;
    private Deck drawDeck, discardDeck;
    private int activePlayerIndex = 0;
    private HashMap<Integer, Integer> scores = new HashMap<>();
    private int finisherID = -1;

    public Game(List<Player> players, Deck drawDeck) {
        this.players = players;
        this.drawDeck = drawDeck;
        this.discardDeck = new Deck(false, Constants.DISCARD_DECK_X, Constants.DISCARD_DECK_Y);
        players.get(activePlayerIndex).updateActive(true);
        for (Player player : players) {
            scores.put(player.getID(), 0);
        }
    }

    public void advanceActivePlayer() {
        activePlayerIndex++;
        activePlayerIndex %= players.size();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).updateActive(i == activePlayerIndex);
        }
    }

    public void updateAndDrawScores(GameCanvas canvas) {
        for (Player player : players) {
            player.updateAndDrawScore(canvas, scores.get(player.getID()));
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

    public boolean matrixFlipCard() {
        return players.get(activePlayerIndex).matrixFlipCard();
    }

    public Card matrixReplaceCard(Card replacement) {
        return players.get(activePlayerIndex).matrixReplaceCard(replacement);
    }

    public void matrixReplaceCard(Card replacement, Vec2 position) {
        players.get(activePlayerIndex).matrixReplaceCard(replacement, position);
    }

    public Vec2 getClickedIndex() {
        return players.get(activePlayerIndex).getClickedIndex();
    }

    public boolean matrixClicked() {
        return players.get(activePlayerIndex).matrixMouseClicked();
    }

    public boolean drawDeckClicked() {
        return drawDeck.clicked();
    }

    public Card drawDeckCard() {
        Card drawnCard = drawDeck.drawCard();
        drawnCard.setFaceDown(false);
        return drawnCard;
    }

    public Card drawFromDiscard() {
        Card drawnCard = discardDeck.drawCard();
        drawnCard.setFaceDown(false);
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

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void advanceRound() {
        for (Player player : players) {
            player.advanceRound();
        }
        activePlayerIndex = 0;
        finisherID = -1;
        discardDeck = new Deck(false, Constants.DISCARD_DECK_X, Constants.DISCARD_DECK_Y);
        drawDeck = new Deck(true, Constants.DRAW_DECK_X, Constants.DRAW_DECK_Y);
        dealCards();
    }

    public boolean isRoundFinished() {
        return isLastRound() && activePlayerIndex == players.size() - 1;
    }

    private boolean isLastRound() {
        for (Player player : players) {
            if (player.roundFinished()) {
                finisherID = player.getID();
                return true;
            }
        }
        return false;
    }

    public boolean isGameFinished() {
        for (Player player : players) {
            if (scores.get(player.getID()) >= 100) {
                return true;
            }
        }
        return false;
    }

    public void scoreRound() {
        SortedSet<Player> playerScores = new TreeSet<>(Comparator.comparingInt(Player::getRoundScore));
        playerScores.addAll(players);
        ArrayList<Player> playersOrdered = new ArrayList<>(playerScores);
        for (Player player : players) {
            scores.put(player.getID(), scores.get(player.getID()) + player.getRoundScore());
        }
        if (playersOrdered.get(0).getID() != finisherID) {
            scores.put(finisherID,
                    scores.get(players.get(finisherID).getID()) + players.get(finisherID).getRoundScore());
        }
    }

    public void dealCards() {
        for (Player player : players) {
            int[] cards = new int[12];
            for (int i = 0; i < 12; i++) {
                cards[i] = drawDeck.drawCard().getNum();
            }
            player.deal(cards);
        }
    }

    public int getNumPlayers() {
        return players.size();
    }
}
