
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


/**
 *
 * Generates the main game screen, with panels for the board,
 * and a side panel for player input
 *
 * @author Tooba
 * @author Zinah
 */
public class MainFrame extends JFrame implements MonopolyView  {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private static final Color BG_COLOR = new Color(69, 255, 156);

    //private static JPanel boardPanel; //For the grid of properties
    private static JPanel sidePanel; // For the buttons and player info

    //Card frame
    private CardFrame cf;

    private ArrayList<JLabel> players;

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
    private static ArrayList<JButton> railroads;
    private static ArrayList<JButton> utilities;
    private static JButton Property;
    private static JLabel Chance;
    private static JButton RailRoad;
    private static JLabel Monopoly;
    private static JButton WaterWorks;
    private static JLabel GoToJail;
    private static JButton ElectricCompany;
    private static JButton Jail;
    private static JButton Go;

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
    public MainFrame(int playerCount) throws IOException {
        super("Monopoly!!");

        cf = null;
  
        model = new Game(playerCount);

        model.addMonopolyView(this);

        //To communicate with the model
        MonopolyController mc = new MonopolyController(model);


        properties = new ArrayList<>();
        railroads = new ArrayList<>();
        utilities = new ArrayList<>();
        players = new ArrayList<>();

        ArrayList<JLabel> players = new ArrayList<>();

        //Make sure we have nice window decorations.
        setDefaultLookAndFeelDecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(this.getContentPane(), mc);

        addListeners(model, mc);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    /**
     *
     * Adds action listeners to all the properties
     *
     * @param m Model
     * @param mc Controller
     */
    private void addListeners(Game m, MonopolyController mc) {
        for(JButton bttn : properties) {
            if(bttn.getText().equals("JAIL")){
                JailCard jf = new JailCard(bttn.getText(), m.getBoard(), mc, m, this);
                bttn.addActionListener(jf);
            }if(bttn.getText().equals("GO")){
                GoCard gf = new GoCard(bttn.getText(), m.getBoard(), mc, m, this);
                bttn.addActionListener(gf);
            }
            else {
                cf = new CardFrame(bttn.getText(), m.getBoard(), mc, m, this);
                bttn.addActionListener(cf);
            }
        }
        for(JButton bttn : railroads){
            RailroadCard rf = new RailroadCard(bttn.getText(), m.getBoard(), mc, m, this);
            bttn.addActionListener(rf);

        }// add another for loop

        for(JButton bttn : utilities){
            UtilityCard uf = new UtilityCard(bttn.getText(), m.getBoard(), mc, m, this);
            bttn.addActionListener(uf);
        }


    }

    /**
     *
     * Adds all the panels to main ontainer
     *
     * @param pane the content pane
     * @param mc the controller
     */
    public static void addComponentsToPane(Container pane, MonopolyController mc) {

        //Create Side Panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(7,2));
        pane.add(sidePanel);
        sidePanel.setBackground(BG_COLOR);

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

        JLabel southSpaceLabel = new JLabel(" ");

        JPanel centerSpacePanel = new JPanel();
        centerSpacePanel.setBackground(BG_COLOR);

        commandButtonsPanel.add(northSpaceLabel, BorderLayout.NORTH);
        commandButtonsPanel.add(centerSpacePanel, BorderLayout.CENTER);
        commandButtonsPanel.add(southSpaceLabel, BorderLayout.SOUTH);
        commandButtonsPanel.add(sidePanel, BorderLayout.EAST);

        // Add Panel to the Content Pane
        pane.add(mainPanel, BorderLayout.CENTER);
        pane.add(commandButtonsPanel, BorderLayout.EAST);
    }

    /**
     *
     * Initializes the panel that will monopoly logo in the middle
     *
     * @return a Jpanel that will holding the center image
     */
    private static JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_COLOR);

        JPanel imagePanel = createImagePanel();


        centerPanel.add(imagePanel);

        return centerPanel;
    }

    /**
     *
     * Adds the monopoly logo to the center panel
     *
     * @return JPanel holding the center Image
     */
    private static JPanel createImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(BG_COLOR);
        JLabel monopoly = new JLabel();
        Icon icon = new ImageIcon(Objects.requireNonNull(MainFrame.class.getResource("images/monopoly.jpg")));
        monopoly.setIcon(icon);

        imagePanel.add(monopoly);
        return imagePanel;
    }

    /**
     * @return the east panel holding all the properties to the right
     */
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

        RailRoad = new JButton("SHORT LINE");
        eastPanel.add(RailRoad);
        railroads.add(RailRoad);

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


    /**
     * @return the west panel holding all the properties to the left of the board
     */
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

        RailRoad = new JButton("PENNSYLVANIA RAILROAD");
        westPanel.add(RailRoad);
        railroads.add(RailRoad);

        Property = new JButton("Virginia Avenue");
        westPanel.add(Property);
        properties.add(Property);

        Property = new JButton("States Avenue");
        westPanel.add(Property);
        properties.add(Property);

        ElectricCompany = new JButton("ELECTRIC COMPANY");
        westPanel.add(ElectricCompany);
        utilities.add(ElectricCompany);

        Property = new JButton("St. Charles Place");
        westPanel.add(Property);
        properties.add(Property);

        return westPanel;
    }

    /**
     * @return the east panel holding all the properties to the bottom of the board
     */
    private static JPanel createSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setBackground(BG_COLOR);


        Jail = new JButton("JAIL");
        //setHeightSouth(Jail);
        southPanel.add(Jail);
        properties.add(Jail);


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

        RailRoad = new JButton("READING RAILROAD");
        southPanel.add(RailRoad);
        railroads.add(RailRoad);

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

        Go = new JButton("GO");
        //setHeightSouth(Go);
        southPanel.add(Go);
        properties.add(Go);


        return southPanel;
    }



    /**
     * @return the North panel holding all the properties to the top of the board
     */
    private static JPanel createNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBackground(BG_COLOR);


        Property = new JButton("FREE PARKING");
        northPanel.add(Property);
        properties.add(Property);


        Property = new JButton("Kentucky Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Chance = new JLabel("Chance");
        northPanel.add(Chance);

        Property = new JButton("Indiana Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Indiana Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Illinois Avenue");
        northPanel.add(Property);
        properties.add(Property);

        RailRoad = new JButton("B. & O. RAILROAD");
        northPanel.add(RailRoad);
        railroads.add(RailRoad);

        Property = new JButton("Atlantic Avenue");
        northPanel.add(Property);
        properties.add(Property);

        Property = new JButton("Ventnor Avenue");
        northPanel.add(Property);
        properties.add(Property);

        WaterWorks = new JButton("WATER WORKS");
        northPanel.add(WaterWorks);
        utilities.add(WaterWorks);

        Property = new JButton("Marvin Gardens");
        northPanel.add(Property);
        properties.add(Property);


        GoToJail = new JLabel("GO TO JAIL");
        northPanel.add(GoToJail);

        return northPanel;
    }


    /**
     *
     * It sets the text on the button, as well as add action listeners
     *
     * @param button refernce to the button
     * @param text the text on the button
     * @param mc the controller
     */
    public static void addButton(JButton button, String text, MonopolyController mc){
        button.setText(text);
        button.setActionCommand(button.getText());
        button.addActionListener(mc);
        sidePanel.add(button);
    }


    @Override
    public void handleMonopolySell(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyRentResult(String result, Property location) {

    }

    @Override
    public void handleMonopolyRentUtility(String result, Property location) {

    }

    @Override
    public void handleMonopolyJailFeePaymentResult(boolean paymentSuccess) {
        if(paymentSuccess){
            String info = "You're free to move out of Jail.\n\nYou can Roll dice, to move out of Jail.";
            JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
            roll.setEnabled(true);
            pass.setEnabled(false);
        }
        else{
            String info = "Your payment failed. You're bankrupt!\nYOU'RE OUT OF THE GAME!\n\n" +
                    "You must pass turn to the next player!";
            JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
            roll.setEnabled(false);
            pass.setEnabled(true);
        }
    }

    @Override
    public void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee) {
        JOptionPane.showMessageDialog(this, result, "Jail Player roll result.", JOptionPane.INFORMATION_MESSAGE);
        if (forceJailFee) {
            roll.setEnabled(false);
            pass.setEnabled(false);
        }
        else{
            String info =  "You must pass turn to the next player!";
            JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);
            roll.setEnabled(false);
            pass.setEnabled(true);
        }
    }

    @Override
    public void handleMonopolyGOResult() {

    }

    @Override
    public void handleMonopolyUtilityRailRoadBuy(boolean success, Property location) {

    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {
        System.out.println("...Notified of command: " + command);
        switch (command) {
            case "roll":
                rollNotification(info);
                break;
            case "pass":
                passNotification(info);
                break;
            case "help":
                helpNotification(info);
                break;
            case "player info":
                infoNotification(info);
                break;
            case "quit":
                quitNotification(info);
                break;
            case "win":
                winNotification(info);
                break;
            default:
                break;
        }
    }

    @Override
    public void handleMonopolyBuy(String success, Property location) {

    }

    /**
     * displays player info
     */
    private void infoNotification(String info) {
        JOptionPane.showMessageDialog(this, info, "Player Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * displays win notification
     */
    private void winNotification(String info) {
        JOptionPane.showMessageDialog(this, info , "Winner", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // methods for to handle each command case

    /**
     * when the user clicks on the quit command they will exit the program
     */
    private void quitNotification(String info){
        JOptionPane.showMessageDialog(this, info , "Quit", JOptionPane.INFORMATION_MESSAGE);
    }



    /**
     * displays help info
     */
    private void helpNotification(String info) {
        JOptionPane.showMessageDialog(this, info, "Help", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Display the information of 'pass' result.
     * The current player and the next player details.
     */
    private void passNotification(String info) {
        JOptionPane.showMessageDialog(this, info, "Pass result", JOptionPane.INFORMATION_MESSAGE);

        roll.setEnabled(true);
        pass.setEnabled(false);
    }

    /**
     * Updates the user on what happens after rolling the dice, plus checks for double rolls
     */
    private void rollNotification(String payRentInfo){
        Dice dice = model.getDice(); // get the dice from model- also need to add the getter in Game class to return the dice
        Player currentPlayer = model.getCurrentPlayer(); // get the current player
        Property location = currentPlayer.getLocation(); // get the location of the current player on the board

        String info = "Dice values:\n";
        info += "   " + dice.getDie1() + "\n";
        info += "   " + dice.getDie2() + "\n\n";
        info += "Player location:\n";
        info += "   " + location.getPropertyName() + "\n";
        JOptionPane.showMessageDialog(this, info, "Roll result", JOptionPane.INFORMATION_MESSAGE);

        //pops open the property landed on
        for(JButton bttn : properties){
            if(bttn.getText().equals(location.getPropertyName())){
                bttn.doClick();
            }
        }

        for(JButton bttn : railroads){
            if(bttn.getText().equals(location.getPropertyName())){
                bttn.doClick();
                break;
            }
        }

        for(JButton bttn : utilities){
            if(bttn.getText().equals(location.getPropertyName())){
                bttn.doClick();
                break;
            }
        }

        if(!payRentInfo.equals("")){
            JOptionPane.showMessageDialog(this, payRentInfo, "Payed Rent", JOptionPane.INFORMATION_MESSAGE);
        }

        //double rolls
        if(dice.getDie1() == dice.getDie2()){
            JOptionPane.showMessageDialog(this, "You rolled doubles, you can roll again", "Roll result", JOptionPane.INFORMATION_MESSAGE);
        }else{
            roll.setEnabled(false);
            pass.setEnabled(true);
        }

        if(model.isPassByJail()){
            pass.setEnabled(true);
        }

    }

    public void enableRollButton() {
        roll.setEnabled(true);

    }
}





