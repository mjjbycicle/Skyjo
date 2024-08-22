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
                cards.add(new Card(-2));
            }
            for (int i = 0; i < 10; i++) {
                cards.add(new Card(-1));
            }
            for (int i = 0; i <= 12; i++) {
                for (int j = 0; j < 10; j++) {
                    cards.add(new Card(i));
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
        if (type == DECK_TYPE.DRAW) {
            setPosition(Constants.DRAW_DECK_X, Constants.DRAW_DECK_Y);
        } else {
            setPosition(Constants.DISCARD_DECK_X, Constants.DISCARD_DECK_Y);
        }
        if (cards.isEmpty()) return;
        cards.get(0).updateAndDraw(canvas);
    }

    public void push(Card card) {
        card.faceDown = false;
        cards.add(0, card);
    }
}
