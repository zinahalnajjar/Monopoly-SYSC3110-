
import java.awt.*;

/**
 * Acts as the deed cards in Monopoly
 * Property holds all the properties of each of the monopoly property card
 *
 * @author Tooba
 */
public class Property {

    public enum State{
        UNOWNED(0),
        RENT(1),
        H1(20),
        H2(40),
        H3(60),
        H4(80),
        HOTEL(100);

        private int rentMultiplier;

        State(int rentMultiplier) {
            this.rentMultiplier = rentMultiplier;
        }
        public int getRentMultiplier(){
            return rentMultiplier;
        }
    }

    private State state;

    private int buyCounter;

    private String propertyName;
    private Color color;
    private Player owner;
    private int initialRent;
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
     * @param initialRent the rent of the property
     * @param cost the cost of the property
     */
    public Property(String propertyName, Color color, int initialRent, int cost) {
        this.propertyName = propertyName;
        this.color = color;
        this.initialRent = initialRent;
        this.cost = cost;
        this.owner = null;
        buyCounter = 0;
        state = State.UNOWNED;
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

        return "Property [property name=" + propertyName + ", cost=" + cost +", rent="+ initialRent +", color=" + color + ", owner id=" + ownerInfo + "]";
    }

    public int getBuyCounter() {
        return buyCounter;
    }

    public void setBuyCounter(int buyCounter) {
        this.buyCounter = buyCounter;
    }

    public void incrementBuyCounter() {
        this.buyCounter++;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

