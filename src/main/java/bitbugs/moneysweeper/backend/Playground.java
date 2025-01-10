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

    public Playground() {
    }

    // Constructor initializes the playground based on the selected difficulty
    public Playground(Difficulty difficulty, int[] difficultySize) {
        this.difficulty = difficulty;
        this.difficultySize = difficultySize;

        // Initialize the fields array with empty Field objects
        setFields(new Field[difficultySize[0]][difficultySize[1]]);
        for (int x = 0; x < difficultySize[0]; x++) {
            for (int y = 0; y < difficultySize[1]; y++) {
                this.fields[x][y] = new Field();
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
            if (!fields[x][y].getIsTagged()) {
                fields[x][y].setIsTagged(true);
            } else {
                fields[x][y].setIsTagged(false);
            }
        }
    }

    // Checks if a specific field contains a mine
    public boolean fieldHasMine(int x, int y) {
        if (x > -1 && x < getDifficultySize()[0] && y > -1 && y < getDifficultySize()[1]) {
            return fields[x][y].getHasBomb();
        }
        return false;
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

        for (int i = 0; i < difficultySize[0]; i++) {
            x = rnd.nextInt(difficultySize[0]);
            y = rnd.nextInt(difficultySize[1]);

            // Only place a mine if the field does not already have one
            if (!fieldHasMine(x, y)) {
                fields[x][y].setHasBomb(true);
            }
        }
    }

    // Helper class representing a position on the field
    private class Position {
        public int x;
        public int y;
        public boolean isChecked;
        public int surroundingMines;

        public Position(int x, int y, boolean isChecked, int surroundingMines) {
            this.x = x;
            this.y = y;
            this.isChecked = isChecked;
            this.surroundingMines = surroundingMines;
        }
    }

    // Calculates all fields to uncover based on the given position
    public ArrayList<Field> calculateUncoverFields(int xPos, int yPos) {
        ArrayList<Field> result = new ArrayList<>();

        // Return an empty result if the position is out of bounds or already uncovered
        if (xPos < 0 || xPos >= getDifficultySize()[0] || yPos >= getDifficultySize()[1] || yPos < 0) {
            return result;
        }
        if (fields[xPos][yPos].getTurnedOver()) {
            return result;
        }

        // If the field contains a mine, uncover only this field
        if (fields[xPos][yPos].getHasBomb()) {
            result.add(fields[xPos][yPos]);
            return result;
        }

        // If the field has surrounding mines, uncover only this field
        if (fields[xPos][yPos].getSurroundingMines() > 0) {
            result.add(fields[xPos][yPos]);
            return result;
        }

        // If the field is empty, recursively uncover all connected empty fields
        ArrayList<Position> calcList = new ArrayList<>();
        addSurroundingFields(xPos, yPos, calcList);

        for (Position position : calcList) {
            result.add(fields[position.x][position.y]);
        }

        return result;
    }

    // Adds all valid surrounding fields to the calculation list
    private void addSurroundingFields(int xPos, int yPos, ArrayList<Position> calcList) {
        addOnlyNewFieldsToList(xPos, yPos, true, calcList); // Center

        addOnlyNewFieldsToList(xPos - 1, yPos - 1, false, calcList); // Top-left
        addOnlyNewFieldsToList(xPos - 1, yPos, false, calcList); // Top
        addOnlyNewFieldsToList(xPos - 1, yPos + 1, false, calcList); // Top-right
        addOnlyNewFieldsToList(xPos, yPos - 1, false, calcList); // Left
        addOnlyNewFieldsToList(xPos, yPos + 1, false, calcList); // Right
        addOnlyNewFieldsToList(xPos + 1, yPos - 1, false, calcList); // Bottom-left
        addOnlyNewFieldsToList(xPos + 1, yPos, false, calcList); // Bottom
        addOnlyNewFieldsToList(xPos + 1, yPos + 1, false, calcList); // Bottom-right
    }

    // Adds a new field to the calculation list if it is not already present
    private void addOnlyNewFieldsToList(int x, int y, boolean checked, ArrayList<Position> calcList) {
        if (!isInRange(x, y)) {
            return;
        }
        for (Position position : calcList) {
            if (position.x == x && position.y == y) {
                return; // Skip if the position already exists in the list
            }
        }
        calcList.add(new Position(x, y, checked, fields[x][y].getSurroundingMines()));
    }

    // Checks if the given position is within the field's bounds
    private boolean isInRange(int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        return x < getDifficultySize()[0] && y < getDifficultySize()[1];
    }

    public boolean checkIfWon() {
        return true;
    }
}
