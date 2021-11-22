
import java.awt.*;

/**
 * Acts as the deed cards in Monopoly
 * Property holds all the properties of each of the monopoly property card
 *
 * @author Tooba
 */
public class Property {

    public enum HouseState{
        UNOWNED(0, 0),
        RENT(1, 0),
        H1(20, 1),
        H2(40, 2),
        H3(60, 3),
        H4(80, 4),
        HOTEL(100, 5);

        private int rentMultiplier;
        private int houseNum;

        HouseState(int rentMultiplier, int house) {
            this.rentMultiplier = rentMultiplier;
            this.houseNum = house;
        }
        public int getRentMultiplier(){
            return rentMultiplier;
        }

        public HouseState next(){
            return values()[ordinal() + 1];
        }

        public int getHouseNum() {
            return houseNum;
        }

    }

    private HouseState state;

    private int buyCounter;

    private String propertyName;
    private Color color;
    private Player owner;
    private int initialRent;
    private int initialCost;
    private int costPerHouse;


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
     * @param initialRent the rent of the property
     * @param initCost the cost of the property
     */
    public Property(String propertyName, Color color, int initialRent, int initCost, int costHouse) {
        this.propertyName = propertyName;
        this.color = color;
        this.initialRent = initialRent;
        this.initialCost = initCost;
        this.owner = null;
        buyCounter = 0;
        state = HouseState.UNOWNED;
        this.costPerHouse = costHouse;
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
    public Color getColor() {
        return color;
    }

    /**
     * @param color holds the color
     */
    public void setColor(Color color) {
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
    public int getInitialRent() {
        return initialRent;
    }

    /**
     * @return gets the rent multiplied by whatever number of houses are bought
     */
    public int getRent() {
        return initialRent * state.getRentMultiplier();
    }

    /**
     * @param initialRent holds the rent
     */
    public void setInitialRent(int initialRent) {
        this.initialRent = initialRent;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return initialCost;
    }

    /**
     * @param cost holds the cost
     */
    public void setCost(int cost) {
        this.initialCost = cost;
    }

    /**
     * @return the string description of each of the variables
     */
    @Override
    public String toString(){
        String ownerInfo = (owner == null) ? "" : owner.getPlayerId() + "";

        return "Property [property name=" + propertyName + ", cost=" + initialCost +", rent="+ initialRent +", color=" + color + ", owner id=" + ownerInfo + "]";
    }

    public void incrementState() {
        this.state = state.next();
    }

    public HouseState getState() {
        return state;
    }

    public void setState(HouseState state) {
        this.state = state;
    }

    public int getCostPerHouse() {
        return costPerHouse;
    }

    public void setCostPerHouse(int costPerHouse) {
        this.costPerHouse = costPerHouse;
    }
}

