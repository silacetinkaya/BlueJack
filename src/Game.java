import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {

    private static final int MAX_HISTORY_SIZE = 10;
    private final GameHistoryEntry[] gameHistory;
    private int historyIndex;
    public enum Color {
        GREEN, YELLOW, BLUE, RED
    }

    public enum SpecialSkill {
        FLIP(1), DOUBLE(2);

        private final int value;

        SpecialSkill(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    // Constants for deck sizes and scores
    private static final int TOTAL_CARDS = 40;
    private static final int PLAYER_HAND_SIZE = 4;
    private static final int SPECIAL_DECK_SIZE = 10;
    private static final int BOARD_SIZE = 9;

    private static final int MAX_CARD_VALUE = 10;

    private static final int SET_WIN_SCORE = 3;
    private static final int TARGET_SCORE = 20;
    private final Player[] Players;
    private final Card[] GameDeck;
    private final Card[] pDeck;
    private final Card[] bDeck;

    public Card[] getGameDeck() {
        return GameDeck;
    }

    public Card[] getbDeck() {
        return bDeck;
    }

    public Card[] getpDeck() {
        return pDeck;
    }


    public Game(Player[] Players) {
        Players[0].setSet_Score(0);
        Players[1].setSet_Score(0);
        Random r = new Random();
        Card[] Deck = new Card[TOTAL_CARDS];
        Card[] SpecialDeckPlayer = new Card[SPECIAL_DECK_SIZE]; // 10 card possibilities
        Card[] SpecialDeckBot = new Card[SPECIAL_DECK_SIZE]; // 10 card possibilities
        Card[] PlayerDeck = new Card[PLAYER_HAND_SIZE]; // 4 card chosen from possible cards
        Card[] BotDeck = new Card[PLAYER_HAND_SIZE]; // 4 card chosen from possible cards
        int[] values = new int[MAX_CARD_VALUE];
        for (int i = 0; i < MAX_CARD_VALUE; i++) {
            values[i] = i + 1;
        }
        Color[] colors = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED};
        SpecialSkill[] skills = {SpecialSkill.FLIP, SpecialSkill.DOUBLE};
        int index = 0;
        for (int k : values) {
            for (Color color : colors) {
                Deck[index] = new Card(color, k, 0);
                index++;
            }
        }
        for (int i = 0; i < Deck.length; i++) {
            int x = r.nextInt(0, (TOTAL_CARDS - 1));//0 is the top 39 is the bottom of the deck
            Card temp = Deck[i];
            Deck[i] = Deck[x];
            Deck[x] = temp;
        }

        for (int i = 0; i < 5; i++) {
            SpecialDeckBot[i] = Deck[i];//5 card of
            Deck[i] = null;
            SpecialDeckPlayer[i] = Deck[39 - i];
            Deck[39 - i] = null;
        }
        for (int i = 0; i < 3; i++) {
            int c = r.nextInt(0, 3);//random for colors
            int y = r.nextInt(1, 6);//chance for random values
            int z = r.nextInt(0, 1);//negative or not
            if (z == 0) {
                y = y * -1;
            }
            Card x = new Card(colors[c], y, 0);
            SpecialDeckBot[i + 5] = x;
        }
        for (int i = 0; i < 3; i++) {
            int c = r.nextInt(0, 3);//random for colors
            int y = r.nextInt(1, 6);//chance for random values
            int z = r.nextInt(0, 1);//negative or not
            if (z == 0) {
                y = y * -1;
            }
            Card x = new Card(colors[c], y, 0);
            SpecialDeckPlayer[i + 5] = x;
        }

        for (int i = 0; i < 2; i++) {

            int c = r.nextInt(0, 100);//chance of special
            int sc = r.nextInt(1, 2);//which special card is it going to be
            int randColors = r.nextInt(0, 3);//random for colors
            int y = r.nextInt(1, 6);//chance for random values
            int z = r.nextInt(0, 1);//negative or not
            if (c > 80) {
                Card s = new Card(null, 0, sc);
                SpecialDeckBot[i + 8] = s;
            } else {
                if (z == 0) {
                    y = y * -1;
                }
                Card s = new Card(colors[randColors], y, 0);
                SpecialDeckBot[i + 8] = s;
            }

        }
        //set flip and double for player
        for (int i = 0; i < 2; i++) {
            int c = r.nextInt(0, 100);//chance of special
            int sc = r.nextInt(1, 2);//which special card is it going to be
            int randColors = r.nextInt(0, 3);//random for colors
            int y = r.nextInt(1, 6);//chance for random values
            int z = r.nextInt(0, 1);//negative or not
            if (c >= 80) {
                Card s = new Card(null, 0, sc);
                SpecialDeckPlayer[i + 8] = s;
            } else {
                if (z == 0) {
                    y = y * -1;
                }
                Card s = new Card(colors[randColors], y, 0);
                SpecialDeckPlayer[i + 8] = s;
            }
        }
        int[] differentPlayer = new int[4];
        for (int i = 0; i < differentPlayer.length; i++) {
            int randomNumber;
            boolean isUnique;
            do {
                randomNumber = r.nextInt(10);
                isUnique = true;
                for (int j = 0; j < i; j++) {
                    if (randomNumber == differentPlayer[j]) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);
            differentPlayer[i] = randomNumber;
        }
        int[] differentBot = new int[4];
        for (int i = 0; i < differentBot.length; i++) {
            int randomNumber;
            boolean isUnique;
            do {
                randomNumber = r.nextInt(10);
                isUnique = true;
                for (int j = 0; j < i; j++) {
                    if (randomNumber == differentBot[j]) {
                        isUnique = false;
                        break;
                    }
                }
            } while (!isUnique);
            differentBot[i] = randomNumber;
        }
        for (int i = 0; i < 4; i++) {
            Card x = SpecialDeckPlayer[differentPlayer[i]];
            Card y = SpecialDeckBot[differentBot[i]];
            PlayerDeck[i] = x;
            BotDeck[i] = y;
        }
        this.GameDeck = Deck;
        this.bDeck = BotDeck;
        this.pDeck = PlayerDeck;
        this.Players = Players;
        this.gameHistory = new GameHistoryEntry[MAX_HISTORY_SIZE];
        this.historyIndex = 0;
    }

    public void Play(Card[] PlayerBoard, Card[] BotBoard, Game game) {
        final int STAND = 1;
        final int PLAY_CARD =2;
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to BlueJack! Let's play three sets.");

        while (game.Players[0].getSet_Score() < SET_WIN_SCORE && game.Players[1].getSet_Score() < SET_WIN_SCORE) {
            System.out.println("==== Set " + (game.Players[0].getSet_Score() + game.Players[1].getSet_Score() + 1) + " ====");
            game.ResetRound(PlayerBoard, BotBoard);
            game.Players[0].setStand(false);
            Card.AutoPlay(PlayerBoard, game.getGameDeck(), 0);

            while (true) {
                Card.PrintBoardAndDeck(bDeck, BotBoard, PlayerBoard, game.getpDeck());
                System.out.println("It's your turn!");

                // Prompt user for action
                System.out.println("1 - Stand");
                System.out.println("2 - Play a card from your hand");

                int move = sc.nextInt();

                switch (move) {
                    case STAND:
                        // Player chooses to stand, end their turn and wait for the opponent
                        Card.PrintBoardAndDeck(bDeck, BotBoard, PlayerBoard, game.getpDeck());
                        game.Players[1].setStand(false);
                        game.Players[0].setStand(true);
                        break;
                    case PLAY_CARD:
                        Card.PrintBoardAndDeck(bDeck, BotBoard, PlayerBoard, game.getpDeck());
                        int choice = -1;
                        boolean validInput = false;

                        while (!validInput) {
                            try {
                                System.out.println("Please enter which card you want to play:");
                                choice = sc.nextInt();
                                if (choice < 1 || choice > 4 || game.getpDeck()[choice - 1] == null) {
                                    throw new InputMismatchException();
                                }
                                validInput = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid card number.");
                                sc.nextLine(); // Clear the buffer
                            }
                        }

                        Card.SpecialPlay(PlayerBoard, game.getpDeck(), choice);
                        game.Players[1].setStand(false);
                        game.Players[0].setStand(true); // Playing a card ends the turn
                        break;
                    default:
                        System.out.println("Invalid move. Please choose again.");
                        continue;
                }

                // Check and display the result of the turn
                int playerScore = Card.CalculateBoard(PlayerBoard);
                int botScore = Card.CalculateBoard(BotBoard);
                Card.PrintBoardAndDeck(bDeck, BotBoard, PlayerBoard, game.getpDeck());

                if (game.Players[0].isStand() && game.Players[1].isStand()) {
                    // Both players stand, determine the winner of the set
                    determineSetWinner(game, PlayerBoard, BotBoard);
                    break;
                } else if (game.Players[0].isStand()) {
                    // If the player stands, give the bot a chance to play
                    Card.BotMove(BotBoard, game.getbDeck(), game.getGameDeck());
                    game.Players[0].setStand(false);
                    game.Players[1].setStand(true);
                }



                if (playerScore > TARGET_SCORE) {
                    System.out.println("You busted! You lost this set!");
                    game.Players[1].setSet_Score(game.Players[1].getSet_Score() + 1);
                    break;
                } else if (Card.RoundCheck(playerScore, PlayerBoard)) {
                    System.out.println("You won the game with BlueJack!");
                    break;
                }

                if (botScore > TARGET_SCORE) {
                    System.out.println("Bot busted! You won this set!");
                    game.Players[0].setSet_Score(game.Players[0].getSet_Score() + 1);
                    break;
                } else if (Card.RoundCheck(botScore, BotBoard)) {
                    System.out.println("Bot won the game with BlueJack!");
                    break;
                }

                if (game.Players[0].isStand() && game.Players[1].isStand()) {
                    determineSetWinner(game, PlayerBoard, BotBoard);
                    break;
                }


                System.out.println("----------------------------------------\n\n\n");
                System.out.println("The set continues...\n");
                Card.AutoPlay(PlayerBoard, game.getGameDeck(), 0);
            }

            // Print the total sets won by each player after a set is played
            System.out.println("Total sets won - Player: " + game.Players[0].getSet_Score() + ", Bot: " + game.Players[1].getSet_Score());

            // Check if any player has won the game
            if (game.Players[0].getSet_Score() == SET_WIN_SCORE) {
                System.out.println("Congratulations! You won the game!");
            } else if (game.Players[1].getSet_Score() == SET_WIN_SCORE) {
                System.out.println("Sorry, you lost the game. Better luck next time!");
            }

            if (game.Players[0].getSet_Score() == SET_WIN_SCORE || game.Players[1].getSet_Score() == SET_WIN_SCORE) {
                // Record game history before exiting
                recordGameHistory(game);
            }
            // Ask if the user wants to play another game
            System.out.println("Do you want to play another game? (yes/no)");
            sc.nextLine(); // Consume the newline character
            String playAgain = sc.nextLine().toLowerCase();

            if (!playAgain.equals("yes")) {
                // Store game history before exiting
                System.out.println("Thanks for playing! Goodbye!");
                break;
            }
        }

        sc.close();
    }
    private void recordGameHistory(Game game) {
        if (historyIndex < MAX_HISTORY_SIZE) {
            // Record game history
            String date = getCurrentDate();
            String player1Name = game.Players[0].getName();
            String player2Name = game.Players[1].getName();
            int player1Score = game.Players[0].getSet_Score();
            int player2Score = game.Players[1].getSet_Score();

            GameHistoryEntry gameEntry = new GameHistoryEntry(date, player1Name, player2Name, player1Score, player2Score);
            game.gameHistory[historyIndex] = gameEntry;
            historyIndex++;
        } else {
            // Remove the oldest entry
            shiftGameHistoryArray(game.gameHistory);

            // Add the new entry
            String date = getCurrentDate();
            String player1Name = game.Players[0].getName();
            String player2Name = game.Players[1].getName();
            int player1Score = game.Players[0].getSet_Score();
            int player2Score = game.Players[1].getSet_Score();

            GameHistoryEntry gameEntry = new GameHistoryEntry(date, player1Name, player2Name, player1Score, player2Score);
            game.gameHistory[MAX_HISTORY_SIZE - 1] = gameEntry;
        }

        // Save the game history to a file
        saveGameHistoryToFile(game.gameHistory);
    }

    private void shiftGameHistoryArray(GameHistoryEntry[] gameHistory) {
        // Shift elements to remove the oldest entry
        System.arraycopy(gameHistory, 1, gameHistory, 0, MAX_HISTORY_SIZE - 1);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private void saveGameHistoryToFile(GameHistoryEntry[] gameHistory) {
        try (FileWriter writer = new FileWriter("game_history.txt", false)) {
            for (GameHistoryEntry entry : gameHistory) {
                if (entry != null) {
                    writer.write(entry + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void determineSetWinner(Game game, Card[] PlayerBoard, Card[] BotBoard) {
        int playerScore = Card.CalculateBoard(PlayerBoard);
        int botScore = Card.CalculateBoard(BotBoard);

        if (playerScore > botScore && playerScore <= TARGET_SCORE) {
            System.out.println("You won this set!");
            game.Players[0].setSet_Score(game.Players[0].getSet_Score() + 1);
        } else if (playerScore < botScore && botScore <= TARGET_SCORE) {
            System.out.println("Bot won this set!");
            game.Players[1].setSet_Score(game.Players[1].getSet_Score() + 1);
        } else {
            System.out.println("It's a tie!");
        }
    }
    public void ResetRound(Card[] PlayerBoard, Card[] BotBoard) {
        // Reset the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            PlayerBoard[i] = null;
            BotBoard[i] = null;
        }
    }
    private static class GameHistoryEntry {
        private final String date;
        private final String player1Name;
        private final String player2Name;
        private final int player1Score;
        private final int player2Score;

        public GameHistoryEntry(String date, String player1Name, String player2Name, int player1Score, int player2Score) {
            this.date = date;
            this.player1Name = player1Name;
            this.player2Name = player2Name;
            this.player1Score = player1Score;
            this.player2Score = player2Score;
        }

        @Override
        public String toString() {
            return player1Name + ":" + player1Score + " - " + player2Name + ":" + player2Score + ", " + date;
        }
    }
}
