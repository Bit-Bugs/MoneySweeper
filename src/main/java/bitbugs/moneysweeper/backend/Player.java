package bitbugs.moneysweeper.backend;

public class Player {
    private static int id = 0;
    private String name;
    private int score;

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        id +=1;
    }
}
