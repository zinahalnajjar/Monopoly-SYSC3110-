import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Corner Frame
 *
 * @author Zinah
 */
public class JailFrame extends JFrame implements MonopolyView, ActionListener {


    //reference to the main view in case it is needed
    MainFrame mf;

    //reference to board and property
    private Board board;
    private Tile property;

    //the panels for different sections of the deed card
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    //jail card values
    private JLabel propertyName;
    private JLabel infoLabel = new JLabel();

    //buttons on the jail tile
    private JButton payJailFee = new JButton("pay fee");

    //reference to the model and controller
    private MonopolyController mc;
    private Game model;

    /**
     *
     * initializes the card view depending on which property it is attached to
     *
     * @param mc controller
     * @param model model
     */
    public JailFrame(CornerTile property, MonopolyController mc, Game model){
        super("Monopoly!!");

        this.property = property;

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        this.setSize(320, 300);

        //set layout for the each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(4,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel


        propertyName = new JLabel(property.getTileName());
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        propertyName.setForeground(Color.white);
        titlePanel.add(propertyName);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));


        //initialize the information on the card
        initInfoPanel(new JLabel("How to Get Out of Jail?"));
        initInfoPanel(new JLabel("You can either pay $50"));
        initInfoPanel(new JLabel("OR"));
        initInfoPanel(new JLabel("You need to roll dice and get doubles."));

        //initialize the buy and sell button the card
        initButtonPanel(payJailFee, mc);

        //add all the panels to the deed card view
        this.add(titlePanel);
        this.add(Box.createRigidArea(new Dimension(5, 10)));
        this.add(infoPanel);
        this.add(buttonPanel);
    }

    /**
     *
     * initializes the info panel
     *
     * @param label the panel that hold the info
     */
    private void initInfoPanel(JLabel label){
        label.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5,5,5,5));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(label);
    }

    /**
     *
     * initializes the button panel
     *
     * @param bttn reference to the button being initialized
     * @param mc controller
     */
    private void initButtonPanel(JButton bttn, MonopolyController mc){
        bttn.setActionCommand(bttn.getText());
        bttn.addActionListener(this);
        bttn.setEnabled(false);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }


    /**
     * @param e performs action based on who clicked the property card
     */
    @Override

    public void actionPerformed(ActionEvent e) {
        if (model.getCurrentPlayer().getLocation().equals(property) && model.getCurrentPlayer().isInJail()) {
            payJailFee.setEnabled(true);
        } else{
            payJailFee.setEnabled(false);
        }
        this.setVisible(true);
    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {

    }

    @Override
    public void handleMonopolyBuy(String info, PropertyTile newLocation) {

    }

    @Override
    public void handleMonopolySell(boolean success, Tile newLocation) {

    }

    public void handleMonopolyJailFeePaymentResult(String info) {
        payJailFee.setEnabled(false);
        JOptionPane.showMessageDialog(this, info, "Fee Payment Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
