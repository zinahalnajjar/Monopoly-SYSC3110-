import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CardFrame extends JFrame implements MonopolyView {

    Board board;
    Property property;

    JPanel titlePanel;
    JPanel infoPanel;
    JPanel buttonPanel;

    JLabel propertyName;

    JLabel propertyInfo;
    JLabel propertyRent;
    JLabel propertyCost;
    JLabel propertyOwner;

    JButton buy;
    JButton sell;
    JButton pass;

    public CardFrame(String pName, Board board){
        super("Monopoly!!");

        this.board = board;
        property = board.getProperty(pName);

        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 400);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel = new JPanel(new GridLayout(4,1));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        infoPanel.setBorder(new EmptyBorder(10,10,10,10));

        propertyName = new JLabel(property.getPropertyName());

        propertyInfo = new JLabel("Property Information");
        propertyRent = new JLabel(String.valueOf("Rent: "+ property.getRent()));
        propertyCost = new JLabel(String.valueOf("Cost: "+ property.getCost()));
        propertyOwner = new JLabel("Owner: None");

        buy = new JButton("buy");
        sell = new JButton("sell");
        pass = new JButton("pass");

        titlePanel.add(propertyName);
        titlePanel.setBackground(property.getColor());

        infoPanel.add(propertyInfo);
        infoPanel.add(propertyCost);
        infoPanel.add(propertyRent);
        infoPanel.add(propertyOwner);

        buy.setEnabled(false);
        sell.setEnabled(false);
        pass.setEnabled(false);

        buttonPanel.add(buy);
        buttonPanel.add(sell);
        buttonPanel.add(pass);

        this.add(titlePanel);
        this.add(infoPanel);
        this.add(buttonPanel);

        this.setVisible(true);
    }

    private void initTitlePanel(){

    }

    private void initInfoPanel(){

    }

    private void initButtonPanel(String bttnLabel, JButton bttn){
        bttn = new JButton(bttnLabel);
        bttn.setEnabled(false);
        buttonPanel.add(bttn);
    }

    public static void main(String[] args){
        new CardFrame("Mediterranean Avenue", new Board());
    }

}
