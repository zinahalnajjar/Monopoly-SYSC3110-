import java.util.ArrayList;
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
        while(gameOver != true){
            // if game isn't over, go to next Player using nextPlayer method.
            System.out.format("It is now %s turn!", currentPlayer.getPlayerId());

            // printing out current board state along with the players.
            System.out.println(board);
            System.out.println(players);

            System.out.println("Rolling the dice as soon as you press Enter.");
            System.console().readLine();
            // calling the roll method from Dice class.
            dice.Roll();

            //currentPlayer.setLocation(currentPlayer.getLocation() + dice.getDie1() + dice.getDie2());
            System.out.format("Your new location is,  %s . Press Enter to continue", currentPlayer.getLocation());
            Board.move(dice.sumOfDice(), currentPlayer.getLocation());
            System.out.format("Your new location is,  %s . Press Enter to continue", currentPlayer.getLocation());

            if(currentPlayer.getLocation().owner == null) {
                System.out.print("The current location is unowned. Would you like to buy the location? (Y/N)");
                System.console().readLine();
                String playerOption = sc.nextLine();

                if (playerOption == "Y") {
                    // remove money
                    // add property to player's properties
                    buy(currentPlayer.getLocation());
                    currentPlayer.addProperty(currentPlayer.getLocation());
                }
            } else {
                payRent(currentPlayer.getLocation());
            }

            System.out.format("Would you like to take any other actions? (Sell/Pass})");
            System.console().readLine();
            String playerOption = sc.nextLine();

            if (playerOption == "Sell") {
                sell(currentPlayer.getLocation());
                System.out.format("Sold the following location, %s", currentPlayer.getLocation());
            }
            pass();
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
}

