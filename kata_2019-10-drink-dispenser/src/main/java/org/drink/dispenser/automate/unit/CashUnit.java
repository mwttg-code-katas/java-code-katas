package org.drink.dispenser.automate.unit;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.money.Currency;

import static org.drink.dispenser.Utilities.sumValues;
import static org.drink.dispenser.Utilities.toMap;

public class CashUnit<T extends Currency> extends Unit<T> {

    public CashUnit(final Map<T, Integer> inventory) {
        super(inventory);
    }

    @SafeVarargs
    public final Option<Map<T, Integer>> exchange(final int price, final T... insertCoins) {
        final int insertValue = sumValues(insertCoins);

        Map<T, Integer> exchange = HashMap.empty();
        int restExchangeValue = insertValue - price;
        for (final T coin : availableCoins()) {
            final int forExchangeNeededCoins = restExchangeValue / coin.value();
            if (forExchangeNeededCoins == 0) {
                continue;
            }

            final int exchangeAmountOfCoin = adjustExchangeableSortOfCoin(forExchangeNeededCoins, coin);
            restExchangeValue = restExchangeValue - (coin.value() * exchangeAmountOfCoin);
            exchange = exchange.put(coin, exchangeAmountOfCoin);
        }

        if (restExchangeValue != 0) {
            return Option.none();
        } else {
            putCoinsToInventory(insertCoins);
            releaseCoinsFromInventory(exchange);
            return Option.some(exchange);
        }
    }

    @SafeVarargs
    private void putCoinsToInventory(final T... insertCoins) {
        final Map<T, Integer> coins = toMap(insertCoins);
        coins.forEach(entry -> {
            final int currentAmount = getInventory().getOrElse(entry._1(), 0);
            setInventory(getInventory().put(entry._1(), currentAmount + entry._2()));
        });
    }

    private void releaseCoinsFromInventory(final Map<T, Integer> exchange) {
        exchange.forEach(entry -> {
            final int currentAmount = getInventory().getOrElse(entry._1(), 0);
            setInventory(getInventory().put(entry._1(), currentAmount - entry._2()));
        });

    }

    private int adjustExchangeableSortOfCoin(final int needed, final T coin) {
        final int available = getInventory().get(coin).getOrElse(0);
        return Math.min(needed, available);
    }

    List<T> availableCoins() {
        return getInventory().filter(entry -> entry._2() != 0).keySet().toList().sorted().reverse();
    }
}
