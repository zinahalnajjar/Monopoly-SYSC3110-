import java.io.Serializable;

/**
 * Passing tiles are tiles such as free parking that can be passed with no affect to the player
 * @author Tooba Sheikh
 */
public class CornerTile extends Tile implements Serializable {

    private boolean isJail;
    private boolean isGoToJail;
    private String description;

    /**
     * initializes the tile
     *
     * @param name String name of the property
     */
    public CornerTile(String name, String description) {
        super(name, TileType.CORNERTILE);
        isJail = false;
        isGoToJail = false;
        this.description = description;
    }

    /**
     * @return whether the property is jail or not
     */
    public boolean isJail() {
        return isJail;
    }

    /**
     * @param jail sets true if the tile is a jail property
     */
    public void setJail(boolean jail) {
        isJail = jail;
    }

    /**
     * @return the description of tile
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return whether the property is a go to jailjail or not
     */
    public boolean isGoToJail() {
        return isGoToJail;
    }

    /**
     * @param goToJail sets true if the tile is a go to jail tile
     */
    public void setIsGoToJail(boolean goToJail) {
        isGoToJail = goToJail;
    }
}
