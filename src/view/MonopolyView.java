/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command, String info);

    void handleMonopolyBuy(String info, PropertyTile newLocation);

    void handleMonopolySell(boolean success, Tile newLocation);

    void handleMonopolyRoll(String info);

    void handleMonopolyJailFeePaymentResult(String info);
}
