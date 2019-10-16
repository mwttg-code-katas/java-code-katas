package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.drink.dispenser.money.CurrencyCoins;

import java.util.Arrays;

public class Utilities {
    public static List<CurrencyCoins> toList(final CurrencyCoins... input) {
        return List.ofAll(Arrays.asList(input));
    }

    public static Map<CurrencyCoins, Integer> toMap(final CurrencyCoins... input) {
        return toList(input).map(item -> new Tuple2<>(item, 1)).collect(HashMap.collector());
    }

    public static int sumValues(final CurrencyCoins... input ) {
        return List.of(input).map(CurrencyCoins::value).sum().intValue();
    }
}
