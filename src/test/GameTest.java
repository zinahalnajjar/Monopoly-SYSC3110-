
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game g = new Game(3);

    @Test
    public void buyIfHasEnoughMoney() {
        g.getCurrentPlayer().setLocation(g.getBoard().move(1,g.getCurrentPlayer().getLocation()));
        assertTrue(g.buy(g.getCurrentPlayer().getLocation()));

    }

    @Test
    public void buyIfNotEnoughMoney() {
        g.getCurrentPlayer().removeMoney(1500);
        g.getCurrentPlayer().setLocation(g.getBoard().move(1,g.getCurrentPlayer().getLocation()));
        assertFalse(g.buy(g.getCurrentPlayer().getLocation()));
    }

    @Test
    public void sell() {
        g.getCurrentPlayer().setLocation(g.getBoard().move(1,g.getCurrentPlayer().getLocation()));
        g.buy(g.getCurrentPlayer().getLocation());
        assertTrue(g.sell(g.getCurrentPlayer().getLocation()));
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
        g.getBoard().getProperty("Oriental Avenue").setOwner(g.getCurrentPlayer());
        g.nextPlayer();
        String info = g.payRent(g.getBoard().getProperty("Oriental Avenue"));
        String actualInfo = "Player " + g.getCurrentPlayer().getPlayerId() + " PAID rent: " + g.getBoard().getProperty("Oriental Avenue").getRent() +
                "\nPlayer" +g.getCurrentPlayer().getPlayerId() + " has $" + g.getCurrentPlayer().getMoney();

        assertEquals(info, actualInfo);
    }

    @Test
    public void payRentIfNotEnoughMoney() {
        g.getCurrentPlayer().removeMoney(1500);
        String info = g.payRent(g.getBoard().getProperty("Oriental Avenue"));
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