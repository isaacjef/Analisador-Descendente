package com.example;

public class Main {
  public static void main(String[] args) {
    // JSONReader reader = new JSONReader();
    Analisador_Desc analisador = new Analisador_Desc();

    String aba = "id+id*id";

    analisador.parser_preditivo(aba);
  }
}  