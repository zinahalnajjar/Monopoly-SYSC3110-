
import java.util.*;

/**
 * Initializes the board and the players.
 * Starts going through a while loop, that goes till the game ends.
 * Game ends with when all but one player have quit or gone bankrupt.
 *
 * @author Tooba
 * @author Zinah
 * @author Kareem
 */
public class Game {


    private static final int JAIL_FEE = 50;
    private static final int GO_AMOUNT = 200;

    private Board board;
    private ArrayList<Player> players;
    private Dice dice;

    private Tile start;

    private Player currentPlayer;
    private int playerCount;
    private Player previousPlayer;
    private Tile newLocation;
    boolean win = false;
    private boolean passByJail = false;

    private List<MonopolyView> views;

    /**
     * Initializes the game, sets up the scanner
     * Calls a function to initialize player
     *
     * @param playerCount the number of player in the game
     */
    public Game(int playerCount, String filename) {
        board = new Board(filename);
        players = new ArrayList<>();
        this.playerCount = playerCount;
        this.dice = new Dice();

        start = board.getTile("GO");

        previousPlayer = new Player(0,-500, start);

        newLocation = null;

        views = new ArrayList<>();

        initPlayers();
    }

    /**
     * Initializes the number of players and fills out the list of players
     */
    private void initPlayers() {
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(1500, i, start)); //player id and rent
        }
        currentPlayer = players.get(0);
    }

    private void notifyViewJailPlayerRoll(String result, boolean forceJailFee) {
        for (MonopolyView view : views) {
            view.handleMonopolyJailPlayerRollResult(result, forceJailFee);
        }

    }

    /**
     * @return
     */
    public void payJailFee() {
        String info = "";
        System.out.println("Player " + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //dispay how much the player owns
        currentPlayer.removeMoney(JAIL_FEE);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        boolean paymentSuccess;
        if (bankrupt) {
            paymentSuccess = false;
        } else {
            info = "Player " + currentPlayer.getPlayerId() + " PAID JAIL FEE: " + JAIL_FEE +
                    "\nPlayer" + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney();
            System.out.println(info);
            paymentSuccess = true;

        }

        for (MonopolyView view : views) {
            view.handleMonopolyJailFeePaymentResult(paymentSuccess);
        }

    }

    public void collect() {
        currentPlayer.addMoney(GO_AMOUNT);
    }

    public void run(String command) {
        if (currentPlayer.getBankruptcy()) {
            nextPlayer();
        }

        if ("roll".equals(command)) {
            //check if player is in Jail

            System.out.println("Rolling the dice...");
            // calling the roll method from Dice class.

            String info = "";

            dice.roll();
            System.out.println("Die 1: " + dice.getDie1());
            System.out.println("Die 2: " + dice.getDie2());

            newLocation = null;

            if (board.getValidLocation(newLocation) == true) {
                newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
                if (board.getValidLocation(newLocation)) {
                    currentPlayer.setLocation(newLocation);
                    if(newLocation.getTYPE().equals(TileType.PROPERTY)){
                        if (((PropertyTile) newLocation).getOwner() != null) {
                            if (((PropertyTile) newLocation).getOwner() != currentPlayer) {
                                info = payRent((PropertyTile)newLocation);
                            }
                        }
                    }
                }
                notifyView(command, info);
            }
        }

        if ("buy".equals(command)) {
            String info1 = "";
            info1 = buy((PropertyTile)newLocation);
            for (MonopolyView view : views) {
                ((CardFrame)view).handleMonopolyBuy(info1, (PropertyTile)newLocation);
            }
        }

        if ("sell".equals(command)) {
            boolean success = sell((PropertyTile)newLocation);
            for (MonopolyView view : views) {
                view.handleMonopolySell(success, newLocation);
            }
        }

        if ("Collect".equals(command)) {
        }

        if ("pass".equals(command)) {
            notifyView(command, pass());
        }
        if ("quit".equals(command)) {
            notifyView(command, quit());
        }
        if ("help".equals(command)) {
            notifyView(command, help());
        }
        if ("player info".equals(command)) {
            notifyView(command, displayPlayerInfo());
        }
        if (win) {
            notifyView("win", checkWin());
        }
    }

    /**
     * method to notify each view that changes has happened
     */

    private void notifyView(String command, String info) {
        for (MonopolyView view : views) {
            ((MainFrame)view).handleMonopolyStatusUpdate(command, info);
        }
    }

    public String buy(PropertyTile property){
        String info = "";
            if(property.getState() == PropertyState.UNOWNED) {
                if (currentPlayer.getLocation() == property){
                    int cost = property.getCost();
                    int money = currentPlayer.getMoney(); //return players total money

                    if (cost > money){
                        info = "You don't have enough money.";
                    } else{
                        property.setOwner(currentPlayer);
                        currentPlayer.addProperty(property);
                        currentPlayer.removeMoney(cost);

                        property.incrementState();

                        info = "You have successfully bought the property.\n";
                        info += "You have " + currentPlayer.getMoney() +"$ left.";
                    }
                } else {
                    info = "You are ineligible to buy this property";
                }
            } else if (property.getState() == PropertyState.HOTEL) {
                info = "This property has the maximum number of houses built on it";
            } else if (currentPlayer == property.getOwner()) {
                int cost = property.getCostPerHouse();
                int money = currentPlayer.getMoney(); //return players total money

                if (cost > money){
                    info = "You don't have enough money.";
                } else{
                    currentPlayer.removeMoney(cost);
                    property.incrementState();

                    info = "You have successfully bought " + property.getState().getHouseNum() + " houses\n";

                    if(property.getState().getHouseNum() == 5){
                        info = "You have successfully bought a Hotel\n";
                    }

                    if(property.getState().getHouseNum() == 1){
                        info = "You have successfully bought " + property.getState().getHouseNum() + " house\n";
                    }

                    info += "You have " + currentPlayer.getMoney() +"$ left.";
                }
            } else {
                info = "You are not the owner of this property";
            }
        return info;
    }

    /**
     * The player will be able to sell
     * @param property
     * @return
     */
    public boolean sell(PropertyTile property){
        if (currentPlayer == property.getOwner()){
            currentPlayer.addMoney(property.getCost());
            currentPlayer.removeProperty(property);
            
            property.setState(PropertyState.UNOWNED);
            property.setOwner(null);
            return true;
        }
        return false;
    }

    /**
     *
     * If a player lands in a property owned by opposing player rent has to be paid
     * @param property the property for which the rent needs to be paid
     */
    public String payRent(PropertyTile property){
        String info = "";

        int rent = property.getRent();

        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //display how much the player owns
        currentPlayer.removeMoney(rent);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        if(bankrupt){
            info = "Player " + currentPlayer.getPlayerId() + " didn't have enough money to pay rent to Player " + property.getOwner().getPlayerId()+ " at " + property.getTileName();
            info += "\n Player " + currentPlayer.getPlayerId() + "has gone bankrupt";
        }

        if (!bankrupt) {
            System.out.println("Player " + currentPlayer.getPlayerId() + " PAID rent: " + rent);// display how much the player paid for rent
            System.out.println("Player " + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney());
            property.getOwner().addMoney(rent);// the owner of the property will receive the rent money
        }

        checkWin();

        return info;

    }


    /**
     * checks if a player still has enough money to play the game before losing
     *
     * @return true if player has gone bankrupt
     */
    public boolean checkBankruptcy() {
        if (currentPlayer.getMoney() < 0) {
            currentPlayer.setBankruptcy(true);
            System.out.println("Player " + currentPlayer.getPlayerId() + "is bankrupt!");
            return true;
        }
        return false;
    }

    /**
     * turn is passed to next player
     */
    public String pass(){
        String info = "Player " + currentPlayer.getPlayerId() +" has finished his turn.\n";
        nextPlayer();
        info += "It is now Player " + currentPlayer.getPlayerId() + "'s turn";

        return info;
    }

    /**
     * player has quit the game/bankrupt
     */
    public String quit(){
        String info = "Player "+ previousPlayer.getPlayerId()+" has quit\n" +
                "It is now Player " +currentPlayer.getPlayerId()+ "'s turn";
        nextPlayer();
        checkWin();
        return info;
    }

    /**
     * decide the next player, in the order as found in the list starting from index 0
     */
    public void nextPlayer() {
        previousPlayer = currentPlayer;
        if (players.size() == players.indexOf(currentPlayer) + 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    /**
     * Winner is whoever is not bankrupt
     */
    public String checkWin(){
        int bankruptCount = 0;
        Player winner = null;

        String info = "";

        //loop to tally the number of the bankrupts
        for(Player p : players){
            if(p.getBankruptcy()){
                bankruptCount++;
            } else {
                winner = p;
            }
        }

        // if bankrupt count is one less than the total player count
        // declare winner
        if (bankruptCount == playerCount - 1) {
            win = true;
            assert winner != null;
            info = "Player " + winner.getPlayerId() + " is the winner!!" ;
            System.out.println(info);
        }

        return info;
    }

    /**
     * Displays all the player Information
     */
    public String displayPlayerInfo() {
        String p = "";
        for (Player player : players) {
            p += player +"\n";
        }
        return p;
    }

    /**
     * Prints out all the goals, and rules of the game.
     */
    public String help(){

        return "Game Goal: \n" +
                "- To be the player who isn't bankrupt.\n\n" +
                "Game Settings: \n" +
                "- There are 22 properties on the board\n" +
                "- Every player starts with 1500$\n\n" +
                "Game Rules: \n" +
                "- Player rolls the dice and moves that many spaces on the board \n" +
                "- When a player lands on an unowned property, players can either buy or pass\n" +
                "- When a player lands on an owned property, players have to pay rent\n" +
                "- If players don't have enough money to pay rent, they go bankrupt\n" +
                "- Goal is to balance your budget so that you won't go bankrupt.\n\n" +
                "Game Commands: \n" +
                "- buy: can be used to buy a property\n" +
                "- pass: can be used to skip your turn\n" +
                "- sell: used to sell your property\n" +
                "- quit: will change player's status to quit and player can exit the game\n" +
                "- help: can be used to view the instructions again\n";
    }

    public Board getBoard() {
        return this.board;
    }

    public Dice getDice() {
        return this.dice;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void addMonopolyView(MonopolyView view) {
        views.add(view);
    }

    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

    public Player getPreviousPlayer(){
        return previousPlayer;
    }

    public boolean isPassByJail(){
        return passByJail;
    }

}

