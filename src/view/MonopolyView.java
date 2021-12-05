/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command, String info);

    void handleMonopolySell(boolean success, Tile location);

    void handleMonopolyJailFeePaymentResult(boolean paymentSuccess);

    void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee);
}
