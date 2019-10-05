package org.code.kata.common;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.collection.List;
import org.testng.annotations.Test;

public class FileServiceTest {

  @Test
  public void testReadFromResources_withValidFile() {
    final String filename = "input.txt";
    final List<String> actual = FileService.readFromResources(filename);
    assertThat(actual).containsExactly("first line", "second line", "third line");
  }

  @Test
  public void testReadFromResources_withInvalidFilename() {
    final String filename = "an-invalid-filename.txt";
    final List<String> actual = FileService.readFromResources(filename);
    assertThat(actual).isEmpty();
  }
}