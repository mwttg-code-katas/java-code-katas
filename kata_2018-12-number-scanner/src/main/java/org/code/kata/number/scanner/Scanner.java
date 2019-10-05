package org.code.kata.number.scanner;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import io.vavr.API;
import io.vavr.collection.List;
import org.code.kata.common.FileService;

class Scanner {
  static void scanNumber(final String filename) {
    final List<String> lines = FileService.readFromResources(filename);
    final int amountOfNumbers = lines.head().length() / 3;


  }

  static int transform(final String numberPresentation) {
    return Match(numberPresentation).of(
        Case($(" _ | ||_|"), 0),
        Case($("     |  |"), 1),
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
