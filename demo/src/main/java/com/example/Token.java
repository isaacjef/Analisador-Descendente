package com.example;

public class Token {
  private final String value;
  private final int position;

  public Token(String value, int position) {
    this.value = value;
    this.position = position;
  }

  public String getValue() {
    return value;
  }

  public int getPosition() {
    return position;
  }
}
