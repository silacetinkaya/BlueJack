import java.util.Random;
public class Deck {
    private String[] color;
    private int[] num;
    private int skill;

    public Deck(String[] color, int[] num) {
        this.num = num;
        this.color = color;
        this.skill = skill;

    }

    public String[] getColor() {
        return color;
    }

    public int[] getNum() {
        return num;
    }

    public int getSkill() {
        return skill;
    }

    public static Deck createDeck() {
        String color[] = {"blue", "red", "yellow", "green"};
        int num[] = new int[40];
        for (int i = 0; i < 40; i++) {
            if (i % 10 == 0) {
                num[i] = (i % 10 + 1) + 10;
            } else if (i % 10 < 3) {
                num[i] = (i % 10 + 1) + 20;
            } else if (i % 10 < 6) {
                num[i] = (i % 10 + 1) + 30;
            } else {
                num[i] = (i % 10 + 1) + 40;
            }


        }
        return new Deck(color, num);

    }public static Deck createPlayerDeck(){
        Random rd=new Random();
        int playerDeckNum[]=new int[5];
        for (int i = 0; i < 3; i++) {
            playerDeckNum[i] = (rd.nextInt(6) + 1) * (rd.nextDouble() < 0.5 ? 1 : -1);
        }
        for (int i = 3; i < 5; i++) {
            double chance = rd.nextDouble();
            if (chance < 0.8) {
                playerDeckNum[i] = (rd.nextInt(6) + 1) * (rd.nextDouble() < 0.5 ? 1 : -1);
            } else {
                double specialChance = rd.nextDouble();
                if (specialChance < 0.2) {
                    // 20% chance of a flip card
                    playerDeckNum[i] = -1;
                } else {
                    // 80% chance of a double card
                    playerDeckNum[i] = -2;
                }
            }
        }return new Deck(null,playerDeckNum);
    }
    public static Deck createComputerCard(){
        String color[] = {"blue", "red", "yellow", "green"};
        Random rd=new Random();
        int[] remainingCards = new int[5];

        for (int i = 0; i < 3; i++) {
            remainingCards[i] = (rd.nextInt(6) + 1) * (rd.nextDouble() < 0.5 ? 1 : -1);
        }
        for (int i = 3; i < 5; i++) {
            double chance = rd.nextDouble();
            if (chance < 0.8) {
                remainingCards[i] = (rd.nextInt(6) + 1) * (rd.nextDouble() < 0.5 ? 1 : -1);
            } else {
                double specialChance = rd.nextDouble();
                if (specialChance < 0.2) {
                    remainingCards[i] = -1; // Flip card
                } else {
                    remainingCards[i] = -2; // Double card
                }
            }
        } String[] randomColors = new String[5];
        for (int i = 0; i < 5; i++) {
            if (remainingCards[i] != -1 && remainingCards[i] != -2) {
                randomColors[i] = color[rd.nextInt(color.length)];
            }
        }
        return new Deck( color ,remainingCards);

    }

}













/*import java.util.Random;
public class Deck {
    private String []color;
    private int []num;

    public Deck(String color[],int num[] ){
        this.color=color;
        this.num=num;


    }


    public String getColor(int index){
        return color[index];
    }
    public int getNum(int index){
        return num[index];
    }

    public static Deck createdeck(){
        String [] color={"blue","green","yellow","red"};
        int num[]=new int[40];
        for (int i = 0; i < 40; i++) {
            num[i] = (i % 10) + 1;
        }

        return new Deck(color,num);
    }
}

import java.util.Random;
public class Deck {
    private int[] green;
    private int[] blue;
    private int[] red;
    private int[] yellow;
    private int [] combinedDeck;

    public Deck(){
        green=createarr("green");
        blue=createarr("blue");
        red=createarr("red");
        yellow=createarr("yellow");
        combineDecks();

    }
    private int[] createarr(){
        int [] array=new int[10];
        for(int i=0;i<array.length;i++){
            array[i]=i+1;
        }
        return array;
    }
    public int[] getGreen(){
        return green;
    }
    public int[] getRed(){
        return red;
    }
    public int[] getBlue(){
        return blue;
    }
    public int[] getYellow(){
        return yellow;
    }
    private void combineDecks() {
        // Toplam eleman sayısını hesapla
        int totalLength = green.length + blue.length + red.length + yellow.length;

        // Birleştirilmiş desteyi oluştur
        combinedDeck = new String[totalLength];

        // Renkli array'leri birleştir
        int index = 0;
        for (String s : green) {
            combinedDeck[index++] = s;
        }
        for (String s : blue) {
            combinedDeck[index++] = s;
        }
        for (String s : red) {
            combinedDeck[index++] = s;
        }
        for (String s : yellow) {
            combinedDeck[index++] = s;
        }
    }

    } public int[] getCombinedDeck() {
        return combinedDeck;
    }

}
*/
