import java.awt.*;
import java.util.ArrayList;

/**
 * Creates and initializes the board
 * implements the move and check validity to assign new location
 *
 * @author Tooba
 * @author Kareem
 * @author Zinah
 */
public class Board {

    private final ArrayList<Property> properties;
    private Property newLocation;
    private Property jailProperty;


    /**
     * Initializes the array that will hold the properties.
     * And then calls a method to create properties to add.
     */
    public Board() {
        this.properties = new ArrayList<>();
        this.newLocation = null;
        this.jailProperty = new Property("JAIL", Color.WHITE);
        createProperties();
        createRailRoads();
        createFreeParking();
        createJAIL();
        createGO();
        createUtility();

    }
    private void createUtility(){
        Property utility1 = new Property("ELECTRIC COMPANY", Color.WHITE, 25, 50, 150);
        properties.add(utility1);
        Property utility2 = new Property("WATER WORKS", Color.WHITE, 25, 50, 150);
        properties.add(utility2);

    }
    private void createGO(){
        properties.add(new Property("GO", Color.WHITE));

    }

    private void createJAIL(){
        properties.add(jailProperty);

    }

    private void createFreeParking() {

        properties.add(new Property("FREE PARKING", Color.WHITE));
    }

    private void createRailRoads(){
        Property railroad1 = new Property("SHORT LINE", Color.WHITE, 25, 50, 100, 200, 200);
        properties.add(railroad1);
        Property railroad2 = new Property("READING RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        properties.add(railroad2);
        Property railroad3 = new Property("PENNSYLVANIA RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        properties.add(railroad3);
        Property railroad4 = new Property("B. & O. RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        properties.add(railroad4);

    }

    /**
     *
     * Initializes the property
     * Adds the created property to the list
     *
     */
    private void createProperties(){
        //All the spaces on the board
        Property space1 = new Property("Mediterranean Avenue", new Color(139,69,19), 2, 60, 50);
        Property space2 = new Property("Baltic Avenue", new Color(139,69,19), 4, 60, 50);
        Property space3 = new Property("Oriental Avenue", Color.CYAN, 6, 100, 50);
        Property space4 = new Property("Vermont Avenue", Color.CYAN, 6, 100, 50);
        Property space5 = new Property("Connecticut Avenue", Color.CYAN, 8, 120, 50);
        Property space6 = new Property("St. Charles Place", Color.PINK, 10, 140, 100);
        Property space7 = new Property("States Avenue",Color.PINK, 10, 140, 100);
        Property space8 = new Property("Virginia Avenue", Color.PINK, 12, 160, 100);
        Property space9 = new Property("St. James Place", Color.ORANGE, 14, 180, 100);
        Property space10 = new Property("Tennessee Avenue", Color.ORANGE, 14, 180, 100);
        Property space11 = new Property("New York Avenue", Color.ORANGE, 16, 200, 100);
        Property space12 = new Property("Kentucky Avenue", Color.RED, 18, 220, 150);
        Property space13  = new Property("Indiana Avenue", Color.RED, 18, 220, 150);
        Property space14 = new Property("Illinois Avenue", Color.RED, 20, 240, 150);
        Property space15 = new Property("Atlantic Avenue", Color.YELLOW, 22, 260, 150);
        Property space16 = new Property("Ventnor Avenue", Color.YELLOW, 22, 260, 150);
        Property space17 = new Property("Marvin Gardens", Color.YELLOW, 24, 280, 150);
        Property space18 = new Property("Pacific Avenue", Color.GREEN, 26, 300, 200);
        Property space19 = new Property("North Carolina Avenue", Color.GREEN, 26, 300, 200);
        Property space20 = new Property("Pennsylvania Avenue", Color.GREEN, 28, 320, 200);
        Property space21 = new Property("Park Place", Color.BLUE, 35, 350, 200);
        Property space22 = new Property("Boardwalk", Color.BLUE, 50, 400, 200);

        initBoard(space1, space2, space3, space4, space5, space6, space7, space8, space9, space10, space11);
        initBoard(space12, space13, space14, space15, space16, space17, space18, space19, space20, space21, space22);
    }

    private void initBoard(Property space1, Property space2, Property space3, Property space4, Property space5, Property space6, Property space7, Property space8, Property space9, Property space10, Property space11) {
        properties.add(space1);
        properties.add(space2);
        properties.add(space3);
        properties.add(space4);
        properties.add(space5);
        properties.add(space6);
        properties.add(space7);
        properties.add(space8);
        properties.add(space9);
        properties.add(space10);
        properties.add(space11);
    }

    /**
     *
     * Is used to find the new location of the property.
     *
     * @param spaces the sum of the two dice.
     * @param location the current location of the player.
     * @return the new location of the player
     */
    public Property move(int spaces, Property location){
        int i = properties.indexOf(location);
        i = i + spaces;
        //The index can not be EQUAL or more.
        // I just added the equal sign
        if(i >= properties.size()){
            i = i - properties.size();
        }
        newLocation = properties.get(i);
        return newLocation;
    }

    /**
     * @return the list of properties
     */
    public ArrayList<Property> propertiesList(){
        return properties;
    }

    public Property getJailProperty(){
        return  this.jailProperty;
    }
    /**

     * @return property identified by name
     */
    public Property getProperty(String name){
        for(Property p : properties){
            if(p.getPropertyName().equals(name)){
                return p;
            }
        }
        return null;
    }

    /**
     * checks if the property chosen is valid spot on the board
     *
     * @param newLocation the location to be checked
     * @return true if the location is valid
     */
    public boolean getValidLocation(Property newLocation) {
        return newLocation != null;
    }

    /**
     * @return the string description of the board
     */
    @Override
    public String toString() {
        return "Board [properties Count=" + properties.size() + "]";
    }

    public Property moveToJail() {
        newLocation = this.jailProperty;
        return newLocation;
    }

}
