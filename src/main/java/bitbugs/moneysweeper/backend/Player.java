package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;

import java.time.LocalTime;

public class Player{
    private final String name;
    private String timeEasy="00:00:00", timeMid="00:00:00", timeHard="00:00:00";
    private int scoreEasy=0, scoreMid=0, scoreHard=0;

    public int getScore(Difficulty dif) {
        //Liefert den Score der gewollten Difficulty zurueck.
        return switch (dif) {
            case EASY -> scoreEasy;
            case MID -> scoreMid;
            case HARD -> scoreHard;
            default -> 0;
        };
    }

    public int getEasyScore()
    {
        //fuer das Sortieren des Scoreboards.
        return scoreEasy;
    }
    public int getMidScore()
    {
        //fuer das Sortieren des Scoreboards.
        return scoreMid;
    }
    public int getHardScore()
    {
        //fuer das Sortieren des Scoreboards.
        return scoreHard;
    }

    public void setScore(int score, Difficulty dif) {
        //fuer das Setzten eines Scores einer speziellen Difficulty.
        switch (dif) {
            case EASY -> scoreEasy = score;
            case MID -> scoreMid = score;
            case HARD -> scoreHard = score;
            default -> {}
        }
    }

    public void addScore(Difficulty dif) {
        //Fuegt nach einem win, dem Difficulty equivalenten score 1 hinzu.
        switch (dif) {
            case EASY -> scoreEasy += 1;
            case MID -> scoreMid += 1;
            case HARD -> scoreHard += 1;
            default -> {}
        }
    }

    public void setTime(String time, Difficulty dif)
    {
        //Setzte die shortest completion time der angegeben Difficulty.
        switch (dif) {
            case EASY -> timeEasy = time;
            case MID -> timeMid = time;
            case HARD -> timeHard = time;
            default -> {}
        }
    }

    public String getTime(Difficulty dif)
    {
        //gibt die shortest completion time der angegeben Difficulty zurueck.
        return switch (dif) {
            case EASY -> timeEasy;
            case MID -> timeMid;
            case HARD -> timeHard;
            default -> "0";
        };
    }

    public boolean isShorterTime(String time, Difficulty dif)
    {
        //Schaut, ob die angegebene completion time kuerzer, als die schon vorhandene ist.
        LocalTime t1 = LocalTime.parse(this.getTime(dif));
        LocalTime t2 = LocalTime.parse(time);
        String shorterTime = t1.isBefore(t2) ? this.getTime(dif) : time;

        return shorterTime.equals(time);
    }

    public LocalTime getEasyTime()
    {
        //fuer das Sortieren des Scoreboards.
        return LocalTime.parse(timeEasy);
    }
    public LocalTime getMidTime()
    {
        //fuer das Sortieren des Scoreboards.
        return LocalTime.parse(timeMid);
    }
    public LocalTime getHardTime()
    {
        ///fuer das Sortieren des Scoreboards.
        return LocalTime.parse(timeHard);
    }

    public String getName()
    {
        //Liefert den Namen des Users zurueck.
        return name;
    }

    @Override
    public String toString() {
        //Fuer das Speichern in das file.
        return name + "," + scoreEasy + "," + scoreMid + "," + scoreHard + "," + timeEasy + "," + timeMid + "," + timeHard;
    }

    public Player(String dif, int score, String name, String time) {
        //Constructor, wenn ein neuer User erstellt wird.
        Difficulty difficulty = Difficulty.valueOf(dif);

        this.name = name;
        setScore(score, difficulty);
        setTime(time, difficulty);
    }

    public Player(String name, int scoreE, int scoreM, int scoreH, String timeE, String timeM, String timeH)
    {
        //Constructor, wenn ein User vom File geladen wird.
        this.name = name;
        this.scoreEasy = scoreE;
        this.scoreMid = scoreM;
        this.scoreHard = scoreH;
        this.timeEasy = timeE;
        this.timeMid = timeM;
        this.timeHard = timeH;
    }
}
