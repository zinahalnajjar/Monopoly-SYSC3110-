

/**
 * Passing tiles are tiles such as free parking that can be passed with no affect to the player
 */
public class CornerTile extends Tile {

    /**
     * initializes the tile
     *
     * @param name String name of the property
     */
    public CornerTile(String name) {
        super(name, TileType.CORNERTILE);
    }
}
