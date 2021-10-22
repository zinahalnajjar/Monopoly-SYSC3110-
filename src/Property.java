public class Property {

    private String propertyName;
    private String color;
    private Player owner;
    private int rent;
    private int cost;

    public Property(String propertyName, String color, int rent, int cost) {
        this.propertyName = propertyName;
        this.color = color;
        this.rent = rent;
        this.cost = cost;
        this.owner = null;
    }


}
