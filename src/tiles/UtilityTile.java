import java.awt.*;

public class UtilityTile implements Property {

    private String propertyName;
    private Color color;
    private Player owner;
    private int rent1;
    private int rent2;

    private int cost;

    public UtilityTile(String propertyName, int rent, int rent2, int cost) {
        this.propertyName = propertyName;
        this.rent1 = rent;
        this.rent2 = rent2;
        this.cost = cost;
        this.owner = null;
    }

    /**
     * @return the property name
     */
    @Override
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return the color
     */
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public HouseState getState() {
        return null;
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

    @Override
    public void incrementState() {

    }

    @Override
    public int getCostPerHouse() {
        return 0;
    }

    @Override
    public int getRent() {
        return 0;
    }

    public int getRent1() {
        return rent1;
    }
    public int getRent2() {
        return rent2;
    }

    public int getCost() {
        return cost;
    }

    /**
     * @return the string description of each of the variables
     */
    @Override
    public String toString(){
        String ownerInfo = (owner == null) ? "" : owner.getPlayerId() + "";

        return "Property [property name=" + propertyName + ", cost=" + cost +", rent="+ rent1 +", color=" + color + ", owner id=" + ownerInfo + "]";
    }

}
