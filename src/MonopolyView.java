public interface MonopolyView {
    void handleMonopolyBuy(boolean success);
    void handleMonopolyRoll();
    void handleMonopolyPass();
    void handleMonopolyQuit();
    void handleMonopolyHelp();
    void handleMonopolyPlayerInfo();
}
