package org.drink.dispenser.automate.unit;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.commodity.Beverage.DUFF_BEER;
import static org.drink.dispenser.commodity.Beverage.NUKA_COLA;
import static org.drink.dispenser.commodity.Beverage.WATER;

public class BeverageUnitTest {

    @Test
    public void testReleaseDrink_drinkExists() {
        final Map<Commodity, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 10
        );
        final BeverageUnit subject = new BeverageUnit(inventory);

        final Option<Commodity> actual = subject.releaseDrink(NUKA_COLA);
        assertThat(actual).isEqualTo(Option.some(NUKA_COLA));

        final Map<Commodity, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 99), new Tuple2<>(WATER, 10));
    }

    @Test
    public void testReleaseDrink_drinkIsEmpty() {
        final Map<Commodity, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 0
        );
        final BeverageUnit subject = new BeverageUnit(inventory);

        final Option<Commodity> actual = subject.releaseDrink(WATER);
        assertThat(actual).isEqualTo(Option.none());

        final Map<Commodity, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 100), new Tuple2<>(WATER, 0));
    }

    @Test
    public void testReleaseDrink_drinkDoesNotExist() {
        final Map<Commodity, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 80
        );
        final BeverageUnit subject = new BeverageUnit(inventory);

        final Option<Commodity> actual = subject.releaseDrink(DUFF_BEER);
        assertThat(actual).isEqualTo(Option.none());

        final Map<Commodity, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 100), new Tuple2<>(WATER, 80));
    }
}