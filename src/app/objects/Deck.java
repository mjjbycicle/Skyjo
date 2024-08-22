package app.objects;

import app.Constants;
import app.Game;
import core.GameCanvas;
import core.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import app.Constants.*;

public class Deck extends GameObject {
    private List<Card> cards;

    public Deck(boolean random) {
        this();
        if (random) {
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(-2, true));
            }
            for (int i = 0; i < 10; i++) {
                cards.add(new Card(-1, true));
            }
            for (int i = 0; i <= 12; i++) {
                for (int j = 0; j < 10; j++) {
                    cards.add(new Card(i, true));
                }
            }
            Collections.shuffle(cards);
        }
    }

    private Deck() {
        cards = new ArrayList<>();
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public void updateAndDraw(GameCanvas canvas, DECK_TYPE type) {
        if (cards.isEmpty()) return;
        if (type == DECK_TYPE.DRAW) {
            cards.get(0).updateAndDrawDeck(canvas, Constants.DRAW_DECK_X, Constants.DRAW_DECK_Y);
        } else {
            cards.get(0).updateAndDrawDeck(canvas, Constants.DISCARD_DECK_X, Constants.DISCARD_DECK_Y);
        }
    }

    public void push(Card card) {
        card.faceDown = false;
        cards.add(0, card);
    }
}
