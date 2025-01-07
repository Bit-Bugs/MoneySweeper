package bitbugs.moneysweeper.backend;

import bitbugs.moneysweeper.gui.Difficulty;

import java.util.*;

public class Playground {
    private Field[][] fields;
    private Difficulty difficulty;
    private int difficultySize;

    public Playground(Difficulty difficulty) {
        this.difficulty = difficulty;

        if (difficulty.equals(Difficulty.EASY))
        {
            this.difficultySize = 8;
        }

        if (difficulty.equals(Difficulty.MID))
        {
            this.difficultySize = 16;
        }

        if (difficulty.equals(Difficulty.HARD))
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

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
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

    public boolean fieldHasMine(int x, int y){
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

                    if (fieldHasMine(xNeighbor, yNeighbor))
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
            if(!(fieldHasMine(x, y))){
                fields[x][y].setHasBomb(true);
            }
        }
    }

private class Position{
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


    public ArrayList<Field> calculateUncoverFields(int xPos, int yPos) {
        ArrayList<Field> result = new ArrayList<>();

        if (xPos < 0 || yPos >= difficultySize) {
            return result; //-> exit empty array out of range
        }
        if (fields[xPos][yPos].getTurnedOver())
        {
            //noch alle aufklappen die eh klar sind
            return result;  //-> exit empty array (field is still turned)
        }

        if (fields[xPos][yPos].getHasBomb()) {
            result.add(fields[xPos][yPos]); //-> exit with bomb as list item
            return result;
        }

        if (fields[xPos][yPos].getSurroundingMines() > 0){
            result.add(fields[xPos][yPos]); //-> exit with this field
            return result;
        }

        //nur mehr der Fall dass man ein Field geklickt hat dass keine nummer hat und keine bombe ist
        ArrayList<Position> calcList = new ArrayList<Position>();

        //berechnet alle Felder die aufgedeckt werden
        addSurroundingFields(xPos, yPos, calcList);

        //result list aufbauen
        for (Position position : calcList) {
            result.add(fields[position.x][position.y]);
        }

        return result;

    }


    private void addSurroundingFields(int xPos, int yPos,  ArrayList<Position> calcList){

        addOnlyNewFieldsToList(xPos, yPos, true,calcList);  //mitte mitte

        addOnlyNewFieldsToList(xPos-1,yPos-1, false, calcList); //links oben
        addOnlyNewFieldsToList(xPos-1, yPos, false, calcList); //links mitte
        addOnlyNewFieldsToList(xPos-1, yPos+1, false, calcList); //links unten
        addOnlyNewFieldsToList(xPos, yPos-1, false, calcList); //mitte oben
        addOnlyNewFieldsToList(xPos, yPos+1, false, calcList); //mitte unten
        addOnlyNewFieldsToList(xPos+1, yPos-1,false, calcList); //rechts oben
        addOnlyNewFieldsToList(xPos+1,yPos,false, calcList); //rechts mitte
        addOnlyNewFieldsToList(xPos+1,yPos+1,false, calcList); //rechts unten
    }

    private void addOnlyNewFieldsToList(int x, int y, boolean checked, ArrayList<Position> calcList) {
        if ( !isInRange(x,y) ) {
            return;
        }
        for (Position position : calcList) {
            if (position.x == x && position.y == y){
                return; //pos existiert schon in list
            }
        }
        calcList.add(new Position(x, y, checked, fields[x][y].getSurroundingMines()));
    }

    private boolean isInRange(int x, int y) {
        if (x<0 || y<0 ) {
            return false;
        }
        if (x >= getDifficultySize() || y >= getDifficultySize() ) {
            return false;
        }
        return true;
    }







//    public void UncoverAllTiles(){
//        for (int row = 0; row < fields.length; row++) {
//            for (int column = 0; column < fields[row].length; column++) {
//                uncoverTile(column, row);
//            }
//        }
//    }
}
