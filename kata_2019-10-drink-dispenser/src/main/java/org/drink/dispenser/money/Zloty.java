package org.drink.dispenser.money;

public enum Zloty implements Currency {
  GROSZY10(10),

  GROSZY20(20),

  GROSZY50(50),

  ZLOTY1(100),

  ZLOTY2(200),

  ZLOTY5(500);

  private final int value;

  Zloty(final int value) {
    this.value = value;
  }

  @Override
  public int value() {
    return value;
  }
}
