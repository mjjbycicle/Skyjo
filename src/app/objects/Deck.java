package app.objects;

import core.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck extends GameObject {
    private List<Card> cards;

    public Deck(boolean random) {
        cards = new ArrayList<>();
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

    public Deck() {
        cards = new ArrayList<>();
    }

    public Card draw() {
        return cards.remove(0);
    }

    public void push(Card card) {
        card.faceDown = false;
        cards.add(0, card);
    }
}
