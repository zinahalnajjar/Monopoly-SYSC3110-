import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CornerFrame extends JFrame implements ActionListener{

    private Tile property;

    //the panels for different sections of the deed card
    private JPanel titlePanel;
    private JPanel infoPanel;

    private JLabel info = new JLabel();

    public CornerFrame(CornerTile property) {
        super("Monopoly!!");

        this.property = property;

        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        this.setSize(300, 150);

        //set layout for each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel propertyName = new JLabel(property.getTileName());

        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        propertyName.setForeground(Color.WHITE);
        titlePanel.add(propertyName);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(new EmptyBorder(5,40,5,40));


        //initialize the information on the card
        info.setText(property.getDescription());
        info.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        info.setBorder(new EmptyBorder(5,5,20,5));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(info);

        //add all the panels to the deed card view
        this.add(titlePanel);
       this.add(Box.createRigidArea(new Dimension(5, 10)));
        this.add(infoPanel);

    }

    public void actionPerformed(ActionEvent e) {
        this.setVisible(true);
    }
}
