package groffse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    Grid grid;

    @Before
    public void setUp() {
        grid = new Grid();
    }

    @Test
    public void testSetHeight() {
        int height = 9;
        grid.setHeight(height);
        assertEquals(height, grid.getHeight());
    }

    @Test
    public void testSetWidth() {
        int width = 2;
        grid.setWidth(width);
        assertEquals(width, grid.getWidth());
    }

    @Test
    public void testSetNumberOfBombs() {
        grid.setWidth(10);
        grid.setHeight(10);
        int numberOfBombs = 10;
        grid.setNumberOfBombs(numberOfBombs);
        assertEquals(numberOfBombs, grid.getNumberOfBombs());
    }

    @Test
    public void testThatExceptionIsThrownWhenNumberOfBombsIsGreaterThanPanels() {
        int height = 2;
        int width = 2;
        int numberOfBombs = (height*width) + 1;
        grid.setHeight(height);
        grid.setWidth(width);
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        ()-> grid.setNumberOfBombs(numberOfBombs),
                        "The number of bombs is greater than the number of panels. Expected setNumberOfBombs() to throw");
        assertTrue(thrown.getMessage().contains("Number of bombs has to be less than height * width"));
    }

    // If height and width does not a equal, an exception should be thrown when we fill the grid with bombs and numbers
    @Test
    public void testThatExcpetionIsThrownWhenGridIsNotSquare(){
        grid.setHeight(4);
        grid.setHeight(5);
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class,
                        () -> grid.generateBoard(15),
                        "Grid is not a square. Expected generateBoard() to throw");
        assertTrue(thrown.getMessage().contains("Width and height has to be equal and greater than zero!"));
    }

    @Test
    public void testReset() {
        grid.setHeight(5);
        grid.setWidth(5);
        grid.setNumberOfBombs(5);
        grid.generateBoard(15);
        grid.reset();
        assertEquals(0, grid.getFlaggedPanels());
        assertEquals(0, grid.getRevealedPanels());
    }

    // Test that the panel grid actually contains the number of bombs by counting each panel, and comparing to the numberOfBombs field
    @Test
    public void testThatPanelGridContainsNumberOfBombs() {
        // 4x4 grid
        grid.setHeight(4);
        grid.setWidth(4);
        grid.setNumberOfBombs(5);
        grid.generateBoard(15);
        int actualNumberOfBombs = 0;
        for(ArrayList<Panel> panel_row : grid.getPanelGrid()) {
            for(Panel panel_index : panel_row) {
                if(panel_index.isBomb())
                    actualNumberOfBombs++;
            }
        }
        assertEquals(grid.getNumberOfBombs(), actualNumberOfBombs);
    }

    /*In this case all panels should be revealed since there are no bombs*/
    @Test
    public void testThatAllPanelsAreRevealedByFloodFill() {
        int height = 5;
        int width = 5;
        grid.setHeight(height);
        grid.setWidth(width);
        grid.setNumberOfBombs(0);
        grid.generateBoard(-1);
        grid.floodFill(0,0,0);
        assertEquals(height*width, grid.getRevealedPanels());
    }

    /*Manually set eight flags around a panel and see that the function returns eight*/
    @Test
    public void testGetFlagsAdjacentToPanel() {
        int height = 5;
        int width = 5;
        grid.setHeight(height);
        grid.setWidth(width);
        grid.setNumberOfBombs(0);
        grid.generateBoard(-1);

        grid.getPanelGrid().get(0).get(0).setFlag(true);
        grid.getPanelGrid().get(0).get(1).setFlag(true);
        grid.getPanelGrid().get(0).get(2).setFlag(true);
        grid.getPanelGrid().get(1).get(0).setFlag(true);
        grid.getPanelGrid().get(2).get(0).setFlag(true);
        grid.getPanelGrid().get(2).get(1).setFlag(true);
        grid.getPanelGrid().get(1).get(2).setFlag(true);
        grid.getPanelGrid().get(2).get(2).setFlag(true);

        assertEquals(8, grid.getFlagsAdjacentToPanel(1,1));
    }

    @Test
    public void testGetFlagsAdjacentToPanelWhenZeroFlags() {
        int height = 5;
        int width = 5;
        grid.setHeight(height);
        grid.setWidth(width);
        grid.setNumberOfBombs(0);
        grid.generateBoard(-1);
        assertEquals(0, grid.getFlagsAdjacentToPanel(1,1));
    }

    /*Test when given panel is in the four corners of the grid*/
    @Test
    public void testGetFlagsAdjacentToPanelEdgeCases() {
        int height = 5;
        int width = 5;
        grid.setHeight(height);
        grid.setWidth(width);
        grid.setNumberOfBombs(0);
        grid.generateBoard(-1);
        assertEquals(0, grid.getFlagsAdjacentToPanel(0,0));
        assertEquals(0, grid.getFlagsAdjacentToPanel(0,width-1));
        assertEquals(0, grid.getFlagsAdjacentToPanel(height-1,0));
        assertEquals(0, grid.getFlagsAdjacentToPanel(height-1,width-1));
    }
}