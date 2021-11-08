public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command);

    void handleMonopolyBuy(boolean success, Property location);

    void handleMonopolySell(boolean success, Property location);

}