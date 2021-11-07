package main;

import java.util.ArrayList;
import java.util.Arrays;
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

    private Board board;
    private ArrayList<Player> players;
    private boolean gameOver;
    private Dice dice;
    private Player currentPlayer;
    private static Scanner scan;
    private int playerCount;
    private int currentPlayerIndex = 0; //index of the current player

    public enum Status {X_WON, O_WON, TIE, UNDECIDED};

    private List<MonopolyView> views;

    /**
     *
     * Initializes the game, sets up the scanner
     * Calls a function to initialize player
     *
     * @param playerCount the number of player in the game
     */
    public Game(int playerCount) {
        board = new Board();
        players = new ArrayList<Player>();
        gameOver = false;
        this.playerCount = playerCount;
        this.dice = new Dice();

        scan = new Scanner(System.in);

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
    private void runTextBased() {
        String command;

        help();
        System.out.println("Game Start!\n");

        while (gameOver != true) {
            // if game isn't over
            //check if the player is bankrupt
            if(currentPlayer.getBankruptcy() == true){
                System.out.println("Player " + currentPlayer.getPlayerId() + " is bankrupt.");
                nextPlayer();
            }

            //if player wasn't bankrupt initiate the turn
            System.out.format("It is now Player %s's turn!\n", currentPlayer.getPlayerId());

            //Displays and gets avalid command from the user
            command = getUserCommand(Arrays.asList("roll","quit", "help", "status"));//original
            if ("roll".equals(command)) {
                System.out.println("Rolling the dice...");
                // calling the roll method from Dice class.
                dice.Roll();
                System.out.println("Die 1: " + dice.getDie1());
                System.out.println("Die 2: " + dice.getDie2());

                Property newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());

                if(board.getValidLocation(newLocation) == true){
                    currentPlayer.setLocation(newLocation);
                }

                // is property found
                if (newLocation != null) {
                    Player owner = newLocation.getOwner();

                    // is property available
                    if (owner == null) {
                        System.out.println("Property available for purchase: ");
                        System.out.println(newLocation);
                        System.out.println("What do you want to do (buy OR pass)?");

                        //ask user if they want to buy or pass
                        command = getUserCommand(Arrays.asList("buy", "pass", "quit", "help", "status"));

                        if ("buy".equals(command)) {
                            buy(newLocation);

                            System.out.format("You, have no moves available. Type pass to end turn\n");
                            command = getUserCommand(Arrays.asList("pass", "quit", "help", "status", "sell"));;
                            if (command.equals("pass")) {
                                System.out.println("Turn passed.");
                                pass();
                            }

                        }

                        if ("pass".equals(command)) {
                            System.out.println("Turn passed.");
                            pass();
                        }

                        if ("sell".equals(command)) {
                            System.out.println("Would you like to sell property to the bank?");

                            pass();
                        }
                    } else if (owner != currentPlayer){
                        //if owner is not current player
                        if (owner.isSetOwned(newLocation)) {
                            System.out.println("***** Set owned property: " + newLocation.getPropertyName());
                        }
                        payRent(newLocation);
                    }
                    else {
                        //if landed on own property, will just pass
                        System.out.println("***** MY own property: " + newLocation.getPropertyName());
                        pass();
                    }

                    // printing out current board state along with the players.
                    System.out.println("\n"+board);
                    displayPlayerInfo();
                }
            }
            if("quit".equals(command)){
                //helps user quit game
                quit();
            }
            if("help".equals(command)){
                //displays the help info
                System.out.println(help());
            }
            if("status".equals(command)) {
                //displays player info
                displayPlayerInfo();
            }

            System.out.println("-------------------");

        }
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

        Property newLocation = null;

        if ("roll".equals(command)) {
            dice.Roll();

            System.out.println("I got here");
            newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
            if(board.getValidLocation(newLocation) == true){
                currentPlayer.setLocation(newLocation);
            }
        }

        if ("buy".equals(command)) {
            boolean success = buy(newLocation);
            for(MonopolyView v: views){
                v.handleMonopolyBuy(success);
            }
        }

        if ("pass".equals(command)) {

        }

        if("quit".equals(command)){
            quit();
        }
        if("help".equals(command)){
            help();
        }
        if("player Info".equals(command)) {
            displayPlayerInfo();
        }
    }

    /**
     *
     * Checks for invalid commands
     * loops till user enter a valid
     *
     * @param list
     * @return the vaild command
     */
    private String getUserCommand(List<String> list) {
        String command = "";

        while (true) {
            if (list == null || list.isEmpty()) {
                // if list is empty/null
                // Print the all available commands
                list = Command.getCommands();
            }
            System.out.println("Commands: " + list);

            System.out.format("Enter command: ");

            command = scan.nextLine();
            if (list.contains(command)) {
                // valid command
                return command;
            } else {
                System.out.format("INVALID command.");
            }

        }
    }

    /**
     * The player will be able to buy once he lands on an unowned property
     * @param property
     */
    public boolean buy(Property property){
        int cost = property.getCost();
        int money = currentPlayer.getMoney(); //return players total money

        if (cost > money){
            System.out.println("You don't have enough money.");
            return false;
        } else{
            property.setOwner(currentPlayer);
            currentPlayer.addProperty(property);
            currentPlayer.removeMoney(cost);

            System.out.println("You have successfully bought the property.");
            System.out.println("You have " + currentPlayer.getMoney() +"$ left.");
            return true;
        }
    }

    /**
     * The player will be able to sell
     * @param property
     */
    public void sell(Property property){
        currentPlayer.addMoney(property.getCost());
        currentPlayer.removeProperty(property);
        property.setOwner(null);
    }

    /**
     * turn is passed to next player
     */
    public void pass(){
        System.out.println("Player " + currentPlayer.getPlayerId() +" has finished his turn");
        nextPlayer();
    }

    /**
     * player has quit the game/bankrupt
     */
    public void quit(){
        System.out.println("Player " + currentPlayer.getPlayerId() + " has quit the game");
        currentPlayer.setBankruptcy(true);
        nextPlayer();
        checkWin();
    }

    /**
     * decide the next player, in the order as found in the list starting from index 0
     */
    public void nextPlayer(){
        if(players.size() == players.indexOf(currentPlayer) + 1){

            currentPlayer = players.get(0);
        }
        else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    /**
     *
     * If a player lands in a property owned by opposing player rent has to be payed
     * @param property the property for which the rent needs to be paid
     */
    public void payRent(Property property){
        int rent = property.getRent();// get rent amount
        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //dispay how much the player owns
        currentPlayer.removeMoney(rent);// remove money from player based on what they paid
        boolean  bankrupt = checkBankruptcy();

        //if the player is bankrupt then don't add money
        if(!bankrupt){
            System.out.println("Player " + currentPlayer.getPlayerId() + " PAID rent: " + rent);// display how much the player paid for rent
            System.out.println("Player " + currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney());
            property.getOwner().addMoney(rent);// the owner of the property will receive the rent money
        }
        checkWin();
    }

    /**
     * checks if a player still has enough money to play the game before losing
     * @return true if player has gone bankrupt
     */
    public boolean checkBankruptcy(){
        if(currentPlayer.getMoney() < 0){
            currentPlayer.setBankruptcy(true);
            System.out.println("Player " + currentPlayer.getPlayerId() + "is bankrupt!");
            return true;
        }
        return false;
    }

    /**
     * Winner is whoever is not bankrupt
     */
    public void checkWin(){
        int bankruptCount = 0;
        Player winner = null;

        //loop to tally the number of the bankrupts
        for(Player p : players){
            if(p.getBankruptcy() == true){
                bankruptCount++;
            }
            else{
                winner = p;
            }
        }

        // if bankrupt count is one less than the total player count
        // declare winner
        if (bankruptCount == playerCount - 1) {
            gameOver = true;
            System.out.println("Player " + winner.getPlayerId() + " is the winner!!");
            System.exit(0);
        }
    }

    /**
     * Displays all the player Information
     */
    public void displayPlayerInfo() {
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println();
    }

    /**
     * Prints out all the goals, and rules of the game.
     */
    public String help(){
        String help = "Game Goal: \n" +
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

        return help;
    }

    public int getPlayerCount(){
        return playerCount;
    }

    public Board getBoard(){return board;}

    public void addMonopolyView(MonopolyView view){
        views.add(view);
    }

    public void removeMonopolyView(MonopolyView view){
        views.remove(view);
    }

    /*

    //welcomes player to the game
    //checks how many players are playing
    //@param args

    public static void main(String[] args) {
        System.out.println("Welcome to Monopoly\n");

        scan = new Scanner(System.in);
        System.out.println("Enter the Number of Players:");
        int playerCount = Integer.parseInt(scan.nextLine());
        Game game = new Game(playerCount);
        game.runTextBased();

    }
    */
}
