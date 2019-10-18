package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.automate.unit.CommodityUnit;
import org.drink.dispenser.automate.unit.CashUnit;
import org.drink.dispenser.commodity.Commodity;
import org.drink.dispenser.money.Currency;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static org.drink.dispenser.Utilities.toMap;

public class VendorMachine<T extends Currency, U extends Commodity> {
    private final CashUnit<T> cashUnit;
    private final CommodityUnit<U> commodityUnit;

    public VendorMachine(final Map<T, Integer> money, final Map<U, Integer> commodity) {
        cashUnit = new CashUnit<>(money);
        commodityUnit = new CommodityUnit<>(commodity);
    }

    @SafeVarargs
    public final Tuple2<Option<U>, Map<T, Integer>> buy(final U commodity, final T... coins) {
        final Option<U> maybe = commodityUnit.releaseCommodity(commodity);
        return Match(maybe).of(
                Case($Some($()), (U item) -> {
                    final Option<Map<T, Integer>> maybeExchange = cashUnit.exchange(item.price(), coins);
                    if (item.price() > Utilities.sumValues(coins)) {
                        return new Tuple2<>(Option.none(), toMap(coins));   // not enough payed
                    } else {
                        return Match(maybeExchange).of(                     // enough payed
                                Case($Some($()), exchange -> new Tuple2<>(Option.some(item), exchange)),    // all good
                                Case($None(), new Tuple2<>(Option.none(), Utilities.toMap(coins)))          // Automate doesn't have enough money for exchange
                        );
                    }
                }),
                Case($None(), new Tuple2<>(Option.none(), toMap(coins)))    // commodity is empty
        );
    }

    void prettyPrint() {
        System.out.println("Automate status");
        System.out.println("----------------------------");
        System.out.println("    Money:");
        cashUnit.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
        System.out.println("    Commodity:");
        commodityUnit.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
    }
}
