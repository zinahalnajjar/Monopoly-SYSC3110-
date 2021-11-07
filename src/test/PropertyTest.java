
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

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
        a.getRent();
    }

    @Test
    public void setRent() {
    }

    @Test
    public void getCost() {
    }

    @Test
    public void setCost() {
    }
}