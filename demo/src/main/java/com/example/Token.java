package com.example;

public class Token {
  private String value;
  private int end_index;

  public Token(String value, int end_index) {
    this.value = value;
    this.end_index = end_index;
  }

  public String getValue() {
      return value;
  }

//   public void setValue(String value) {
//       this.value = value;
//   }

  public int getEnd() {
      return end_index;
  }

//   public void setEnd(int end_index) {
//       this.end_index = end_index;
//   }
}
