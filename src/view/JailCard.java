import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JailCard extends JFrame implements MonopolyView, ActionListener {


    //reference to the main view in case it is needed
    MainFrame mf;

    //reference to board and property
    private Board board;
    private Tile property;

    //the panels for different sections of the deed card
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    //deed card values
    private JLabel propertyName;
    private JLabel titleLabel = new JLabel();
    private JLabel infoLabel = new JLabel();

    //buttons on the deed card
    private JButton payJailFee = new JButton("Pay Jail Fee");
    private JButton rollDice = new JButton("Roll Dice");

    //reference to the model and controller
    private MonopolyController mc;
    private Game model;

    /**
     *
     * initializes the card view depending on which property it is attached to
     *
     * @param pName property name
     * @param board current board
     * @param mc controller
     * @param model model
     * @param mf main frame
     */
    public JailCard(String pName, Board board, MonopolyController mc, Game model, MainFrame mf){
        super("Monopoly!!");

        this.board = board;

        this.mf = mf;
        System.out.println("property.getPropertyName(): " + pName);
        property = board.getTile(pName);

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(490, 390);

        //set layout for the each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(3,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel


        propertyName = new JLabel("JAIL");
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        propertyName.setForeground(Color.white);
        titlePanel.add(propertyName);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));


        String how = "You can either pay $50 \n OR \n" +
                "You need to roll dice and get doubles.";


        //initialize the information on the card
        initInfoPanel("How to Get Out of Jail?", titleLabel);
        initInfoPanel(how, infoLabel);

        //initialize the buy and sell button the card
        initButtonPanel("Pay Jail Fee", payJailFee, mc);
        initButtonPanel("Roll Dice", rollDice, mc);

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
        bttn.setActionCommand(bttn.getText());
        bttn.addActionListener(this);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }


    /**
     * @param e performs action based on who clicked the property card
     */
    @Override

    public void actionPerformed(ActionEvent e) {
        if ("JAIL".equals(e.getActionCommand())) {
            //current player is on the property that was clicked

            //CHECK IF pass by jail
            if(!model.isPassByJail()) {
                if (model.getCurrentPlayer().getLocation().equals(property)) {
                    this.setVisible(true);
                }
            }
        }
        else if ("Pay Jail Fee".equals(e.getActionCommand())) {
            //current player is on the property that was clicked
            if (model.getCurrentPlayer().getLocation().equals(property)) {
                model.payJailFee();
                //The RESULT will be handled by handleMonopolyJailFeePaymentResult()
            }
        }
        else if ("Roll Dice".equals(e.getActionCommand())) {
            //Roll Dice is choice by the player as an option not to pay jail fee
            //this will enable roll button on the main frame.
            mf.enableRollButton();
//            this.rollDice.setEnabled(false);
//            this.payJailFee.setEnabled(false);
            String info = "You need to click Roll dice, to move out of Jail.";
            JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {

    }

    /**
     *
     * handles the sell update
     *  @param success true if property sold successfully
     * @param location to be sold
     */
    @Override
    public void handleMonopolySell(boolean success, Tile location) {
        //DO NOTHING
    }

    @Override
    public void handleMonopolyJailFeePaymentResult(boolean paymentSuccess) {
        if(paymentSuccess){
            payJailFee.setEnabled(false);
            rollDice.setEnabled(false);
            mf.enableRollButton();
            titleLabel.setText("Payment Success!");
            infoLabel.setText("You're Free to roll dice to move out.");


        }
        else{

        }
    }

    @Override
    public void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee) {
        if (forceJailFee) {
            titleLabel.setText("3 failed attempts to get doubles.");
            infoLabel.setText("You MUST Pay Jail Fee, to move out.");
//            roll button disable whould be handled by handleMonopolyJailPlayerRollResult() in MainFrame
        }
    }

}
