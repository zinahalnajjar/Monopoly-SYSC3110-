import java.awt.*;

/**
 * Property tile that holds the blue color set with their defined constant rent and cost
 *
 * @author Tooba
 */
public class BluePropertyTile implements Property {

    //Constants for the properties of the blue set
    private final int HOUSE_COST = 200;
    private final int INIT_COST = 350;
    private final int INIT_RENT = 35;

    //The last deed card in a colored set is usually higher value.
    //The following constants hold the multiplier.
    private final int END_RENT_VALUE = 15;
    private final int END_COST_VALUE = 50;

    //Color of the property
    private final Color TILE_COLOR = Color.BLUE;

    private String tileName;
    private HouseState state; //how many houses built
    private Player owner;
    private boolean endSet; //end set is true when it is the last card of the set because more expensive than the rest of the set

    //Type of property
    private final PropertyType TYPE = PropertyType.PROPERTY;

    /**
     * Initializes the properties of this orange set tile
     *
     * @param name String name of the property
     * @param endSet boolean value which is true if the card is the last card of the deck
     */
    public BluePropertyTile(String name, boolean endSet){
        this.tileName = name;
        this.endSet = endSet;
        this.owner = null;
        this.state = HouseState.UNOWNED;
    }

    /**
     * @return the property name
     */
    public String getPropertyName() {
        return tileName;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return TILE_COLOR;
    }

    /**
     * @return the owner (type player) of the property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @param owner holds the owner of the property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * @return gets the rent multiplied by whatever number of houses are bought
     */
    public int getRent() {
        if(endSet){
            return (INIT_RENT + END_RENT_VALUE) * state.getRentMultiplier();
        }
        return INIT_RENT * state.getRentMultiplier();
    }

    /**
     * @return the cost
     */
    public int getCost() {
        if(endSet){
            return (INIT_COST + END_COST_VALUE);
        }
        return INIT_COST;
    }

    /**
     * @return The type of the property
     */
    public PropertyType getTYPE() {
        return TYPE;
    }

    /**
     * sets the state of the houses
     *
     * @param s holds the states that we want to set
     */
    @Override
    public void setState(HouseState s) {
        this.state = s;
    }


    /**
     * @return the string description of each of the variables
     */

    public void incrementState() {
        this.state = state.next();
    }

    /**
     * @return the number houses - the "state" of property - in
     */
    public HouseState getState() {
        return state;
    }


    /**
     * @return the cost of the houses
     */
    public int getCostPerHouse() {
        return HOUSE_COST;
    }

    /**
     * @return the string description of the class
     */
    @Override
    public String toString(){
        String ownerInfo = (owner == null) ? "" : owner.getPlayerId() + "";

        int rent = INIT_RENT;

        if(endSet){
            rent = (rent + END_RENT_VALUE) * state.getRentMultiplier();
        }

        return "Property [property name=" + tileName + ", cost=" + INIT_COST + ", rent="+ rent +", House Cost="+ HOUSE_COST +"Number of houses built=" + state.getHouseNum() + "color=" + TILE_COLOR + ", owner id=" + ownerInfo + "]";
    }

}
