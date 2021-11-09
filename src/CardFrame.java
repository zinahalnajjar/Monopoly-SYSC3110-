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
    private JLabel propertyInfo = new JLabel();
    private JLabel propertyRent = new JLabel();
    private JLabel propertyCost = new JLabel();
    private JLabel propertyOwner = new JLabel();

    //buttons on the deed card
    private JButton buy = new JButton();
    private JButton sell = new JButton();
//    private JButton pass = new JButton();

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
    public CardFrame(String pName, Board board, MonopolyController mc, Game model, MainFrame mf){
        super("Monopoly!!");

        this.board = board;

        this.mf = mf;

        property = board.getProperty(pName);

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(250, 290);

        //set layout for the each panel
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(4,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //initialize the title section of the panel
        propertyName = new JLabel(property.getPropertyName());
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));

        //initialize the information on the card
        initInfoPanel("Property Information", propertyInfo);
        initInfoPanel("Rent: "+ property.getRent(), propertyRent);
        initInfoPanel("Cost: "+ property.getCost(), propertyCost);
        initInfoPanel("Owner: None", propertyOwner);

        //initialize the buy and sell button the card
        initButtonPanel("buy", buy, mc);
        initButtonPanel("sell", sell, mc);
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
            propertyOwner.setText("Owner: Player " + location.getOwner().getPlayerId());
        }else{
            propertyOwner.setText("Owner: None");
        }
    }

    /**
     * @return the buy button
     */
    public JButton getBuy(){
        return buy;
    }

    /**
     * @return the sell button
     */
    public JButton getSell(){
        return sell;
    }

    /**
     * @return this frame
     */
    public CardFrame currentFrame(Property p){
        if(property == p ){
            return this;
        }return null;
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
            } //if property owner is some other player
            else if(property.getOwner() != model.getCurrentPlayer()){
                buy.setEnabled(false);
                sell.setEnabled(false);
                String info = model.payRent(property);
                JOptionPane.showMessageDialog(this, info, "Pay rent", JOptionPane.INFORMATION_MESSAGE);
            } //if property is owned by current player himself
            else if(property.getOwner() == model.getCurrentPlayer()){
                buy.setEnabled(false);
                sell.setEnabled(true);
            }
        } //if player not on property but owns the property
        else if(property.getOwner() == model.getCurrentPlayer()){
            buy.setEnabled(false);
            sell.setEnabled(true);
        }
        else{ //if not on property and does not own the property
            buy.setEnabled(false);
            sell.setEnabled(false);
        }

        this.setVisible(true);
    }

    @Override
    public void handleMonopolyStatusUpdate(String command) { }//updated with the paramter

    /**
     * handles the buy update
     *
     * @param success true if property bought successfully
     * @param location to bought
     */
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

    /**
     *
     * handles the sell update
     *
     * @param success true if property sold successfully
     * @param location to be sold
     */
    @Override
    public void handleMonopolySell(boolean success, Property location) {
        if(property == location && success == false){
            if(model.getCurrentPlayer() != property.getOwner()){
                JOptionPane.showMessageDialog(this,"You are not the owner");
                sell.setEnabled(false);
                this.setVisible(true);
            }
        }
        else if(success && property == location){
            updateInfo(location);
            sell.setEnabled(false);
            this.setVisible(true);
        }
    }

}
