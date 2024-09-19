package app.objects;

import app.Constants;
import core.GameCanvas;
import core.math.Vec2;

public class Matrix {
    private Card[][] matrix;
    public boolean active;

    public Matrix() {
        active = false;
    }

    public void deal(int[] cards) {
        matrix = new Card[3][4];
        for (int i = 0; i < cards.length; i++) {
            if (i < matrix[0].length) {
                matrix[0][i] = new Card(cards[i], true);
            } else if (i < 2 * matrix[0].length) {
                matrix[1][i - matrix[0].length] = new Card(cards[i], true);
            } else {
                matrix[2][i - 2 * matrix[0].length] = new Card(cards[i], true);
            }
        }
        int a = (int)(Math.random() * 3);
        int b = a + (int)(Math.random() * (3 - a) + 1);
        int c = (int)(Math.random() * 2);
        int d = c + (int)(Math.random() * (2 - c) + 1);
        System.out.println(a + " " + b + " " + c + " " + d);
        matrix[c][a].setFaceDown(false);
        matrix[d][b].setFaceDown(false);
    }

    public boolean mouseClicked() {
        for (Card[] i : matrix) {
            for (Card c : i) {
                if (c.clicked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Card replaceCard(Card replacement) {
        if (active) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j].clicked()) {
                        Card res = matrix[i][j];
                        matrix[i][j] = replacement;
                        return res;
                    }
                }
            }
        }
        return null;
    }

    public Vec2 getClicked() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].clicked()) {
                    return new Vec2(i, j);
                }
            }
        }
        return new Vec2(0, 0);
    }

    public void replaceCard(Card replacement, Vec2 clicked) {
        matrix[(int)clicked.x][(int)clicked.y] = replacement;
    }

    public boolean flipCard() {
        for (Card[] i : matrix) {
            for (Card c : i) {
                if (c.clicked() && c.isFaceDown()) {
                    c.setFaceDown(false);
                    return true;
                }
            }
        }
        return false;
    }

    public void updateAndDraw(GameCanvas canvas, boolean active, int index) {
        if (active) updateAndDrawActive(canvas);
        else updateAndDrawInactive(canvas, index);
    }

    private void updateAndDrawActive(GameCanvas canvas) {
        while (getRemovableCol() != -1) {
            int col = getRemovableCol();
            removeCol(col);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j].updateAndDrawActive(canvas, i, j, 0, 0);
            }
        }
        active = true;
    }

    private void updateAndDrawInactive(GameCanvas canvas, int index) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].updateAndDrawInactive(canvas, i, j, Constants.ZERO_X + 30 + index * Constants.INACTIVE_MATRIX_WIDTH_WITH_PADDING, Constants.ZERO_Y + 80);
            }
        }
        active = false;
    }

    public void removeCol(int col) {
        Card[][] newMatrix = new Card[3][matrix[0].length - 1];
        for (int i = 0; i < matrix.length; i++) {
            int index = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == col) continue;
                newMatrix[i][index++] = matrix[i][j];
            }
        }
        matrix = newMatrix;
    }

    public int getRemovableCol() {
        for (int col = 0; col < matrix[0].length; col++) {
            int start = matrix[0][col].getNum();
            boolean equal = true;
            for (Card[] cards : matrix) {
                if (cards[col].getNum() != start || cards[col].isFaceDown()) {
                    equal = false;
                    break;
                }
            }
            if (equal) return col;
        }
        return -1;
    }

    public boolean finished() {
        for (Card[] cards : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (cards[j].isFaceDown()) return false;
            }
        }
        return true;
    }

    public int getScore() {
        int score = 0;
        for (Card[] i : matrix) {
            for (Card j : i) {
                if (!j.isFaceDown()) {
                    score += j.getNum();
                }
            }
        }
        return score;
    }
}
