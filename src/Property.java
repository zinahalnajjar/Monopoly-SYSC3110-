import java.awt.*;

public interface Property {
    String getPropertyName();

    Color getColor();

    HouseState getState();

    Player getOwner();

    int getCost();

    void setOwner(Player currentPlayer);

    void incrementState();

    int getCostPerHouse();

    int getRent();
}

