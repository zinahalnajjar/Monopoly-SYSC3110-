import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private ArrayList<Player> players;
    private boolean gameOver;
    private Dice dice;
    private Player currentPlayer;
    private static Scanner scan;
    private int playerCount;
    private int currentPlayerIndex = 0; //index of the current player
      
    public Game(int playerCount) {
        board = new Board();
        players = new ArrayList<Player>();
        gameOver = false;
        this.playerCount = playerCount;
        this.dice = new Dice();

        scan = new Scanner(System.in);

        initPlayers();
        run();
    }

    private void initPlayers() {
        for(int i = 1; i <= playerCount; i++){
            players.add(new Player(1500, i)); //player id and rent
        }
        currentPlayer = players.get(0);
    }

    private void run() {
        String command;

        help();
        System.out.println("Game Start!\n");

        while (gameOver != true) {
            // if game isn't over, go to next Player using nextPlayer method.

            if(currentPlayer.getBankruptcy() == true){
                System.out.println("Player " + currentPlayer.getPlayerId() + " is bankrupt.");
                nextPlayer();
            }

            System.out.format("It is now Player %s's turn!\n", currentPlayer.getPlayerId());

			command = getUserCommand(null);//original
            if ("roll".equals(command)) {
//				System.out.println("Rolling the dice as soon as you press Enter.");
                System.out.println("Rolling the dice...");
                // calling the roll method from Dice class.
                dice.Roll();
                System.out.println("Die 1: " + dice.getDie1());
                System.out.println("Die 2: " + dice.getDie2());
            }


            Property newLocation = board.move(dice.sumOfDice(), currentPlayer.getLocation());
            currentPlayer.setLocation(newLocation);

            // is property found
            if (newLocation != null) {
                Player owner = newLocation.getOwner();

                // is property available
                if (owner == null) {
                    System.out.println("Property available for purchase: " + newLocation.getPropertyName());
                    System.out.println(" - Price: " + newLocation.getCost());
                    System.out.println(" - Rent: " + newLocation.getRent());
                    System.out.println(" - Color: " + newLocation.getColor());
                    System.out.println("");
                    System.out.println("What do you want to do (buy OR pass)?");

					command = getUserCommand(Arrays.asList("buy", "pass"));

                    if ("buy".equals(command)) {
                        buy(newLocation);
                    } else if ("pass".equals(command)) {
                        System.out.println("Turn passed.");
                        pass();
                    }
                } else if (owner != currentPlayer){
                    if (owner.isSetOwned(newLocation)) {
                        System.out.println("***** Set owned property: " + newLocation.getPropertyName());
                    }
                    payRent(newLocation);
                }
                else {
                    System.out.println("***** MY own property: " + newLocation.getPropertyName());
                }
            }
            // printing out current board state along with the players.
            System.out.println(board);
            displayPlayerInfo();

            System.out.format("Press Enter to continue", currentPlayer.getLocation());
            scan.nextLine();
            System.out.println("-------------------");
        }
    }

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

    public void buy(Property property){
        int cost = property.getCost();
        int money = currentPlayer.getMoney(); //return players total money

        if (cost > money){
            System.out.println("You don't have enough money.");
        } else{
            property.setOwner(currentPlayer);
            currentPlayer.addProperty(property);
            currentPlayer.removeMoney(cost);

            System.out.println("You have successfully bought the property.");
            System.out.println("You have " + currentPlayer.getMoney() +"$ left.");
        }
    }

    public void sell(Property property){
        currentPlayer.addMoney(property.getCost());
        currentPlayer.removeProperty(property);
        property.setOwner(null);
    }

    public void pass(){
        System.out.println("Player " + currentPlayer.getPlayerId() +" has finished his turn");
        nextPlayer();
        System.out.println("Player " + currentPlayer.getPlayerId() + "'s turn");
    }

    public void quit(){
        System.out.println("Player " + currentPlayer.getPlayerId() + " has quit the game");
        currentPlayer.setBankruptcy(true);
        nextPlayer();
        checkWin();
    }
  
    /**
     * decide the next player, in the orser as found in the list starting from index 0
     */
    public void nextPlayer(){
        if(players.size() == players.indexOf(currentPlayer)){
            currentPlayer = players.get(0);
        }
        else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    /**
     *
     *
     * @param property
     */
    public void payRent(Property property){
  
        boolean  bankrupt = checkBankruptcy();
        //if the player is bankrupt then don't add money
        if(!bankrupt){
            int rent = property.getRent();// get rent amount
            System.out.println(currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney()); //dispay how much the player owns
            currentPlayer.removeMoney(rent);// remove money from player based on what they paid
            System.out.println(currentPlayer.getPlayerId() + " PAID rent: " + rent);// dispay how much the player paid for rent
            System.out.println(currentPlayer.getPlayerId() + " has $" + currentPlayer.getMoney());
            property.getOwner().addMoney(rent);// the owner of the property will receive the rent money


        }
        checkWin();
    }
     
    public boolean checkBankruptcy(){
        if(currentPlayer.getMoney() < 0){
            currentPlayer.setBankruptcy(true);
            System.out.println("Player " + currentPlayer.getPlayerId() + "is bankrupt!");
            return true;
        }
        return false;
    }

    public void checkWin(){
        int bankruptCount = 0;
        Player winner = null;
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
        }
    }

    public static void main(String[] args) {

        System.out.println("Welcome to Monopoly\n");

        scan = new Scanner(System.in);
        System.out.println("Enter the Number of Players:");
        int playerCount = Integer.parseInt(scan.nextLine());
        Game game = new Game(playerCount);
        game.run();
    }

    public void displayPlayerInfo() {
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println();
    }

    public void help(){
        System.out.println("Game Goal: \n" +
                "- To be the player who isn't bankrupt.\n");

        System.out.println("Game Settings: \n" +
                "- There are 22 properties on the board\n" +
                "- Every player starts with 1500$\n");

        System.out.println("Game Rules: \n" +
                "- Player rolls the dice and moves that many spaces on the board \n" +
                "- When a player lands on an unowned property, players can either buy or pass\n" +
                "- When a player lands on an owned property, players have to pay rent\n" +
                "- If players don't have enough money to pay rent, they go bankrupt\n" +
                "- Goal is to balance your budget so that you won't go bankrupt.");
        System.out.println("\n\nGame Commands: \n" +
                "- buy: can be used to buy a property\n" +
                "- pass: can be used to skip your turn\n" +
                "- sell: used to sell your property\n" +
                "- quit: will change player's status to quit and player can exit the game\n" +
                "- help: can be used to view the instructions again\n");
    }
}


