import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame implements MonopolyView  {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    private static JPanel boardPanel; //For the grid of properties
    private static JPanel sidePanel; // For the buttons and player info

    //for the functionality buttons
    private static JButton pass;
    private static JButton quit;
    private static JButton help;
    private static JButton roll;
    private static JButton playerInfo;

    //To communicate with the model
    private MonopolyController mc;

    //Each spot on the board
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

        Game model = new Game(playerCount);

        model.addMonopolyView(this);

        mc = new MonopolyController(model);

        //Make sure we have nice window decorations.
        this.setDefaultLookAndFeelDecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(this.getContentPane());

        //Display the window.
        this.pack();
        this.setVisible(true);
    }

    public static void addComponentsToPane(Container pane) {
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

        Chance = new JLabel("Chance");
        c.gridx = 2;
        c.gridy = 0;
        boardPanel.add(Chance, c);

        Property = new JButton("Indiana Avenue");
        c.gridx = 3;
        c.gridy = 0;
        boardPanel.add(Property, c);


        Property = new JButton("Illinois Avenue");
        c.gridx = 4;
        c.gridy = 0;
        boardPanel.add(Property, c);

        RailRoad = new JLabel("B. & O. RAILROAD");
        c.gridx = 5;
        c.gridy = 0;
        boardPanel.add(RailRoad, c);

        Property = new JButton("Atlantic Avenue");
        c.gridx = 6;
        c.gridy = 0;
        boardPanel.add(Property, c);

        Property = new JButton("Ventnor Avenue");
        c.gridx = 7;
        c.gridy = 0;
        boardPanel.add(Property, c);

        WaterWorks = new JLabel("WATER WORKS");
        c.gridx = 8;//col
        c.gridy = 0;//row
        boardPanel.add(WaterWorks, c);

        Property = new JButton("Marvin Gardens");
        c.gridx = 9;
        c.gridy = 0;
        boardPanel.add(Property, c);

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

        Property = new JButton("North Carolina Avenue");
        c.gridx = 10;
        c.gridy = 2;
        boardPanel.add(Property, c);

        empty = new JLabel(" ");
        c.gridx = 10;
        c.gridy = 3;
        boardPanel.add(empty, c);


        Property = new JButton("Pennsylvania Avenue");
        c.gridx = 10;
        c.gridy = 4;
        boardPanel.add(Property, c);

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

        empty = new JLabel(" ");
        c.gridx = 10;//col
        c.gridy = 8;//row
        boardPanel.add(empty, c);

        Property = new JButton("Boardwalk");
        c.gridx = 10;
        c.gridy = 9;
        boardPanel.add(Property, c);

        Go = new JLabel("GO");
        c.gridx = 10;
        c.gridy = 10;
        boardPanel.add(Go, c);

        // sidepanel on col 11

        /*
        sidePanel = new JPanel();
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sidePanel.setSize(300, 500);
        sidePanel.setAlignmentX(pane.RIGHT_ALIGNMENT);
        pass = new JButton("Pass");
        roll = new JButton("Roll");
        playerInfo = new JButton("Player Info");
        sidePanel.add(pass);
        sidePanel.add(roll);
        sidePanel.add(playerInfo);
        c.gridx = 10;
        pane.add(sidePanel, c);


         */




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

        Property = new JButton("Vermont Avenue");
        c.gridx = 2;//col
        c.gridy = 10;//row
        boardPanel.add(Property, c);

        Chance = new JLabel("Chance");
        c.gridx = 3;
        c.gridy = 10;
        boardPanel.add(Chance, c);


        Property = new JButton("Oriental Avenue");
        c.gridx = 4;
        c.gridy = 10;
        boardPanel.add(Property, c);

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

        empty = new JLabel(" ");
        c.gridx = 8;//col
        c.gridy = 10;//row
        boardPanel.add(empty, c);

        Property = new JButton("Mediterranean Avenue");
        c.gridx = 9;
        c.gridy = 10;
        boardPanel.add(Property, c);

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

        Property = new JButton("Tennessee Avenue");
        c.gridx = 0;
        c.gridy = 2;
        boardPanel.add(Property, c);

        empty = new JLabel("Indiana Avenue");
        c.gridx = 0;
        c.gridy = 3;
        boardPanel.add(empty, c);


        Property = new JButton("St. James Place");
        c.gridx = 0;
        c.gridy = 4;
        boardPanel.add(Property, c);

        RailRoad = new JLabel("PENNSYLVANIA RAILROAD");
        c.gridx = 0;
        c.gridy = 5;
        boardPanel.add(RailRoad, c);

        Property = new JButton("Virginia Avenue");
        c.gridx = 0;
        c.gridy = 6;
        boardPanel.add(Property, c);

        Property = new JButton("States Avenue");
        c.gridx = 0;
        c.gridy = 7;
        boardPanel.add(Property, c);

        ElectricCompany = new JLabel("ELECTRIC COMPANY");
        c.gridx = 0;//col
        c.gridy = 8;//row
        boardPanel.add(ElectricCompany, c);

        Property = new JButton("St. Charles Place");
        c.gridx = 0;
        c.gridy = 9;
        boardPanel.add(Property, c);

        Jail = new JLabel("JAIL ");
        c.gridx = 0;
        c.gridy = 10;
        boardPanel.add(Jail, c);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(6,2));
        //sidePanel.setSize(111, 111);
        pane.add(sidePanel);

        addButton(roll, "roll");
        addButton(pass, "pass");
        addButton(help, "help");
        addButton(quit, "quit");
        addButton(playerInfo, "player info");
    }

    public static void addButton(JButton button, String text){
        button = new JButton(text);
        sidePanel.add(button);
    }


    public static void main(String[] args) {
        new MainFrame(2);
    }

    @Override
    public void handleMonopolyStatusUpdate() {

    }
}


