package org.drink.dispenser.automate.unit;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Beverage;
import org.drink.dispenser.commodity.Commodity;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.commodity.Beverage.DUFF_BEER;
import static org.drink.dispenser.commodity.Beverage.NUKA_COLA;
import static org.drink.dispenser.commodity.Beverage.WATER;

public class CommodityUnitTest {

    @Test
    public void testReleaseDrink_drinkExists() {
        final Map<Beverage, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 10
        );
        final CommodityUnit<Beverage> subject = new CommodityUnit<>(inventory);

        final Option<Beverage> actual = subject.releaseCommodity(NUKA_COLA);
        assertThat(actual).isEqualTo(Option.some(NUKA_COLA));

        final Map<Beverage, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 99), new Tuple2<>(WATER, 10));
    }

    @Test
    public void testReleaseDrink_drinkIsEmpty() {
        final Map<Beverage, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 0
        );
        final CommodityUnit<Beverage> subject = new CommodityUnit<>(inventory);

        final Option<Beverage> actual = subject.releaseCommodity(WATER);
        assertThat(actual).isEqualTo(Option.none());

        final Map<Beverage, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 100), new Tuple2<>(WATER, 0));
    }

    @Test
    public void testReleaseDrink_drinkDoesNotExist() {
        final Map<Beverage, Integer> inventory = HashMap.of(
                NUKA_COLA, 100,
                WATER, 80
        );
        final CommodityUnit<Beverage> subject = new CommodityUnit<>(inventory);

        final Option<Beverage> actual = subject.releaseCommodity(DUFF_BEER);
        assertThat(actual).isEqualTo(Option.none());

        final Map<Beverage, Integer> current = subject.getInventory();
        assertThat(current).containsExactlyInAnyOrder(new Tuple2<>(NUKA_COLA, 100), new Tuple2<>(WATER, 80));
    }
}