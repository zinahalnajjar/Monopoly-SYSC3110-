import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements MonopolyView {

    JPanel boardPanel; //For the grid of properties
    JPanel sidePanel; // For the buttons and player info

    JButton pass;
    JButton roll;
    JButton playerInfo;

    public MainFrame(){
        super("Monopoly!!");

       this.setLayout(new BorderLayout());

        boardPanel = new JPanel();
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        this.add(boardPanel);
        this.add(sidePanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);

        boardPanel.setSize(500,500);
        sidePanel.setSize(300, 500);

        boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.black));

        pass = new JButton("Pass");
        roll = new JButton("Roll");
        playerInfo = new JButton("Player Info");

        addButton(pass);
        addButton(roll);
        addButton(playerInfo);

        this.setVisible(true);
    }

    public void addButton(JButton button){
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(button);
    }

    public static void main(String[] args){
        new MainFrame();
    }
}

