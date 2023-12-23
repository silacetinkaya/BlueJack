import java.util.Arrays;

public class Card {
    public Game.Color color;
    private int value;

    private static final int BOARD_SIZE = 9;
    private static final int TARGET_SCORE = 20;
    private int skill;

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public Game.Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor(Game.Color color) {
        this.color = color;
    }

    public Card(Game.Color color, int value, int skill) {
        this.color = color;
        this.value = value;
        this.skill = skill;
    }

    public static void SpecialPlay(Card[] board, Card[] hand, int chosen) {
        if (chosen >= 1 && chosen <= hand.length) {
            Card x = hand[chosen - 1];
            hand[chosen - 1] = null;

            // Find the first empty slot in the board and add the card
            for (int i = 0; i < board.length; i++) {
                if (board[i] == null) {
                    // Apply special skill if present
                    if (x.getSkill() == Game.SpecialSkill.FLIP.ordinal() + 1) {
                        handleFlipCard(board, i);
                    } else if (x.getSkill() == Game.SpecialSkill.DOUBLE.ordinal() + 1) {
                        handleDoubleCard(board, i);
                    }

                    break; // Exit the loop after adding the card
                }
            }
        } else {
            // Handle the case where chosen is out of bounds
            System.out.println("Invalid card selection.");
        }
    }

    // Add parameters to indicate the position of the last played card
    private static void handleFlipCard(Card[] board, int position) {
        if (position > 0) {
            // Change the sign of the last played card's value
            if (board[position - 1] != null) {
                board[position - 1].setValue(-Math.abs(board[position - 1].getValue()));
            }

            // Remove the flip card from the game
            board[board.length - 1] = null;
        }
    }


    // Add parameters to indicate the position of the last played card
    private static void handleDoubleCard(Card[] board, int position) {
        if (board[position - 1] != null) {
            board[position - 1].setValue((board[position - 1].getValue())*2);
        }
        board[board.length - 1] = null;
    }




    public static void AutoPlay(Card[] board, Card[] deck, int playerIndex) {//drow card function for bot and player from game deck
        Card x = null;
        for (int i = 0; i < deck.length; i++) {
            if (deck[i] != null) {
                x = deck[i];
                deck[i] = null;
                break; // Exit the loop after finding the first non-null card
            }
        }
        if (x != null) {
            // Find the first null position on the board and add the drawn card
            for (int i = 0; i < board.length; i++) {
                if (board[i] == null) {
                    board[i] = x;
                    break; // Exit the loop after adding the drawn card to the board
                }
            }
        }
    }


    public static void PrintBoardAndDeck(Card[] bDeck, Card[] botboard, Card[] playerboard, Card[] playerdeck) {
        System.out.println("Bot's Deck:");
        printBotDeck(bDeck);

        System.out.println("Bot Board:");
        printCards(botboard);

        System.out.println("Player Board:");
        printCards(playerboard);

        System.out.println("Player Deck:");
        printCards(playerdeck);
    }


    private static void printCards(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null && cards[i].getSkill() == 0) {
                System.out.print((cards[i].getValue() + " " + cards[i].getColor() + " | "));
            } else if (cards[i] != null && cards[i].getSkill() == 1) {
                System.out.print("Flip Card | ");
            } else if (cards[i] != null && cards[i].getSkill() == 2) {
                System.out.print("Double Card | ");
            }
        }
        System.out.println();
    }

    public static void printBotDeck(Card[] bDeck) {
        for (Card card : bDeck) {
            if (card != null) {
                System.out.print("X | ");  // Print "O" when a card is present
            } else {
                System.out.print("O | ");  // Print "X" when no card is present
            }
        }
        System.out.println();
    }



    public static int CalculateBoard(Card[] board) {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {

            if (board[i] != null) {
                if (board[i].getSkill() == 0) {
                    sum = sum + board[i].getValue();
                } else if (board[i].getSkill() == 1 && i > 0 && board[i - 1] != null) {
                    sum = sum + (board[i - 1].getValue() * (-2));//for flip card
                } else if (board[i].getSkill() == 2 && i > 0 && board[i - 1] != null) {
                    sum = sum + (board[i - 1].getValue() * 2);//for double card
                }
            }
        }
        return sum;
    }

    private static boolean allBlueCardsUsed(Card[] board) {
        int usedBlueCards = 0;

        for (Card card : board) {
            if (card != null && card.getColor() == Game.Color.BLUE) {
                usedBlueCards++;
            }
        }

        // Compare with the total number of blue cards
        return usedBlueCards == BOARD_SIZE; // Adjust TOTAL_BLUE_CARDS based on your card structure
    }


    public static boolean RoundCheck(int boardValue, Card[] board) {
        boolean control = boardValue == TARGET_SCORE && allBlueCardsUsed(board);

        return control;
    }

    public static void BotMove(Card[] botBoard, Card[] botDeck, Card[] gameDeck) {
        int bot = CalculateBoard(botBoard);

        // Evaluate the risk of drawing a card
        boolean riskyMove = bot < 15;

        if (riskyMove) {
            // If the bot's total score is low, draw a card
            AutoPlay(botBoard, gameDeck, 0);
        } else {
            // Simulate scenarios for drawing a card and standing
            int[] values = new int[2];
            Card[] tempBoard = Arrays.copyOf(botBoard, botBoard.length);

            // Simulate drawing a card
            AutoPlay(tempBoard, gameDeck, 0);//AUTO PLAYDE DEĞİL BOTDECK İÇİNDEN SPECİAL PLAYLE ALINCAK HALE GETİR
            values[0] = CalculateBoard(tempBoard);

            // Simulate standing
            values[1] = bot;

            // Choose the action that maximizes the chances of winning the set
            int bestMove = values[0] > values[1] ? 0 : 1;//condition? situation1 :situation2
            SpecialPlay(botBoard, botDeck, bestMove);
        }
    }
}
