package com.example;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {

    Tratador tratador = new Tratador();

    ArrayList<String> input_lines = tratador.readInput();
    for (String line : input_lines) {
      Analisador_Desc analisador = new Analisador_Desc();
      StringBuilder result = analisador.exibir_saida(line);
      tratador.writeOutput(result);
    }
  }
}  
