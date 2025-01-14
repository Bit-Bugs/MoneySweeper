package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;

import java.util.*;

public class Playground {
    // Represents the playing field as a 2D array of Field objects
    private Field[][] fields;

    // Stores the difficulty level of the game
    private Difficulty difficulty;

    // Array representing the size of the playground based on difficulty
    private int[] difficultySize;

    // Stores the bomb-count of the playground
    private int bombs;

    public Playground()
    {
    }

    // Constructor initializes the playground based on the selected difficulty
    public Playground(Difficulty difficulty, int[] difficultySize) {
        this.difficulty = difficulty;
        this.difficultySize = difficultySize;

        // Initialize the fields array with empty Field objects
        setFields(new Field[difficultySize[0]][difficultySize[1]]);
        for (int x = 0; x < difficultySize[0]; x++) {
            for (int y = 0; y < difficultySize[1]; y++) {
                this.fields[x][y] = new Field(x,y);
            }
        }

        // Place mines on the field
        placeMines();

        // Calculate the number of surrounding mines for each field
        calculateSurroundingMines();
    }

    public Playground(Difficulty difficulty) {
        this(difficulty, getGameboardSize(difficulty));
    }

    private static int[] getGameboardSize(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> new int[]{8, 8};
            case MID -> new int[]{16, 16};
            case HARD -> new int[]{30, 16};
            default -> throw new IllegalStateException("Unexpected value: " + difficulty);
        };
    }

    // Getters and setters for the fields, difficulty, and difficultySize
    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int[] getDifficultySize() {
        return difficultySize;
    }

    public void setDifficultySize(int[] difficultySize) {
        this.difficultySize = difficultySize;
    }

    // Toggles the "tagged" state of a field (used for marking suspected mines)
    public void tagField(int x, int y) {
        if (x > -1 && x < getDifficultySize()[0] && y > -1 && y < getDifficultySize()[1]) {
            fields[x][y].setIsTagged(!fields[x][y].getIsTagged());
        }
    }

    // Checks if a specific field contains a mine
    public boolean fieldHasMine(int x, int y) {
        if (x > -1 && x < getDifficultySize()[0] && y > -1 && y < getDifficultySize()[1]) {
            return fields[x][y].getHasBomb();
        }
        return false;
    }

    //Checks if player has won
    public boolean checkIfWon()
    {
        //Iterate through the plaground
        for (int x = 0; x < getDifficultySize()[0]; x++)
        {
            for (int y = 0; y <getDifficultySize()[1] ; y++)
            {
                //Condition 1: Check if every non-bomb-field is turned over
              if (!fieldIsTurnedOver(x,y) && fieldHasMine(x, y))
              {
                  return false;
              }

              //Condition 2: Check if every bomb-field is tagged
              if (!fieldIsTagged(x,y) && fieldHasMine(x, y))
              {
                  return false;
              }

            }
        }
        return true;
    }

    // Calculates the number of surrounding mines for each field
    private void calculateSurroundingMines() {
        // Relative positions of the eight neighboring fields
        int[][] positions = {
                {1, 0}, {1, 1}, {0, 1},
                {-1, 1}, {-1, 0}, {-1, -1},
                {0, -1}, {1, -1}
        };

        // Iterate over each field in the playground
        for (int x = 0; x < getDifficultySize()[0]; x++) {
            for (int y = 0; y < getDifficultySize()[1]; y++) {
                // Check all neighboring positions
                for (int[] pos : positions) {
                    int xNeighbor = x + pos[0];
                    int yNeighbor = y + pos[1];
                    // If a neighboring field has a mine, increment the count
                    if (fieldHasMine(xNeighbor, yNeighbor)) {
                        fields[x][y].setSurroundingMines(fields[x][y].getSurroundingMines() + 1);
                    }
                }
            }
        }
    }

    // Randomly places mines on the field
    private void placeMines() {
        Random rnd = new Random();
        int x, y;

       switch (difficulty)
       {
           case CUSTOM:
               break;

           case HARD:
               bombs = Integer.valueOf((int)(difficultySize[0] * difficultySize[1] * 0.25));

           case MID:
               bombs = Integer.valueOf((int)(difficultySize[0] * difficultySize[1] * 0.15));

           case EASY:
               bombs = Integer.valueOf((int)(difficultySize[0] * difficultySize[1] * 0.1));
       }

       while(bombs>0)
       {
           x = rnd.nextInt(difficultySize[0]);
           y = rnd.nextInt(difficultySize[1]);

           if(!fieldHasMine(x,y))
           {
               fields[x][y].setHasBomb(true);
               bombs--;
           }
       }
    }

    // Calculates all fields to uncover based on the given position
    public ArrayList<Field> calculateUncoverFields(int xPos, int yPos) {
        ArrayList<Field> result = new ArrayList<>();

        // Return an empty result if the position is out of bounds or already uncovered
        if (xPos < 0 || xPos >= getDifficultySize()[0] || yPos >= getDifficultySize()[1] || yPos < 0) {
            return result;
        }
        if (fieldIsTurnedOver(xPos, yPos)) {
            //noch alle aufklappen die eh klar sind
            return result;
        }

        // If the field contains a mine, uncover only this field
        if (fieldHasMine(xPos, yPos)) {
            result.add(fields[xPos][yPos]);
            return result;
        }

        // If the field has surrounding mines, uncover only this field
        if (fields[xPos][yPos].getSurroundingMines() > 0) {
            result.add(fields[xPos][yPos]);
            return result;
        }

        //nur mehr der Fall dass man ein Field geklickt hat dass keine nummer hat und keine bombe ist
        ArrayList<Field> calcList = new ArrayList<Field>();

        //berechnet alle Felder die aufgedeckt werden
        addSurroundingFields(xPos, yPos, calcList);

        //result list aufbauen
        for (Field field : calcList) {
            result.add(fields[field.x][field.y]);
        }

        return result;

    }

    public boolean fieldIsTurnedOver(int x, int y)
    {
        if (fields[x][y].getTurnedOver())
        {
            return true;
        }
        return false;
    }

    public boolean fieldIsTagged(int x, int y)
    {
        if(fields[x][y].getIsTagged())
        {
            return true;
        }
        return false;
    }

    // Adds all valid surrounding fields to the calculation list
    private void addSurroundingFields(int xPos, int yPos, ArrayList<Field> calcList) {
        addOnlyNewFieldsToList(xPos, yPos, true, calcList);  //mitte mitte
        addOnlyNewFieldsToList(xPos - 1, yPos - 1, false, calcList); //links oben
        addOnlyNewFieldsToList(xPos - 1, yPos, false, calcList); //links mitte
        addOnlyNewFieldsToList(xPos - 1, yPos + 1, false, calcList); //links unten
        addOnlyNewFieldsToList(xPos, yPos - 1, false, calcList); //mitte oben
        addOnlyNewFieldsToList(xPos, yPos + 1, false, calcList); //mitte unten
        addOnlyNewFieldsToList(xPos + 1, yPos - 1, false, calcList); //rechts oben
        addOnlyNewFieldsToList(xPos + 1, yPos, false, calcList); //rechts mitte
        addOnlyNewFieldsToList(xPos + 1, yPos + 1, false, calcList); //rechts unten
    }

    private void addOnlyNewFieldsToList(int x, int y, boolean checked, ArrayList<Field> calcList) {
        if (!isInRange(x, y)) {
            return;
        }
        for (Field field : calcList) {
            if (field.x == x && field.y == y) {
                return; // Skip if the position already exists in the list
            }
        }
        calcList.add(new Field(x, y, checked, fields[x][y].getSurroundingMines()));

        if (fields[x][y].getSurroundingMines() == 0){
            addSurroundingFields(x,y, calcList);
        }
    }

    // Checks if the given position is within the field's bounds
    private boolean isInRange(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= getDifficultySize()[0] || y >= getDifficultySize()[1]) {
            return false;
        }
        return true;
    }

    public Field getField(int x, int y) {
        return fields[x][y];
    }

    public int getBombs()
    {
        return this.bombs;
    }
}
