import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;

/**
 * Parses XML files into tiles
 *
 * @author Tooba Sheikh
 */
public class XmlFileHandler extends DefaultHandler {


    //tiles properties
    private String type;
    private String name;
    private int square;
    private int rent;
    private int cost;
    private int houseCost;
    private Color color;
    private String description;

    //tye other fees and currency in the game
    private int go;
    private int jail;
    private int initMoney;

    private String currentElement = "";

    private Tile[] tiles = new Tile[32];

    /**
     * @return fully initialized tile class
     */
    public Tile[] getBoardTiles()
    {
        return tiles;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if (qName.equals("tile")) {
            type = attributes.getValue("type");
        }else if(qName.equals("")){

        }else{
            currentElement = qName;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if(qName.equals("tile")){
            if(type.equals("property")){
                tiles[square] = new PropertyTile(name, TileType.PROPERTY, color, rent,cost, houseCost);
            } else if (type.equals("utility")){
                tiles[square] = new PropertyTile(name, TileType.UTILITY, Color.white, rent, cost, 0);
            } else if (type.equals("railroad")){
                tiles[square] = new PropertyTile(name, TileType.RAILROAD, Color.LIGHT_GRAY, rent, cost, 0);
            } else if (type.equals("cornertile")){
                tiles[square] = new CornerTile(name, description);
                if(square == 7){
                    ((CornerTile)tiles[square]).setJail(true);
                }
                if(square == 25){
                    ((CornerTile)tiles[square]).setIsGoToJail(true);
                }
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {

        String s = new String (ch, start, length);

        if(currentElement.equals("square")){
            square = Integer.parseInt(s);
        }
        if(currentElement.equals("name")){
            name = s;
        }
        if(currentElement.equals("color")){
            color = Color.decode(s);
        }
        if(currentElement.equals("rent")){
            rent = Integer.parseInt(s);
        }
        if(currentElement.equals("cost")){
            cost = Integer.parseInt(s);
        }
        if(currentElement.equals("housecost")){
            houseCost = Integer.parseInt(s);
        }
        if(currentElement.equals("gofee")){
            go = Integer.parseInt(s);
        }
        if(currentElement.equals("jailfee")){
            jail = Integer.parseInt(s);
        }
        if(currentElement.equals("startmoney")){
            initMoney = Integer.parseInt(s);
        }
        if(currentElement.equals("description")){
            description = s;
        }

        currentElement = "";
    }

    /**
     * @return the go amount
     */
    public int getGo() {
        return go;
    }

    /**
     * @return the jail amount
     */
    public int getJail() {
        return jail;
    }

    /**
     * @return the inital fee
     */
    public int getInit() {
        return initMoney;
    }
}