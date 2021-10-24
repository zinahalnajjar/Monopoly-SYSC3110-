import java.util.*;

public class Player {

    public int playerId;
    private List<Property> properties;
    private int money;
    private String propertyLocation;
    private boolean isBankrupt;
    private int location;
    private Map<String, List<Property>> colourPropertyMap = new HashMap<>();

    public Player(String aLocation, int money, int aPlayerID) {
        if ("Start".equals(aLocation)) {// set the start postion of each player to square 0 which is the GO square in board
            this.location =0;
        }
        this.playerId = aPlayerID;
        this.money = money;
        this.propertyLocation = aLocation;
        this.properties = new ArrayList<>();
        this.isBankrupt = false;
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public String getPropertyLocation(){
        return this.propertyLocation;
    }
    // setter for property location
    public void setPropertyLocation(String propertyLocation){
        this.propertyLocation = propertyLocation;
    }
    //setLocation-method
    // sets the current location of the player on the board
    public void setLocation(int location){
        this.location = location;

    }
    //getter for location
    public int getLocation(int location){
        return this.location;

    }

    public int getMoney(){
        return this.money;
    }

    public boolean getBankruptcy(){
        return this.isBankrupt;
    }

    public void setBankruptcy(boolean status){
        isBankrupt = status;
        if(!isBankrupt){ // if the player hasn't lost all of their money
            isBankrupt = true;

        }

    }

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

    //adds a property to the list of properties for each player
    public void addProperty(Property property) {
        this.properties.add(property);

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

    /**
     * check if the property is part of a "set" of 3 properties of the same colour
     * one list  contains 3 properies of the same colour and it must be owned by the same owner
     *
     * @param property
     * @return
     */

    public boolean isSetOwnedProperty(Property property){
        String colour = property.getColor();
        List<Property> list = colourPropertyMap.get(colour);
        return (list != null) && (list.size() == 3);
        //for the purpose of testing fast we will make the list size 2 instead of 3
        //return (list != null) && (list.size() == 2);
    }

    //removes a property from the list of properties for each player
    public void removeProperty(Property property) {
        this.properties.remove(property);

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
        if(propertyBuyCost>= 0){ // make sure that the property has a valid price
            if (propertyBuyCost> money){
                this.money =0;
                this.setBankruptcy(true);
            }
            else{
              this.money -=  propertyBuyCost;

            }


        }

        //method to get information about each player
        //money
        //properties
        //their ID





    }












}
