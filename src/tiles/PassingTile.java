import java.awt.*;

/**
 * Passing tiles are tiles such as free parking that can be passed with no affect to the player
 */
public class PassingTile implements Property {

    //name and color of the property tile
    private String propertyName;
    private Color color;

    //represents the type of property
    private final PropertyType TYPE = PropertyType.PASSING_TILE;

    /**
     * The following methods are inherited from the implemented interface
     *
     * @param name String name of the property
     * @param white the assigned color of the property
     */
    public PassingTile(String name, Color white) {
        this.propertyName = name;
        this.color = white;
    }

    /**
     * The following methods are inherited from the implemented interface
     *
     */
    @Override
    public void setState(HouseState s) {
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public HouseState getState() {
        return null;
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public void setOwner(Player currentPlayer) {

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

    public PropertyType getTYPE() {
        return TYPE;
    }
}
