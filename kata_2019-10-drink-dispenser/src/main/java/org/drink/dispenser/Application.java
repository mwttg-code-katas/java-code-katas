package org.drink.dispenser;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.drink.dispenser.commodity.Beverage;
import org.drink.dispenser.commodity.CandyBar;
import org.drink.dispenser.money.Euro;
import org.drink.dispenser.money.Zloty;

import static org.drink.dispenser.commodity.Beverage.BOOTY_SWEAT;
import static org.drink.dispenser.commodity.Beverage.DUFF_BEER;
import static org.drink.dispenser.commodity.Beverage.NUKA_COLA;
import static org.drink.dispenser.commodity.Beverage.NUKA_COLA_QUANTUM;
import static org.drink.dispenser.commodity.Beverage.WATER;
import static org.drink.dispenser.commodity.CandyBar.KITKAT;
import static org.drink.dispenser.commodity.CandyBar.LION;
import static org.drink.dispenser.commodity.CandyBar.MARS;
import static org.drink.dispenser.commodity.CandyBar.MILKYWAY;
import static org.drink.dispenser.money.Euro.CENT10;
import static org.drink.dispenser.money.Euro.CENT20;
import static org.drink.dispenser.money.Euro.CENT50;
import static org.drink.dispenser.money.Euro.EURO1;
import static org.drink.dispenser.money.Euro.EURO2;
import static org.drink.dispenser.money.Zloty.GROSZY10;
import static org.drink.dispenser.money.Zloty.GROSZY20;
import static org.drink.dispenser.money.Zloty.GROSZY50;
import static org.drink.dispenser.money.Zloty.ZLOTY1;
import static org.drink.dispenser.money.Zloty.ZLOTY2;
import static org.drink.dispenser.money.Zloty.ZLOTY5;

public class Application {
  public static void main(String[] args) {
    exampleDrinkDispenser();
    System.out.println("\n=============================================\n");
    exampleCandyBarDispenser();
  }

  private static void exampleDrinkDispenser() {
    // here goes a vendor machine for drinks with euros
    final Map<Euro, Integer> dispenserCash = HashMap.of(
        EURO2, 100,
        EURO1, 100,
        CENT50, 100,
        CENT20, 100,
        CENT10, 100);
    final Map<Beverage, Integer> dispenserInventory = HashMap.of(
        WATER, 50,
        NUKA_COLA, 50,
        NUKA_COLA_QUANTUM, 50,
        DUFF_BEER, 50,
        BOOTY_SWEAT, 50);
    final VendorMachine<Euro, Beverage> euroDrinkDispenser = new VendorMachine<>(dispenserCash, dispenserInventory);
    System.out.println("A DrinkDispenser was created... \n");
    euroDrinkDispenser.prettyPrint();

    System.out.println(String.format("\n You try to buy a %s with the following coins: %s %s %s", NUKA_COLA_QUANTUM, EURO2, EURO1, EURO1));
    final Tuple2<Option<Beverage>, Map<Euro, Integer>> result = euroDrinkDispenser.buy(NUKA_COLA_QUANTUM, EURO2, EURO1, EURO1);

    System.out.println(String.format("\n Your transactions results in %s", result));
    System.out.println();
    euroDrinkDispenser.prettyPrint();
  }

  private static void exampleCandyBarDispenser() {
    // here goes a vendor machine for candy bars with zloty
    final Map<Zloty, Integer> dispenserCash = HashMap.of(
        ZLOTY5, 100,
        ZLOTY2, 200,
        ZLOTY1, 100,
        GROSZY50, 100,
        GROSZY20, 100,
        GROSZY10, 100);
    final Map<CandyBar, Integer> dispenserInventory = HashMap.of(
        KITKAT, 50,
        MARS, 50,
        LION, 50,
        MILKYWAY, 50);
    final VendorMachine<Zloty, CandyBar> zlotyCandyBarDispenser = new VendorMachine<>(dispenserCash, dispenserInventory);
    System.out.println("A CandyBarDispenser was created... \n");
    zlotyCandyBarDispenser.prettyPrint();

    System.out.println(String.format("\n You try to buy a %s with the following coins: %s", MILKYWAY, ZLOTY5));
    final Tuple2<Option<CandyBar>, Map<Zloty, Integer>> result = zlotyCandyBarDispenser.buy(MILKYWAY, ZLOTY5);

    System.out.println(String.format("\n Your transactions results in %s", result));
    System.out.println();
    zlotyCandyBarDispenser.prettyPrint();
  }
}
