import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * choose version of game screen
 *
 * @author Tooba Sheikh
 */
public class ChooseVersionScreen extends JFrame implements ActionListener{

    private int numPlayers;
    private int numAIPlayers;

    private JPanel panel;
    /**
     * constructor
     * Initializes the start screen
     */
    public ChooseVersionScreen (int numPlayers, int numAIPlayers) {
        super("Monopoly");

        this.numPlayers = numPlayers;
        this.numAIPlayers = numAIPlayers;

        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        panel.setBorder(new EmptyBorder(30,30,30,30));
        JLabel text = new JLabel("Choose a monopoly edition to play:");
        text.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        panel.add(text);

        panel.add(Box.createRigidArea(new Dimension(5, 20)));

        editionButton(new JButton("Original"));
        editionButton(new JButton("Here and Now Edition"));
        editionButton(new JButton("Carleton University Edition"));

        this.setSize(400, 300);
        this.add(panel);
        this.setVisible(true);
    }

    /**
     * method that adds action listner to button
     * @param button is the any button being initialized
     */
    private void editionButton(JButton button){
        button.setActionCommand(button.getText());
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(5, 10)));

    }

    /**
     * performs the action of closing the chooseVersionScreen and openeing the mainframe
     * @param e text on the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        try {
            new MainFrame(numPlayers, numAIPlayers,e.getActionCommand()+".xml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}