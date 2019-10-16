package org.drink.dispenser.automate.unit;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.money.CurrencyCoins;

import static org.drink.dispenser.Utilities.sumValues;

public class CashUnit extends Unit<CurrencyCoins> {

    public CashUnit(final Map<CurrencyCoins, Integer> inventory) {
        super(inventory);
    }

    public Option<Map<CurrencyCoins, Integer>> exchange(final int price, final CurrencyCoins... insertCoins) {
        final int insertValue = sumValues(insertCoins);

        Map<CurrencyCoins, Integer> exchange = HashMap.empty();
        int restExchangeValue = insertValue - price;
        for (final CurrencyCoins coin : availableCoins()) {
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
            return Option.some(exchange);
        }
    }

    private int adjustExchangeableSortOfCoin(final int needed, final CurrencyCoins coin) {
        final int available = getInventory().get(coin).getOrElse(0);
        return Math.min(needed, available);
    }

    List<CurrencyCoins> availableCoins() {
        return getInventory().filter(entry -> entry._2() != 0).keySet().toList().sorted().reverse();
    }
}
