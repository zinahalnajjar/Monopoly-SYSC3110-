
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

    //player id is key and roll count is value
    private Map<Integer, Integer> rollCount = new HashMap<>() ;
    private Board board;
    private ArrayList<Player> players;
    private boolean gameOver;
    private Dice dice;
    private Player currentPlayer;
    private static Scanner scan;
    private int playerCount;
    private Player previousPlayer;
    Property newLocation;
    boolean win = false;

    /**
     *
     * @return
     */
    public void payJailFee() {
        String info = "";
        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //dispay how much the player owns
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

        for (MonopolyView view : views){
            view.handleMonopolyJailResult(paymentSuccess);
        }


    }



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

        previousPlayer = null;

        newLocation = null;

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
                //check if player is in Jail
                boolean jailPlayer = currentPlayer.getLocation().getPropertyName().equals("JAIL");
                if(jailPlayer) {
                    Integer value = rollCount.get(currentPlayer.getPlayerId());
                    if(value == null){
                        //first roll
                        rollCount.put(currentPlayer.getPlayerId(), 1);
                    }
                    else if(value == 3){
                        //if roll count is 3 do not proceed to roll
                        System.out.println("Rolling dice PROHIBITED for JAIL Player: " + currentPlayer.getPlayerId()
                        + ". And must pay Jail Fee.");
                        //check
                        //                        //notify that can't proceed
                        //                        //pending
                    }
                    else{
                        rollCount.put(currentPlayer.getPlayerId(), value + 1);
                    }
                }

                System.out.println("Rolling the dice...");
                // calling the roll method from Dice class.
                dice.roll();
                System.out.println("Die 1: " + dice.getDie1());
                System.out.println("Die 2: " + dice.getDie2());

                //if in Jail check if roll result is doubles
                if(jailPlayer) {
                    if(dice.getDie1() == dice.getDie2()){
                        //doubles
                        //let him proceed

                    }
                    else{
                        //return
                        //notify that can't proceed
                        //pending
                    }
                }


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

        if ("roll".equals(command)) {
            dice.roll();

            newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
            if(board.getValidLocation(newLocation) == true){
                currentPlayer.setLocation(newLocation);
            }
            notifyView(command);
        }

        if ("buy".equals(command)) {
            boolean success = buy(newLocation);
            for (MonopolyView view : views){
                view.handleMonopolyBuy(success, newLocation);
            }
            //notifyView(command);
        }
        if ("sell".equals(command)) {
            boolean success = sell(newLocation);
            for (MonopolyView view : views){
                view.handleMonopolySell(success, newLocation);
            }
        }

        if("rent".equals(command)){
            String result = payRent(newLocation);
            for (MonopolyView view : views){
                view.handleMonopolyRentResult(result, newLocation);
            }
        }

        if ("pass".equals(command)) {
            nextPlayer();
            notifyView(command);
        }

        if("quit".equals(command)){
            quit();
            notifyView(command);
        }
        if("help".equals(command)){
            help();
            notifyView(command);
        }
        if("player info".equals(command)) {
            String info = displayPlayerInfo();
            notifyView(command);
        }
        if(win){
            notifyView("win");
        }


    }

    /**
     * method to notify each view that chnages has happened
     */

    private void notifyView(String command){
        for (MonopolyView view : views){
            view.handleMonopolyStatusUpdate(command);
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
        if (currentPlayer.getLocation() == property){

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
        return false;
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
     * If a player lands in a property owned by opposing player rent has to be payed
     * @param property the property for which the rent needs to be paid
     */
    public String payRent(Property property){
        String info = "";
        int rent = property.getRent();// get rent amount
        System.out.println("Player "+currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //dispay how much the player owns
        currentPlayer.removeMoney(rent);// remove money from player based on what they paid
        boolean bankrupt = checkBankruptcy();

        if(bankrupt){
            info = "bankrupt";
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

        String info = "";

        // if bankrupt count is one less than the total player count
        // declare winner
        if (bankruptCount == playerCount - 1) {
            gameOver = true;
            win = true;
            info = "Player " + winner.getPlayerId() + " is the winner!!" ;
            System.out.println(info);
        }
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

    public void displayAllPlayerInfo() {
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

    public Player getPreviousPlayer(){
        return previousPlayer;
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

