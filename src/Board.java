import java.util.ArrayList;

public class Board {

    private ArrayList<Property> properties;

    public Board() {
        this.properties = new ArrayList<>();

        createProperties();
    }

    private void createProperties(){
        Property one = new Property("Mediterranean Avenue", "Brown", 2, 60);
        Property two = new Property("Baltic Avenue", "Brown", 4, 60);
        Property three = new Property("Oriental Avenue", "Light Blue", 6, 100);
        Property four = new Property("Vermont Avenue", "Light Blue", 6, 100);
        Property five = new Property("Connecticut Avenue", "Light Blue", 8, 120);
        Property six = new Property("St. Charles Place", "Pink", 10, 140);
        Property seven = new Property("States Avenue", "Pink", 10, 140);
        Property eight = new Property("Virginia Avenue", "Pink", 12, 160);
        Property nine = new Property("St. James Place", "Orange", 14, 180);
        Property ten = new Property("Tennessee Avenue", "Orange", 14, 180);
        Property eleven = new Property("New York Avenue", "Orange", 16, 200);
        Property twelve = new Property("Kentucky Avenue", "Red", 18, 220);
        Property thirteen  = new Property("Indiana Avenue", "Red", 18, 220);
        Property fourteen = new Property("Illinois Avenue", "Red", 20, 240);
        Property fifteen = new Property("Atlantic Avenue", "Yellow", 22, 260);
        Property sixteen = new Property("Ventnor Avenue", "Yellow", 22, 260);
        Property seventeen = new Property("Marvin Gardens", "Yellow", 24, 280);
        Property eighteen = new Property("Pacific Avenue", "Green", 26, 300);
        Property nineteen = new Property("North Carolina Avenue", "Green", 26, 300);
        Property twenty = new Property("Pennsylvania Avenue", "Green", 28, 320);
        Property twentyOne = new Property("Park Place", "Dark Blue", 35, 350);
        Property twentyTwo = new Property("Boardwalk", "Dark Blue", 50, 400);
    }
}
