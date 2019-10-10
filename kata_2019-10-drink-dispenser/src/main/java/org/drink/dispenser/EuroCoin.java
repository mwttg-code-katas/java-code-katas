package org.drink.dispenser;

public enum EuroCoin implements Currency {
  CENT10(10),
  
  CENT20(20),
  
  CENT50(50),
  
  EURO1(100),
  
  EURO2(200);
  
  private final int value;
  
  EuroCoin(final int value) {
    this.value = value;
  }
  
  @Override
  public int value() {
    return value;
  }
}
