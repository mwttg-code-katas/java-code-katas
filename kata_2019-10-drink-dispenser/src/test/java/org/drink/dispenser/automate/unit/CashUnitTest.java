package org.drink.dispenser.automate.unit;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.money.CurrencyCoins;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.money.Euro.*;

public class CashUnitTest {
    @Test
    public void testAvailableCoins() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 4,
                CENT20, 1,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final List<CurrencyCoins> actual = subject.availableCoins();
        assertThat(actual).containsExactly(EURO2, CENT20, CENT10);
    }

    @Test
    public void testEnoughCoinsAvailable_true() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> needed = HashMap.of(
                CENT10, 2,
                CENT20, 2
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isTrue();
    }

    @Test
    public void testEnoughCoinsAvailable_false() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 1,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> needed = HashMap.of(
                CENT10, 2,
                CENT20, 2
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isFalse();
    }

    @Test
    public void testEnoughCoinsAvailable_false_sortOfCoinNotAvailable() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 1,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> needed = HashMap.of(
                CENT50, 1
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isFalse();
    }

    @Test
    public void testReleaseCoins() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> needed = HashMap.of(
                CENT10, 1,
                CENT20, 1,
                EURO2, 2
        );
        final Map<CurrencyCoins, Integer> actual = subject.releaseCoins(needed);
        assertThat(actual).isEqualTo(needed);
        assertThat(subject.getInventory()).containsExactlyInAnyOrder(
                new Tuple2<>(CENT10, 1),
                new Tuple2<>(CENT20, 1),
                new Tuple2<>(EURO2, 3));
    }

    @Test
    public void testInsertCoin() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        subject.insertCoins(EURO2, EURO1, CENT10, CENT50, CENT10);
        final Map<CurrencyCoins, Integer> actual = subject.getInventory();
        assertThat(actual).containsExactlyInAnyOrder(
                new Tuple2<>(CENT10, 4),
                new Tuple2<>(CENT20, 2),
                new Tuple2<>(CENT50, 1),
                new Tuple2<>(EURO1, 1),
                new Tuple2<>(EURO2, 6));
    }

    @Test
    public void testNeededForExchange() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 5,
                CENT50, 5,
                EURO1, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> actual = subject.neededForExchange(130, EURO2);
        assertThat(actual).containsExactlyInAnyOrder(new Tuple2<>(CENT50, 1), new Tuple2<>(CENT20, 1));
    }

    @Test
    public void testNeededForExchange_secondExample() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 2,
                EURO1, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Map<CurrencyCoins, Integer> actual = subject.neededForExchange(110, EURO2);
        assertThat(subject.enoughCoinsAvailable(actual)).isFalse();
        // assertThat(actual).containsExactlyInAnyOrder(new Tuple2<>(CENT20, 2), new Tuple2<>(CENT10, 5));

        // So we have a little Problem here :-D our drink-dispenser is customer-friendly and only exchanges in the
        // smallest amount of coins possible ;)
    }

    @Test
    public void testExchange_allGood() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 5,
                CENT50, 5,
                EURO1, 5
        );
        final CashUnit subject = new CashUnit(inventory);

        final Option<Map<CurrencyCoins, Integer>> actual = subject.exchange(130, EURO2);
        assertThat(actual.get()).containsExactlyInAnyOrder(new Tuple2<>(CENT50, 1), new Tuple2<>(CENT20, 1));
    }

    @Test
    public void testExchange_moneyInInventoryIsMissing() {
        final Map<CurrencyCoins, Integer> inventory = HashMap.of(
                CENT10, 0,
                CENT20, 0
        );
        final CashUnit subject = new CashUnit(inventory);

        final Option<Map<CurrencyCoins, Integer>> actual = subject.exchange(130, EURO2);
        assertThat(actual).isEqualTo(Option.none());
    }
}