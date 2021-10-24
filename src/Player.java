import java.util.*;

public class Player {

    public int playerId;
    private List<Property> properties;
    private int money;
    private String propertyLocation;
    private boolean isBankrupt;

    public Player(String aPropertyLocation, int money, int aPlayerID) {
        this.playerId = aPlayerID;
        this.money = money;
        this.propertyLocation = aPropertyLocation;
        this.properties = new ArrayList<>();
        this.isBankrupt = false;
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public String getPropertyLocation(){
        return this.propertyLocation;
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

    }

    //setLocation







}
