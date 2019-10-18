package org.drink.dispenser.automate.unit;

import io.vavr.collection.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class Unit<T> {
  private Map<T, Integer> inventory;

  Unit(final Map<T, Integer> inventory) {
    this.inventory = checkNotNull(inventory);
  }

  public Map<T, Integer> getInventory() {
    return inventory;
  }

  void setInventory(final Map<T, Integer> newInventory) {
    this.inventory = checkNotNull(newInventory);
  }
}
