package org.code.kata.number.scanner;

import com.google.common.base.Splitter;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;

class Extractor {
  static List<String> getLcdNumbers(final List<String> lines) {
    final List<List<String>> ofLengthThree = lines.map(line -> List.ofAll(Splitter.fixedLength(3).splitToList(line)));
    final List<List<String>> combinedRows = ofLengthThree.head().zipWith(ofLengthThree.get(1), (u, v) -> List.of(u, v)).zipWith(ofLengthThree.get(2), List::append);
    return combinedRows.map(Traversable::mkString);
  }
}
