import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Initializes the board and the players.
 * Starts going through a while loop, that goes till the game ends.
 * Game ends with when all but one player have quit or gone bankrupt.
 *
 * @author Tooba
 */
public class CardFrame extends JFrame implements MonopolyView, ActionListener {

    private final Property property;

    private final JPanel infoPanel;
    private final JPanel buttonPanel;

    private final JLabel propertyOwner = new JLabel();
    private final JLabel propertyHouses = new JLabel();


    //buttons on the deed card
    private final JButton buy = new JButton();
    private final JButton sell = new JButton();
//    private JButton pass = new JButton();

    private final Game model;
    private final Object mf;

    /**
     *
     * initializes the card view depending on which property it is attached to
     * @param pName property name
     * @param board current board
     * @param mc controller
     * @param model model
     * @param mainFrame
     */
    public CardFrame(String pName, Board board, MonopolyController mc, Game model, MainFrame mainFrame){
        super("Monopoly!!");

        //reference to board and property

        this.mf = mainFrame;
        System.out.println("property.getPropertyName(): " + pName);
        property = board.getProperty(pName);

        //reference to the model and controller
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(250, 320);

        //set layout for each panel
        //the panels for different sections of the deed card
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(5,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel
        //deed card values
        JLabel propertyName = new JLabel(property.getPropertyName());

        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));

        //initialize the information on the card
        JLabel propertyInfo = new JLabel();
      
        initInfoPanel("Property Information", propertyInfo);
        JLabel propertyRent = new JLabel();
        initInfoPanel("Rent: "+ property.getRent(), propertyRent);
        JLabel propertyCost = new JLabel();
        initInfoPanel("Cost: "+ property.getCost(), propertyCost);
        initInfoPanel("Owner: None", propertyOwner);
        initInfoPanel("Houses Owned: None", propertyHouses);

        if(property.getPropertyName().equals(board.getJailProperty().getPropertyName())) {

        }
        else{
            //initialize the buy and sell button the card
            initButtonPanel("buy", buy, mc);
            initButtonPanel("sell", sell, mc);
        }
      
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
     * @param button reference to the button being initialized
     * @param buttonLabel the text that goes on the button
     * @param mc controller
     */
    private void initButtonPanel(String buttonLabel, JButton button, MonopolyController mc){
        button.setText(buttonLabel);
        button.setEnabled(false);
        button.setActionCommand(button.getText());
        button.addActionListener(mc);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(button);
    }

    /**
     * @param location updates whether the property owner field
     */
    private void updateBuyInfo(Property location) {
        System.out.println(property.getState());
        if(property.getState() == HouseState.RENT){
            propertyOwner.setText("Owner: Player " + location.getOwner().getPlayerId());
        }
        sell.setEnabled(true);
        propertyHouses.setText("Houses Owned: " + property.getState().getHouseNum());
        if(property.getState() == HouseState.HOTEL){
            propertyHouses.setText("Hotel Owned");
            buy.setEnabled(false);
        }
    }


    /**
     */
    private void updateSellInfo() {
        propertyOwner.setText("Owner: None");
    }

    /**
     * @param e performs action based on who clicked the property card
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //current player is on the property that was clicked
        if(model.getCurrentPlayer().getLocation().equals(property)){
            if(property.getOwner() == null){ //if property owner is null
                buy.setEnabled(true);
                sell.setEnabled(false);
            }
            //if property owner is some other player
            else if(property.getOwner() != model.getCurrentPlayer()){
                buy.setEnabled(false);
                sell.setEnabled(false);
            }
           //if property is owned by current player himself
            else if(property.getOwner() == model.getCurrentPlayer()){
               buy.setEnabled(property.getState() != HouseState.HOTEL);
               sell.setEnabled(true);

           }
        } //if player not on property but owns the property
        else if(property.getOwner() == model.getCurrentPlayer()) {
            buy.setEnabled(property.getState() != HouseState.HOTEL);
            sell.setEnabled(true);
        }
        else { //if not on property and does not own the property
            buy.setEnabled(false);
            sell.setEnabled(false);
        }

        this.setVisible(true);
    }
  
    private void FreeParkingNotification(){
        String info = "Press Pass to move to the next turn";
        JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleMonopolyUtilityRailRoadBuy(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) { }//updated with the parameter

    /**
     * handles the buy update
     *
     * @param info Holds info about whether the property was bought successfully
     * @param location to bought
     */
    @Override
    public void handleMonopolyBuy(String info, Property location) {
        if(location == property){
            JOptionPane.showMessageDialog(this,info);
            updateBuyInfo(location);
            this.setVisible(true);
        }
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
        if(property == location && !success){
            if(model.getCurrentPlayer() != property.getOwner()){
                JOptionPane.showMessageDialog(this,"You are not the owner");
                sell.setEnabled(false);
                this.setVisible(true);
            }
        }
        else if(success && property == location){
            updateSellInfo();
            sell.setEnabled(false);
            this.setVisible(true);
        }
    }

    /**
     * Inherited methods
     */

    @Override
    public void handleMonopolyRentResult(String result, Property location) {

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
