package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.drink.dispenser.money.Currency;

import java.util.Arrays;

public class Utilities {
    public static List<Currency> toList(final Currency... input) {
        return List.ofAll(Arrays.asList(input));
    }

    public static Map<Currency, Integer> toMap(final Currency... input) {
        return toList(input).map(item -> new Tuple2<>(item, 1)).collect(HashMap.collector());
    }
}
