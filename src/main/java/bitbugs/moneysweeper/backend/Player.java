package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;

public class Player{
    private String name, timeEasy="0", timeMid="0", timeHard="0";
    private int scoreEasy=0, scoreMid=0, scoreHard=0;

    public int getScore(Difficulty dif) {
        return switch (dif) {
            case EASY -> scoreEasy;
            case MID -> scoreMid;
            case HARD -> scoreHard;
            default -> 0;
        };
    }
    public int getEasyScore()
    {
        return scoreEasy;
    }
    public int getMidScore()
    {
        return scoreMid;
    }
    public int getHardScore()
    {
        return scoreHard;
    }

    public void setScore(int score, Difficulty dif) {
        switch (dif) {
            case EASY -> scoreEasy = score;
            case MID -> scoreMid = score;
            case HARD -> scoreHard = score;
            default -> {}
        }
    }

    public void addScore(Difficulty dif) {
        switch (dif) {
            case EASY -> scoreEasy += 1;
            case MID -> scoreMid += 1;
            case HARD -> scoreHard += 1;
            default -> {}
        }
    }

    public void setTime(String time, Difficulty dif)
    {
        switch (dif) {
            case EASY -> timeEasy = time;
            case MID -> timeMid = time;
            case HARD -> timeHard = time;
            default -> {}
        }
    }

    public String getTime(Difficulty dif)
    {
        return switch (dif) {
            case EASY -> timeEasy;
            case MID -> timeMid;
            case HARD -> timeHard;
            default -> "0";
        };
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return name + "," + scoreEasy + "," + scoreMid + "," + scoreHard + "," + timeEasy + "," + timeMid + "," + timeHard;
    }

    public String scoreBoardEntry(Difficulty dif)
    {
        return switch (dif) {
            case EASY -> scoreEasy + " - " + name + " - " + timeEasy + "s";
            case MID -> scoreMid + " - " + name + " - " + timeMid + "s";
            case HARD -> scoreHard + " - " + name + " - " + timeHard + "s";
            default -> "";
        };
    }

    public Player(String dif, int score, String name, String time) {
        Difficulty difficulty = Difficulty.valueOf(dif);

        this.name = name;
        setScore(score, difficulty);
        setTime(time, difficulty);
    }

    public Player(String name, int scoreE, int scoreM, int scoreH, String timeE, String timeM, String timeH)
    {
        this.name = name;
        this.scoreEasy = scoreE;
        this.scoreMid = scoreM;
        this.scoreHard = scoreH;
        this.timeEasy = timeE;
        this.timeMid = timeM;
        this.timeHard = timeH;
    }
}
