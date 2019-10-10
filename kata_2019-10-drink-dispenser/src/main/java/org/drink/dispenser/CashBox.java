package org.drink.dispenser;

import io.vavr.collection.List;
import io.vavr.collection.Map;

public class CashBox {
  private final Map<Currency, Integer> inventory;
  
  public CashBox(final Map<Currency, Integer> inventory) {
    this.inventory = inventory;
  }
  
  public Map<Currency, Integer> exchange(final int price, final Currency... coins) {
    final int inputValue = List.of(coins).map(Currency::value).sum().intValue();
    final int exchangeValue = inputValue - price;
    
    System.out.println(inputValue);
    System.out.println(exchangeValue );
    
    return null;
  }
}
