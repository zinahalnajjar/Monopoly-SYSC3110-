import java.util.*;

public class Player {

    public int playerId;
    private List<Property> properties;
    private int money;
    private String propertyLocation;
    private boolean isBankrupt;
    private Property location;

    private Map<String, List<Property>> colourPropertyMap = new HashMap<>();

    //private Map<String, Boolean> colourPropertyMap = new HashMap<>();

    /**
     * @param money
     * @param aPlayerID
     */
    public Player(int money, int aPlayerID) {

        this. location = new Property();

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
     * @return
     */
    public int getPlayerId(){
        return this.playerId;
    }

    /**
     * @return
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
     * @return
     */
    public int getMoney(){
        return this.money;
    }

    /**
     * @return
     */
    public boolean getBankruptcy(){
        return this.isBankrupt;
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
     *
     */
    // this class returns the list of properties for each player
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
     * @param property
     */
    //adds a property to the list of properties for each player
    public void addProperty(Property property) {
        this.properties.add(property);

        //checkSet(property);

        //group properties based on Color
        String colour = property.getColor();
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
     * @return
     */

    public boolean isSetOwned(Property property){
        String colour = property.getColor();
        List<Property> list = colourPropertyMap.get(colour);
        return (list != null) && (list.size() == 3);
        //for the purpose of testing fast we will make the list size 2 instead of 3
        //return (list != null) && (list.size() == 2);

       // return colourPropertyMap.get(property.getColor());
    }

    //removes a property from the list of properties for each player
    public void removeProperty(Property property) {
        this.properties.remove(property);

    }

    public void setLocation(Property location) {
        this.location = location;

    }

    public Property getLocation(){
        return location;
    }

    @Override
    public String toString() {
        return "Player [playerId=" + playerId + ", money=" + money + ", isBankrupt=" + isBankrupt + ", location="
                + location.getPropertyName() + "]";
    }

    /*
    the player will get money when they sell a property
     */
    public void addMoney(int propertySellCost){
        if(propertySellCost>= 0){
            this.money += propertySellCost;
        }

    }

    /*
    when the player buys a property the amount of money they own gets reduced based on the cost of the property they bought

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
}
