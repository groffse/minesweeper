package groffse;

import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class PanelTest {

    public Panel panel;

    @Before
    public void setUp() {
        panel = new Panel(12);
    }

    @Test
    public void testConstructor() {
        assertEquals(12,panel.getID());
    }

    @Test
    public void testThatPanelIsBomb() {
        panel.setBomb(true);
        assertEquals(true, panel.isBomb());
    }
    // Test that we can set a number of adjacent bombs and then read that value back
    @Test
    public void testAdjecentBombs() {
        int number_of_bombs = 5;
        panel.setAdjacentBombs(number_of_bombs);
        assertEquals(number_of_bombs, panel.getAdjacentBombs());
    }

    @Test
    public void testThatPanelIsRevealed() {
        panel.reveal();
        assertEquals(true, panel.isRevealed());
    }

    @Test
    public void testReset() {
        panel.reset();
        assertEquals(0, panel.getAdjacentBombs());
        assertEquals(false, panel.isBomb());
        assertEquals(false, panel.isRevealed());
        assertEquals(false, panel.isFlagged());
    }
    // Test that an exception is thrown when setFlag() is invoked with revealed equal to true
    @Test
    public void testExceptionWhenSetFlagWithRevealedTrue() {
        panel.reveal();
        IllegalStateException thrown =
                assertThrows(IllegalStateException.class,
                        ()->panel.setFlag(true),
                        "Panel is already revealed and setFlag() should throw but did not!");
        assertTrue(thrown.getMessage().contains("Panel is already revealed. A flag cannot be set!"));
    }

    // TEst that an exception is thrown when reveal() is invoken when isFlagged is true
    @Test
    public void testExceptionWhenRevealWithIsFlaggedTrue() {
        panel.setFlag(true);
        IllegalStateException thrown =
                assertThrows(IllegalStateException.class,
                        ()->panel.reveal(),
                        "Panel is flagged and reveal() should throw but did not!");
        assertTrue(thrown.getMessage().contains("Panel is flagged and cannot be revealed!"));
    }
}