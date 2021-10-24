import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Board board;
    private ArrayList<Player> players;
    private boolean gameOver;
    private Dice dice;
    private Player currentPlayer;
    Scanner sc = new Scanner(System.in);

    public Game() {
        board = new Board();
        players = new ArrayList<Player>();
        gameOver = false;

        initPlayers();
        run();

    }

    private void initPlayers() {
        for(int i = 1; i < 5; i++){
            players.add(new Player(i, 1500)); //player id and rent
        }

        currentPlayer = players.get(0);
    }

    private void run() {
        String command;
        while (gameOver != true) {
            // if game isn't over, go to next Player using nextPlayer method.
            nextPlayer();
            System.out.format("It is now %s turn!\n", currentPlayer.getPlayerId());

//			command = getUserCommand(null);//original
            command = "roll";//temporary - testing purpose.
            if ("roll".equals(command)) {
//				System.out.println("Rolling the dice as soon as you press Enter.");
                System.out.println("Rolling the dice...");
                // calling the roll method from Dice class.
                dice.Roll();
                System.out.println("Die 1: " + dice.getDie1());
                System.out.println("Die 2: " + dice.getDie2());
            }

//            currentPlayer.setLocation(currentPlayer.getPropertyLocation() + dice.getDie1() + dice.getDie2());//ORIGINAL
            int newLocation = board.getValidLocation(currentPlayer.getLocation() + dice.getDie1() + dice.getDie2());
            currentPlayer.setLocation(newLocation);
            Property property = board.getProperty(newLocation);
            // is property found
            if (property != null) {
                Player owner = property.getOwner();
                // is property available
                if (owner == null) {
                    System.out.println("Property available for purchase: " + property);
                    System.out.println("What do you want to do (buy OR pass)?");
//					command = getUserCommand(Arrays.asList("buy", "pass"));//original
                    command = "buy";//temporary - testing purpose.
                    if ("buy".equals(command)) {
                        buy(property);
                    } else if ("pass".equals(command)) {
                        // do nothing
                        System.out.println("Turn passed.");
                    }
                } else if (owner != currentPlayer){
                    if (owner.isSetOwnedProperty(property)) {
                        System.out.println("***** Set owned property: " + property);
                        payRent(property);
                    }
                    else {
                        System.out.println("***** Stay without RENT on: " + property);
                    }
                }
                else {
                    System.out.println("***** MY own property: " + property);
                }
            }
            // printing out current board state along with the players.
            System.out.println(board);
            displayPlayerInfo();

            System.out.format("Your new location is,  %s . Press Enter to continue", currentPlayer.getLocation());
            scan.nextLine();
            System.out.println("-------------------");
        }
    }

    private String getUserCommand(List<String> list) {
        String command;
        while (true) {
            if (list == null || list.isEmpty()) {
                // if list is empty/null
                // Print the ALL available commands
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
		return command;
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
        System.out.println("Player " + currentPlayer.getId() +" has finished his turn");
        nextPlayer();
        System.out.println("Player " + currentPlayer.getId() + "'s turn");
    }

    public void quit(){
        System.out.println("Player " + currentPlayer.getId() + " has quit the game");
        currentPlayer.setBankruptcy(true);
        nextPlayer();
        checkWin();
    }

    public void nextPlayer(){
        if(players.size() == players.indexOf(currentPlayer)){
            currentPlayer = players.get(0);
        }
        else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    //Does not work yet
    public void payRent(Property property){
        int rent = property.getRent();
        currentPlayer.removeMoney(rent);

        boolean  b = checkBankruptcy();

        property.getOwner().addMoney(rent);

        checkWin();
    }

    public boolean checkBankruptcy(){
        if(currentPlayer.getMoney() < 0){
            currentPlayer.setBankruptcy(true);
            System.out.println("Player " + currentPlayer.getId());
            return true;
        }
        return false;
    }

    public void checkWin(){
        int i = 0;
        Player winner = new Player();
        for(Player p : players){
            if(p.getBankruptcy() == true){
                i++;
            }
            else{
                winner = p;
            }
        }
        if(i == 3){
            gameOver = true;
            System.out.println("Player " + winner.getId() + " is the winner!!");
        }
    }

    public static void main(String[] args) {

    }
    public void displayPlayerInfo() {
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println();
    }
}


