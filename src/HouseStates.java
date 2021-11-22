enum HouseState{
    UNOWNED(1, 0),
    RENT(1, 0),
    H1(1000, 1),
    H2(2000, 2),
    H3(3000, 3),
    H4(4000, 4),
    HOTEL(5000, 5);

    private final int rentMultiplier;
    private final int houseNum;

    HouseState(int rentMultiplier, int house) {
        this.rentMultiplier = rentMultiplier;
        this.houseNum = house;
    }
    public int getRentMultiplier(){
        return rentMultiplier;
    }

    public HouseState next(){
        return values()[ordinal() + 1];
    }

    public int getHouseNum() {
        return houseNum;
    }

}
