import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardFrame extends JFrame implements MonopolyView {

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

    public CardFrame(String pName, Board board){
        super("Monopoly!!");

        this.board = board;
        property = board.getProperty(pName);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        initButtonPanel("buy", buy);
        initButtonPanel("sell", sell);
        initButtonPanel("pass", pass);

        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);

        this.setVisible(true);
    }

    private void initInfoPanel(String labelText, JLabel label){
        label = new JLabel(labelText);
        label.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        label.setBorder(new EmptyBorder(5,5,5,5));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(label);
    }

    private void initButtonPanel(String bttnLabel, JButton bttn){
        bttn = new JButton(bttnLabel);
        bttn.setEnabled(false);
        buttonPanel.setBorder(new EmptyBorder(15,20,10,20));
        buttonPanel.add(bttn);

    }

    public static void main(String[] args){
        new CardFrame("Mediterranean Avenue", new Board());
    }

    @Override
    public void handleMonopolyStatusUpdate() {

    }
}
