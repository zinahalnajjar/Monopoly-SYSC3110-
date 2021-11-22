/**
 * Interface
 *
 * @author Tooba
 */
public interface MonopolyView {

    void handleMonopolyStatusUpdate(String command, String info);

    void handleMonopolyBuy(String success, Property location);

    void handleMonopolySell(boolean success, Property location);

}
