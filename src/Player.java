import java.awt.*;
import java.io.Serializable;
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
 * @author Walid
 */

public class Player implements Serializable {

    public final int playerId;
    private final List<Property> properties;
    private int money;
    private boolean isBankrupt;
    private Property location;
    private boolean isAI;
    private int jailCounter;

    private final Map<Color, List<Property>> colourPropertyMap = new HashMap<>();

    //private Map<String, Boolean> colourPropertyMap = new HashMap<>();

    /**
     * creates a player on the board
     * @param money the initial amount of money the player has
     * @param aPlayerID players id
     * @param start the start location of the player
     */
    public Player(int money, int aPlayerID, Property start) {

        this.location = start;

        this.playerId = aPlayerID;
        this.money = money;
        this.properties = new ArrayList<>();
        this.isBankrupt = false;
        this.isAI = false;
        this.jailCounter = 0;


    }

    /**
     * @return the current player ID number
     */
    public int getPlayerId(){return this.playerId;
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
     * @param status the bankruptcy status of the player
     */
    public void setBankruptcy(boolean status){
        isBankrupt = status;
        if(!isBankrupt){ // if the player hasn't lost all of their money
            isBankrupt = true;

        }

    }

    /**
     * adds a property to the list of properties for each player
     * @param property the property to be added to the list
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


    /**
     * check if the property is part of a "set" of 3 properties of the same colour
     * one list  contains 3 properties of the same colour, and it must be owned by the same owner
     *
     * @param property the property to be checked for in the set
     * @return true if the set is owned
     */

    public boolean isSetOwned(Property property){
        Color colour = property.getColor();
        List<Property> list = colourPropertyMap.get(colour);
        return (list != null) && (list.size() == 3);
        //for the purpose of testing fast we will make the list size 2 instead of 3

    }

    /**
     * removes a property from the list of properties for each player
     * @param property the property to be removed
     */

    public boolean removeProperty(Property property) {
        return this.properties.remove(property);
    }

    /**
     * Sets the locations
     *
     * @param location the location of the player
     */

    public void setLocation(Property location) {
        this.location = location;

    }

    /**
     *
     * @return location the location of the player on the board
     */
    public Property getLocation(){
        return location;
    }

    /**
     * the player will get money when they sell a property
     * @param propertySellCost money to be added to the players bank
     */
    public void addMoney(int propertySellCost){
        if(propertySellCost>= 0){
            this.money += propertySellCost;
        }

    }

    /**
     *
     * when the player buys a property the amount of money they own gets reduced based on the cost of the property they bought
     * @param propertyBuyCost cost of the property being bought
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

    /**
     * @return the list of properties in string version
     */
    public String getStringProperties() {
        String p = "";

        for(Property property : properties){
            p += property.getPropertyName() + "\n";
        }

        return p;
    }

    /**
     *
     * @return the playerID, money, isBankrupt, location
     */
    @Override
    public String toString() {
        if(isBankrupt){
            return "Player " + playerId + " is bankrupt.\n";
        }
        return "Player " + playerId + "\nMoney: " + money + ", Location: "
                + location.getPropertyName() + "\nProperties owned: \n"+ getStringProperties();
    }

    /**
     * Sets a player as AI
     */
    public void setAI() {
        this.isAI = true;
    }

    /**
     *  Unsets a player from AI to not an AI
     */
    public void unsetAI(){
        this.isAI = false;
    }

    /**
     * returns the status of a player if it is an AI or not
     * @return
     */
    public boolean getAIStatus(){
        return isAI;
    }

    /**
     * Increments the Jail Counter
     */
    public void addJailCounter() {
        jailCounter++;
    }

    /**
     * resets the jail counter for the AI player
     */
    public void resetJailCounter() {
        jailCounter = 0;
    }

    /**
     * Returns the amount of the jail counter
     * @return
     */
    public int getJailCounter() {
        return jailCounter;
    }

    /**
     * Pays for whatever needs to be paid
     * @param cost
     */
    public void pay(int cost) {
        money -= cost;
    }
}