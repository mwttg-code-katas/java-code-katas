package org.drink.dispenser.money;

public enum Euro implements Currency {
  CENT10(10),

  CENT20(20),

  CENT50(50),

  EURO1(100),

  EURO2(200);

  private final int value;

  Euro(final int value) {
    this.value = value;
  }

  @Override
  public int value() {
    return value;
  }
}
