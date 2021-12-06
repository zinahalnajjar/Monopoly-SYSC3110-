/*
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * TEsts all the commands, and getters/setters
 *
 * @Tooba
 */

/*
public class GameTest {

    final Game g = new Game(3, "Original.xml");

    @Test
    public void buyIfHasEnoughMoney() {
        g.getCurrentPlayer().setLocation(g.getBoard().move(1,g.getCurrentPlayer().getLocation()));
        //assertTrue(g.buy(g.getCurrentPlayer().getLocation()));

    }

    @Test
    public void buyIfNotEnoughMoney() {
        g.getCurrentPlayer().removeMoney(1500);
        g.getCurrentPlayer().setLocation(g.getBoard().move(1,g.getCurrentPlayer().getLocation()));
       // assertFalse(g.buy(g.getCurrentPlayer().getLocation()));
    }

    @Test
    public void sell() {
    }

    @Test
    public void pass() {
        g.pass();
        assertEquals(g.getCurrentPlayer().getPlayerId(), 2);
    }

    @Test
    public void quit() {
        g.quit();
        assertEquals(g.getCurrentPlayer().getPlayerId(), 2);
    }

    @Test
    public void nextPlayer() {
        g.nextPlayer();
        assertEquals(g.getCurrentPlayer().getPlayerId(), 2);
    }

    @Test
    public void payRentIfHasEnoughMoney() {
    }

    @Test
    public void payRentIfNotEnoughMoney() {
        g.getCurrentPlayer().removeMoney(1500);
        String info = "bankrupt";
        String actualInfo = "bankrupt";
        assertEquals(info, actualInfo);
    }

    @Test
    public void checkBankruptcyIfNotBankrupt() {
        assertFalse(g.checkBankruptcy());
    }

    @Test
    public void checkBankruptcyIfBankrupt() {
        g.getCurrentPlayer().removeMoney(1500);
        assertTrue(g.checkBankruptcy());
    }

    @Test
    public void checkWinIfWinner() {
        g.quit();
        g.quit();
        assertTrue(g.win);
    }

    @Test
    public void checkWinIfNoWinnerYet() {
        assertFalse(g.win);
    }

    @Test
    public void getPlayerCount() {
        assertEquals(g.getPlayerCount(),3);
    }
}


 */