package org.code.kata.common;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {
  public static List<String> readFromResources(final String filename) {
    return Match(getMaybeContent(filename))
        .of(
            Case($Success($()), content -> content),
            Case($Failure($()), error -> {
              System.out.println("An error occurred during reading a file from resources.");
              error.printStackTrace(); // Not the way to go, but good enough for now (for code-katas) ;)
              return List.empty();
            })
        );
  }

  private static Try<List<String>> getMaybeContent(final String filename) {
    final URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
    return Try.of(() -> List.ofAll(Files.readAllLines(Paths.get(url.toURI()))));
  }
}
