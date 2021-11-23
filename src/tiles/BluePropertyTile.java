import java.awt.*;

/**
 * Class holding the properties of blue color set properties
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

    //constant for the color of the sett
    private final Color TILE_COLOR = Color.BLUE;

    private String tileName;
    private HouseState state;
    private Player owner;
    private boolean endSet;

    private final PropertyType TYPE = PropertyType.PROPERTY;

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

    public PropertyType getTYPE() {
        return TYPE;
    }

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

    public HouseState getState() {
        return state;
    }

    public int getCostPerHouse() {
        return HOUSE_COST;
    }

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
