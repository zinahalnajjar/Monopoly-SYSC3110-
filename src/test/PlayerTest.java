import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Testing the player functionalities
 *
 * @author Tooba
 */
public class PlayerTest {

    final Property a = new BluePropertyTile("Carleton", false);
    final Property b = new BluePropertyTile("Carleton2", false);

    final Player p = new Player(1500,1, new GoTile("Go", Color.WHITE));

    @Test
    public void getPlayerId() {
        assertEquals(p.getPlayerId(),1);
    }

    @Test
    public void setPropertyLocation() {
        p.setLocation(a);
        assertEquals(p.getLocation(), a);
    }

    @Test
    public void getMoney() {
        assertEquals(p.getMoney(),1500);
    }

    @Test
    public void addMoney() {
        p.addMoney(15);
        assertEquals(p.getMoney(),1515);
    }

    @Test
    public void removeMoney() {
        p.removeMoney(20);
        assertEquals(p.getMoney(),1480);
    }

    @Test
    public void setBankruptcy() {
        p.setBankruptcy(true);
        assertTrue(p.getBankruptcy());
    }

    @Test
    public void removeProperty() {
        p.addProperty(a);
        assertTrue(p.removeProperty(a));
    }

    @Test
    public void setLocation() {
        p.setLocation(b);
        assertEquals(p.getLocation(), b);
    }

}