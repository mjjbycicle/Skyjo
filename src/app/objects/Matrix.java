package app.objects;

import core.GameCanvas;

public class Matrix {
    private Card[][] matrix;
    public boolean active;

    public Matrix() {
        matrix = new Card[3][4];
        active = false;
    }

    public void deal(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            if (i < 4) {
                matrix[0][i] = cards[i];
            } else if (i < 8) {
                matrix[1][i - 4] = cards[i];
            } else {
                matrix[2][i - 8] = cards[i];
            }
        }
    }

    public void onMouseClick() {
        for (Card[] i : matrix) {
            for (Card c : i) {
                c.onMouseClick();
            }
        }
    }

    public void updateAndDrawActive(GameCanvas canvas) {

    }

    public void updateAndDrawInactive(GameCanvas canvas, int index) {

    }
}
