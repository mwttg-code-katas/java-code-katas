package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;
import org.drink.dispenser.commodity.Drink;
import org.drink.dispenser.money.Currency;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.commodity.Drink.DUFF_BEER;
import static org.drink.dispenser.commodity.Drink.NUKA_COLA;
import static org.drink.dispenser.commodity.Drink.WATER;
import static org.drink.dispenser.money.EuroCoin.CENT10;
import static org.drink.dispenser.money.EuroCoin.CENT20;
import static org.drink.dispenser.money.EuroCoin.CENT50;
import static org.drink.dispenser.money.EuroCoin.EURO1;
import static org.drink.dispenser.money.EuroCoin.EURO2;

public class DrinkDispenserTest {

    @Test
    public void testBuy() {
        final Map<Currency, Integer> cash = HashMap.of(
                EURO2, 100,
                EURO1, 100,
                CENT50, 100,
                CENT20, 100,
                CENT10, 100);

        final Map<Commodity, Integer> commodity = HashMap.of(
                WATER, 50,
                NUKA_COLA, 50,
                DUFF_BEER, 50);
        final DrinkDispenser subject = new DrinkDispenser(cash, commodity);

        final Tuple2<Option<Commodity>, Map<Currency, Integer>> actual = subject.buy(NUKA_COLA, EURO2);
        assertThat(actual._1()).isEqualTo(Option.some(NUKA_COLA));
        assertThat(actual._2()).containsExactlyInAnyOrder(
                new Tuple2<>(CENT50, 1),
                new Tuple2<>(CENT20, 1),
                new Tuple2<>(CENT10, 1)
        );
    }
}