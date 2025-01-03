package bitbugs.moneysweeper.backend;

import java.util.Random;

public class Playground {
    private Field[][] fields;
    private String difficulty;
    private int difficultySize;

    public Playground(String difficulty) {
        this.difficulty = difficulty;

        if (difficulty == "easy")
        {
            this.difficultySize = 10;
        }

        if (difficulty == "mid")
        {
            this.difficultySize = 15;
        }

        if (difficulty == "hard")
        {
            this.difficultySize = 25;
        }

        this.fields = new Field[difficultySize] [difficultySize];
        for (int x = 0; x < difficultySize; x++)
        {
            for (int y = 0; y < difficultySize; y++)
            {
                this.fields[x][y] = new Field();
            }
        }
        PlaceMines();
        //CalculateSurroundingMines <--->
    }


    public Field[][] getFields() {
        return fields;
    }
    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficultySize() {
        return difficultySize;
    }
    public void setDifficultySize(int difficultySize) {
        this.difficultySize = difficultySize;
    }


    private boolean FieldHasMine(int x, int y){
        if(x>-1 && x<difficultySize && y>-1 && y<difficultySize)
        {
            return fields[x][y].getHasBomb();
        }
        return false;
    }

    private void PlaceMines(){
        Random rnd = new Random();
        int x = 0;
        int y = 0;

        for (int i = 0; i < difficultySize ; i++)
        {
            x = rnd.nextInt(difficultySize);
            y = rnd.nextInt(difficultySize);
            if(!(FieldHasMine(x, y))){
                fields[x][y].setHasBomb(true);
            }
        }
    }
}
