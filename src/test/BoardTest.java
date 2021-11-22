
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class BoardTest {

    final Board b = new Board();

    @Test
    public void move() {
        assertEquals( b.move(3, new Property("Start", null,0,0,0)), b.getProperty("Oriental Avenue"));
    }

    @Test
    public void getValidLocationIfNull() {
        assertFalse(b.getValidLocation(null));
    }
    @Test
    public void getValidLocationIfNotNull() {
        assertTrue(b.getValidLocation(b.getProperty("Oriental Avenue")));
    }
}