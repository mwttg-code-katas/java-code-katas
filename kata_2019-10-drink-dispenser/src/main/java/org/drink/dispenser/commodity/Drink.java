package org.drink.dispenser.commodity;

public enum Drink implements Commodity {
    WATER(80),

    NUKA_COLA(120),

    NUKA_COLA_QUANTUM(340),

    DUFF_BEER(230),

    BOOTY_SWEAT(250);

    private final int price;

    Drink(final int price) {
        this.price = price;
    }

    @Override
    public int price() {
        return price;
    }
}
