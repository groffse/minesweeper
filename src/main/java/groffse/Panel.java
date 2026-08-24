package groffse;

public class Panel {
    private boolean isBomb;
    private int ID;
    private int adjacentBombs;
    private boolean isRevealed; // If true, the panel should reveal the number of adjacent bombs or that it is a bomb
    private boolean isFlagged;

    Panel(int ID) {this.ID = ID;}

    public void setBomb(boolean b) {
        isBomb = b;
    }
    public boolean isBomb() {
        return isBomb;
    }
    public void reveal() {
        if(isFlagged)
            throw new IllegalStateException("Panel is flagged and cannot be revealed!");
        isRevealed = true;
    }
    public void setFlag(boolean f) {
        if(isRevealed)
            throw new IllegalStateException("Panel is already revealed. A flag cannot be set!");
        isFlagged = f;
    }
    public int getAdjacentBombs () {return adjacentBombs;}
    public void setAdjacentBombs(int adjacentBombs) {this.adjacentBombs = adjacentBombs;}
    public boolean isRevealed() {return isRevealed;}
    public boolean isFlagged () {return isFlagged;}
    public int getID() {return ID;}
    public void reset() {
        isBomb = false;
        adjacentBombs = 0;
        isRevealed = false;
        isFlagged = false;
    }
}
