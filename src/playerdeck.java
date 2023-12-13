import java.util.Random;

class playerdeck {
    private Card[] cards;
    private String[] colors = {"Blue", "Red", "Green", "Yellow"};

    public playerdeck() {
        this.cards = new Card[10];
        initializeDeck();
    }

    private void initializeDeck() {
        for (int i = 0; i < 5; i++) {
            String color = getRandomColor();
            int value = getRandomNumber(1, 7);
            char sign = getRandomSign();
            cards[i] = new Card(color, value, sign);
        }

        // Add three positive/negative cards
        for (int i = 5; i < 8; i++) {
            String color = getRandomColor();
            int value = getRandomNumber(1, 7);
            char sign = getRandomSign();
            cards[i] = new Card(color, value, sign);
        }

        // Add one flip card and one double card with 4% chance
        if (getRandomNumber(1, 101) <= 4) {
            cards[8] = new SpecialCard("Flip");
            cards[9] = new SpecialCard("Double");
        } else {
            // Add two more positive/negative cards
            for (int i = 8; i < 10; i++) {
                String color = getRandomColor();
                int value = getRandomNumber(1, 7);
                char sign = getRandomSign();
                cards[i] = new Card(color, value, sign);
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private String getRandomColor() {
        return colors[getRandomNumber(0, 3)]; // Assuming 0-3 represent different colors
    }

    private char getRandomSign() {
        return (getRandomNumber(0, 1) == 0) ? '+' : '-';
    }

    public void printDeck() {
        for (Card card : cards) {
            System.out.println(card.toString());
        }
    }
}




