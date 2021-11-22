import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RailroadCard extends JFrame implements MonopolyView, ActionListener {


    MainFrame mf;

    //reference to board and property

    //the panels for different sections of the deed card
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    //deed card values
    private JLabel railroadName;
    private JLabel railroadInfo = new JLabel();
    private JLabel railroadRent1 = new JLabel();
    private JLabel railroadRent2 = new JLabel();
    private JLabel railroadRent3 = new JLabel();
    private JLabel railroadRent4 = new JLabel();
    private JLabel railroadCost = new JLabel();
    private JLabel railroadOwner = new JLabel();

    //buttons on the deed card
    private JButton buy = new JButton();
    private JButton rent1 = new JButton();
    private JButton rent2 = new JButton();
    private JButton rent3 = new JButton();
    private JButton rent4 = new JButton();
//    private JButton pass = new JButton();


    //reference to the model and controller
    private MonopolyController mc;
    private Game model;
    private Board board;
    private Property property;


    public RailroadCard(String pName, Board board, MonopolyController mc, Game model, MainFrame mf){

        super("Monopoly!!");

        this.board = board;

        this.mf = mf;
        System.out.println("property.getPropertyName(): " + pName);
        property = board.getProperty(pName);

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(400, 400);


        //set layout for the each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(7,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel
        railroadName = new JLabel(property.getPropertyName());
        railroadName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(railroadName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));

        //--start

        //initialize the information on the card
        initInfoPanel("Property Information", railroadInfo);
        initInfoPanel("Rent: "+ property.getRent(), railroadRent1);
        initInfoPanel("Rent2: "+ property.getRent2(), railroadRent2);
        initInfoPanel("Rent3: "+ property.getRent3(), railroadRent3);
        initInfoPanel("Rent4: "+ property.getRent4(), railroadRent4);
        initInfoPanel("Cost: "+ property.getCost(), railroadCost);
        initInfoPanel("Owner: None",railroadOwner);

        //initialize the buy and sell button the card
        initButtonPanel("buy", buy, mc);
        initButtonPanel("rent1", rent1, mc);
        initButtonPanel("rent2", rent2, mc);
        initButtonPanel("rent3", rent3, mc);
        initButtonPanel("rent4", rent4, mc);
//        initButtonPanel("pass", pass, mc);
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
        bttn.addActionListener(mc);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }

    /**
     * @param location updates whether the property owner field
     */
    private void updateInfo(Property location) {
        if(property.getOwner() != null){
            railroadOwner.setText("Owner: Player " + location.getOwner().getPlayerId());
        }else{
            railroadOwner.setText("Owner: None");
        }
    }



    /**
     * @param e performs action based on who clicked the property card
     */

    public void actionPerformed(ActionEvent e) {
        //current player is on the property that was clicked

        if(model.getCurrentPlayer().getLocation().equals(property)){
            this.setVisible(true);

            if(property.getOwner() == null){ //if property owner is null
                buy.setEnabled(true);
                rent1.setEnabled(false);
                rent2.setEnabled(false);
                rent3.setEnabled(false);
                rent4.setEnabled(false);
                //sell.setEnabled(false);
            } //if property owner is some other player
            else if(property.getOwner() != model.getCurrentPlayer()){
                buy.setEnabled(false);
                rent1.setEnabled(true);
                rent2.setEnabled(true);
                rent3.setEnabled(true);
                rent4.setEnabled(true);
                //sell.setEnabled(false);

//
            } //if property is owned by current player himself
            else if(property.getOwner() == model.getCurrentPlayer()){
                buy.setEnabled(false);
                rent1.setEnabled(false);
                rent2.setEnabled(false);
                rent3.setEnabled(false);
                rent4.setEnabled(false);
                //sell.setEnabled(true);
            }
        } //if player not on property but owns the property
        else if(property.getOwner() == model.getCurrentPlayer()){
            buy.setEnabled(false);
            rent1.setEnabled(false);
            rent2.setEnabled(false);
            rent3.setEnabled(false);
            rent4.setEnabled(false);
            //sell.setEnabled(true);
        }
        else{ //if not on property and does not own the property
            buy.setEnabled(false);
            rent1.setEnabled(false);
            rent2.setEnabled(false);
            rent3.setEnabled(false);
            rent4.setEnabled(false);
            //sell.setEnabled(false);
        }

        this.setVisible(true);
    }

    /**
     * handles the buy update
     *
     * @param success true if property bought successfully
     * @param location to bought
     */
    @Override
    public void handleMonopolyRailRoadBuy(boolean success, Property location) {

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
    public void handleMonopolyUtilityBuy(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {

    }

    @Override
    public void handleMonopolyBuy(String success, Property location) {

    }

    /**
     *
     * handles the sell update
     *
     * @param success true if property sold successfully
     * @param location to be sold
     */
    @Override
    public void handleMonopolySell(boolean success, Property location) {

    }


    @Override
    public void handleMonopolyRentResult(String result, Property location) {
        if(property == location) {
            // update the view with the owner of the railroad

            updateInfo(location);

            //post rent payment disable all rent buttons

            rent1.setEnabled(false);
            rent2.setEnabled(false);
            rent3.setEnabled(false);
            rent4.setEnabled(false);
;

            this.setVisible(true);
        }

    }

    @Override
    public void handleMonopolyRentUtility(String result, Property location) {

    }

    @Override
    public void handleMonopolyJailFeePaymentResult(boolean paymentSuccess) {
        //DO NOTHING
    }

    @Override
    public void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee) {
        //DO NOTHING

    }

    @Override
    public void handleMonopolyGOResult() {

    }
}

