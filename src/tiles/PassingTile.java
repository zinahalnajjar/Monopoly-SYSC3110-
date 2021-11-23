import java.awt.*;

public class PassingTile implements Property {

    private String propertyName;
    private Color color;

    private final PropertyType TYPE = PropertyType.PASSING_TILE;

    public PassingTile(String name, Color white) {
        this.propertyName = name;
        this.color = white;
    }

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
