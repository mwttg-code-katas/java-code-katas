package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.drink.dispenser.money.Currency;

import java.util.Arrays;

public class Utilities {
    @SafeVarargs
    public static <T extends Currency> List<T> toList(final T... input) {
        return List.ofAll(Arrays.asList(input));
    }

    @SafeVarargs
    public static <T extends Currency> Map<T, Integer> toMap(final T... input) {
        final List<Tuple2<T, Integer>> tuples = toList(input).map(item -> new Tuple2<>(item, 1));
        return tuples.foldLeft(HashMap.empty(), (Map<T, Integer> accumulator, Tuple2<T, Integer> tuple) -> {
            final int current = accumulator.getOrElse(tuple._1(), 0);
            return accumulator.put(tuple._1(), current + 1);
        });
    }

    public static int sumValues(final Currency... input ) {
        return List.of(input).map(Currency::value).sum().intValue();
    }
}
