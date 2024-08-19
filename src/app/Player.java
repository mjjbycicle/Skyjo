package app;

public class Player {
    private Card[][] matrix = new Card[3][4];
    private final String name;
    private final int id;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
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
}
