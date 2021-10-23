import java.util.ArrayList;

public class Game {

    private Board board;
    private ArrayList<Player> players;
    private boolean gameOver;
    private Dice dice;
    private Player currentPlayer;

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
        System.out.println("Player " + currentPlayer.getId() +" has finished his turn ");
        if(players.size() == players.indexOf(currentPlayer)){
            currentPlayer = players.get(0);
        }
        else {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
        System.out.println("Player " + currentPlayer.getId() + "'s turn");
    }

    public void payRent(Property property){
        int rent = property.getRent();
        currentPlayer.removeMoney(rent);
        checkBankruptcy();
    }

    public void checkBankruptcy(){

    }

    public void checkWin(){

    }

    public static void main(String[] args) {

    }
}

