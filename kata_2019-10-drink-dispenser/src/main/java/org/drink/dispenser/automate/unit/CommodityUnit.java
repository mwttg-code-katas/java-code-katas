package org.drink.dispenser.automate.unit;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

public class CommodityUnit<T extends Commodity> extends Unit<T> {

    public CommodityUnit(final Map<T, Integer> inventory) {
        super(inventory);
    }

    public Option<T> releaseCommodity(final T commodity) {
        final Option<Integer> maybe = getInventory().get(commodity);
        return Match(maybe).of(
                Case($Some($()), amount -> {
                    if (amount > 0) {
                        setInventory(getInventory().put(commodity, amount - 1));
                        return Option.some(commodity);
                    } else {
                        return Option.none();
                    }
                }),
                Case($None(), Option.none())
        );
    }
}