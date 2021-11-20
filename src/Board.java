

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Creates and initializes the board
 * implements the move and check validity to assign new location
 *
 * @author Tooba
 * @author Kareem
 */
public class Board {

    private ArrayList<Property> properties;
    private Property newLocation;

    /**
     * Initializes the array that will hold the properties.
     * And then calls a method to create properties to add.
     */
    public Board() {
        this.properties = new ArrayList<>();
        this.newLocation = null;
        createProperties();
        createRailRoads();
        createFreeParking();

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
        Property space1 = new Property("Mediterranean Avenue", new Color(139,69,19), 2, 60);
        Property space2 = new Property("Baltic Avenue", new Color(139,69,19), 4, 60);
        Property space3 = new Property("Oriental Avenue", Color.CYAN, 6, 100);
        Property space4 = new Property("Vermont Avenue", Color.CYAN, 6, 100);
        Property space5 = new Property("Connecticut Avenue", Color.CYAN, 8, 120);
        Property space6 = new Property("St. Charles Place", Color.PINK, 10, 140);
        Property space7 = new Property("States Avenue",Color.PINK, 10, 140);
        Property space8 = new Property("Virginia Avenue", Color.PINK, 12, 160);
        Property space9 = new Property("St. James Place", Color.ORANGE, 14, 180);
        Property space10 = new Property("Tennessee Avenue", Color.ORANGE, 14, 180);
        Property space11 = new Property("New York Avenue", Color.ORANGE, 16, 200);
        Property space12 = new Property("Kentucky Avenue", Color.RED, 18, 220);
        Property space13  = new Property("Indiana Avenue", Color.RED, 18, 220);
        Property space14 = new Property("Illinois Avenue", Color.RED, 20, 240);
        Property space15 = new Property("Atlantic Avenue", Color.YELLOW, 22, 260);
        Property space16 = new Property("Ventnor Avenue", Color.YELLOW, 22, 260);
        Property space17 = new Property("Marvin Gardens", Color.YELLOW, 24, 280);
        Property space18 = new Property("Pacific Avenue", Color.GREEN, 26, 300);
        Property space19 = new Property("North Carolina Avenue", Color.GREEN, 26, 300);
        Property space20 = new Property("Pennsylvania Avenue", Color.GREEN, 28, 320);
        Property space21 = new Property("Park Place", Color.BLUE, 35, 350);
        Property space22 = new Property("Boardwalk", Color.BLUE, 50, 400);

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
        properties.add(space12);
        properties.add(space13);
        properties.add(space14);
        properties.add(space15);
        properties.add(space16);
        properties.add(space17);
        properties.add(space18);
        properties.add(space19);
        properties.add(space20);
        properties.add(space21);
        properties.add(space22);
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
        if(i > properties.size()){
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
        if (newLocation == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return the string description of the board
     */
    @Override
    public String toString() {
        return "Board [properties Count=" + properties.size() + "]";
    }

}
