import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Welcome Screen
 *
 * @author Tooba Sheikh
 */
public class ChooseVersionScreen extends JFrame implements ActionListener{

    private int numPlayers;
    private JPanel panel;
    /**
     * Initializes the start screen
     */
    public ChooseVersionScreen (int numPlayers) {
        super("Monopoly");

        this.numPlayers = numPlayers;

        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        panel.setBorder(new EmptyBorder(30,30,30,30));
        JLabel text = new JLabel("Choose a monopoly edition to play:");
        text.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        panel.add(text);

        panel.add(Box.createRigidArea(new Dimension(5, 20)));

        editionButton(new JButton("Original"));
        editionButton(new JButton("Here and Now Edition"));


        this.setSize(400, 200);
        this.add(panel);
        this.setVisible(true);
    }

    private void editionButton(JButton button){
        button.setActionCommand(button.getText());
        button.addActionListener(this);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(5, 10)));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        try {
            new MainFrame(numPlayers,e.getActionCommand()+".xml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}