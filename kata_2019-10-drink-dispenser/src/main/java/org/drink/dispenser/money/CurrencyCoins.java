package org.drink.dispenser.money;

import java.util.Comparator;

public interface CurrencyCoins extends Comparator<Integer> {
  int value();
}
