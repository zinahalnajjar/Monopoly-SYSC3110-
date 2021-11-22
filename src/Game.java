

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private final Board board;
    private final ArrayList<Player> players;
    private final Dice dice;
    private Player currentPlayer;
    private final int playerCount;
    private Player previousPlayer;
    Property newLocation;
    boolean win = false;

    public enum Status {}

    private final List<MonopolyView> views;

    /**
     *
     * Initializes the game, sets up the scanner
     * Calls a function to initialize player
     *
     * @param playerCount the number of player in the game
     */
    public Game(int playerCount) {
        board = new Board();
        players = new ArrayList<>();
        this.playerCount = playerCount;
        this.dice = new Dice();

        previousPlayer = null;

        newLocation = null;

        @SuppressWarnings("unused") Scanner scan = new Scanner(System.in);

        views = new ArrayList<>();

        initPlayers();
    }

    /**
     *
     * Initializes the number of players and fills out the list of players
     *
     */
    private void initPlayers() {
        for(int i = 1; i <= playerCount; i++){
            players.add(new Player(1500, i)); //player id and rent
        }
        currentPlayer = players.get(0);
    }


    /**
     *
     * Implements the run loop that goes till the game is ended by finding a winner
     * Implements all the commands
     *  -roll: rolls the dice
     *  -buy: helps player buy the property
     *  -pass: ends the turn
     *  -status: print the status
     *  -help: calls help
     *  -quit: sets the current player to bankrupt and goes to the next player
     *
     *  Checks
     */
    public void run(String command) {
        if(currentPlayer.getBankruptcy()){
            nextPlayer();
        }

        if ("roll".equals(command)) {
            String info = "";
            dice.roll();

            newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
            if(board.getValidLocation(newLocation)){
                currentPlayer.setLocation(newLocation);
                if(newLocation.getOwner() != null) {
                    if (newLocation.getOwner() != currentPlayer){
                        info = payRent(newLocation);
                    }
                }
            }
            notifyView(command, info);
        }

        if ("buy".equals(command)) {
            String info = buy(newLocation);
            for (MonopolyView view : views){
                view.handleMonopolyBuy(info, newLocation);
            }

        }

        if ("sell".equals(command)) {
            boolean success = sell(newLocation);
            for (MonopolyView view : views){
                view.handleMonopolySell(success, newLocation);
            }
        }

        if ("pass".equals(command)) {
            notifyView(command,pass());
        }

        if("quit".equals(command)){
            notifyView(command, quit());
        }
        if("help".equals(command)){
            notifyView(command, help());
        }
        if("player info".equals(command)) {
            notifyView(command, displayPlayerInfo());
        }
        if(win){
            notifyView("win", checkWin());
        }
    }

    /**
     * method to notify each view that changes has happened
     */

    private void notifyView(String command, String info){
        for (MonopolyView view : views){
            view.handleMonopolyStatusUpdate(command, info);
        }
    }

    /**
     * The player will be able to buy once he lands on an unowned property
     * @param property
     */
    public String buy(Property property){

        String info;
        if(property.getState() == Property.HouseState.UNOWNED) {
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
        } else if (property.getState() == Property.HouseState.HOTEL) {
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
    public boolean sell(Property property){
        if (currentPlayer == property.getOwner()){
            currentPlayer.addMoney(property.getCost());
            currentPlayer.removeProperty(property);
            property.setOwner(null);
            return true;
        }
        return false;
    }

    /**
     * turn is passed to next player
     */
    public String pass(){
        String info = "Player " + currentPlayer.getPlayerId() +" has finished his turn";
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
    public void nextPlayer(){
        previousPlayer = currentPlayer;
        if(players.size() == players.indexOf(currentPlayer) + 1){
            currentPlayer = players.get(0);
        }
        else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    /**
     *
     * If a player lands in a property owned by opposing player rent has to be paid
     * @param property the property for which the rent needs to be paid
     */
    public String payRent(Property property){
        String info = "";
        int rent;

        rent = property.getRent();// get rent amount

        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //display how much the player owns
        currentPlayer.removeMoney(rent);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        if(bankrupt){
            info = "Player " + currentPlayer.getPlayerId() + " didn't have enough money to pay rent to Player " + property.getOwner().getPlayerId()+ " at " + property.getPropertyName();
            info += "\n Player " + currentPlayer.getPlayerId() + "has gone bankrupt";
        }

        //if the player is bankrupt then don't add money
        if(!bankrupt){
            info = "Player " + currentPlayer.getPlayerId() + " PAID rent: " + rent +
                    "\nPlayer" + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney();
            System.out.println(info);
            property.getOwner().addMoney(rent);// the owner of the property will receive the rent money
        }

        checkWin();

        return info;
    }

    /**
     * checks if a player still has enough money to play the game before losing
     * @return true if player has gone bankrupt
     */
    public boolean checkBankruptcy(){
        if(currentPlayer.getMoney() <= 0){
            currentPlayer.setBankruptcy(true);
            System.out.println("Player " + currentPlayer.getPlayerId() + " is bankrupt!");
            return true;
        }
        return false;
    }

    /**
     * Winner is whoever is not bankrupt
     */
    public String checkWin(){
        int bankruptCount = 0;
        Player winner = null;

        //loop to tally the number of the bankrupts
        for(Player p : players){
            if(p.getBankruptcy()){
                bankruptCount++;
            }
            else{
                winner = p;
            }
        }

        String info = "";

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

    public int getPlayerCount(){
        return playerCount;
    }

    public Board getBoard(){return board;}

    /**
     * Getter for currentPlayer.
     *
     * @return
     */
    public Player getCurrentPlayer(){
        //Getter added for access by MainFrame class
        //for Notification update.
        return currentPlayer;
    }
    /**
     * Getter for dice.
     *
     * @return
     */
    public Dice getDice(){
        return dice;
    }

    public void addMonopolyView(MonopolyView view){
        views.add(view);
    }

    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

}
