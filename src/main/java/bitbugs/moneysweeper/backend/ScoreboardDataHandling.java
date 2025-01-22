package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardDataHandling {

    private static ArrayList<Player> scoreboard = new ArrayList<>();
    static String tempDir = System.getProperty("java.io.tmpdir");
    static File scoreboardFile = new File(tempDir, "Scoreboard.txt");

    public static boolean isNameTaken(String name)
    {
        //geht alle User durch, und schaut nach, ob der Name des Users schon existiert.
        for (Player user : scoreboard)
        {
            if (user.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }


    public static void save(String username, String time, Difficulty dif)
    {
        //Wenn man gewonnen hat, wird der Highscore entweder um 1 erhöht, oder der User neu angelegt.
        for (Player user : scoreboard)
        {
            if (isNameTaken(username))
            {
                LocalTime t1 = LocalTime.parse(user.getTime(dif));
                LocalTime t2 = LocalTime.parse(time);
                String shorterTime = t1.isBefore(t2) ? user.getTime(dif) : time;

                user.addScore(dif);
                if(shorterTime.equals(time))
                {
                    user.setTime(time, dif);
                }
            }else
            {
                Player newUser = new Player(dif.toString(), 1, username, time);
                scoreboard.add(newUser);
                break;
            }
        }
        if (scoreboard.isEmpty()) //Wenn dieser win der erste ist.
        {
            Player newUser = new Player(dif.toString(), 1, username, time);
            scoreboard.add(newUser);
        }
        writeToFile();
    }

    public static ScoreboardEntry[] loadScoreboard(Difficulty dif)
    {
        //macht alle Daten zu ScoreboardEntries, und steckt die dann in ein Array
        sortScoreboard(dif);
        ArrayList<ScoreboardEntry> tempScoreboardUsers = new ArrayList<>();

        for (Player user : scoreboard)
        {
            if (user.getScore(dif) != 0)
            {
                ScoreboardEntry entry = new ScoreboardEntry(user.getScore(dif), user.getName(), user.getTime(dif));
                tempScoreboardUsers.add(entry);
            }
        }

        return tempScoreboardUsers.toArray(new ScoreboardEntry[0]);
    }

    public static void writeToFile()
    {
        //schreibt alle Daten in das Scoreboard file im temp ordner des Users.
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(scoreboardFile)))
        {
            for(Player line : scoreboard)
            {
                writer.write(line.toString());
                writer.newLine();
            }

            System.out.println("File saved to:" + scoreboardFile.getAbsolutePath());
        }catch (IOException ex)
        {
            ex.printStackTrace();
            System.out.println("Something went wrong while saving the data.");
        }
    }




    public static void loadFromFile()
    {
        //Laed die Daten aus dem File im Temp order des Users. Falls das File nicht existiert, passiert nichts.
        try(BufferedReader reader = new BufferedReader(new FileReader(scoreboardFile)))
        {
            String line = reader.readLine();
            while(line != null)
            {
                if(!line.isBlank())
                {
                    List<String> data = List.of(line.split(","));
                    Player newEntry = new Player(data.get(0), Integer.parseInt(data.get(1)), Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)), data.get(4), data.get(5), data.get(6));
                    scoreboard.add(newEntry);
                }
                line = reader.readLine();
            }
        }catch (FileNotFoundException ex)
        {
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void sortScoreboard(Difficulty dif)
    {
        //Hier wird das Scoreboard sortiert, basierend darauf, welche Difficulty ausfewählt wurde.
        switch (dif)
            {
                case EASY:
                    scoreboard.sort(Comparator.comparing(Player::getEasyScore));
                    break;
                case MID:
                    scoreboard.sort(Comparator.comparing(Player::getMidScore));
                    break;
                case HARD:
                    scoreboard.sort(Comparator.comparing(Player::getHardScore));
                    break;
                default:
                    break;
            }
    }
}
