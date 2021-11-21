
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class PropertyTest {

    Property a = new Property("Carleton", Color.RED, 5000, 6000);
    Player T = new Player(1500,1);
    @Test
    public void getPropertyName() {
        assertEquals(a.getPropertyName(),"Carleton");
    }

    @Test
    public void setPropertyName() {
        a.setPropertyName("Minto");
        assertEquals(a.getPropertyName(),"Minto");
    }

    @Test
    public void getColor() {
        assertEquals(a.getColor(),Color.RED);
    }

    @Test
    public void setColor() {
        a.setColor(Color.BLACK);
        assertEquals(a.getColor(),Color.BLACK);
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
        assertEquals(a.getOwner(),null);
    }

    @Test
    public void getRent() {
        assertEquals(a.getInitialRent(),5000);
    }

    @Test
    public void setRent() {
        a.setInitialRent(1);
        assertEquals(a.getInitialRent(),1);
    }

    @Test
    public void getCost() {
        assertEquals(a.getCost(),6000);
    }

    @Test
    public void setCost() {
        a.setCost(3);
        assertEquals(a.getCost(),3);
    }
}