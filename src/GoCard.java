import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoCard extends JFrame implements MonopolyView, ActionListener{

    //reference to the main view in case it is needed
    MainFrame mf;

    //reference to board and property
    private Board board;
    private Property property;

    //the panels for different sections of the deed card
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    //deed card values
    private JLabel propertyName;
    private JLabel jailInfo = new JLabel();

    //buttons on the deed card
    private JButton collect = new JButton("Collect");

    //reference to the model and controller
    private MonopolyController mc;
    private Game model;

    public GoCard(String pName, Board board, MonopolyController mc, Game model, MainFrame mf) {
        super("Monopoly!!");

        this.board = board;

        this.mf = mf;
        System.out.println("property.getPropertyName(): " + pName);
        property = board.getProperty(pName);

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(250, 290);

        //set layout for the each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(1, 1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel
        propertyName = new JLabel("GO");
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));


        //initialize the information on the card
        initInfoPanel("collect the $200", jailInfo);

        //initialize the buy and sell button the card
        initButtonPanel("Collect", collect, mc);

        //add all the panels to the deed card view
        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);

    }

    /**
     *
     * initializes the info panel
     *
     * @param labelText text that goes on the panel
     * @param label the panel that hold the info
     */
    private void initInfoPanel(String labelText, JLabel label){
        label.setText(labelText);
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
     * @param bttnLabel the text that goes on the bttn
     * @param mc controller
     */
    private void initButtonPanel(String bttnLabel, JButton bttn, MonopolyController mc){
        bttn.setText(bttnLabel);
        bttn.setEnabled(false);
        bttn.setActionCommand(bttn.getText());
        bttn.addActionListener(this);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }

    public void actionPerformed(ActionEvent e) {
        if ("GO".equals(e.getActionCommand())) {
            //current player is on the property that was clicked
            if (model.getCurrentPlayer().getLocation().equals(property)) {
                this.setVisible(true);
                model.collect();
                collect.setEnabled(true);
            }
        }

    }

    @Override
    public void handleMonopolyUtilityRailRoadBuy(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {

    }

    @Override
    public void handleMonopolyBuy(String info, Property location) {

    }

    @Override
    public void handleMonopolySell(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyRentResult(String result, Property location) {

    }

    @Override
    public void handleMonopolyRentUtility(String result, Property location) {

    }

    @Override
    public void handleMonopolyJailFeePaymentResult(boolean paymentSuccess) {

    }

    @Override
    public void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee) {
        //DO NOTHING

    }

    @Override
    public void handleMonopolyGOResult() {
        String info = "You have received $200";
        JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
    }


}
