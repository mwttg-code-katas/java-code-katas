package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Beverage;
import org.drink.dispenser.money.Euro;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.commodity.Beverage.DUFF_BEER;
import static org.drink.dispenser.commodity.Beverage.NUKA_COLA;
import static org.drink.dispenser.commodity.Beverage.WATER;
import static org.drink.dispenser.money.Euro.CENT10;
import static org.drink.dispenser.money.Euro.CENT20;
import static org.drink.dispenser.money.Euro.CENT50;
import static org.drink.dispenser.money.Euro.EURO1;
import static org.drink.dispenser.money.Euro.EURO2;

public class VendorMachineTest {

  @Test
  public void testBuy_allGood() {
    final Map<Euro, Integer> cash = HashMap.of(
        EURO2, 100,
        EURO1, 100,
        CENT50, 100,
        CENT20, 100,
        CENT10, 100);

    final Map<Beverage, Integer> commodity = HashMap.of(
        WATER, 50,
        NUKA_COLA, 50,
        DUFF_BEER, 50);
    final VendorMachine<Euro, Beverage> subject = new VendorMachine<>(cash, commodity);

    final Tuple2<Option<Beverage>, Map<Euro, Integer>> actual = subject.buy(NUKA_COLA, EURO2);
    assertThat(actual._1()).isEqualTo(Option.some(NUKA_COLA));
    assertThat(actual._2()).containsExactlyInAnyOrder(
        new Tuple2<>(CENT50, 1),
        new Tuple2<>(CENT20, 1),
        new Tuple2<>(CENT10, 1)
    );
  }

  @Test
  public void testBuy_notEnoughMoneyPayed() {
    final Map<Euro, Integer> cash = HashMap.of(
        EURO2, 100,
        EURO1, 100,
        CENT50, 100,
        CENT20, 100,
        CENT10, 100);

    final Map<Beverage, Integer> commodity = HashMap.of(
        WATER, 50,
        NUKA_COLA, 50,
        DUFF_BEER, 50);
    final VendorMachine<Euro, Beverage> subject = new VendorMachine<>(cash, commodity);

    final Tuple2<Option<Beverage>, Map<Euro, Integer>> actual = subject.buy(NUKA_COLA, EURO1);
    assertThat(actual._1()).isEqualTo(Option.none());
    assertThat(actual._2()).containsExactlyInAnyOrder(
        new Tuple2<>(EURO1, 1)
    );
  }

  @Test
  public void testBuy_noutEnoughMoneyForExchange() {
    final Map<Euro, Integer> cash = HashMap.of(
        EURO2, 1,
        EURO1, 1,
        CENT50, 1,
        CENT20, 1,
        CENT10, 0);

    final Map<Beverage, Integer> commodity = HashMap.of(
        WATER, 50,
        NUKA_COLA, 50,
        DUFF_BEER, 50);
    final VendorMachine<Euro, Beverage> subject = new VendorMachine<>(cash, commodity);

    final Tuple2<Option<Beverage>, Map<Euro, Integer>> actual = subject.buy(NUKA_COLA, EURO2);
    assertThat(actual._1()).isEqualTo(Option.none());
    assertThat(actual._2()).containsExactlyInAnyOrder(
        new Tuple2<>(EURO2, 1)
    );
  }

  @Test
  public void testBuy_drinkEmpty() {
    final Map<Euro, Integer> cash = HashMap.of(
        EURO2, 100,
        EURO1, 100,
        CENT50, 100,
        CENT20, 100,
        CENT10, 100);

    final Map<Beverage, Integer> commodity = HashMap.of(
        WATER, 50,
        NUKA_COLA, 0,
        DUFF_BEER, 50);
    final VendorMachine<Euro, Beverage> subject = new VendorMachine<>(cash, commodity);

    final Tuple2<Option<Beverage>, Map<Euro, Integer>> actual = subject.buy(NUKA_COLA, EURO2);
    assertThat(actual._1()).isEqualTo(Option.none());
    assertThat(actual._2()).containsExactlyInAnyOrder(
        new Tuple2<>(EURO2, 1)
    );
  }
}