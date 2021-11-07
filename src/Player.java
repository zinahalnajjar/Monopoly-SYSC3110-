import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class Player- Player in monopoly game
 * this class is part of the "Monopoly Game" application
 *
 *
 * Player has a location on the board, can own properties, gain/lose money from buying and renting properties.They can also go bankrupt if they
 * lose all of their money
 *
 * @author Zinah
 * @author Kareem
 */

public class Player {

    public int playerId;
    private List<Property> properties;
    private int money;
    private String propertyLocation;
    private boolean isBankrupt;
    private Property location;

    private Map<Color, List<Property>> colourPropertyMap = new HashMap<>();

    //private Map<String, Boolean> colourPropertyMap = new HashMap<>();

    /**
     * creates a player on the board
     * @param money
     * @param aPlayerID
     */
    public Player(int money, int aPlayerID) {

        this. location = new Property("Start");

        this.playerId = aPlayerID;
        this.money = money;
        this.propertyLocation = "";
        this.properties = new ArrayList<>();
        this.isBankrupt = false;

        /*colourPropertyMap.put("Brown", false);
        colourPropertyMap.put("Light Blue", false);
        colourPropertyMap.put("Pink", false);
        colourPropertyMap.put("Orange", false);
        colourPropertyMap.put("Red", false);
        colourPropertyMap.put("Yellow", false);
        colourPropertyMap.put("Green", false);
        colourPropertyMap.put("Dark Blue", false);*/
    }

    /**
     * @return the current player ID number
     */
    public int getPlayerId(){return this.playerId;
    }

    /**
     * @return the property location where the player is currently on
     */
    public String getPropertyLocation(){
        return this.propertyLocation;
    }

    /**
     * @param propertyLocation
     */
    // setter for property location
    public void setPropertyLocation(String propertyLocation){
        this.propertyLocation = propertyLocation;
    }

    /**
     * @return current amount of money for each player
     */
    public int getMoney(){
        return this.money;
    }

    /**
     * @return true if the player is bankrupt
     */
    public boolean getBankruptcy(){ return this.isBankrupt;
    }

    /**
     * @param status
     */
    public void setBankruptcy(boolean status){
        isBankrupt = status;
        if(!isBankrupt){ // if the player hasn't lost all of their money
            isBankrupt = true;

        }

    }

    /**
     * Print the list of property for each player
     */

    public void getProperty(){
        if(properties.isEmpty()){
            System.out.println("You do not own any properties yet");
        }else{
            for(Property property: properties){
                System.out.println(property);
            }
        }
    }

    /**
     * adds a property to the list of properties for each player
     * @param property
     */
    public void addProperty(Property property) {
        this.properties.add(property);

        //checkSet(property);

        //group properties based on Color
        Color colour = property.getColor();
        List<Property> list = colourPropertyMap.get(colour);
        if(list == null){
            //first property for this colour
            list = new ArrayList<>();
            colourPropertyMap.put(colour,list);

        }
        // add property to the list for (current colour).
        list.add(property);


    }

    //Tooba version color set - incomplete
    public void checkSet(Property property){
        // going to check if the property is in a colored set
        // if it is set to true the player owns all the properties of that color.
    }


    /**
     * check if the property is part of a "set" of 3 properties of the same colour
     * one list  contains 3 properies of the same colour and it must be owned by the same owner
     *
     * @param property
     * @return true if the set is owned
     */

    public boolean isSetOwned(Property property){
        Color colour = property.getColor();
        List<Property> list = colourPropertyMap.get(colour);
        return (list != null) && (list.size() == 3);
        //for the purpose of testing fast we will make the list size 2 instead of 3
        //return (list != null) && (list.size() == 2);

       // return colourPropertyMap.get(property.getColor());
    }

    /**
     * removes a property from the list of properties for each player
     * @param property
     */

    public void removeProperty(Property property) {
        this.properties.remove(property);

    }

    /**
     *
     * @param location
     */

    public void setLocation(Property location) {
        this.location = location;

    }

    /**
     *
     * @return location
     */
    public Property getLocation(){
        return location;
    }

    /**
     *
     * @return toString
     */
    public String getInfo(){ return toString();}

    /**
     * the player will get money when they sell a property
     * @param propertySellCost
     */
    public void addMoney(int propertySellCost){
        if(propertySellCost>= 0){
            this.money += propertySellCost;
        }

    }

    /**
     *
     * when the player buys a property the amount of money they own gets reduced based on the cost of the property they bought
     * @param propertyBuyCost
     */
    public void removeMoney(int propertyBuyCost){
        if (propertyBuyCost >= 0) { // make sure that the property has a valid price
            if (propertyBuyCost > money) {
                this.money = 0;
                this.setBankruptcy(true);
            } else {
                this.money -= propertyBuyCost;

            }
        }
    }

    public String getStringProperties() {
        String p = "";

        for(Property property : properties){
            p += property.getPropertyName() + "\n";
        }

        return p;
    }

    public String getColourPropertyMap() {
        String p = "";

        for(Property property : properties){
            p += property.getPropertyName() + ", ";
        }

        return p;
    }

    /**
     *
     * @return the playerID, money, isBankrupt, location
     */
    @Override
    public String toString() {
        return "Player: " + playerId + ",  money: " + money + ", isBankrupt: " + isBankrupt + ", location: "
                + location.getPropertyName() + "\n - Properties owned: \n"+ getStringProperties();
    }

}
