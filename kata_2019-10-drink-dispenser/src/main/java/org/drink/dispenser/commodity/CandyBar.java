package org.drink.dispenser.commodity;

public enum CandyBar implements Commodity {
  KITKAT(120),

  LION(130),

  MARS(230),

  MILKYWAY(220);

  private final int price;

  CandyBar(final int price) {
    this.price = price;
  }

  @Override
  public int price() {
    return price;
  }
}
