import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Creates and initializes the board
 * implements the move and check validity to assign new location
 *
 * @author Tooba
 * @author Kareem
 * @author Zinah
 */
public class Board {

    private Tile tiles[];
    private Tile newLocation;

    /**
     * Initializes the array that will hold the properties.
     * And then calls a method to create properties to add.
     */
    public Board(String fileName)  {
        this.tiles = new Tile[32];
        this.newLocation = null;
        importFromXmlFile(fileName);
    }

    public void importFromXmlFile(String filename) {
        File f = new File(filename);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        XmlFileHandler handler = new XmlFileHandler();


        try {
            saxParser = factory.newSAXParser();
            saxParser.parse(f, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tiles = handler.getBoardTiles();

        for(Tile t : tiles){
            System.out.println(t.getTileName());
        }
    }


    /**
     *
     * Is used to find the new location of the property.
     *
     * @param spaces the sum of the two dice.
     * @param location the current location of the player.
     * @return the new location of the player
     */
    public Tile move(int spaces, Tile location){
        int i = Arrays.asList(tiles).indexOf(location);
        i = i + spaces;

        if(i >= tiles.length){
            i = i - tiles.length;
        }
        newLocation = tiles[i];
        return newLocation;
    }

    /**
     * @return the list of properties
     */
    public Tile[] tilesList(){
        return tiles;
    }
    
    /**

     * @return property identified by name
     */
    public Tile getTile(String name){
        for(Tile t : tiles){
            if(t.getTileName().equals(name)){
                return t;
            }
        }
        return null;
    }

    /**
     * checks if the property chosen is valid spot on the board
     *
     * @param newLocation the location to be checked
     * @return true if the location is valid
     */
    public boolean getValidLocation(Tile newLocation) {
        return newLocation != null;
    }

    /**
     * @return the string description of the board
     */
    @Override
    public String toString() {
        return "Board [properties Count=" + tiles.length + "]";
    }

    /**
     * Literally moves person to Jail
     *
     * @return returns jail as the new property location
     */
    public Tile moveToJail() {
        newLocation = tiles[7];
        return newLocation;
    }

    /**
     * Initializes and adds the jail property in the Array
     */
    public Tile getJailProperty() {
        for(Tile t : tiles){
            if(t.getTileName().equals("JAIL")){
                return t;
            }
        }
        return null;
    }
}
