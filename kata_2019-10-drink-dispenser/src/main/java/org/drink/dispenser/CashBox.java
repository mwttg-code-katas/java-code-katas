package org.drink.dispenser;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

public class CashBox {
  private Map<Currency, Integer> inventory;
  
  public CashBox(final Map<Currency, Integer> inventory) {
    this.inventory = inventory;
  }
  
  public Map<Currency, Integer> exchange(final int price, final Currency... coins) {
    increaseInventory(coins);
    final int inputValue = List.of(coins).map(Currency::value).sum().intValue();
    final int exchangeValue = inputValue - price;
    return getExchangeCoins(exchangeValue);
  }

  public Map<Currency, Integer> getInventory() {
    return inventory;
  }

  // TODO: What if not enough coins for exchange are inside the cashbox?
  private Map<Currency, Integer> getExchangeCoins(final int exchangeValue) {
    Map<Currency, Integer> result = HashMap.empty();
    final List<Currency> availableCoins = inventory.keySet().toList().sorted().reverse();

    int newExchangeValue = exchangeValue;
    for(final Currency coin : availableCoins) {
      final int coinValue = coin.value();
      final int quotient = newExchangeValue / coinValue;

      if (quotient == 0) {
        continue;
      }

      newExchangeValue = newExchangeValue - (coinValue * quotient);
      result = result.put(coin, quotient);

      decreaseInventory(coin, quotient);
    }
    return result;
  }

  private void decreaseInventory(final Currency coin, final int quotient) {
    final int coinAmount = inventory.get(coin).get();
    final int newCoinAmount = coinAmount - quotient;
    inventory = inventory.put(coin, newCoinAmount);
  }

  private void increaseInventory(final Currency... coins) {
    for (final Currency coin : coins) {
      final int currentAmount = inventory.get(coin).get();
      final int newAmount = currentAmount + 1;
      inventory = inventory.put(coin, newAmount);
    }
  }
}
