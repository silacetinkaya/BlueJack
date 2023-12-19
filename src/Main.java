import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int MIN_RANDOM = 0;
        final int MAX_RANDOM_PLAYER_DECK = 9;
        final int MAX_RANDOM_PLAYER_NAME = 100;
        Random r = new Random();
        Scanner sc = new Scanner(System.in);
        Player[] Players = new Player[2];
        Card[] PBoard = new Card[MAX_RANDOM_PLAYER_DECK];
        Card[] BBoard = new Card[MAX_RANDOM_PLAYER_DECK];
        System.out.println("Welcome to the BlueJack Game!!!");
        System.out.println("Please enter your name:");
        String p_name = sc.nextLine();
        Player player = new Player(p_name, 0, 0);//player
        int b = r.nextInt(MIN_RANDOM, MAX_RANDOM_PLAYER_NAME);
        String b_name = "Bot " + b;
        Player bot = new Player(b_name, 0, 0);//bot
        Players[0] = player;
        Players[1] = bot;
        Game game = new Game(Players);
        game.Play(PBoard, BBoard, game);
    }

}

