import java.awt.*;

/**
 * Property tile that holds the blue color set with their defined constant rent and cost
 *
 * @author Tooba
 */
public class PropertyTile extends Tile {

    //Color of the property
    private PropertyState state;
    private Player owner;

    //color
    private Color color;

    //The money value of houses, rent
    private int rent;
    private int cost;
    private int houseCost;

    /**
     * Initializes the properties of this orange set tile
     *
     * @param name String name of the property
     */
    public PropertyTile(String name, TileType type, Color color, int rent, int cost, int houseCost){
        super(name, type);

        this.owner = null;
        this.state = PropertyState.UNOWNED;

        this.color = color;

        this.rent = rent;
        this.cost = cost;
        this.houseCost = houseCost;
    }


    /**
     * @return the color
     */
    public Color getColor() {
        return color;
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
        return rent * state.getRentMultiplier();
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return the cost of the houses
     */
    public int getCostPerHouse() {
        return houseCost;
    }

    /**
     * sets the state of the houses
     *
     * @param s holds the states that we want to set
     */
    public void setState(PropertyState s) {
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
    public PropertyState getState() {
        return state;
    }


    /**
     * @return the string description of the class
     */
    @Override
    public String toString(){
        String ownerInfo = (owner == null) ? "" : owner.getPlayerId() + "";

        return "Property [property name=" + super.getTileName() + ", cost=" + cost + ", rent="+ rent * state.getRentMultiplier() +", House Cost="+ houseCost +"Number of houses built=" + state.getHouseNum() + "color=" + color + ", owner id=" + ownerInfo + "]";
    }

}
