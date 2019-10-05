package org.code.kata.number.scanner;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

class Transformer {
  static int toNumber(final String input) {
    return Match(input).of(
        Case($(" _ | ||_|"), 0),
        Case($("     |  |"), 1),
        Case($(" _  _||_ "), 2),
        Case($(" _  _| _|"), 3),
        Case($("   |_|  |"), 4),
        Case($(" _ |_  _|"), 5),
        Case($(" _ |_ |_|"), 6),
        Case($(" _   |  |"), 7),
        Case($(" _ |_||_|"), 8),
        Case($(" _ |_| _|"), 9)
    );
  }
}
