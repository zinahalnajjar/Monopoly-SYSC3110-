import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardFrame extends JFrame implements MonopolyView, ActionListener {

    private Board board;
    private Property property;

    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel buttonPanel;

    private JLabel propertyName;

    private JLabel propertyInfo;
    private JLabel propertyRent;
    private JLabel propertyCost;
    private JLabel propertyOwner;

    private JButton buy;
    private JButton sell;
    private JButton pass;

    MonopolyController mc;

    public CardFrame(String pName, Board board, MonopolyController mc){
        super("Monopoly!!");

        this.board = board;

        property = board.getProperty(pName);

        this.mc = mc;

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
        initButtonPanel("pass", pass, mc);

        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);
    }

    private void initInfoPanel(String labelText, JLabel label){
        label = new JLabel(labelText);
        label.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5,5,5,5));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(label);
    }

    private void initButtonPanel(String bttnLabel, JButton bttn, MonopolyController mc){
        bttn = new JButton(bttnLabel);
        bttn.setEnabled(false);
        bttn.setActionCommand(bttn.getText());
        bttn.addActionListener(mc);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);
    }

    private void updateInfo() {
        if(property.getOwner()!=null){
            propertyOwner.setText("Owner: Player " + property.getOwner().getPlayerId());
        }
    }

    public static void main(String[] args){
    }

    @Override
    public void handleMonopolyStatusUpdate(String command) { //updated with the paramter

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateInfo();
        this.setVisible(true);
    }
}
