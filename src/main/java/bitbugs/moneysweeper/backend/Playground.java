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
            this.difficultySize = 8;
        }

        if (difficulty == "mid")
        {
            this.difficultySize = 16;
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
        placeMines();
        calculateSurroundingMines();
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



    public void tagField(int x, int y) {
        Field field = fields[x][y];
        if ((!field.getIsTagged()))
        {
            field.setIsTagged(true);
        }
        if ((field.getIsTagged()))
        {
            field.setIsTagged(false);
        }
    }

    public boolean FieldHasMine(int x, int y){
        if(x>-1 && x<difficultySize && y>-1 && y<difficultySize)
        {
            return fields[x][y].getHasBomb();
        }
        return false;
    }

    private void calculateSurroundingMines(){
         int[][] positions ={
                {1,0},{1,1},{0,1},
                {-1,1},{-1,0},{-1,-1},
                {0,-1},{1,-1}
         };
        for (int x = 0; x < difficultySize; x++)
        {
            for (int y = 0; y < difficultySize; y++)
            {
                Field field = fields[x][y];
                for (int[] pos: positions)
                {
                    int xNeighbor = x+pos[0];
                    int yNeighbor = y+pos[1];

                    if (FieldHasMine(xNeighbor, yNeighbor))
                    {
                        field.setSurroundingMines(field.getSurroundingMines()+1);
                    }
                }
            }
        }
    }


    private void placeMines(){
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

    // Covers/Uncovers one Tile
    private void CoverUncoverTile(int xPos, int yPos, boolean state){
        fields[xPos][yPos].setTurnedOver(state);
    }

    //Uncovers all Tiles in the Field Array
    private void UncoverAllTiles(){
        for (int row = 0; row < fields.length; row++) {
            for (int column = 0; column < fields[row].length; column++) {
                CoverUncoverTile(column, row, true);
            }
        }
    }
}
