package groffse;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private ArrayList<ArrayList<Panel>> panel_grid;
    private int width;
    private int height;
    private int numberOfBombs;

    /*Public methods*/

    public Grid() {
        panel_grid = new ArrayList<ArrayList<Panel>>();
        width = 0;
        height = 0;
        numberOfBombs = 0;
    }

    public void reset() {
        for(ArrayList<Panel> panel_row : panel_grid) {
            for(Panel panel_index : panel_row) {
                panel_index.reset();
            }
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        if(numberOfBombs > width*height)
            throw new IllegalArgumentException("Number of bombs has to be less than height * width");
        this.numberOfBombs = numberOfBombs;
    }

    public int getHeight() {return height;}

    public int getWidth() {return width;}

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getFlaggedPanels() {
        int flaggedPanels = 0;
        for(ArrayList<Panel> panel_row : panel_grid) {
            for(Panel panel_index : panel_row) {
                if(panel_index.isRevealed())
                    flaggedPanels++;
            }
        }
        return flaggedPanels;
    }

    public int getRevealedPanels() {
        int revealedPanels = 0;
        for(ArrayList<Panel> panel_row : panel_grid) {
            for(Panel panel_index : panel_row) {
                if(panel_index.isRevealed())
                    revealedPanels++;
            }
        }
        return revealedPanels;
    }

    // Returns a shallow copy of panel grid
    public ArrayList<ArrayList<Panel>> getPanelGrid() {
        return new ArrayList<ArrayList<Panel>>(panel_grid);
    }

    public void floodFill(int i, int j, int lastV) {
        // Make sure we are not out of bounds
        if( (j < width && j >= 0) && ( i < height && i >= 0) ) {
            // If neighbour is zero and not isRevealed()
            if (panel_grid.get(i).get(j).getAdjacentBombs() == 0 && !panel_grid.get(i).get(j).isRevealed() && !panel_grid.get(i).get(j).isFlagged() && !panel_grid.get(i).get(j).isBomb()) {

                panel_grid.get(i).get(j).reveal();
                int v = panel_grid.get(i).get(j).getAdjacentBombs();
                floodFill(i, j+1, v);
                floodFill(i, j-1, v);

                floodFill(i-1, j+1, v);
                floodFill(i-1, j-1, v);
                floodFill(i-1, j, v);

                floodFill(i+1, j, v);
                floodFill(i+1, j+1, v);
                floodFill(i+1, j-1, v);
            }

            else if(panel_grid.get(i).get(j).getAdjacentBombs() != 0 && lastV == 0 && !panel_grid.get(i).get(j).isRevealed() && !panel_grid.get(i).get(j).isFlagged() && !panel_grid.get(i).get(j).isBomb()) {
                panel_grid.get(i).get(j).reveal();
                int v = panel_grid.get(i).get(j).getAdjacentBombs();
                floodFill(i, j+1, v);
                floodFill(i, j-1, v);

                floodFill(i-1, j+1, v);
                floodFill(i-1, j-1, v);
                floodFill(i-1, j, v);

                floodFill(i+1, j, v);
                floodFill(i+1, j+1, v);
                floodFill(i+1, j-1, v);
            }

        }
        return;
    }

    public void generateBoard(int skip_bomb) {
        if( (height != width) || height < 1 || width < 1)
            throw new IllegalArgumentException("Width and height has to be equal and greater than zero!");
        // We don't want to alter the 2dim array if we continue with the same size
        if( (height != panel_grid.size()) || (width != panel_grid.get(0).size()) ) {
            panel_grid.clear();

            int panelID = 0;

            for(int i = 0; i < height; i++) {
                panel_grid.add(new ArrayList<Panel>());
                for(int j = 0; j < width; j++) {
                    panel_grid.get(i).add(new Panel(panelID));
                    panelID++;
                }
            }
        }
        reset();
        generate_bomb_positions(skip_bomb);
        setAdjacentBombsForPanels();
    }

    /*Private methods*/

    // Row and column is an index of the first clicked panel. To avoid losing on the first click, this panel will not contain a bomb
    private void generate_bomb_positions(int skip_bomb) {
        ArrayList<Integer> bomb_positions = new ArrayList<Integer>();
        Random rand = new Random();
        // Clearing array with neutral values
        for(int k = 0; k < numberOfBombs; k++) {
            bomb_positions.add(-50);
        }

        for(int i = 0; i < numberOfBombs;) {
            int n = rand.nextInt(height*width);

            // Avoiding to place bomb on the first click
            if(n == skip_bomb)
                continue;
            if(bomb_positions.indexOf(n) < 0) {
                bomb_positions.set(i, n);
                i++;
            }
        }

        // Sets bombs
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                for(int k = 0; k < numberOfBombs; k++) {
                    if(panel_grid.get(i).get(j).getID() == bomb_positions.get(k)) {
                        panel_grid.get(i).get(j).setBomb(true);
                        break;
                    }
                }
            }
        }
    }

    // Update all panels with adjacent bombs numbers
    // Must be called after generate_bomb_poisitions
    private void setAdjacentBombsForPanels() {
        for(int i = 0; i < height; i++) {
            int bombs_around = 0;
            for(int j = 0; j < width; j++) {

                if(!panel_grid.get(i).get(j).isBomb()) {

                    // Check upper left corner
                    if ( (i - 1 >= 0) && (j - 1 >= 0) ) {
                        if((panel_grid.get(i - 1).get(j - 1).isBomb())) {
                            bombs_around++;
                        }
                    }
                    // Check straight up
                    if ((i - 1 >= 0)) {
                        if ((panel_grid.get(i - 1).get(j).isBomb())) {
                            bombs_around++;
                        }
                    }
                    // Check upper right corner
                    if ((i - 1 >= 0 ) && (j+1 < width) ) {
                        if( (panel_grid.get(i - 1).get(j + 1).isBomb()) ) {
                            bombs_around++;
                        }
                    }
                    // Check to right
                    if ((j + 1 < width) ) {
                        if ( (panel_grid.get(i).get(j + 1).isBomb()) ) {
                            bombs_around++;
                        }
                    }
                    // Check lower right corner
                    if ((i + 1 < height && j + 1 < width) ) {
                        if( (panel_grid.get(i + 1).get(j + 1).isBomb())) {
                            bombs_around++;
                        }
                    }
                    // Check straight below
                    if ((i + 1 < height) ) {
                        if( (panel_grid.get(i + 1).get(j).isBomb())) {
                            bombs_around++;
                        }
                    }
                    // Check lower left corner
                    if ((i + 1 < height && j - 1 >= 0) ) {
                        if( (panel_grid.get(i + 1).get(j - 1).isBomb())) {
                            bombs_around++;
                        }
                    }
                    // Check to left
                    if ((j - 1 >= 0) ) {
                        if( (panel_grid.get(i).get(j - 1).isBomb())) {
                            bombs_around++;
                        }
                    }
                    panel_grid.get(i).get(j).setAdjacentBombs(bombs_around);
                    bombs_around = 0;
                }
            }
        }
    }

    public int getFlagsAdjacentToPanel(int i, int j) {

        int flagsAround = 0;


        // TODO: Make boundaries.
        if(i + 1 < height) {
            if (panel_grid.get(i + 1).get(j).isFlagged()) {
                flagsAround++;
            }
        }
        if(i + 1 < height && j + 1 < width) {
            if (panel_grid.get(i + 1).get(j + 1).isFlagged() ) {
                flagsAround++;
            }
        }
        if(i + 1 < height && j-1 >= 0) {
            if (panel_grid.get(i + 1).get(j - 1).isFlagged() ) {
                flagsAround++;
            }
        }
        if(j + 1 < width) {
            if (panel_grid.get(i).get(j + 1).isFlagged() ) {
                flagsAround++;
            }
        }
        if(j-1 >= 0) {
            if (panel_grid.get(i).get(j - 1).isFlagged() ) {
                flagsAround++;
            }
        }
        if(j + 1 < width && i-1 >= 0) {
            if (panel_grid.get(i - 1).get(j + 1).isFlagged() ) {
                flagsAround++;
            }
        }
        if(i-1 >= 0) {
            if (panel_grid.get(i - 1).get(j).isFlagged() ) {
                flagsAround++;
            }
        }
        if(i-1 >= 0 && j-1 >= 0) {
            if (panel_grid.get(i - 1).get(j - 1).isFlagged() ) {
                flagsAround++;
            }
        }
        return flagsAround;
    }

    public void printPanelGrid() {
        for(int row = 0; row < height; row++) {
            System.out.print("|");
            for(int column = 0; column < width; column++) {
                if(panel_grid.get(row).get(column).isRevealed()) {
                    if (panel_grid.get(row).get(column).isBomb())
                        System.out.print("*|");
                    else {
                        System.out.print(panel_grid.get(row).get(column).getAdjacentBombs() + "|");
                    }
                }
                else {
                    System.out.print("x|");
                }
            }
            System.out.println("");
        }
        System.out.println("_________");
    }
}