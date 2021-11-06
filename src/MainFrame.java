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

        pass = new JButton("Pass");
        roll = new JButton("Roll");
        playerInfo = new JButton("Player Info");

        addButton(pass);
        addButton(roll);
        addButton(playerInfo);

        this.setVisible(true);
    }

    public void addButton(JButton button){
        //button.setAlignmentX(sidePanel.CENTER_ALIGNMENT);
        sidePanel.add(button);
    }

    public static void main(String[] args){
        new MainFrame();
    }
}

