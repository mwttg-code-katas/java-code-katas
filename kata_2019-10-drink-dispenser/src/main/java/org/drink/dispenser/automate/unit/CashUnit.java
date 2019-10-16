package org.drink.dispenser.automate.unit;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.money.CurrencyCoins;

import static org.drink.dispenser.Utilities.toList;

public class CashUnit extends Unit<CurrencyCoins> {

    public CashUnit(final Map<CurrencyCoins, Integer> inventory) {
        super(inventory);
    }

    public Option<Map<CurrencyCoins, Integer>> exchange(final int price, final CurrencyCoins... insertCoins) {
        final Map<CurrencyCoins, Integer> neededExchange = neededForExchange(price, insertCoins);
        if (enoughCoinsAvailable(neededExchange)) {
            insertCoins(insertCoins);
            return Option.some(releaseCoins(neededExchange));
        } else {
            return Option.none();
        }
    }

    // TODO fix 'feature' ;)
    Map<CurrencyCoins, Integer> neededForExchange(final int price, final CurrencyCoins... insert) {
        final int inputValue = List.of(insert).map(CurrencyCoins::value).sum().intValue();
        final int exchangeValue = inputValue - price;

        Map<CurrencyCoins, Integer> neededForExchange = HashMap.empty();
        int needed = exchangeValue;
        // well, not perfect - drink-dispenser only exchanges in smallest amount of coins possible ;)
        for (final CurrencyCoins coin : availableCoins()) {
            final int quotient = needed / coin.value();

            if (quotient == 0) {
                continue;
            }

            needed = needed - (coin.value() * quotient);
            neededForExchange = neededForExchange.put(coin, quotient);
        }

        return neededForExchange;
    }

    void insertCoins(final CurrencyCoins... coins) {
        toList(coins).forEach(coin -> {
            final int currentAmount = getInventory().getOrElse(coin, 0);
            setInventory(getInventory().put(coin, currentAmount + 1));
        });
    }

    Map<CurrencyCoins, Integer> releaseCoins(final Map<CurrencyCoins, Integer> needed) {
        needed.forEach(entry -> {
            final CurrencyCoins key = entry._1;
            final int amount = entry._2;
            final int currentAmount = getInventory().get(key).get(); // checked before -> #enoughCoinsAvailable
            setInventory(getInventory().put(key, currentAmount - amount));
        });
        return needed;
    }

    boolean enoughCoinsAvailable(final Map<CurrencyCoins, Integer> neededForExchange) {
        return neededForExchange.map(tuple -> {
            final CurrencyCoins coin = tuple._1;
            final int neededAmount = tuple._2;
            final int availableAmount = getInventory().getOrElse(coin, 0);
            return neededAmount <= availableAmount;
        }).foldLeft(true, (accumulator, current) -> accumulator && current);
    }

    List<CurrencyCoins> availableCoins() {
        return getInventory().keySet().toList().sorted().reverse();
    }
}
