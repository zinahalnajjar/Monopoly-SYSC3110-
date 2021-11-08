import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements MonopolyView  {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private static final Color BG_COLOR = new Color(69, 255, 156);

    //    private static JPanel boardPanel; //For the grid of properties
    private static JPanel sidePanel; // For the buttons and player info

    //for the functionality buttons
    private static JButton pass = new JButton();
    private static JButton quit = new JButton();
    private static JButton help = new JButton();
    private static JButton roll = new JButton();
    private static JButton playerInfo = new JButton();

    //To communicate with the model
    private MonopolyController mc;

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
    private static JPanel mainPanel;

    private static int NORTH_HEIGHT = 100;
    private static int SOUTH_HEIGHT = 100;

    //The model
    private Game model;

    /**
     * Constructor
     *
     * @param playerCount
     */
    public MainFrame(int playerCount){
        super("Monopoly!!");

//        Game model = new Game(playerCount); //OLD CODE

        //Make model as member field so that
        //it can be accessed by other methods like *Notification
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

        //Create Side Panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(7,2));
        pane.add(sidePanel);
        sidePanel.setBackground(BG_COLOR);

        addEmptyLabel(sidePanel, 1);

        addButton(roll, "roll", mc);
        addButton(pass, "pass", mc);
        addButton(help, "help", mc);
        addButton(quit, "quit", mc);
        addButton(playerInfo, "player info", mc);
      
        pass.setEnabled(false);
      
        //Create Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createNorthPanel(), BorderLayout.NORTH);
        mainPanel.add(createSouthPanel(), BorderLayout.SOUTH);
        mainPanel.add(createEastPanel(), BorderLayout.EAST);
        mainPanel.add(createWestPanel(), BorderLayout.WEST);

        // Add Side Panel to the Command Buttons Panel
        JPanel commandButtonsPanel = new JPanel(new BorderLayout());
        commandButtonsPanel.setBackground(BG_COLOR);

        JLabel northSpaceLabel = new JLabel(" ");
        setHeightNorth(northSpaceLabel);
        JLabel southSpaceLabel = new JLabel(" ");
        setHeightSouth(southSpaceLabel);
        JPanel centerSpacePanel = new JPanel();
        centerSpacePanel.setBackground(BG_COLOR);
        addEmptyLabel(centerSpacePanel, 5);

        commandButtonsPanel.add(northSpaceLabel, BorderLayout.NORTH);
        commandButtonsPanel.add(centerSpacePanel, BorderLayout.CENTER);
        commandButtonsPanel.add(southSpaceLabel, BorderLayout.SOUTH);
        commandButtonsPanel.add(sidePanel, BorderLayout.EAST);

        // Add Panel to the Content Pane
        pane.add(mainPanel, BorderLayout.CENTER);
        pane.add(commandButtonsPanel, BorderLayout.EAST);
    }

    private static JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_COLOR);

        JPanel imagePanel = createImagePanel();

        centerPanel.add(imagePanel);

        return centerPanel;
    }

    private static JPanel createImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(BG_COLOR);

        Monopoly = new JLabel();
        Icon icon = new ImageIcon(MainFrame.class.getResource("images/monopoly.jpg"));
        Monopoly.setIcon(icon);

        imagePanel.add(Monopoly);
        return imagePanel;
    }

    private static JPanel createEastPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(10, 1));
        eastPanel.setBackground(BG_COLOR);

        Property = new JButton("Pacific Avenue");
        eastPanel.add(Property);
        properties.add(Property);

        Property = new JButton("North Carolina Avenue");
        eastPanel.add(Property);
        properties.add(Property);

        empty = new JLabel(" ");
        eastPanel.add(empty);

        Property = new JButton("Pennsylvania Avenue");
        eastPanel.add(Property);
        properties.add(Property);

        RailRoad = new JLabel("SHORT LINE");
        eastPanel.add(RailRoad);

        Chance = new JLabel("Chance");
        eastPanel.add(Chance);

        Property = new JButton("Park Place");
        eastPanel.add(Property);
        properties.add(Property);

        empty = new JLabel(" ");
        eastPanel.add(empty);

        Property = new JButton("Boardwalk");
        eastPanel.add(Property);
        properties.add(Property);

        return eastPanel;
    }

    private static JPanel createWestPanel() {
        JPanel westPanel = new JPanel(new GridLayout(11, 1));
        westPanel.setBackground(BG_COLOR);

        Property = new JButton("New York Avenue");
        westPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Tennessee Avenue");
        westPanel.add(Property);
        properties.add(Property);

        empty = new JLabel("Indiana Avenue");
        westPanel.add(empty);


        Property = new JButton("St. James Place");
        westPanel.add(Property);
        properties.add(Property);

        RailRoad = new JLabel("PENNSYLVANIA RAILROAD");
        westPanel.add(RailRoad);

        Property = new JButton("Virginia Avenue");
        westPanel.add(Property);
        properties.add(Property);

        Property = new JButton("States Avenue");
        westPanel.add(Property);
        properties.add(Property);

        ElectricCompany = new JLabel("ELECTRIC COMPANY");
        westPanel.add(ElectricCompany);

        Property = new JButton("St. Charles Place");
        westPanel.add(Property);
        properties.add(Property);

        return westPanel;
    }

    private static JPanel createSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setBackground(BG_COLOR);

        addEmptyLabel(southPanel, 3);

        Jail = new JLabel("JAIL");
        setHeightSouth(Jail);
        southPanel.add(Jail);

        addEmptyLabel(southPanel, 3);

        addEmptyLabel(southPanel, 6);

        empty = new JLabel(" ");
        southPanel.add(empty);

        Property = new JButton("Connecticut Avenue");
        southPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Vermont Avenue");
        southPanel.add(Property);
        properties.add(Property);

        Chance = new JLabel("Chance");
        southPanel.add(Chance);

        Property = new JButton("Oriental Avenue");
        southPanel.add(Property);
        properties.add(Property);

        RailRoad = new JLabel("READING RAILROAD");
        southPanel.add(RailRoad);

        empty = new JLabel(" ");
        southPanel.add(empty);

        Property = new JButton("Baltic Avenue");
        southPanel.add(Property);
        properties.add(Property);

        empty = new JLabel(" ");
        southPanel.add(empty);

        Property = new JButton("Mediterranean Avenue");
        southPanel.add(Property);
        properties.add(Property);

        addEmptyLabel(southPanel, 6);

        Go = new JLabel("GO");
        setHeightSouth(Go);
        southPanel.add(Go);

        addEmptyLabel(southPanel, 6);

        return southPanel;
    }

    private static void addEmptyLabel(JPanel panel, int count) {
//        for (int i = 0; i < count; i++) {
//            panel.add(new JLabel(" "));
//        }
    }

    private static JPanel createNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBackground(BG_COLOR);

        addEmptyLabel(northPanel, 3);

        FreeParking = new JLabel("FREE PARKING");
        setHeightNorth(FreeParking);
        northPanel.add(FreeParking);

        addEmptyLabel(northPanel, 3);

        Property = new JButton("Kentucky Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Chance = new JLabel("Chance");
        northPanel.add(Chance);


        Property = new JButton("Indiana Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Illinois Avenue");
        northPanel.add(Property);
        properties.add(Property);

        RailRoad = new JLabel("B. & O. RAILROAD");
        northPanel.add(RailRoad);

        Property = new JButton("Atlantic Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Ventnor Avenue");
        northPanel.add(Property);
        properties.add(Property);

        WaterWorks = new JLabel("WATER WORKS");
        northPanel.add(WaterWorks);

        Property = new JButton("Marvin Gardens");
        northPanel.add(Property);
        properties.add(Property);

        addEmptyLabel(northPanel, 3);

        GoToJail = new JLabel("GO TO JAIL");
        setHeightNorth(GoToJail);
        northPanel.add(GoToJail);

        addEmptyLabel(northPanel, 3);

        return northPanel;
    }

    private static void setHeightNorth(JLabel label) {
//        Dimension size = label.getPreferredSize();
//        label.setPreferredSize(new Dimension((int) size.getWidth(), NORTH_HEIGHT));
    }

    private static void setHeightSouth(JLabel label) {
//        Dimension size = label.getPreferredSize();
//        label.setPreferredSize(new Dimension((int) size.getWidth(), SOUTH_HEIGHT));
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

    public static void main(String[] args) {
        new MainFrame(2);
    }

    @Override
    public void handleMonopolyStatusUpdate(String command) {
        System.out.println("...Notified of command: " + command);
        switch (command) {
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




    private void quitNotification() {
        System.exit(0);
    }

    private void helpNotification() {
        String info = model.help();
        JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Display the information of 'pass' result.
     * The current player and the next player details.
     */
    private void passNotification() {
        Player currentPlayer = model.getCurrentPlayer();
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

}





