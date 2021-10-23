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
            players.add(new Player(i, 1500));
        }

        currentPlayer = players.get(0);
    }

    private void run() {
        while(gameOver != true){

        }
    }

    public void buy(){

    }

    public void sell(){

    }

    public void pass(){

    }

    public void CheckWin(){

    }

    public static void main(String[] args) {

    }
}

