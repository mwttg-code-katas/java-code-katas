package org.code.kata.number.scanner;

import static org.assertj.core.api.Assertions.assertThat;

import org.code.kata.common.FileService;
import org.testng.annotations.Test;

public class TransformerTest {

  @Test
  public void toNumber_Zero() {
    final String input = FileService.readFromResources("0.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(0);
  }

  @Test
  public void toNumber_One() {
    final String input = FileService.readFromResources("1.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(1);
  }

  @Test
  public void toNumber_Two() {
    final String input = FileService.readFromResources("2.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(2);
  }

  @Test
  public void toNumber_Three() {
    final String input = FileService.readFromResources("3.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(3);
  }

  @Test
  public void toNumber_Four() {
    final String input = FileService.readFromResources("4.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(4);
  }

  @Test
  public void toNumber_Five() {
    final String input = FileService.readFromResources("5.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(5);
  }

  @Test
  public void toNumber_Six() {
    final String input = FileService.readFromResources("6.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(6);
  }

  @Test
  public void toNumber_Seven() {
    final String input = FileService.readFromResources("7.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(7);
  }

  @Test
  public void toNumber_Eight() {
    final String input = FileService.readFromResources("8.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(8);
  }

  @Test
  public void toNumber_Nine() {
    final String input = FileService.readFromResources("9.txt").mkString();
    assertThat(Transformer.toNumber(input)).isEqualTo(9);
  }
}