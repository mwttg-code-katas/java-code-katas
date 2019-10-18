package org.drink.dispenser.automate.unit;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.money.Euro;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.money.Euro.CENT10;
import static org.drink.dispenser.money.Euro.CENT20;
import static org.drink.dispenser.money.Euro.CENT50;
import static org.drink.dispenser.money.Euro.EURO1;
import static org.drink.dispenser.money.Euro.EURO2;

public class CashUnitTest {
  @Test
  public void testAvailableCoins() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 4,
        CENT50, 3,
        CENT20, 1,
        EURO2, 5,
        EURO1, 2
    );

    // Seems there is compare stuff already inside the enum (ordering by value)... :/
    for (int i = 0; i < 10000; i++) {
      final CashUnit<Euro> subject = new CashUnit<>(inventory);
      final List<Euro> actual = subject.availableCoins();
      assertThat(actual).containsExactly(EURO2, EURO1, CENT50, CENT20, CENT10);
    }
  }

  @Test
  public void testAvailableCoins_filterOutZeros() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 4,
        CENT20, 0,
        CENT50, 0,
        EURO1, 0,
        EURO2, 5
    );
    final CashUnit<Euro> subject = new CashUnit<>(inventory);

    final List<Euro> actual = subject.availableCoins();
    assertThat(actual).containsExactly(EURO2, CENT10);
  }

  @Test
  public void testExchange_allGoodReturnBiggestCoinsPossible() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 10,
        CENT20, 5,
        CENT50, 5,
        EURO1, 5
    );
    final CashUnit<Euro> subject = new CashUnit<>(inventory);

    final Option<Map<Euro, Integer>> actual = subject.exchange(130, EURO2);
    assertThat(actual.get()).containsExactlyInAnyOrder(new Tuple2<>(CENT50, 1), new Tuple2<>(CENT20, 1));
  }

  @Test
  public void testExchange_moneyInInventoryIsMissing() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 0,
        CENT20, 0
    );
    final CashUnit<Euro> subject = new CashUnit<>(inventory);

    final Option<Map<Euro, Integer>> actual = subject.exchange(130, EURO2);
    assertThat(actual).isEqualTo(Option.none());
  }

  @Test
  public void testNeededForExchange_notEnoughBigCoins() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 10,
        CENT20, 1,
        EURO1, 5
    );
    final CashUnit<Euro> subject = new CashUnit<>(inventory);

    final Option<Map<Euro, Integer>> actual = subject.exchange(110, EURO2);
    assertThat(actual.get()).containsExactlyInAnyOrder(new Tuple2<>(CENT20, 1), new Tuple2<>(CENT10, 7));
  }

  @Test
  public void testNeededForExchange_10CentsAreMissingInTheInventory() {
    final Map<Euro, Integer> inventory = HashMap.of(
        CENT10, 6,
        CENT20, 1,
        EURO1, 5
    );
    final CashUnit<Euro> subject = new CashUnit<>(inventory);

    final Option<Map<Euro, Integer>> actual = subject.exchange(110, EURO2);
    assertThat(actual).isEqualTo(Option.none());
  }
}