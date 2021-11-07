

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Main playing screen
 *
 * @author Tooba
 * @author Zinah
 */
public class MainFrame extends JFrame implements MonopolyView  {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private static JPanel boardPanel; //For the grid of properties
    private static JPanel sidePanel; // For the buttons and player info

    //for the functionality buttons
    private static JButton pass = new JButton();
    private static JButton quit = new JButton();
    private static JButton help = new JButton();
    private static JButton roll = new JButton();
    private static JButton playerInfo = new JButton();

    //To communicate with the model
    private MonopolyController mc;
    //the model
    private Game model;

    //Each spot on the board
    private static ArrayList<JButton> properties;
    private static JButton Property;
    private static JLabel FreeParking;
    private static JLabel Chance;
    private static JLabel RailRoad;
    private static JLabel Monopoly;
    private static JLabel WaterWorks;
    private static JLabel GoToJail;
    private static JLabel ElectricCompany;
    private static JLabel Jail;
    private static JLabel Go;
    private static JLabel empty;

    public MainFrame(int playerCount){
        super("Monopoly!!");

       // Game model = new Game(playerCount);
        // i needed to access the model to get the help() order to update the view when based on each command
        //since my method is outide of the constructor then i can't access it with the way you have

        // same thing but now i have a private field of type game
        model = new Game(playerCount);

        model.addMonopolyView(this);

        mc = new MonopolyController(model);

        properties = new ArrayList<>();

        //Make sure we have nice window decorations.
        this.setDefaultLookAndFeelDecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(this.getContentPane(), mc);

        addListeners(model, mc);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    private void addListeners(Game m, MonopolyController mc) {
        for(JButton bttn : properties){
            CardFrame c = new CardFrame(bttn.getText(), m.getBoard(), mc, m);
            bttn.addActionListener(c);
        }
    }

    public static void addComponentsToPane(Container pane, MonopolyController mc) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        // top fixed row which is row 0 gridy = 0


        pane.setLayout(new FlowLayout());

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridBagLayout());

        pane.add(boardPanel);

        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        FreeParking = new JLabel("FREE PARKING");
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.gridx = 0;
        c.gridy = 0;
        boardPanel.add(FreeParking, c);

        Property = new JButton("Kentucky Avenue");
        c.gridx = 1;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        Chance = new JLabel("Chance");
        c.gridx = 2;
        c.gridy = 0;
        boardPanel.add(Chance, c);

        Property = new JButton("Indiana Avenue");
        c.gridx = 3;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("Illinois Avenue");
        c.gridx = 4;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        RailRoad = new JLabel("B. & O. RAILROAD");
        c.gridx = 5;
        c.gridy = 0;
        boardPanel.add(RailRoad, c);

        Property = new JButton("Atlantic Avenue");
        c.gridx = 6;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("Ventnor Avenue");
        c.gridx = 7;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        WaterWorks = new JLabel("WATER WORKS");
        c.gridx = 8;//col
        c.gridy = 0;//row
        boardPanel.add(WaterWorks, c);

        Property = new JButton("Marvin Gardens");
        c.gridx = 9;
        c.gridy = 0;
        boardPanel.add(Property, c);
        properties.add(Property);

        GoToJail = new JLabel("GO TO JAIL");
        c.gridx = 10;
        c.gridy = 0;
        boardPanel.add(GoToJail, c);

        Monopoly = new JLabel("MONOPOLY");
        c.ipady = 40;      //make this component tall
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER ;
        boardPanel.add(Monopoly, c);



// right fixed col which is 10 gridx = 10
        FreeParking = new JLabel("");
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.gridx = 10;
        c.gridy = 0;
        boardPanel.add(FreeParking, c);


        Property = new JButton("Pacific Avenue");
        c.gridx = 10;
        c.gridy = 1;
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("North Carolina Avenue");
        c.gridx = 10;
        c.gridy = 2;
        boardPanel.add(Property, c);
        properties.add(Property);

        empty = new JLabel(" ");
        c.gridx = 10;
        c.gridy = 3;
        boardPanel.add(empty, c);


        Property = new JButton("Pennsylvania Avenue");
        c.gridx = 10;
        c.gridy = 4;
        boardPanel.add(Property, c);
        properties.add(Property);

        RailRoad = new JLabel("SHORT LINE");
        c.gridx = 10;
        c.gridy = 5;
        boardPanel.add(RailRoad, c);

        Chance = new JLabel("Chance");
        c.gridx = 10;
        c.gridy = 6;
        boardPanel.add(Chance, c);

        Property = new JButton("Park Place");
        c.gridx = 10;
        c.gridy = 7;
        boardPanel.add(Property, c);
        properties.add(Property);

        empty = new JLabel(" ");
        c.gridx = 10;//col
        c.gridy = 8;//row
        boardPanel.add(empty, c);

        Property = new JButton("Boardwalk");
        c.gridx = 10;
        c.gridy = 9;
        boardPanel.add(Property, c);
        properties.add(Property);

        Go = new JLabel("GO");
        c.gridx = 10;
        c.gridy = 10;

        // fixed down row gridy = 10

        empty = new JLabel(" ");
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.gridx = 0;//col
        c.gridy = 10;//row
        boardPanel.add(empty, c);


        Property = new JButton("Connecticut Avenue");
        c.gridx = 1;// col
        c.gridy = 10; // row
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("Vermont Avenue");
        c.gridx = 2;//col
        c.gridy = 10;//row
        boardPanel.add(Property, c);
        properties.add(Property);

        Chance = new JLabel("Chance");
        c.gridx = 3;
        c.gridy = 10;
        boardPanel.add(Chance, c);


        Property = new JButton("Oriental Avenue");
        c.gridx = 4;
        c.gridy = 10;
        boardPanel.add(Property, c);
        properties.add(Property);

        RailRoad = new JLabel("READING RAILROAD");
        c.gridx = 5;
        c.gridy = 10;
        boardPanel.add(RailRoad, c);

        empty = new JLabel(" ");
        c.gridx = 6;
        c.gridy = 10;
        boardPanel.add(empty, c);

        Property = new JButton("Baltic Avenue");
        c.gridx = 7;
        c.gridy = 10;
        boardPanel.add(Property, c);
        properties.add(Property);

        empty = new JLabel(" ");
        c.gridx = 8;//col
        c.gridy = 10;//row
        boardPanel.add(empty, c);

        Property = new JButton("Mediterranean Avenue");
        c.gridx = 9;
        c.gridy = 10;
        boardPanel.add(Property, c);
        properties.add(Property);

        empty = new JLabel(" ");
        c.gridx = 10;//col
        c.gridy = 10;//row
        boardPanel.add(empty, c);



        // fixed col left gridx = 0

        FreeParking = new JLabel("FREE PARKING");
        if (shouldWeightX) {
            c.weightx = 0.5;
        }
        c.gridx = 0;
        c.gridy = 0;
        boardPanel.add(FreeParking, c);


        Property = new JButton("New York Avenue");
        c.gridx = 0;
        c.gridy = 1;
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("Tennessee Avenue");
        c.gridx = 0;
        c.gridy = 2;
        boardPanel.add(Property, c);
        properties.add(Property);

        empty = new JLabel("Indiana Avenue");
        c.gridx = 0;
        c.gridy = 3;
        boardPanel.add(empty, c);


        Property = new JButton("St. James Place");
        c.gridx = 0;
        c.gridy = 4;
        boardPanel.add(Property, c);
        properties.add(Property);

        RailRoad = new JLabel("PENNSYLVANIA RAILROAD");
        c.gridx = 0;
        c.gridy = 5;
        boardPanel.add(RailRoad, c);

        Property = new JButton("Virginia Avenue");
        c.gridx = 0;
        c.gridy = 6;
        boardPanel.add(Property, c);
        properties.add(Property);

        Property = new JButton("States Avenue");
        c.gridx = 0;
        c.gridy = 7;
        boardPanel.add(Property, c);
        properties.add(Property);

        ElectricCompany = new JLabel("ELECTRIC COMPANY");
        c.gridx = 0;//col
        c.gridy = 8;//row
        boardPanel.add(ElectricCompany, c);

        Property = new JButton("St. Charles Place");
        c.gridx = 0;
        c.gridy = 9;
        boardPanel.add(Property, c);
        properties.add(Property);

        Jail = new JLabel("JAIL ");
        c.gridx = 0;
        c.gridy = 10;
        boardPanel.add(Jail, c);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(6,2));
        //sidePanel.setSize(111, 111);
        pane.add(sidePanel);

        addButton(roll, "roll", mc);
        addButton(pass, "pass", mc);
        addButton(help, "help", mc);
        addButton(quit, "quit", mc);
        addButton(playerInfo, "player info", mc);

        pass.setEnabled(false);
    }

    public static void addButton(JButton button, String text, MonopolyController mc){
        button.setText(text);
        button.setActionCommand(button.getText());
        button.addActionListener(mc);
        sidePanel.add(button);
    }

    public JButton getRollButton(){
        return roll;
    }

    public JButton getPassButton(){
        return pass;
    }

    @Override
    public void handleMonopolyBuy(boolean success, Property location) {
    }

    @Override
    public void handleMonopolySell(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyStatusUpdate(String command) {
        switch (command){
            case "roll":
                rollNotification();
                break;
            case "pass":
                passNotification();
                break;
            case "help":
                helpNotification();
                break;
            case "quit":
                quitNotification();
                break;
            case "win":
                winNotification();
                break;
            default:
                break;
        }

    }

    private void winNotification() {
        String info = "Player " +model.getCurrentPlayer().getPlayerId()+ " has won the game!!!";
        JOptionPane.showMessageDialog(this, info , "Winner", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // methods for to handle each command case

    /**
     * when the user clicks on the quit command they will exit the program
     */
    private void quitNotification(){
        String info = "Player "+model.getPreviousPlayer().getPlayerId()+" has quit\n" +
                "It is now Player " +model.getCurrentPlayer().getPlayerId()+ "'s turn";
        JOptionPane.showMessageDialog(this, info , "Quit", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * when the user clicks on the help command they will get the instructions of the game
     */

    private void helpNotification(){
        String info = model.help();
        // a dialog message that shows the instruction of the game
        JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * when the user clicks on the pass command the turn will be changed from current player to next player
     *
     */

    private void passNotification(){
        Player currentPlayer = model.getCurrentPlayer();// there was no getter method in game that returns the current player so i implemented it
        String info = "The turn has been passed on to:\n\n";
        info += "Player " + currentPlayer.getPlayerId(); // i used the ID to represent the players- Kareem might need to change this
        JOptionPane.showMessageDialog(this, info, "Pass result", JOptionPane.INFORMATION_MESSAGE);

        roll.setEnabled(true);
        pass.setEnabled(false);
    }

    private void rollNotification(){
        Dice dice = model.getDice(); // get the dice from model- also need to add the getter in Game class to return the dice
        Player currentPlayer = model.getCurrentPlayer(); // get the current player
        Property location = currentPlayer.getLocation(); // get the location of the current player on the board

        String info = "Dice values:\n";
        info += "   " + dice.getDie1() + "\n";
        info += "   " + dice.getDie2() + "\n\n";
        info += "Player location:\n";
        info += "   " + location.getPropertyName() + "\n";
        JOptionPane.showMessageDialog(this, info, "Roll result", JOptionPane.INFORMATION_MESSAGE);

        for(JButton bttn : properties){
            if(bttn.getText().equals(location.getPropertyName())){
                bttn.doClick();
            }
        }

        if(dice.sumOfDice() == 12){
            JOptionPane.showMessageDialog(this, "You rolled doubles, you can roll again", "Roll result", JOptionPane.INFORMATION_MESSAGE);
        }else{
            roll.setEnabled(false);
            pass.setEnabled(true);
        }

    }

    public static void main(String[] args) {
        new MainFrame(2);
    }
}


