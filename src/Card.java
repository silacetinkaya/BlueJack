class Card {
    private String color;
    private int value;
    private char sign;

    public Card(String color, int value, char sign) {
        this.color = color;
        this.value = value;
        this.sign = sign;
    }


    public String toString() {
        return "Color: " + color + ", Value: " + value + ", Sign: " + sign;
    }
}

class SpecialCard extends Card {
    private String type;

    public SpecialCard(String type) {
        super("", 0, ' '); // Special cards do not have color, value, or sign
        this.type = type;
    }


    public String toString() {
        return "Special Card: " + type;
    }
}

