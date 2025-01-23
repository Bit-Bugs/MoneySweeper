package bitbugs.moneysweeper.backend;

public class Field {
    public int x;
    public int y;
    public boolean checked;
    private boolean isTagged;
    private boolean hasBomb;
    private int surroundingMines;
    private boolean turnedOver;


    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasBomb = false;
        this.turnedOver = false;
        this.isTagged = false;
        this.surroundingMines = 0;
    }

    public Field(int x, int y, boolean checked, int surroundingMines) {
        this.x = x;
        this.y = y;
        this.checked = checked;
        this.hasBomb = false;
        this.turnedOver = false;
        this.isTagged = false;
        this.surroundingMines = surroundingMines;
    }

    public boolean getIsTagged() {
        return isTagged;
    }

    public void setIsTagged(boolean isTagged) {
        this.isTagged = isTagged;
    }

    public boolean getHasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

    public boolean getTurnedOver() {
        return turnedOver;
    }

    public void setTurnedOver(boolean turnedOver) {
        this.turnedOver = turnedOver;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
