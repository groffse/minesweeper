package groffse

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue

class PanelTest {

    private lateinit var panel: Panel

    @Before
    fun setUp() {
        panel = Panel(12)
    }

    @Test
    fun testConstructor() {
        assertEquals(12, panel.getID())
    }

    @Test
    fun testThatPanelIsBomb() {
        panel.setBomb(true)
        assertEquals(true, panel.isBomb())
    }

    @Test
    fun testAdjecentBombs() {
        val numberOfBombs = 5
        panel.setAdjacentBombs(numberOfBombs)
        assertEquals(numberOfBombs, panel.getAdjacentBombs())
    }

    @Test
    fun testThatPanelIsRevealed() {
        panel.reveal()
        assertEquals(true, panel.isRevealed())
    }

    @Test
    fun testReset() {
        panel.reset()
        assertEquals(0, panel.getAdjacentBombs())
        assertEquals(false, panel.isBomb())
        assertEquals(false, panel.isRevealed())
        assertEquals(false, panel.isFlagged())
    }

    @Test
    fun testExceptionWhenSetFlagWithRevealedTrue() {
        panel.reveal()
        val thrown = assertThrows(IllegalStateException::class.java) {
            panel.setFlag()
        }
        assertTrue(thrown.message?.contains("Panel is already revealed. A flag cannot be set!") == true)
    }

    @Test
    fun testExceptionWhenRevealWithIsFlaggedTrue() {
        panel.setFlag()
        val thrown = assertThrows(IllegalStateException::class.java) {
            panel.reveal()
        }
        assertTrue(thrown.message?.contains("Panel is flagged and cannot be revealed!") == true)
    }
}
