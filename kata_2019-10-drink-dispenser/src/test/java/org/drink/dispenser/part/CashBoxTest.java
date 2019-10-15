package org.drink.dispenser.part;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.drink.dispenser.money.Currency;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.money.EuroCoin.*;

public class CashBoxTest {
    @Test
    public void testAvailableCoins() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 4,
                CENT20, 1,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        final List<Currency> actual = subject.availableCoins();
        assertThat(actual).containsExactly(EURO2, CENT20, CENT10);
    }

    @Test
    public void testEnoughCoinsAvailable_true() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> needed = HashMap.of(
                CENT10, 2,
                CENT20, 2
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isTrue();
    }

    @Test
    public void testEnoughCoinsAvailable_false() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 1,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> needed = HashMap.of(
                CENT10, 2,
                CENT20, 2
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isFalse();
    }

    @Test
    public void testEnoughCoinsAvailable_false_sortOfCoinNotAvailable() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 1,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> needed = HashMap.of(
                CENT50, 1
        );
        final boolean actual = subject.enoughCoinsAvailable(needed);
        assertThat(actual).isFalse();
    }

    @Test
    public void testReleaseCoins() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> needed = HashMap.of(
                CENT10, 1,
                CENT20, 1,
                EURO2, 2
        );
        final Map<Currency, Integer> actual = subject.releaseCoins(needed);
        assertThat(actual).isEqualTo(needed);
        assertThat(subject.getInventory()).containsExactlyInAnyOrder(
                new Tuple2<>(CENT10, 1),
                new Tuple2<>(CENT20, 1),
                new Tuple2<>(EURO2, 3));
    }

    @Test
    public void testInsertCoin() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 2,
                CENT20, 2,
                EURO2, 5
        );
        final CashBox subject = new CashBox(inventory);

        subject.insertCoins(EURO2, EURO1, CENT10, CENT50, CENT10);
        final Map<Currency, Integer> actual = subject.getInventory();
        assertThat(actual).containsExactlyInAnyOrder(
                new Tuple2<>(CENT10, 4),
                new Tuple2<>(CENT20, 2),
                new Tuple2<>(CENT50, 1),
                new Tuple2<>(EURO1, 1),
                new Tuple2<>(EURO2, 6));
    }

    @Test
    public void testNeededForExchange() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 5,
                CENT50, 5,
                EURO1, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> actual = subject.neededForExchange(130, EURO2);
        assertThat(actual).containsExactlyInAnyOrder(new Tuple2<>(CENT50, 1), new Tuple2<>(CENT20, 1));
    }

    @Test
    public void testNeededForExchange_secondExample() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 2,
                EURO1, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Map<Currency, Integer> actual = subject.neededForExchange(110, EURO2);
        assertThat(subject.enoughCoinsAvailable(actual)).isFalse();
        // assertThat(actual).containsExactlyInAnyOrder(new Tuple2<>(CENT20, 2), new Tuple2<>(CENT10, 5));

        // So we have a little Problem here :-D our drink-dispenser is customer-friendly and only exchanges in the
        // smallest amount of coins possible ;)
    }

    @Test
    public void testExchange_allGood() {
        final Map<Currency, Integer> inventory = HashMap.of(
                CENT10, 10,
                CENT20, 5,
                CENT50, 5,
                EURO1, 5
        );
        final CashBox subject = new CashBox(inventory);

        final Tuple2<Boolean, Map<Currency, Integer>> actual = subject.exchange(130, EURO2);
        assertThat(actual._1).isTrue();
        assertThat(actual._2).containsExactlyInAnyOrder(new Tuple2<>(CENT50, 1), new Tuple2<>(CENT20, 1));
    }

}