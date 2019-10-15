package org.drink.dispenser.money;

import java.util.Comparator;

public interface Currency extends Comparator<Integer> {
  int value();
}
