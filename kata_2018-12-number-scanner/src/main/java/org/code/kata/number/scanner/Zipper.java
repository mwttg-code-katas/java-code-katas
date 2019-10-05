package org.code.kata.number.scanner;

import com.google.common.base.Splitter;
import io.vavr.collection.List;

class Zipper {
  static List<String> extractNumbers(final List<String> lines) {
    lines.map(line -> Splitter.fixedLength(3).splitToList(line))
  }
}
