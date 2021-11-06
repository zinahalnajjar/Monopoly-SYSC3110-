import com.sun.tools.javac.comp.Flow;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class WelcomeScreen {
    JFrame frame;
    int numPlayers;
    WelcomeScreen() {
        final FlowLayout layout = new FlowLayout();
        layout.setVgap(10);

        // creating instance of JFrame with name "first way"
        frame = new JFrame("Monopoly");
        //frame.setLayout(new GridLayout(10, 1, 5, 5));
        frame.setLayout(layout);
        JLabel l = new JLabel("Enter the number of players:");
        JLabel err = new JLabel("Enter some integer value please");
        err.setVisible(false);
        JTextField in = new JTextField(20);
        // creates instance of JButton
        JButton button = new JButton("Play");
        button.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         String input = in.getText();

                                         if (input.length() == 0) {
                                            err.setVisible(true);
                                         } else {
                                             try {
                                                 numPlayers = Integer.parseInt(input);
                                                 if (numPlayers > 0) {
                                                     frame.dispose();
                                                 } else {
                                                     err.setVisible(true);
                                                 }
                                             } catch (NumberFormatException ex) {
                                                 err.setVisible(true);
                                             }
                                         }
                                     }
                                 });

        frame.add(l);
        frame.add(in);
        frame.add(button);
        frame.add(err);
        frame.setSize(300, 200);
        // makes the frame visible
        frame.setVisible(true);
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }
}
