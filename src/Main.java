public class Main {
    public static void main(String[] args) {
        /*
        Deck deck = Deck.createDeck();

        String[] colors = deck.getColor();
        int[] numbers = deck.getNum();

        for (int i = 0; i < colors.length * 10; i++) {
            System.out.println((i % 10 + 1) + " " + colors[i /10 %colors.length]);
        }
        playerdeck playerDeck = new playerdeck();
        playerDeck.printDeck();

         */
        Deck standardDeck = Deck.createDeck();

        // Create a player's deck
        Deck playerDeck = Deck.createPlayerDeck();
        System.out.println("Player's Deck:");
        printDeck(playerDeck);

        // Create remaining cards
        Deck remainingCards = Deck.createComputerCard();
        System.out.println("\nRemaining Cards:");
        printDeck(remainingCards);
    }

    private static void printDeck(Deck deck) {
        String[] colors = deck.getColor();
        int[] nums = deck.getNum();

        for (int i = 0; i < nums.length; i++) {
            String color = (colors != null && i < colors.length) ? colors[i] : "No Color";
            String value = (nums[i] >= 0) ? "+" + nums[i] : String.valueOf(nums[i]);

            System.out.println("Card " + (i + 1) + ": Color - " + color + ", Value - " + value);
        }

    }
}
