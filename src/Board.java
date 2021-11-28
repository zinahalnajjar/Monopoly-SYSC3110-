import java.awt.*;
import java.util.Arrays;

/**
 * Creates and initializes the board
 * implements the move and check validity to assign new location
 *
 * @author Tooba
 * @author Kareem
 * @author Zinah
 */
public class Board {

    private Property tiles[];
    private Property newLocation;

    private boolean endSet = false;
    Property jail = new JailTile("JAIL", Color.WHITE);
    Property goToJail = new JailTile("GoToJail", Color.WHITE);




    /**
     * Initializes the array that will hold the properties.
     * And then calls a method to create properties to add.
     */
    public Board() {
        this.tiles = new Property[32];
        this.newLocation = null;
        createProperties();
        createRailRoads();
        createFreeParking();
        createJAIL();
        createGO();
        createUtility();
    }


    /**
     * Initializes and adds the utility property in the Array
     */
    private void createUtility(){
        Property space12 = new UtilityTile("ELECTRIC COMPANY", 25, 50, 150);
        tiles[9] = space12;
        Property space28 = new UtilityTile("WATER WORKS", 25, 50, 150);
        tiles[23] = space28;

    }

    /**
     * Initializes and adds the GO tile in the Array
     */
    private void createGO(){
        tiles[0] = new GoTile("GO", Color.WHITE);
    }

    /**
     * Initializes and adds the jail property in the Array
     */
    private void createJAIL(){
        tiles[25] = goToJail;
        tiles[7] = jail;

    }

    /**
     * Initializes and adds the free parking property in the Array
     */
    private void createFreeParking() {
        Property space20 = new PassingTile("FREE PARKING", Color.WHITE);
        tiles[16] = space20;
    }

    /**
     * Initializes and adds the railroads  in the Array
     */
    private void createRailRoads(){
        Property space5 = new RailRoadTile("READING RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        tiles[3] = space5;
        Property space15 = new RailRoadTile("PENNSYLVANIA RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        tiles[12] = space15;
        Property space25 = new RailRoadTile("B. & O. RAILROAD", Color.WHITE, 25, 50, 100, 200, 200);
        tiles[20] = space25;
        Property space35 = new RailRoadTile("SHORT LINE", Color.WHITE, 25, 50, 100, 200, 200);
        tiles[29] = space35;
    }

    /**
     *
     * Initializes the property
     * Adds the created property to the list
     *
     */
    private void createProperties(){

        //Brown set tiles
        Property space1 = new BrownPropertyTile("Mediterranean Avenue", endSet);
        Property space3 = new BrownPropertyTile("Baltic Avenue", !endSet);

        //Cyan set tiles
        Property space6 = new CyanPropertyTile("Oriental Avenue", endSet);
        Property space8 = new CyanPropertyTile("Vermont Avenue", endSet);
        Property space9 = new CyanPropertyTile("Connecticut Avenue",!endSet);

        //Pink set tiles
        Property space11 = new PinkPropertyTile("St. Charles Place", endSet);
        Property space13 = new PinkPropertyTile("States Avenue",endSet);
        Property space14 = new PinkPropertyTile("Virginia Avenue", !endSet);

        //Orange set tiles
        Property space16 = new OrangePropertyTile("St. James Place", endSet);
        Property space18 = new OrangePropertyTile("Tennessee Avenue", endSet);
        Property space19 = new OrangePropertyTile("New York Avenue", !endSet);

        //Red set tiles
        Property space21 = new RedPropertyTile("Kentucky Avenue", endSet);
        Property space23 = new RedPropertyTile("Indiana Avenue",endSet);
        Property space24 = new RedPropertyTile("Illinois Avenue", !endSet);

        //Yellow set tiles
        Property space26 = new YellowPropertyTile("Atlantic Avenue", endSet);
        Property space27 = new YellowPropertyTile("Ventnor Avenue", endSet);
        Property space29 = new YellowPropertyTile("Marvin Gardens", !endSet);

        //Green set tiles
        Property space31 = new GreenPropertyTile("Pacific Avenue", endSet);
        Property space32 = new GreenPropertyTile("North Carolina Avenue", endSet);
        Property space34 = new GreenPropertyTile("Pennsylvania Avenue", !endSet);

        //Blue set tiles
        Property space37 = new BluePropertyTile("Park Place", endSet);
        Property space39 = new BluePropertyTile("Boardwalk", !endSet);

        tiles[1] = space1;
        tiles[2] = space3;
        tiles[4] = space6;
        tiles[5] = space8;
        tiles[6] = space9;
        tiles[8] = space11;
        tiles[10] = space13;
        tiles[11] = space14;
        tiles[13] = space16;
        tiles[14] = space18;
        tiles[15] = space19;
        tiles[17] = space21;
        tiles[18] = space23;
        tiles[19] = space24;
        tiles[21] = space26;
        tiles[22] = space27;
        tiles[24] = space29;
        tiles[26] = space31;
        tiles[27] = space32;
        tiles[28] = space34;
        tiles[30] = space37;
        tiles[31] = space39;
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
        int i = Arrays.asList(tiles).indexOf(location);
        i = i + spaces;

        if(i >= tiles.length){
            i = i - tiles.length;
        }
        newLocation = tiles[i];
        return newLocation;
    }

    /**
     * @return the list of properties
     */
    public Property[] tilesList(){
        return tiles;
    }
    
    /**

     * @return property identified by name
     */
    public Property getProperty(String name){
        for(Property t : tiles){
            if(t.getPropertyName().equals(name)){
                return t;
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
        return "Board [properties Count=" + tiles.length + "]";
    }

    /**
     * Literally moves person to Jail
     *
     * @return returns jail as the new property location
     */
    public Property moveToJail() {
        newLocation = tiles[7];
        return newLocation;
    }

    /**
     * Initializes and adds the jail property in the Array
     */
    public Property getJailProperty() {
        return jail;
    }
}
