package org.drink.dispenser.part;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.drink.dispenser.money.Currency;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

public class CashBox {
    private Map<Currency, Integer> inventory;

    public CashBox(final Map<Currency, Integer> inventory) {
        checkNotNull(inventory);
        this.inventory = inventory;
    }

    public Map<Currency, Integer> getInventory() {
        return inventory;
    }

    public Tuple2<Boolean, Map<Currency, Integer>> exchange(final int price, final Currency... insertCoins) {
        final Map<Currency, Integer> neededExchange = neededForExchange(price, insertCoins);
        if (enoughCoinsAvailable(neededExchange)) {
            insertCoins(insertCoins);
            return new Tuple2<>(true, releaseCoins(neededExchange));
        } else {
            final Map<Currency, Integer> insertAsMap = toList(insertCoins).map(item -> new Tuple2<>(item, 1)).collect(HashMap.collector());
            return new Tuple2<>(false, insertAsMap);
        }
    }

    // TODO fix 'feature' ;)
    Map<Currency, Integer> neededForExchange(final int price, final Currency... insert) {
        final int inputValue = List.of(insert).map(Currency::value).sum().intValue();
        final int exchangeValue = inputValue - price;

        Map<Currency, Integer> neededForExchange = HashMap.empty();
        int needed = exchangeValue;
        // well, not perfect - drink-dispenser only exchanges in smallest amount of coins possible ;)
        for (final Currency coin : availableCoins()) {
            final int quotient = needed / coin.value();

            if (quotient == 0) {
                continue;
            }

            needed = needed - (coin.value() * quotient);
            neededForExchange = neededForExchange.put(coin, quotient);
        }

        return neededForExchange;
    }

    void insertCoins(final Currency... coins) {
        toList(coins).forEach(coin -> {
            final int currentAmount = inventory.getOrElse(coin, 0);
            inventory = inventory.put(coin, currentAmount + 1);
        });
    }

    Map<Currency, Integer> releaseCoins(final Map<Currency, Integer> needed) {
        needed.forEach(entry -> {
            final Currency key = entry._1;
            final int amount = entry._2;
            final int currentAmount = inventory.get(key).get(); // checked before -> #enoughCoinsAvailable
            inventory = inventory.put(key, currentAmount - amount);
        });
        return needed;
    }

    boolean enoughCoinsAvailable(final Map<Currency, Integer> neededForExchange) {
        return neededForExchange.map(tuple -> {
            final Currency coin = tuple._1;
            final int neededAmount = tuple._2;
            final int availableAmount = inventory.getOrElse(coin, 0);
            return neededAmount <= availableAmount;
        }).foldLeft(true, (accumulator, current) -> accumulator && current);
    }

    List<Currency> availableCoins() {
        return inventory.keySet().toList().sorted().reverse();
    }

    private List<Currency> toList(final Currency... coins) {
        return List.ofAll(Arrays.asList(coins));
    }
}
