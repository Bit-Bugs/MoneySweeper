package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;
import bitbugs.moneysweeper.gui.dto.ScoreboardEntry;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardDataHandling {

    private static ArrayList<ScoreboardEntry> scoreboard = new ArrayList<>();
    static String tempDir = System.getProperty("java.io.tmpdir");
    static File scoreboardFile = new File(tempDir, "Scoreboard.txt");

    public ScoreboardDataHandling()
    {
        loadFromFile();
    }


    public static void save(ScoreboardEntry scoreboardEntry)
    {
        scoreboard.add(scoreboardEntry);
    }

    public static ScoreboardEntry[] loadScoreboard(Difficulty dif)
    {
        ScoreboardEntry[] scoreboardUsers = {};

        for (ScoreboardEntry entry : scoreboard)
        {

        }

        return scoreboardUsers;
    }

    public static void writeToFile()
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(scoreboardFile)))
        {
            for(ScoreboardEntry line : scoreboard)
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




    private static void loadFromFile()
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(scoreboardFile)))
        {
            String line = reader.readLine().trim();
            while(!line.isBlank())
            {
                ScoreboardEntry newEntry = new ScoreboardEntry(line.split("\\s+")[0], line.split("\\s+")[1]);
                scoreboard.add(newEntry);
                line = reader.readLine();
            }
        }catch (FileNotFoundException ex)
        {
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void sortScoreboard()
    {

    }
}
