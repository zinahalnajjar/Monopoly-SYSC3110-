/**
 * Enum that holds each house as a state and their multipliers
 * that can be found on the board
 *
 * @author Tooba
 */
enum PropertyState {
    UNOWNED(1, 0),
    RENT(1, 0),
    H1(1000, 1),
    H2(2000, 2),
    H3(3000, 3),
    H4(4000, 4),
    HOTEL(5000, 5),
    RENT1(1, 1),
    RENT2(2, 2),
    RENT3(4, 3),
    RENT4(8, 4);

    private final int rentMultiplier;
    private final int houseNum;

    /**
     *
     * Initializes the variables for this enum
     *
     * @param rentMultiplier the multiplier for the rent
     * @param house the house number
     */
    PropertyState(int rentMultiplier, int house) {
        this.rentMultiplier = rentMultiplier;
        this.houseNum = house;
    }


    /**
     * @return the rent multiplier of the current state
     */
    public int getRentMultiplier(){
        return rentMultiplier;
    }

    /**
     * @return the next state basically to increment
     */
    public PropertyState next(){
        return values()[ordinal() + 1];
    }

    /**
     * @return the number of the house
     */
    public int getHouseNum() {
        return houseNum;
    }

}
