package org.drink.dispenser.part;

import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

public class DrinkBox {
    private Map<Commodity, Integer> inventory;

    public DrinkBox(final Map<Commodity, Integer> inventory) {
        this.inventory = checkNotNull(inventory);
    }

    public Map<Commodity, Integer> getInventory() {
        return inventory;
    }

    public Option<Commodity> releaseDrink(final Commodity drink) {
        final Option<Integer> maybe = inventory.get(drink);
        return Match(maybe).of(
                Case($Some($()), amount -> {
                    if (amount > 0) {
                        inventory = inventory.put(drink, amount - 1);
                        return Option.some(drink);
                    } else {
                        return Option.none();
                    }
                }),
                Case($None(), Option.none())
        );
    }
}
