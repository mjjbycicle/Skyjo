package app.objects;

import app.Constants;
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
        if (active) {
            for (Card[] i : matrix) {
                for (Card c : i) {
                    c.onMouseClick();
                }
            }
        }
    }

    public void updateAndDrawActive(GameCanvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j].updateAndDrawActive(canvas, i, j, 0, 0);
            }
        }
    }

    public void updateAndDrawInactive(GameCanvas canvas, int index) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j].updateAndDrawInactive(canvas, i, j, Constants.ZERO_X + 50 + index * 200, 400);
            }
        }
    }
}
