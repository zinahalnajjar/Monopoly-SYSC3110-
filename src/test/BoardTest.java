
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class BoardTest {

    Board b = new Board();
    Property space3 = new Property("Oriental Avenue", Color.CYAN, 6, 100,200);

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