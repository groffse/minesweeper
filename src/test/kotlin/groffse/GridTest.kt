package groffse

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue

class GridTest {

    private lateinit var grid: Grid

    @Before
    fun setUp() {
        grid = Grid()
    }

    @Test
    fun testSetHeight() {
        val height = 9
        grid.setHeight(height)
        assertEquals(height, grid.getHeight())
    }

    @Test
    fun testSetWidth() {
        val width = 2
        grid.setWidth(width)
        assertEquals(width, grid.getWidth())
    }

    @Test
    fun testSetNumberOfBombs() {
        grid.setWidth(10)
        grid.setHeight(10)
        val numberOfBombs = 10
        grid.setNumberOfBombs(numberOfBombs)
        assertEquals(numberOfBombs, grid.getNumberOfBombs())
    }

    @Test
    fun testThatExceptionIsThrownWhenNumberOfBombsIsGreaterThanPanels() {
        val height = 2
        val width = 2
        val numberOfBombs = (height * width) + 1
        grid.setHeight(height)
        grid.setWidth(width)
        val thrown = assertThrows(IllegalArgumentException::class.java) {
            grid.setNumberOfBombs(numberOfBombs)
        }
        assertTrue(thrown.message?.contains("Number of bombs has to be less than height * width") == true)
    }

    @Test
    fun testThatExcpetionIsThrownWhenGridIsNotSquare() {
        grid.setHeight(4)
        grid.setHeight(5)
        val thrown = assertThrows(IllegalArgumentException::class.java) {
            grid.generateBoard(15)
        }
        assertTrue(thrown.message?.contains("Width and height has to be equal and greater than zero!") == true)
    }

    @Test
    fun testReset() {
        grid.setHeight(5)
        grid.setWidth(5)
        grid.setNumberOfBombs(5)
        grid.generateBoard(15)
        grid.reset()
        assertEquals(0, grid.getFlaggedPanels())
        assertEquals(0, grid.getRevealedPanels())
    }

    @Test
    fun testThatPanelGridContainsNumberOfBombs() {
        grid.setHeight(4)
        grid.setWidth(4)
        grid.setNumberOfBombs(5)
        grid.generateBoard(15)
        var actualNumberOfBombs = 0
        for (panelRow in grid.getPanelGrid()) {
            for (panelIndex in panelRow) {
                if (panelIndex.isBomb()) {
                    actualNumberOfBombs++
                }
            }
        }
        assertEquals(grid.getNumberOfBombs(), actualNumberOfBombs)
    }

    @Test
    fun testThatAllPanelsAreRevealedByFloodFill() {
        val height = 5
        val width = 5
        grid.setHeight(height)
        grid.setWidth(width)
        grid.setNumberOfBombs(0)
        grid.generateBoard(-1)
        grid.floodFill(0, 0, 0)
        assertEquals(height * width, grid.getRevealedPanels())
    }

    @Test
    fun testGetFlagsAdjacentToPanel() {
        val height = 5
        val width = 5
        grid.setHeight(height)
        grid.setWidth(width)
        grid.setNumberOfBombs(0)
        grid.generateBoard(-1)

        grid.getPanelGrid()[0][0].setFlag()
        grid.getPanelGrid()[0][1].setFlag()
        grid.getPanelGrid()[0][2].setFlag()
        grid.getPanelGrid()[1][0].setFlag()
        grid.getPanelGrid()[1][2].setFlag()
        grid.getPanelGrid()[2][0].setFlag()
        grid.getPanelGrid()[2][1].setFlag()
        grid.getPanelGrid()[2][2].setFlag()

        assertEquals(8, grid.getFlagsAdjacentToPanel(1, 1))
    }

    @Test
    fun testGetFlagsAdjacentToPanelWhenZeroFlags() {
        val height = 5
        val width = 5
        grid.setHeight(height)
        grid.setWidth(width)
        grid.setNumberOfBombs(0)
        grid.generateBoard(-1)
        assertEquals(0, grid.getFlagsAdjacentToPanel(1, 1))
    }

    @Test
    fun testGetFlagsAdjacentToPanelEdgeCases() {
        val height = 5
        val width = 5
        grid.setHeight(height)
        grid.setWidth(width)
        grid.setNumberOfBombs(0)
        grid.generateBoard(-1)
        assertEquals(0, grid.getFlagsAdjacentToPanel(0, 0))
        assertEquals(0, grid.getFlagsAdjacentToPanel(0, width - 1))
        assertEquals(0, grid.getFlagsAdjacentToPanel(height - 1, 0))
        assertEquals(0, grid.getFlagsAdjacentToPanel(height - 1, width - 1))
    }
}
