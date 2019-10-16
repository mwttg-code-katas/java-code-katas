package org.drink.dispenser.automate.unit;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

public class BeverageUnit extends Unit<Commodity> {

    public BeverageUnit(final Map<Commodity, Integer> inventory) {
        super(inventory);
    }

    public Option<Commodity> releaseDrink(final Commodity drink) {
        final Option<Integer> maybe = getInventory().get(drink);
        return Match(maybe).of(
                Case($Some($()), amount -> {
                    if (amount > 0) {
                        setInventory(getInventory().put(drink, amount - 1));
                        return Option.some(drink);
                    } else {
                        return Option.none();
                    }
                }),
                Case($None(), Option.none())
        );
    }
}