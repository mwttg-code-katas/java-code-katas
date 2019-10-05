package org.code.kata.number.scanner;

import io.vavr.collection.List;
import org.code.kata.common.FileService;

public class Application {
  public static void main(String[] args) {
    final List<String> lines = FileService.readFromResources("numbers.txt");
    final List<String> lcdNumbers = Extractor.getLcdNumbers(lines);
    final List<Integer> numbers = Transformer.convert(lcdNumbers);
    System.out.println("The scanned number is: " + numbers.map(Object::toString).mkString());
  }
}
