
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Testing the getters in welcome screen
 *
 * @author Tooba
 */
public class WelcomeScreenTest {

    WelcomeScreen a = new WelcomeScreen(2);

    @Test
    public void testGetNumPlayers() {
        a.setNumPlayers(3);
        assertEquals(a.getNumPlayers(),3);
    }
}