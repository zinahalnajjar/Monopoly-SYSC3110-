import java.awt.*;

public class RailRoadTile implements Property {

    private String propertyName;
    private Color color;
    private Player owner;
    private int rent1;
    private int rent2;
    private int rent3;
    private int rent4;
    private int cost;

    private final PropertyType TYPE = PropertyType.RAILROAD;

    /**
     * Initializes all the properties
     *
     * @param propertyName name of property
     * @param color color of the property to help determine set
     * @param rent the rent of the property
     * @param rent2 the cost of the property
     */
    public RailRoadTile(String propertyName, Color color, int rent, int rent2, int rent3, int rent4, int cost) {
        this.propertyName = propertyName;
        this.color = color;
        this.rent1 = rent;
        this.rent2 = rent2;
        this.rent3 = rent3;
        this.rent4 = rent4;
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
    public int getRent3() {
        return rent3;
    }
    public int getRent4() {
        return rent4;
    }


    public int getCost() {
        return cost;
    }

    @Override
    public void setState(HouseState s) {
    }

    /**
     * @return the string description of each of the variables
     */
    @Override
    public String toString(){
        String ownerInfo = (owner == null) ? "" : owner.getPlayerId() + "";

        return "Property [property name=" + propertyName + ", cost=" + cost +", rent="+ rent1 +", color=" + color + ", owner id=" + ownerInfo + "]";
    }

    public PropertyType getTYPE() {
        return TYPE;
    }
}
