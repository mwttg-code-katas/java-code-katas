package org.drink.dispenser;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.testng.annotations.Test;

public class CashBoxTest {

  @Test
  public void testExchange() {
    final Map<Currency, Integer> inventory = HashMap.of(
        EuroCoin.CENT10, 100, 
        EuroCoin.CENT20, 100, 
        EuroCoin.CENT50, 100, 
        EuroCoin.EURO1, 100, 
        EuroCoin.EURO2, 100
    );
    
    final CashBox subject = new CashBox(inventory);
    
    subject.exchange(120, EuroCoin.EURO1, EuroCoin.CENT50);
    
  }
}