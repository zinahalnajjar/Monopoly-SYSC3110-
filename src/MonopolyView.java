/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command);

    void handleMonopolyBuy(boolean success, Property location);

    void handleMonopolySell(boolean success, Property location);

    void handleMonopolyRentResult(String result, Property location);

    void handleMonopolyJailFeePaymentResult(boolean paymentSuccess);

    void handleMonopolyJailPlayerRollResult(String result);

    void handleMonopolyGOResult();
}
