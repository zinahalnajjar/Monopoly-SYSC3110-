

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;
import java.io.IOException;

/**
 * Welcome Screen
 *
 * @author Kareem
 * @author Walid
 */
public class WelcomeScreen {
    final JFrame frame;
    int numPlayers;
    int numAIPlayers;

    /**
     * Initializes the start screen
     */
    public WelcomeScreen() {

        final FlowLayout layout = new FlowLayout();
        layout.setVgap(10);

        // creating instance of JFrame with name "first way"
        frame = new JFrame("Monopoly");
        //frame.setLayout(new GridLayout(10, 1, 5, 5));
        frame.setLayout(layout);
        JLabel l = new JLabel("Enter the total number of players:");
        JLabel l2 = new JLabel("Enter the desired number of AI players:");
        JLabel err = new JLabel("Invalid Entry. Input must be an integer above 1, and AI players must be less than or equal to total number of players.");
        err.setVisible(false);
        JTextField in = new JTextField("Enter total players here:",20);
        JTextField in2 = new JTextField("Enter number of AI players here:", 20);
        // creates instance of JButton
        JButton button = new JButton("Play");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = in.getText();
                String inputAI = in2.getText();

                if (input.length() == 0 || Integer.parseInt(inputAI) > Integer.parseInt(input)) {
                    err.setVisible(true);
                } else {
                    try {
                        numPlayers = Integer.parseInt(input);
                        numAIPlayers = Integer.parseInt(inputAI);
                        if (numPlayers > 1) {
                            frame.dispose();
                            new MainFrame(numPlayers);
                        } else {
                            err.setVisible(true);
                        }
                    } catch (NumberFormatException | IOException ex) {
                        err.setVisible(true);
                    }
                }
            }
        });

        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                JTextArea textarea = new JTextArea("" +
                        "Game Goal: \n" +
                        "- To be the player who isn't bankrupt.\n" +
                        "\n" +
                        "Game Settings: \n" +
                        "- There are 22 properties on the board\n" +
                        "- Every player starts with 1500$\n" +
                        "\n" +
                        "Game Rules: \n" +
                        "- Player rolls the dice and moves that many spaces on the board \n" +
                        "- When a player lands on an unowned property, players can either buy or pass\n" +
                        "- When a player lands on an owned property, players have to pay rent\n" +
                        "- If players don't have enough money to pay rent, they go bankrupt\n" +
                        "- Goal is to balance your budget so that you won't go bankrupt.\n" +
                        "\n" +
                        "\n" +
                        "Game Commands: \n" +
                        "- buy: can be used to buy a property\n" +
                        "- pass: can be used to skip your turn\n" +
                        "- sell: used to sell your property\n" +
                        "- quit: will change player's status to quit and player can exit the game\n" +
                        "- help: can be used to view the instructions again");
                textarea.setEditable(false);
                dialog.add(textarea);
                dialog.setModalityType(ModalityType.APPLICATION_MODAL);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setSize(600, 600);

                dialog.setVisible(true);

            }
        });

        frame.add(l);
        frame.add(l2);
        frame.add(in);
        frame.add(in2);
        frame.add(button);
        frame.add(help);
        frame.add(err);
        frame.setSize(300, 200);
        // makes the frame visible
        frame.setVisible(true);


    }

    public void setNumPlayers(int players) {
        numPlayers = players;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public void setNumAIPlayers() {
        numAIPlayers = numAIPlayers;
    }
    public int getNumAIPlayers(){
        return this.numAIPlayers;
    }

    public static void main(String[] args) {
        WelcomeScreen w = new WelcomeScreen();
    }
}
