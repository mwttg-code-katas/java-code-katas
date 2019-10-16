package org.drink.dispenser.money;

import com.google.common.collect.ComparisonChain;

public enum Euro implements CurrencyCoins {
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

  @Override
  public int compare(final Integer left, final Integer right) {
    return ComparisonChain
            .start()
            .compare(left, right)
            .result();
  }
}
