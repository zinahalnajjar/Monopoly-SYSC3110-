public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command);

    void handleMonopolyBuy(boolean success);
}
