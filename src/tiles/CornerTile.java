import java.io.Serializable;

/**
 * Passing tiles are tiles such as free parking that can be passed with no affect to the player
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

    public boolean isJail() {
        return isJail;
    }

    public void setJail(boolean jail) {
        isJail = jail;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGoToJail() {
        return isGoToJail;
    }

    public void setIsGoToJail(boolean goToJail) {
        isGoToJail = goToJail;
    }
}