

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializes the board and the players.
 * Starts going through a while loop, that goes till the game ends.
 * Game ends with when all but one player have quit or gone bankrupt.
 *
 */
public class Game implements Serializable {

    private final String BUY = "buy";
    private final String SELL = "sell";
    private final String PASS = "pass";
    private final String QUIT = "quit";
    private final String HELP = "help";
    private final String ROLL = "roll";
    private final String FEE = "pay fee";
    private final String INFO = "player info";
    private final String AI = "AI";

    private int MAX_JAIL_COUNTER = 3;

    private int JAIL_FEE;
    private int GO_AMOUNT;

    private Board board;
    private ArrayList<Player> players;
    private Dice dice;

    private Tile start;

    private Player currentPlayer;
    private int playerCount;
    private Player previousPlayer;
    private Tile newLocation;
    boolean win = false;

    private int numAIPlayers;

    private final List<MonopolyView> views;
    private boolean isAi = false;

    /**
     * Initializes the game, sets up the scanner
     * Calls a function to initialize player
     *
     * @param playerCount the number of player in the game
     */
    public Game(int playerCount, int numAIPlayers, String filename) {
        board = new Board(filename);
        players = new ArrayList<>();
        this.playerCount = playerCount;
        this.numAIPlayers = numAIPlayers;
        this.dice = new Dice();

        JAIL_FEE = board.getJailFee();
        GO_AMOUNT = board.getGoFee();

        start = board.tilesList()[0];

        previousPlayer = new Player(0,-500, start);

        newLocation = null;

        views = new ArrayList<>();

        initPlayers(board.getInitialMoney());
        setAIPlayers(numAIPlayers);
    }

    /**
     * Initializes the number of players and fills out the list of players
     */
    private void initPlayers(int initMoney) {
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(initMoney, i, start)); //player id and rent
        }
        currentPlayer = players.get(0);
    }

    public void run(String command) {

        if (ROLL.equals(command)) {
            notifyView(command, rollCommand());
        }

        if (BUY.equals(command)) {
            String info = "";
            info = buy((PropertyTile)newLocation);
            for (MonopolyView view : views) {
                view.handleMonopolyBuy(info, (PropertyTile)newLocation);
            }
        }

        if (SELL.equals(command)) {
            boolean success = sell((PropertyTile)newLocation);
            for (MonopolyView view : views) {
                view.handleMonopolySell(success, newLocation);
            }
        }

        if (FEE.equals(command)) {
            String info = payJailFee();
            for (MonopolyView view : views) {
                view.handleMonopolyJailFeePaymentResult(info);
            }
        }

        if (PASS.equals(command)) {
            notifyView(command, pass());
        }
        if (QUIT.equals(command)) {
            notifyView(command, quit());
        }
        if (HELP.equals(command)) {
            notifyView(command, help());
        }
        if (INFO.equals(command)) {
            notifyView(command, displayPlayerInfo());
        }

        if(AI.equals(command) || isAi){
            notifyView(AI, AITurn());
            if(currentPlayer.getAIStatus()){
                run(AI);
            }
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
            view.handleMonopolyStatusUpdate(command, info);
        }
    }

    public String rollCommand(){
        System.out.println("Rolling the dice...");
        // calling the roll method from Dice class.

        String info = "";

        dice.roll();
        System.out.println("Die 1: " + dice.getDie1());
        System.out.println("Die 2: " + dice.getDie2());

        if(currentPlayer.isInJail()){ //if player in jail
            if(dice.isDouble()){
                currentPlayer.resetJailRollCounter();
                currentPlayer.setIsInJail(false);
                info = "You are free from " + currentPlayer.getLocation().getTileName();
            }else{
                if(currentPlayer.getJailRollCounter() == MAX_JAIL_COUNTER){
                    info = "You have made 3 failed attempts to get doubles.\n" +
                            "You MUST Pay Fee, to move out.\n Attempting To Pay Fee:\n";
                    info += payJailFee();
                }else{
                    currentPlayer.incrementJailRollCounter();
                    info = "You have made " + currentPlayer.getJailRollCounter() + " failed attempts to roll doubles";
                }
            }
        }else{ //if player not in jail
            newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
            currentPlayer.setLocation(newLocation);

            //if passed by go
            if(board.getPassedGo()){
                collect();
                info = "You have passed GO and collected: " + GO_AMOUNT +"$\n\n";
            }

            //if landed on go to jail
            if(newLocation.getTYPE().equals(TileType.CORNERTILE)){
                if (((CornerTile) newLocation).isGoToJail()) {
                    currentPlayer.setLocation(board.moveToJail());
                    info += "Oh no! You have ended up in " + newLocation.getTileName() + "\n";
                    info += "You need to pay the fee or roll doubles to get free";
                }
            } else { //if landed on a property owned by another player
                if (((PropertyTile) newLocation).getOwner() != null) {
                    if (((PropertyTile) newLocation).getOwner() != currentPlayer) {
                        info += payRent((PropertyTile)newLocation);
                    }
                }
            }

        }

        return info;
    }

    /**
     * @return
     */
    public String payJailFee() {
        String info = "";
        currentPlayer.removeMoney(JAIL_FEE);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        if (!bankrupt) {
            info = "Player " + currentPlayer.getPlayerId() + " PAID FEE: " + JAIL_FEE +
                    "\nPlayer" + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney();
            System.out.println(info);
            currentPlayer.setIsInJail(false);
            currentPlayer.resetJailRollCounter();
        } else{
            info = "Player " + currentPlayer.getPlayerId() + " didn't have enough money to pay the fee";
            info += "\n Player " + currentPlayer.getPlayerId() + "has gone bankrupt";
        }

        checkWin();

        return info;
    }

    public void collect() {
        currentPlayer.addMoney(GO_AMOUNT);
        board.setPassedGo(false);
    }

    public String buy(PropertyTile property){
        String info = "";

        if(property.getTYPE() == TileType.PROPERTY){
            info = buyProperty(property);
        } else if(property.getTYPE() == TileType.RAILROAD) {
            info = buyRailRoadUtility(property, TileType.RAILROAD);
        } else if(property.getTYPE() == TileType.UTILITY){
            info = buyRailRoadUtility(property, TileType.UTILITY);
        }

        return info;
    }

    public String buyProperty(PropertyTile property){
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
            }else{
                info = "You are not eligible to buy this property";
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

    public String buyRailRoadUtility(PropertyTile property, TileType type){
        String info = "";

        if(property.getState() == PropertyState.UNOWNED) {
            if (currentPlayer.getLocation() == property) {
                int cost = property.getCost();
                int money = currentPlayer.getMoney(); //return players total money

                if (cost > money) {
                    info = "You don't have enough money.";
                } else {
                    property.setOwner(currentPlayer);
                    currentPlayer.addProperty(property);
                    currentPlayer.removeMoney(cost);

                    int i = 0;

                    for (Tile t : currentPlayer.getOwnedProperties()) {
                        if (t.getTYPE() == type) {
                            i++;
                        }
                    }

                    if (i == 1) {
                        property.setState(PropertyState.RENT1);
                    }
                    if (i == 2) {
                        property.setState(PropertyState.RENT2);
                    }
                    if (i == 3) {
                        property.setState(PropertyState.RENT3);
                    }
                    if (i == 4) {
                        property.setState(PropertyState.RENT4);
                    }

                    info = "You have successfully bought the property.\n";
                    info += "You have " + currentPlayer.getMoney() + "$ left.";
                }
            }
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
            info = "Player " + currentPlayer.getPlayerId() + " PAID rent: " + rent;// display how much the player paid for rent
            info += "\nPlayer " + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney();
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
        currentPlayer.setBankruptcy(true);
        nextPlayer();
        String info = "Player "+ previousPlayer.getPlayerId()+" has quit\n" +
                "It is now Player " +currentPlayer.getPlayerId()+ "'s turn";
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

        if (currentPlayer.getAIStatus()){
            isAi = true;
        } else{
            isAi = false;
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

    public void addMonopolyView(MonopolyView view) {
        views.add(view);
    }

    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

    /**
     * sets the AI players in the game
     * by changing the AI field in the player class to true
     *
     * @param numAIPlayers number of AI players in the game
     */
    private void setAIPlayers(int numAIPlayers) {
        if (!(numAIPlayers == 0)) {
            for (int i = 0; i < numAIPlayers; i++) {
                players.get(playerCount - i - 1).setAI();
            }
        }
    }

    /**
     * Processes the entirety of the AITurn
     */
    public String AITurn() {

        String info = "";

        aiRollDice();
        if (currentPlayer.isInJail()) {
            currentPlayer.incrementJailRollCounter();
            info = aiInJail();
        } else {
            info = aiMove();
            info += aiLand();
            if (dice.getDie1() == dice.getDie2()) {
                AITurn();
            } else {
                info += pass();
            }
        }

        return info;
    }

    /**
     * Method to roll dice for the AI player
     */
    public void aiRollDice() {
        dice.roll();

    }

    /**
     * Moves the AI to the rolled on location
     */
    public String aiMove() {
        int diceRoll = dice.sumOfDice();
        Tile currentPosition = currentPlayer.getLocation();
        newLocation = board.move(diceRoll, currentPosition);
        currentPlayer.setLocation(newLocation);

        String data = "Player " + currentPlayer.getPlayerId() + " has rolled " + diceRoll;
        data += "\n Player has landed on " + newLocation.getTileName() + "\n\n";

        return data;
    }

    /**
     * Determines what the AI does after it lands
     */
    public String aiLand() {
        String data = "";

        if (newLocation.getTYPE() == TileType.CORNERTILE) {
            if(((CornerTile)newLocation).isGoToJail()){
                currentPlayer.setLocation(board.moveToJail());
                currentPlayer.setIsInJail(true);
                data = "Need to roll doubles or pay fee to get out\n";
            }
            data += pass();
        } else {
            if (((PropertyTile)newLocation).getOwner() == null && ((PropertyTile)newLocation).getCost() < currentPlayer.getMoney()) {
                data = aiBuy();
            } else {
                if (((PropertyTile)newLocation).getRent() < currentPlayer.getMoney()) {
                    data = aiPayRent();
                } else {
                    currentPlayer.setBankruptcy(true);
                    data = "Player " + currentPlayer.getPlayerId() + "is bankrupt!\n";
                    ((PropertyTile)newLocation).getOwner().addMoney(currentPlayer.getMoney());
                }
            }
        }

        return data;
    }

    /**
     * Buys property for AI player
     */
    public String aiBuy () {
        currentPlayer.removeMoney(((PropertyTile)newLocation).getCost());
        ((PropertyTile)newLocation).setOwner(currentPlayer);
        currentPlayer.addProperty(((PropertyTile)newLocation));

        String data = "Player " + currentPlayer.getPlayerId() + " has bought " + newLocation.getTileName() + "\n";

        return data;
    }

    /**
     * AI pay rent
     */
    public String aiPayRent () {
        int rent = ((PropertyTile)newLocation).getRent();
        currentPlayer.removeMoney(rent);
        ((PropertyTile)newLocation).getOwner().addMoney(rent);

        String data = "Player " + currentPlayer.getPlayerId() + " has paid "+ rent + " rent on "
                + newLocation.getTileName() + " to Player " + ((PropertyTile) newLocation).getOwner().getPlayerId() + "\n";

        return data;
    }

    public String aiInJail (){

        String data = "";

        // Check jailCounter
        if (dice.getDie1() == dice.getDie2()) {
            data = "Player " + currentPlayer.getPlayerId() + " has rolled doubles and gotten out of"+ currentPlayer.getLocation().getTileName() +"\n";
            data += pass();
            currentPlayer.resetJailRollCounter();
            currentPlayer.setIsInJail(false);
        } else {
            if (currentPlayer.getJailRollCounter() < MAX_JAIL_COUNTER) {
                data = "Still stuck in " + currentPlayer.getLocation().getTileName() + "\n";
                data += pass(); //Do nothing, because no double and don't want to pay
            }
            // Then its time to get out, jail counter is 3 and no double
            else {
                if (currentPlayer.getMoney() < JAIL_FEE) {
                    currentPlayer.setBankruptcy(true);
                    data = "Player " + currentPlayer.getPlayerId() + "is bankrupt! \n They couldn't pay the fee to get out\n";
                    data += pass();
                } else {
                    payJailFee();
                    currentPlayer.setIsInJail(false);
                    currentPlayer.resetJailRollCounter(); //He has paid and its time to get out
                    data = "Player " + currentPlayer.getPlayerId() + " has paid the fee and gotten out of"+ currentPlayer.getLocation().getTileName() +"\n";
                    data += pass();
                }
            }
        }

        return data;
    }

}

