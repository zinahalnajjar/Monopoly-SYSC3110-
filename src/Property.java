/**
 * Acts as the deed cards in Monopoly
 * Property holds all the properties of each of the monopoly property card
 *
 * @author Tooba
 */
public class Property {

    private String propertyName;
    private String color;
    private Player owner;
    private int rent;
    private int cost;

    /**
     *
     * Used for special cards that do not have
     * Only used for the starting point
     *
     * @param name name of the property
     */
    public Property(String name) {
        this.propertyName = name;
    }

    /**
     * Initializes all the properties
     *
     * @param propertyName name of property
     * @param color color of the property to help determine set
     * @param rent the rent of the property
     * @param cost the cost of the property
     */
    public Property(String propertyName, String color, int rent, int cost) {
        this.propertyName = propertyName;
        this.color = color;
        this.rent = rent;
        this.cost = cost;
        this.owner = null;
    }

    /**
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName holds the property name
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color holds the color
     */
    public void setColor(String color) {
        this.color = color;
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
     * @return gets the rent
     */
    public int getRent() {
        return rent;
    }

    /**
     * @param rent holds the rent
     */
    public void setRent(int rent) {
        this.rent = rent;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost holds the cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * @return the string description of each of the variables
     */
    @Override
    public String toString(){
        String ownerInfo = (owner ==null) ? "" : owner.getPlayerId() + "";

        return "Property [property name=" + propertyName + ", cost=" + cost +", rent="+ rent +", color=" + color + ", owner id=" + ownerInfo + "]";
    }






}

