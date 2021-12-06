/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command, String info);

    void handleMonopolyBuy(String info, PropertyTile newLocation);

    void handleMonopolySell(boolean success, Tile newLocation);

    void handleMonopolyJailFeePaymentResult(String info);
}
