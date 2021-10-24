/**
 *
 */
public class Property {

    private String propertyName;
    private String color;
    private Player owner;
    private int rent;
    private int cost;

    /**
     * @param propertyName
     * @param color
     * @param rent
     * @param cost
     */
    public Property(String propertyName, String color, int rent, int cost) {
        this.propertyName = propertyName;
        this.color = color;
        this.rent = rent;
        this.cost = cost;
        this.owner = null;
    }

    /**
     * @return
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @param propertyName
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @param owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * @return
     */
    public int getRent() {
        return rent;
    }

    /**
     * @param rent
     */
    public void setRent(int rent) {
        this.rent = rent;
    }

    /**
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
  
  //mathod for properties
}

