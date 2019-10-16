package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.automate.unit.BeverageUnit;
import org.drink.dispenser.automate.unit.CashUnit;
import org.drink.dispenser.commodity.Commodity;
import org.drink.dispenser.money.CurrencyCoins;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static org.drink.dispenser.Utilities.toMap;

public class DrinkDispenser {
    private final CashUnit cashUnit;
    private final BeverageUnit beverageUnit;

    public DrinkDispenser(final Map<CurrencyCoins, Integer> money, final Map<Commodity, Integer> commodity) {
        cashUnit = new CashUnit(money);
        beverageUnit = new BeverageUnit(commodity);
    }

    public Tuple2<Option<Commodity>, Map<CurrencyCoins, Integer>> buy(final Commodity drink, final CurrencyCoins... coins) {
        final Option<Commodity> maybe = beverageUnit.releaseDrink(drink);
        return Match(maybe).of(
                Case($Some($()), (Commodity item) -> {
                    final Option<Map<CurrencyCoins, Integer>> maybeExchange = cashUnit.exchange(item.price(), coins);
                    if (item.price() > Utilities.sumValues(coins)) {
                        return new Tuple2<>(Option.none(), toMap(coins));   // not enough payed
                    } else {
                        return Match(maybeExchange).of(                     // all good
                                Case($Some($()), exchange -> new Tuple2<>(Option.some(item), exchange)),
                                Case($None(), new Tuple2<>(Option.none(), Utilities.toMap(coins)))
                        );
                    }
                }),
                Case($None(), new Tuple2<>(Option.none(), toMap(coins)))    // drink is empty
        );
    }

    void prettyPrint() {
        System.out.println("Automate status");
        System.out.println("----------------------------");
        System.out.println("    Money:");
        cashUnit.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
        System.out.println("    Commodity:");
        beverageUnit.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
    }
}
