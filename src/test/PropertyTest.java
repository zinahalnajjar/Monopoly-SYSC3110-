
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class PropertyTest {

    final Property a = new BluePropertyTile("Carleton", false);
    final Player T = new Player(1500,1, new GoTile("GO", Color.WHITE));
    @Test
    public void getPropertyName() {
        assertEquals(a.getPropertyName(),"Carleton");
    }

    @Test
    public void getColor() {
        assertEquals(a.getColor(),Color.RED);
    }


    @Test
    public void getOwner() {
        a.setOwner(T);
        assertEquals(a.getOwner(),T);
    }

    @Test
    public void testSetOwner() {
        a.setOwner(null);
        assertNotEquals(a.getOwner(),T);
    }

    @Test
    public void testSetOwnerNull() {
        a.setOwner(null);
        assertNull(a.getOwner());
    }

    @Test
    public void getRent() {
        assertEquals(a.getRent(),5000);
    }


    @Test
    public void getCost() {
        assertEquals(a.getCost(),6000);
    }

}