
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tooba
 */
public class DiceTest {

    Dice d = new Dice();

    @Test
    public void rollDie1() {
        d.roll();
        assertNotEquals(d.getDie1(), 0);
    }

    @Test
    public void rollDie2() {
        d.roll();
        assertNotEquals(d.getDie2(), 0);
    }

    @Test
    public void sumOfDice() {
        d.setDie1(2);
        d.setDie2(2);
        assertEquals(d.sumOfDice(), 4);
    }
}