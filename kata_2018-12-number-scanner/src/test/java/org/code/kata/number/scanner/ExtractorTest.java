package org.code.kata.number.scanner;

import io.vavr.collection.List;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractorTest {

  @Test
  public void testExtractNumbers() {
    final List<String> input = List.of("111aaa$$$", "222bbb%%%", "333ccc===");
    final List<String> result = Extractor.getLcdNumbers(input);
    assertThat(result).containsExactly("111222333", "aaabbbccc", "$$$%%%===");
  }
}