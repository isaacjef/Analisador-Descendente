package com.example;

import java.util.ArrayList;

//palavra-chave: final
//palavra-chave: var
//classe java.io and BufferedReader
//atributos.terminal.add(this.value);
// Pesquisar sobre comparator

// ** Analisar a gramática, verificar se terminais são válidos?
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