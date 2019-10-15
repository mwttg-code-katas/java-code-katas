package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Commodity;
import org.drink.dispenser.money.Currency;

import static org.drink.dispenser.commodity.Drink.BOOTY_SWEAT;
import static org.drink.dispenser.commodity.Drink.DUFF_BEER;
import static org.drink.dispenser.commodity.Drink.NUKA_COLA;
import static org.drink.dispenser.commodity.Drink.NUKA_COLA_QUANTUM;
import static org.drink.dispenser.commodity.Drink.WATER;
import static org.drink.dispenser.money.EuroCoin.CENT10;
import static org.drink.dispenser.money.EuroCoin.CENT20;
import static org.drink.dispenser.money.EuroCoin.CENT50;
import static org.drink.dispenser.money.EuroCoin.EURO1;
import static org.drink.dispenser.money.EuroCoin.EURO2;

public class Application {
    public static void main(String[] args) {
        final Map<Currency, Integer> dispenserCash = HashMap.of(
                EURO2, 100,
                EURO1, 100,
                CENT50, 100,
                CENT20, 100,
                CENT10, 100);
        final Map<Commodity, Integer> dispenserInventory = HashMap.of(
                WATER, 50,
                NUKA_COLA, 50,
                NUKA_COLA_QUANTUM, 50,
                DUFF_BEER, 50,
                BOOTY_SWEAT, 50);
        final DrinkDispenser drinkDispenser = new DrinkDispenser(dispenserCash, dispenserInventory);
        System.out.println("A DrinkDispenser was created... \n");
        drinkDispenser.prettyPrint();

        System.out.println(String.format("\n You try to buy a %s with the following coins: %s %s %s", NUKA_COLA_QUANTUM, EURO2, EURO1, EURO1));
        final Tuple2<Option<Commodity>, Map<Currency, Integer>> result = drinkDispenser.buy(NUKA_COLA_QUANTUM, EURO2, EURO1, EURO1);

        System.out.println(String.format("\n Your transactions results in %s", result));
        System.out.println();
        drinkDispenser.prettyPrint();
    }
}
