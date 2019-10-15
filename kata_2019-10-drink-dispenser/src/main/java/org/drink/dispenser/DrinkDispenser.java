package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;
import org.drink.dispenser.money.Currency;
import org.drink.dispenser.part.CashBox;
import org.drink.dispenser.part.DrinkBox;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static org.drink.dispenser.Utilities.toMap;

public class DrinkDispenser {
    private final CashBox cashBox;
    private final DrinkBox drinkBox;

    public DrinkDispenser(final Map<Currency, Integer> money, final Map<Commodity, Integer> commodity) {
        cashBox = new CashBox(money);
        drinkBox = new DrinkBox(commodity);
    }

    public Tuple2<Option<Commodity>, Map<Currency, Integer>> buy(final Commodity drink, final Currency... coins) {
        final Option<Commodity> maybe = drinkBox.releaseDrink(drink);
        return Match(maybe).of(
                Case($Some($()), (Commodity item) -> {
                    final Option<Map<Currency, Integer>> maybeExchange = cashBox.exchange(item.price(), coins);
                    return Match(maybeExchange).of(
                            Case($Some($()), exchange -> new Tuple2<>(Option.some(item), exchange)),
                            Case($None(), new Tuple2<>(Option.none(), Utilities.toMap(coins)))
                    );
                }),
                Case($None(), new Tuple2<>(Option.none(), toMap(coins)))
        );
    }

    public void prettyPrint() {
        System.out.println("Automate status");
        System.out.println("----------------------------");
        System.out.println("    Money:");
        cashBox.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
        System.out.println("    Commodity:");
        drinkBox.getInventory().forEach(item -> System.out.println(String.format("        %s ............... %s", item._1(), item._2)));
    }
}
