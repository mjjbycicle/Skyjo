package app.objects;

import app.Constants;
import app.Game;
import core.GameCanvas;
import core.GameObject;
import core.behaviors.ButtonBehavior;
import core.behaviors.RoundedRectRendererBehavior;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck extends GameObject {
    private List<Card> cards;

    public Deck(boolean random, int x, int y) {
        this(x, y);
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

    private Deck(int x, int y) {
        cards = new ArrayList<>();
        this.addBehavior(new ButtonBehavior());
        this.addBehavior(
                new RoundedRectRendererBehavior(
                        20,
                        Color.WHITE,
                        new Color(0, 0, 0, 20)
                )
        ).setPosition(x, y).setSize(Constants.ACTIVE_CARD_WIDTH, Constants.ACTIVE_CARD_HEIGHT);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public void updateAndDraw(GameCanvas canvas) {
        if (!cards.isEmpty()) {
            cards.get(0).updateAndDrawDeck(canvas, (int) this.getPosition().x, (int) this.getPosition().y);
        }
        else super.updateAndDraw(canvas);
    }

    public void push(Card card) {
        card.setFaceDown(false);
        cards.add(0, card);
    }

    public boolean clicked() {
        if (!cards.isEmpty()) {
            return cards.get(0).clicked();
        }
        return this.findBehavior(ButtonBehavior.class).isHovered();
    }

    public boolean empty() {
        return cards.isEmpty();
    }
}
