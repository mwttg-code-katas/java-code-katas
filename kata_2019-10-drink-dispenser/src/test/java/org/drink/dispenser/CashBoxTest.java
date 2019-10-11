package org.drink.dispenser;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.drink.dispenser.EuroCoin.*;

public class CashBoxTest {

  @Test
  public void testExchange_firstPositiveExample() {
    final Map<Currency, Integer> startInventory = HashMap.of(
        CENT10, 100,
        CENT20, 100,
        CENT50, 100,
        EURO1, 100,
        EURO2, 100
    );
    final CashBox subject = new CashBox(startInventory);

    final Map<Currency, Integer> actual = subject.exchange(120, EURO1, CENT50);
    assertThat(actual).isEqualTo(HashMap.of(CENT10, 1, CENT20, 1));

    final Map<Currency, Integer> currentInventory = subject.getInventory();
    assertThat(currentInventory).isEqualTo(
            HashMap.of(
                    CENT10, 99,
                    CENT20, 99,
                    CENT50, 101,
                    EURO1, 101,
                    EURO2, 100
            )
    );
  }

  @Test
  public void testExchange_secondPositiveExample() {
    final Map<Currency, Integer> startInventory = HashMap.of(
            CENT10, 10,
            CENT20, 10,
            CENT50, 10,
            EURO1, 10,
            EURO2, 10
    );
    final CashBox subject = new CashBox(startInventory);

    final Map<Currency, Integer> actual = subject.exchange(510, EURO1, CENT50, CENT50, EURO2, EURO2);
    assertThat(actual).isEqualTo(HashMap.of(CENT50, 1, CENT20, 2));

    final Map<Currency, Integer> currentInventory = subject.getInventory();
    assertThat(currentInventory).isEqualTo(
            HashMap.of(
                    CENT10, 10,
                    CENT20, 8,
                    CENT50, 11,
                    EURO1, 11,
                    EURO2, 12
            )
    );
  }
}