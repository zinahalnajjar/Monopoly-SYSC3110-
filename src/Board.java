import java.util.ArrayList;
import java.util.List;

public class Board {

    private ArrayList<Property> properties;
    private Property newLocation;

    public Board() {
        this.properties = new ArrayList<>();
        this.newLocation = null;
        createProperties();
    }

    private void createProperties(){
        Property space1 = new Property("Mediterranean Avenue", "Brown", 2, 60);
        Property space2 = new Property("Baltic Avenue", "Brown", 4, 60);
        Property space3 = new Property("Oriental Avenue", "Light Blue", 6, 100);
        Property space4 = new Property("Vermont Avenue", "Light Blue", 6, 100);
        Property space5 = new Property("Connecticut Avenue", "Light Blue", 8, 120);
        Property space6 = new Property("St. Charles Place", "Pink", 10, 140);
        Property space7 = new Property("States Avenue", "Pink", 10, 140);
        Property space8 = new Property("Virginia Avenue", "Pink", 12, 160);
        Property space9 = new Property("St. James Place", "Orange", 14, 180);
        Property space10 = new Property("Tennessee Avenue", "Orange", 14, 180);
        Property space11 = new Property("New York Avenue", "Orange", 16, 200);
        Property space12 = new Property("Kentucky Avenue", "Red", 18, 220);
        Property space13  = new Property("Indiana Avenue", "Red", 18, 220);
        Property space14 = new Property("Illinois Avenue", "Red", 20, 240);
        Property space15 = new Property("Atlantic Avenue", "Yellow", 22, 260);
        Property space16 = new Property("Ventnor Avenue", "Yellow", 22, 260);
        Property space17 = new Property("Marvin Gardens", "Yellow", 24, 280);
        Property space18 = new Property("Pacific Avenue", "Green", 26, 300);
        Property space19 = new Property("North Carolina Avenue", "Green", 26, 300);
        Property space20 = new Property("Pennsylvania Avenue", "Green", 28, 320);
        Property space21 = new Property("Park Place", "Dark Blue", 35, 350);
        Property space22 = new Property("Boardwalk", "Dark Blue", 50, 400);

        properties.add(space1);
        properties.add(space2);
        properties.add(space3);
        properties.add(space4);
        properties.add(space5);
        properties.add(space6);
        properties.add(space7);
        properties.add(space8);
        properties.add(space9);
        properties.add(space10);
        properties.add(space11);
        properties.add(space12);
        properties.add(space13);
        properties.add(space14);
        properties.add(space15);
        properties.add(space16);
        properties.add(space17);
        properties.add(space18);
        properties.add(space19);
        properties.add(space20);
        properties.add(space21);
        properties.add(space22);
    }

    public Property move(int spaces, Property location){
        int i = properties.indexOf(location);
        i = i + spaces;
        if(i > properties.size()){
            i = i - properties.size();
        }
        newLocation = properties.get(i);
        return newLocation;
    }

    public ArrayList<Property> propertiesList(){
        return properties;
    }
}
