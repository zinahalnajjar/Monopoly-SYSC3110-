import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements MonopolyView {

    JPanel boardPanel; //For the grid of properties
    JPanel sidePanel; // For the buttons and player info

    JButton pass;
    JButton quit;
    JButton help;
    JButton roll;
    JButton playerInfo;

    MonopolyController mc;

    public MainFrame(){

        super("Monopoly!!");

        Game model = new Game(2);

        model.addMonopolyView(this);

        mc = new MonopolyController(model);

        this.setLayout(new FlowLayout());

        boardPanel = new JPanel();
        sidePanel = new JPanel();
        // boardPanel.setLayout()
        sidePanel.setLayout(new GridLayout(6,1));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);

        boardPanel.setSize(500,500);
        sidePanel.setSize(300, 500);

        boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        boardPanel.setAlignmentX(this.LEFT_ALIGNMENT);
        sidePanel.setAlignmentX(this.RIGHT_ALIGNMENT);

        this.add(boardPanel);
        this.add(sidePanel);

        addButton(roll, "roll");
        addButton(pass, "pass");
        addButton(help, "help");
        addButton(quit, "quit");
        addButton(playerInfo, "player info");

        this.setVisible(true);
    }

    public void addButton(JButton button, String text){
        button = new JButton(text);
        sidePanel.add(button);
    }

    public static void main(String[] args){
        new MainFrame();
    }

    @Override
    public void handleMonopolyStatusUpdate() {

    }
}

