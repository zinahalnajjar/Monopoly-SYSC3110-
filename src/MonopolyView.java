/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyUtilityRailRoadBuy(boolean success, Property location);

    void handleMonopolyStatusUpdate(String command, String info);

    void handleMonopolyBuy(String success, Property location);

    void handleMonopolySell(boolean success, Property location);

    void handleMonopolyRentResult(String result, Property location);

    void handleMonopolyRentUtility(String result, Property location);

    void handleMonopolyJailFeePaymentResult(boolean paymentSuccess);

    void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee);

    void handleMonopolyGOResult();
}
