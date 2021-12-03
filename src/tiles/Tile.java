

public abstract class Tile {

    private TileType type;
    private final String tileName;

    /**
     * Initializes the properties of this orange set tile
     *
     * @param name String name of the property
     */
    public Tile(String name, TileType type){
        this.tileName = name;
        this.type = type;
    }

    /**
     * @return the property name
     */
    public String getTileName() {
        return tileName;
    }

    /**
     * @return The type of the property
     */
    public TileType getTYPE() {
        return type;
    }
}
