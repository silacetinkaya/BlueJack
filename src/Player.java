public class Player {
    private String Name;
    private int Set_Score;
    private int Point;
    private boolean stand; // New attribute to track standing status

    public void setName(String name) {
        Name = name;
    }

    public int getSet_Score() {
        return Set_Score;
    }

    public String getName() {
        return Name;
    }

    public void setSet_Score(int set_Score) {
        Set_Score = set_Score;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    // New method to set the standing status
    public void setStand(boolean stand) {
        this.stand = stand;
    }

    // New method to check the standing status
    public boolean isStand() {
        return stand;
    }
    public Player(String name, int set_Score, int point) {
        this.Set_Score = set_Score;
        this.Point = point;
        this.Name = name;
        this.stand = false; // Initialize standing status to false
    }
}



