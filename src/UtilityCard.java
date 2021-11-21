import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UtilityCard extends JFrame implements MonopolyView, ActionListener{

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
    private JLabel utilityInfo = new JLabel();
    private JLabel utilityRent1 = new JLabel();
    private JLabel utilityRent2 = new JLabel();
    private JLabel utilityCost = new JLabel();
    private JLabel utilityOwner = new JLabel();

    //buttons on the deed card
//buttons on the deed card
    private JButton buy = new JButton();
    private JButton rent1 = new JButton();
    private JButton rent2 = new JButton();


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
    public UtilityCard (String pName, Board board, MonopolyController mc, Game model, MainFrame mf){
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
        infoPanel = new JPanel(new GridLayout(1,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel


        propertyName = new JLabel(property.getPropertyName());
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));


        //initialize the information on the card
        initInfoPanel("Property Information", utilityInfo);
        initInfoPanel("Rent: "+ property.getRent(), utilityRent1);
        initInfoPanel("Rent2: "+ property.getRent2(), utilityRent1);
        initInfoPanel("Cost: "+ property.getCost(), utilityCost);
        initInfoPanel("Owner: None",utilityOwner);

        //initialize the buy and sell button the card
        initButtonPanel("buy", buy, mc);
        initButtonPanel("rent1", rent1, mc);
        initButtonPanel("rent2", rent2, mc);

        //add all the panels to the deed card view
        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);


        this.setVisible(true);
    }


    private void updateInfo(Property location) {
        if(property.getOwner() != null){
            utilityOwner.setText("Owner: Player " + location.getOwner().getPlayerId());
        }else{
            utilityOwner.setText("Owner: None");
        }
    }
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


    @Override
    public void handleMonopolyStatusUpdate(String command) {

    }

    @Override
    public void handleMonopolyBuy(boolean success, Property location) {
        if(property == location && success == false){
            if(model.getCurrentPlayer().getLocation() != property){
                JOptionPane.showMessageDialog(this,"You are not eligible to buy this property");
                buy.setEnabled(false);
                this.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this,"You don't have enough money");
            }
        }
        else if(property == location && success){
            updateInfo(location);
            buy.setEnabled(false);
            this.setVisible(true);
        }

    }

    @Override
    public void handleMonopolySell(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyRentResult(String result, Property location) {
        if(property == location) {
            // update the view with the owner of the railroad

            updateInfo(location);

            //enable buy button if the property is not owned by anyone.
            if(property.getOwner() == null){
                buy.setEnabled(false);
            }
            else if(property.getOwner() != model.getCurrentPlayer()) {
                //enable rent buttons if the property is owned by someone else
                rent1.setEnabled(true);
                rent2.setEnabled(true);
            }


            this.setVisible(true);
        }

    }

    @Override
    public void handleMonopolyJailFeePaymentResult(boolean paymentSuccess) {

    }

    @Override
    public void handleMonopolyJailPlayerRollResult(String result) {
        //DO NOTHING
    }

    @Override
    public void handleMonopolyGOResult() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //current player is on the property that was clicked

        if(model.getCurrentPlayer().getLocation().equals(property)){
            /*
            if("FREE PARKING".equals(e.getActionCommand())){
                FreeParkingNotification();
                return;
            }
            */
            if(property.getOwner() == null){ //if property owner is null
                buy.setEnabled(true);
                rent1.setEnabled(false);
                rent2.setEnabled(false);
                //sell.setEnabled(false);
            } //if property owner is some other player
            else if(property.getOwner() != model.getCurrentPlayer()){
                buy.setEnabled(false);
                rent1.setEnabled(false);
                rent2.setEnabled(false);
                //sell.setEnabled(false);
                String info = model.payUtilityRent(property);
                JOptionPane.showMessageDialog(this, info, "Pay rent", JOptionPane.INFORMATION_MESSAGE);
            } //if property is owned by current player himself
            else if(property.getOwner() == model.getCurrentPlayer()){
                buy.setEnabled(false);
                rent1.setEnabled(true);
                rent2.setEnabled(true);

                //sell.setEnabled(true);
            }
        } //if player not on property but owns the property
        else if(property.getOwner() == model.getCurrentPlayer()){
            buy.setEnabled(false);
            rent1.setEnabled(true);
            rent2.setEnabled(true);
            //sell.setEnabled(true);
        }
        else{ //if not on property and does not own the property
            buy.setEnabled(false);
            rent1.setEnabled(false);
            rent2.setEnabled(false);
            //sell.setEnabled(false);
        }

        this.setVisible(true);

    }
}
