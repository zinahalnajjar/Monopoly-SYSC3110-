import java.awt.*;

/**
 * Stores the properties and functionality of Go Tile
 * implements property interface
 *
 * @author Tooba
 */
public class GoTile implements Property {

    //The name and  assigned color of the tile
    private String propertyName;
    private Color color;

    //The property type represents what type property this class is
    private final PropertyType TYPE = PropertyType.GO;

    /**
     * Constructor initializes the name and color of the property
     *
     * @param name string name of the property
     * @param white assigned color value
     */
    public GoTile(String name, Color white) {
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
