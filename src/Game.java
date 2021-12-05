
import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
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
public class Game implements Serializable {
//
//    public static final String GAME_FILE_PATH = "monopoly-game";

    private static final int JAIL_FEE = 50;
    private static final int GO_AMOUNT = 200;

//    private static boolean DEBUG_FIRST = true;

    //keeps track of rolls count for jail-player
    //player id is key and roll count is value
    private Map<Integer, Integer> jailPlayerRollCountMap = new HashMap<>();
    //keeps track of payment by jail-player
    //player id is key and paid tru/false is value
    private Map<Integer, Boolean> jailPlayerPaymentStatusMap = new HashMap<>();
    //keeps track of double rolls count for non-jail-player
    //player id is key and roll count is value
    private Map<Integer, Integer> playerDoubleRollCountMap = new HashMap<>();


    private Board board;
    private ArrayList<Player> players;
    private Dice dice;

    private Property start;

    private Player currentPlayer;
    private int playerCount;
    private Player previousPlayer;
    private Property newLocation;
    boolean win = false;
    private boolean passByJail = false;

    private List<MonopolyView> views;

    /**
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

        start = board.getProperty("GO");

        previousPlayer = new Player(0,-500, start);

        newLocation = null;

        views = new ArrayList<MonopolyView>();

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

    /**
     * if current property is owned by opponent
     * then player pays rent but if it is unowned
     * then player buys it
     *
     * @return current location of the player
     */
    private Property AILand() {

        Property property = currentPlayer.getLocation();
        if (currentPlayer.getLocation().getOwner().isSetOwned(property)) {
            rentRegular(property);
        } else {
            buy(property);
        }
        return property;
    }//AI-Land

    /**
     * Firstly this method rolls dice and gets results
     * then gets the sum of the two dice rolling results
     * Moves the player according to the sum of the dice
     * location of the player is updated
     * next  is the turn of next player
     */
    private void AITurn() {
        dice.roll();
        int sum = dice.sumOfDice();
        Property newLocationOfThePlayer = board.move(sum, AILand());
        int currentPlayerIndex = 0;
        currentPlayer = players.get(currentPlayerIndex);
        currentPlayerIndex += 1;
        currentPlayer.setLocation(newLocationOfThePlayer);
        if (currentPlayerIndex > playerCount) {
            currentPlayerIndex = 0;
        }

    }//AI-Turn

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
            jailPlayerPaymentStatusMap.put(currentPlayer.getPlayerId(), true);

        }

        for (MonopolyView view : views) {
            view.handleMonopolyJailFeePaymentResult(paymentSuccess);
        }

    }

    public void collect() {
        currentPlayer.addMoney(GO_AMOUNT);
        for (MonopolyView view : views) {
            view.handleMonopolyGOResult();
        }
    }

    public void run(String command) {
        if (currentPlayer.getBankruptcy()) {
            nextPlayer();
        }

        if ("roll".equals(command)) {
            //check if player is in Jail
            boolean jailPlayer = !passByJail && currentPlayer.getLocation().getPropertyName().equals("JAIL");
            System.out.println(jailPlayer);
            if (jailPlayer) {
                if (hasCurrentPlayerPaidJailFee()) {// if they haven't paid the jail fee yet
                    System.out.println("JAIL. Player: " + currentPlayer.getPlayerId() + " MADE PAYMENT, rolling permitted.");
                } else {
                    System.out.println("JAIL. Player: " + currentPlayer.getPlayerId() + " has NOT paid. Attempts rolling.");
                    Integer value = jailPlayerRollCountMap.get(currentPlayer.getPlayerId()); // this is for when the player is choosing to roll and get double to get out of jai;
                    if (value == null) {
                        //first double
                        // init to zero. Increment happens below.
                        value = 0;
                    }
                    //increment by 1
                    value += 1;
                    jailPlayerRollCountMap.put(currentPlayer.getPlayerId(), value);

                    if (value == 3) {
                        //if roll count is 3 do not proceed to roll
                        //this should not happen. The roll button shouldn't be enabled for this case.
                        System.out.println("Rolling dice PROHIBITED for JAIL Player: " + currentPlayer.getPlayerId()
                                + ". And must pay Jail Fee.");

                    } else {
                        System.out.println("JAIL. Player: " + currentPlayer.getPlayerId() + ", rolling count: " + value);
                    }
                }
            }

            System.out.println("Rolling the dice...");
            // calling the roll method from Dice class.

            String info = "";

            dice.roll();
            System.out.println("Die 1: " + dice.getDie1());
            System.out.println("Die 2: " + dice.getDie2());

            newLocation = null;

            //if in Jail check if roll result is doubles
            boolean playerMovedToJail = false;
            if (jailPlayer) {
                if (hasCurrentPlayerPaidJailFee()) {
                    System.out.println("JAIL. Player: " + currentPlayer.getPlayerId() + " MADE PAYMENT, DOUBLE check NOT required.");
                } else {
                    System.out.println("JAIL. Player: " + currentPlayer.getPlayerId() + " has NOT paid, DOUBLE check required.");
                    if (!dice.isDouble()) {// this is for checking if th eplayer got double while attempting tp roll while still in jail
                        Integer doubleAttemptCount = jailPlayerRollCountMap.get(currentPlayer.getPlayerId());
                        boolean forceJailFee;
                        String result;
                        if (doubleAttemptCount == 3) {
                            result = "No DOUBLE in Dice roll.\n"
                                    + "You've attempted " + doubleAttemptCount + " times.\n"
                                    + "You must pay Jail Fee.";
                            forceJailFee = true;// if they don't get double by the third attempt then force the jail fee
                        } else {
                            result = "No DOUBLE in Dice roll. You can't proceed!\n"
                                    + "You've attempted " + doubleAttemptCount + " times.";
                            forceJailFee = false;
                        }

                        notifyViewJailPlayerRoll(result, forceJailFee);
                        return;
                    }
                }
            }
            // not in jail we keep track of the rolling doubles
            else if (dice.isDouble()) {
                //check if double for a non-jail player
                Integer value = playerDoubleRollCountMap.get(currentPlayer.getPlayerId());
                if (value == null) {
                    //first double
                    // init to zero. Increment happens below.
                    value = 0;
                }
                //increment by 1
                value += 1;
                playerDoubleRollCountMap.put(currentPlayer.getPlayerId(), value);

                if (value == 3) {
                    //3 times doubles. move to Jail
                    newLocation = board.moveToJail();
                    playerMovedToJail = true;
                    playerDoubleRollCountMap.put(currentPlayer.getPlayerId(), null);
                    System.out.println("3 Doubles. Moved to JAIL. Player: " + currentPlayer.getPlayerId());
                } else {
                    System.out.println("Player: " + currentPlayer.getPlayerId() + ", Double count: " + value);
                }
            }

            if (playerMovedToJail) {
//                Player Moved JAIL. Player. No need to move in board as per dice value.
                System.out.println("Player Moved JAIL. Player: " + currentPlayer.getPlayerId());
            } else {
                //Current player is not moved to Jail.
                //So, move as per dice values.
                newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());

                if (newLocation.equals(board.getJailProperty())) {
                    //player lands in jail
                    System.out.println("Player lands in jail. Player: " + currentPlayer.getPlayerId());
                    passByJail = true;

                } else if (jailPlayer) {
                    passByJail = false;
                    //already player in jail
                    System.out.println("Moved OUT OF JAIL. Player: " + currentPlayer.getPlayerId());
                    //REMOVE tracking for the jail player.
                    jailPlayerRollCountMap.put(currentPlayer.getPlayerId(), null);
                    jailPlayerPaymentStatusMap.put(currentPlayer.getPlayerId(), null);
                } else {
                    passByJail = false;
                }
            }

            if (board.getValidLocation(newLocation) == true) {
                newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
                if (board.getValidLocation(newLocation)) {
                    currentPlayer.setLocation(newLocation);
                    if (newLocation.getOwner() != null) {
                        if (newLocation.getOwner() != currentPlayer) {
                            info = rentRegular(newLocation);
                        }
                    }
                }
                notifyView(command, info);
            }
        }

        if ("buy".equals(command)) {
            String info1 = "";
            if (newLocation.getTYPE() == PropertyType.PROPERTY) {
                info1 = buy(newLocation);
                for (MonopolyView view : views) {
                    view.handleMonopolyBuy(info1, newLocation);
                }
            } else if (newLocation.getTYPE() == PropertyType.RAILROAD || newLocation.getTYPE() == PropertyType.UTILITY){
                boolean b = buyUtility(newLocation);
                for (MonopolyView view : views) {
                    view.handleMonopolyUtilityRailRoadBuy(b, newLocation);
                }
            }
        }

        if ("sell".equals(command)) {
            boolean success = sell(newLocation);
            for (MonopolyView view : views) {
                view.handleMonopolySell(success, newLocation);
            }
        }


        if (command.startsWith("rent") && !command.equals("rent")) {
            int rentLevel = getRentLevel(command);
            String result1 = rentUtility(newLocation, rentLevel);
            for (MonopolyView view : views) {
                view.handleMonopolyRentResult(result1, newLocation);
                view.handleMonopolyRentUtility(result1, newLocation);
            }

        }

        if ("Collect".equals(command)) {
            for (MonopolyView view : views) {
                view.handleMonopolyGOResult();
            }
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
//
//    private boolean saveGame() {
//        try {
//            //Save the 'CURRENT OBJECT'
//            FileUtil.writeToFile(this, GAME_FILE_PATH);
//            return  true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }

    private Boolean hasCurrentPlayerPaidJailFee() {
        Boolean paid = jailPlayerPaymentStatusMap.get(currentPlayer.getPlayerId());
        if (paid == null) {
            paid = false;
        }
        return paid;
    }

    /**
     * method to notify each view that changes has happened
     */

    private void notifyView(String command, String info) {
        for (MonopolyView view : views) {
            view.handleMonopolyStatusUpdate(command, info);
        }
    }

    // rents for railraod and utility

    private int getRentLevel(String buttonLabel) {
        switch (buttonLabel) {
            case "rent1":
                return 1;
            case "rent2":
                return 2;
            case "rent3":
                return 3;
            case "rent4":
                return 4;
        }
        return 1;
    }
    public boolean buyUtility(Property property){
        if(currentPlayer.getLocation()==property){
            int cost = property.getCost();
            int money = currentPlayer.getMoney(); //return players total money

            if (cost > money) {
                System.out.println("You don't have enough money.");
                return false;
            } else {
                property.setOwner(currentPlayer);
                currentPlayer.addProperty(property);
                currentPlayer.removeMoney(cost);

                System.out.println("You have successfully bought the property.");
                System.out.println("You have " + currentPlayer.getMoney() + "$ left.");
                return true;
            }
        }
        return false;
    }

    public String buy(Property property){
        String info = "";
            if(property.getState() == HouseState.UNOWNED) {
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
            } else if (property.getState() == HouseState.HOTEL) {
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
            
            property.setState(HouseState.UNOWNED);
            property.setOwner(null);
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
     *
     * If a player lands in a property owned by opposing player rent has to be paid
     * @param property the property for which the rent needs to be paid
     */
    public String rentRegular(Property property){
        int rent =  property.getRent();
        return payRent(property, rent);
    }

    public String rentUtility(Property property, int rentLevel){
        String info = "";
        int rentUtil = 0;

        // get rent amount
        if(rentLevel == 1) {
            rentUtil = ((RailRoadTile)property).getRent1();
        }
        else if(rentLevel == 2) {
            rentUtil = ((RailRoadTile)property).getRent2();
        }
        else if(rentLevel == 3) {
            rentUtil = ((RailRoadTile)property).getRent3();
        }
        else if(rentLevel == 4) {
            rentUtil = ((RailRoadTile)property).getRent4();
        }

        return payRent(property, rentUtil);
    }


    /**
     *
     * If a player lands in a property owned by opposing player rent has to be paid
     * @param property the property for which the rent needs to be paid
     */
    public String payRent(Property property, int rent){
        String info = "";

        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //display how much the player owns
        currentPlayer.removeMoney(rent);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        if(bankrupt){
            info = "Player " + currentPlayer.getPlayerId() + " didn't have enough money to pay rent to Player " + property.getOwner().getPlayerId()+ " at " + property.getPropertyName();
            info += "\n Player " + currentPlayer.getPlayerId() + "has gone bankrupt";
        }

        //if the player is bankrupt then don't add money
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

    public boolean isPlayerInJail() {
        return board.getJailProperty().equals(currentPlayer.getLocation());
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

