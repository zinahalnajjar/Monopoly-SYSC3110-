
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class MainFrame extends JFrame implements MonopolyView, ActionListener {

    public static final String GAME_FILE_PATH = "monopoly-game";

    private final Color BG_COLOR = new Color(69, 255, 156);

    //private static JPanel boardPanel; //For the grid of properties
    private JPanel sidePanel; // For the buttons and player info

    private ArrayList<JLabel> players;

    //for the functionality buttons
    private JButton pass = new JButton();
    private JButton quit = new JButton();
    private JButton help = new JButton();
    private JButton roll = new JButton();
    private JButton playerInfo = new JButton();

    //To communicate with the model
    private MonopolyController mc;

    //Each spot on the board
    private ArrayList<JButton> properties;
    private ArrayList<JButton> cornerTiles;
    private JButton Property;
    private JButton CornerTile;

    private JPanel mainPanel;
  
    //The model
    private Game model;

    private Tile[] tiles;


    /**
     * Constructor
     *
     * @param playerCount
     */
    public MainFrame(int playerCount, int aiCount, String filename) throws IOException {
        super("Monopoly!!");
  
        model = new Game(playerCount, aiCount, filename);

        model.addMonopolyView(this);

        //To communicate with the model
        MonopolyController mc = new MonopolyController(model);

        //To name the buttons on the board view
        tiles = model.getBoard().tilesList();

        properties = new ArrayList<>();
        cornerTiles = new ArrayList<>();

        players = new ArrayList<>();

        //Make sure we have nice window decorations.
        setDefaultLookAndFeelDecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(this.getContentPane(), mc);

        //Add Save And Exit Button
        JButton saveAndExit = new JButton("Save And Exit");
        sidePanel.add(saveAndExit);
        saveAndExit.addActionListener(this);

        addListeners(model, mc);

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    // method to relaod the saved version of the game
    public static void reloadGame() {
        try {
            //Read game object from file.
            Object object = FileUtil.readFromFile(GAME_FILE_PATH);

            //Cast to MainFrame Object.
            MainFrame mainFrame = (MainFrame) object;
            System.out.println("Game loaded from file.");
            mainFrame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could NOT read game from file: " + GAME_FILE_PATH , "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
            CardFrame cf = new CardFrame((PropertyTile)m.getBoard().getTile(bttn.getText()), mc, m);
            bttn.addActionListener(cf);
        }

        for(JButton bttn : cornerTiles) {

            System.out.println(bttn.getText());

            if(((CornerTile)m.getBoard().getTile(bttn.getText())).isJail()){
                JailFrame jf = new JailFrame((CornerTile)m.getBoard().getTile(bttn.getText()), mc, m);
                bttn.addActionListener(jf);
            } else{
                CornerFrame cf = new CornerFrame((CornerTile)m.getBoard().getTile(bttn.getText()));
                bttn.addActionListener(cf);
            }
        }
    }

    /**
     *
     * Adds all the panels to main ontainer
     *
     * @param pane the content pane
     * @param mc the controller
     */
    public void addComponentsToPane(Container pane, MonopolyController mc) {

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
     * @return a JPanel that will hold the center image
     */
    private JPanel createCenterPanel() {
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
    private JPanel createImagePanel() {
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
    private JPanel createEastPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(8, 1));
        eastPanel.setBackground(BG_COLOR);

        for(int i = 26; i < 32; i++){
            initializeTileButtons(eastPanel, i);
        }

        return eastPanel;
    }


    /**
     * @return the west panel holding all the properties to the left of the board
     */
    private JPanel createWestPanel() {
        JPanel westPanel = new JPanel(new GridLayout(8, 1));
        westPanel.setBackground(BG_COLOR);

        int j = 15;
        for(int i = 8; i < 16; i++){
            initializeTileButtons(westPanel, j);
            j--;
        }

        return westPanel;
    }

    /**
     * @return the east panel holding all the properties to the bottom of the board
     */
    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setBackground(BG_COLOR);

        int j = 7;
        for(int i = 0; i < 8; i++){
            initializeTileButtons(southPanel, j);
            j--;
        }

        return southPanel;
    }

    /**
     * @return the North panel holding all the properties to the top of the board
     */
    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBackground(BG_COLOR);

        for(int i = 16; i < 26; i++){
            initializeTileButtons(northPanel, i);
        }

        return northPanel;
    }


    private void initializeTileButtons(JPanel panel, int i) {
        if(!(tiles[i].getTYPE().equals(TileType.CORNERTILE))){
            Property = new JButton(tiles[i].getTileName());
            panel.add(Property);
            properties.add(Property);
        } else{
            CornerTile = new JButton(tiles[i].getTileName());
            panel.add(CornerTile);
            cornerTiles.add(CornerTile);
        }
    }


    /**
     *
     * It sets the text on the button, as well as add action listeners
     *
     * @param button reference to the button
     * @param text the text on the button
     * @param mc the controller
     */
    public void addButton(JButton button, String text, MonopolyController mc){
        button.setText(text);
        button.setActionCommand(button.getText());
        button.addActionListener(mc);
        sidePanel.add(button);
    }

    @Override
    public void handleMonopolyStatusUpdate(String command, String info) {
        System.out.println("...Notified of command: " + command);
        commandNotifications(info, command);
    }

    @Override
    public void handleMonopolyBuy(String info, PropertyTile newLocation) {

    }

    @Override
    public void handleMonopolySell(boolean success, Tile newLocation) {

    }

    /**
     * displays player info
     */
    private void commandNotifications(String info, String title) {
        JOptionPane.showMessageDialog(this, info, title, JOptionPane.INFORMATION_MESSAGE);

        if(title.equals("win")){
            System.exit(0);
        } else if(title.equals("pass")){
            roll.setEnabled(true);
            pass.setEnabled(false);
        }
    }

    /**
     * Updates the user on what happens after rolling the dice, plus checks for double rolls
     */
    public void handleMonopolyRoll(String JailRentInfo){
        Dice dice = model.getDice(); // get the dice from model- also need to add the getter in Game class to return the dice
        Player currentPlayer = model.getCurrentPlayer(); // get the current player
        Tile location = currentPlayer.getLocation(); // get the location of the current player on the board

        String info = "Dice values:\n";
        info += "   " + dice.getDie1() + "\n";
        info += "   " + dice.getDie2() + "\n\n";
        info += "Player location:\n";
        info += "   " + location.getTileName() + "\n";
        info += JailRentInfo;
        JOptionPane.showMessageDialog(this, info, "Roll result", JOptionPane.INFORMATION_MESSAGE);

        //pops open the property landed on
        for(JButton bttn : properties){
            if(bttn.getText().equals(location.getTileName())){
                bttn.doClick();
            }
        }

        //pops open the property landed on
        for(JButton bttn : cornerTiles){
            if(bttn.getText().equals(location.getTileName())){
                bttn.doClick();
            }
        }

        //double rolls
        if(dice.isDouble()){
            JOptionPane.showMessageDialog(this, "You rolled doubles, you can roll again", "Roll result", JOptionPane.INFORMATION_MESSAGE);
        }else{
            roll.setEnabled(false);
            pass.setEnabled(true);
        }

    }

    @Override
    public void handleMonopolyJailFeePaymentResult(String info) {

    }

    private void saveGame() {
        try {
            //Save the 'CURRENT OBJECT'
            FileUtil.writeToFile(this, GAME_FILE_PATH);
            System.out.println("Game Saved!");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could NOT save game", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveGame();
    }
}





