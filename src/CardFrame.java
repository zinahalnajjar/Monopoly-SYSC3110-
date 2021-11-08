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

    private Board board;
    private Property property;

    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    private JLabel propertyName;

    private JLabel propertyInfo = new JLabel();
    private JLabel propertyRent = new JLabel();
    private JLabel propertyCost = new JLabel();
    private JLabel propertyOwner = new JLabel();

    private JButton buy = new JButton();
    private JButton sell = new JButton();
//    private JButton pass = new JButton();

    MonopolyController mc;
    Game model;

    public CardFrame(String pName, Board board, MonopolyController mc, Game model){
        super("Monopoly!!");

        this.board = board;

        property = board.getProperty(pName);

        this.mc = mc;
        this.model = model;
        model.addMonopolyView(this);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setSize(250, 290);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(4,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        propertyName = new JLabel(property.getPropertyName());
        propertyName.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());
        titlePanel.setBorder(new EmptyBorder(10,20,10,20));

        initInfoPanel("Property Information", propertyInfo);
        initInfoPanel("Rent: "+ property.getRent(), propertyRent);
        initInfoPanel("Cost: "+ property.getCost(), propertyCost);
        initInfoPanel("Owner: None", propertyOwner);

        initButtonPanel("buy", buy, mc);
        initButtonPanel("sell", sell, mc);
//        initButtonPanel("pass", pass, mc);

        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);
    }

    private void initInfoPanel(String labelText, JLabel label){
        label.setText(labelText);
        label.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5,5,5,5));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(label);
    }

    private void initButtonPanel(String bttnLabel, JButton bttn, MonopolyController mc){
        bttn.setText(bttnLabel);
        bttn.setEnabled(false);
        bttn.setActionCommand(bttn.getText());
        bttn.addActionListener(mc);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }

    private void updateInfo(Property location) {
        if(property.getOwner() != null){
            propertyOwner.setText("Owner: Player " + location.getOwner().getPlayerId());
        }else{
            propertyOwner.setText("Owner: None");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(model.getCurrentPlayer().getLocation().equals(property)){
            if(property.getOwner() == null){
                buy.setEnabled(true);
                sell.setEnabled(false);
            } else if(property.getOwner() != model.getCurrentPlayer()){
                buy.setEnabled(false);
                sell.setEnabled(false);
                String info = model.payRent(property);
                JOptionPane.showMessageDialog(this, info, "Pay rent", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(property.getOwner() == model.getCurrentPlayer()){
                buy.setEnabled(false);
                sell.setEnabled(true);
            }
        } else if(property.getOwner() == model.getCurrentPlayer()){
            buy.setEnabled(false);
            sell.setEnabled(true);
        }
        else{
            buy.setEnabled(false);
            sell.setEnabled(false);
        }

        this.setVisible(true);
    }

    public void handleMonopolyStatusUpdate(String command) { }//updated with the paramter

    public void handleMonopolyBuy(boolean success, Property location) {
        if(property == location){
            if(success){
                updateInfo(location);
                buy.setEnabled(false);
                this.setVisible(true);
            } else{
                JOptionPane.showMessageDialog(this,"You don't have enough money");
            }
        }
    }

    @Override
    public void handleMonopolySell(boolean success, Property location) {
        if(success && property == location){
            updateInfo(location);
            sell.setEnabled(false);
            this.setVisible(true);
        }
    }

}