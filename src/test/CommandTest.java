
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class CommandTest {

    final Command c = new Command("a");

    @Test
    public void getUserCommand() {
        assertEquals(c.getUserCommand(), "a");
    }

    @Test
    public void validCommands() {
        assertTrue(c.validCommands("buy"));
    }
    @Test
    public void notValidCommands() {
        assertFalse(c.validCommands("a"));
    }
}