import java.awt.*;

/**
 * Property interface used to represent the squares of the board
 * It is implemented by other tiles classes
 *
 * @author Tooba
 *
 */
public interface Property {

    /**
     * @return the string name of the property
     */
    String getPropertyName();

    /**
     * @return the color of the property
     */
    Color getColor();

    /**
     * @return the state of the property
     */
    HouseState getState();

    /**
     * @return the owner of the property
     */
    Player getOwner();

    /**
     * @return the cost of the property
     */
    int getCost();

    /**
     * @param currentPlayer sets the current player as the owner of the property
     */
    void setOwner(Player currentPlayer);

    /**
     * increments the state of each property
     */
    void incrementState();

    /**
     * @return gets the cost of each house
     */
    int getCostPerHouse();

    /**
     * @return gets the rent of the properties
     */
    int getRent();

    /**
     * @return the type of the property
     */
    PropertyType getTYPE();

    /**
     * @param s the state of the houses on the property
     */
    void setState(HouseState s);
}

