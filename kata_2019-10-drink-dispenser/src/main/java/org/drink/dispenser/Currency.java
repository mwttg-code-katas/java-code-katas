package org.drink.dispenser;

import java.util.Comparator;

public interface Currency extends Comparator<Integer> {
  int value();
}
