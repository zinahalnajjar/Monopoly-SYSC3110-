/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyUtilityRailRoadBuy(boolean success, Tile location);

    void handleMonopolyStatusUpdate(String command, String info);

    //void handleMonopolyBuy(String success, PropertyTile location);

    void handleMonopolySell(boolean success, Tile location);

    void handleMonopolyRentResult(String result, Tile location);

    void handleMonopolyRentUtility(String result, Tile location);

    void handleMonopolyJailFeePaymentResult(boolean paymentSuccess);

    void handleMonopolyJailPlayerRollResult(String result, boolean forceJailFee);

    void handleMonopolyGOResult();
}
