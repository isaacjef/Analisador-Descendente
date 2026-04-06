package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Tratador {
  Atributos atributos;
  // String resources_dir = System.getProperty("user.dir") + "/edu/src/main/resources/";
  String resources_dir = System.getProperty("user.dir") + "/demo/src/main/resources/";

  /*
   * Converte os atributos JSON em objetos Java.
   * O nome de cada objeto JSON deve ser igual ao nome do atributo da Classe.
   */
  public Atributos ler_atributos() {
    var json = """
        { "terminal": ["+", "*", "(", ")", "id", "$"],
        """;
    json += """
        "nonterminal": ["E", "X", "T", "Y", "F"],
        """;
    json += """
        "grammar": { 
          "E": ["TX"],
          "X": ["+TX", "null"],
          "T": ["FY"],
          "Y": ["*FY", "null"],
          "F": ["(E)", "id"]
        },
        """;
    json += """
        "first": {
          "E": ["(", "id"],
          "X": ["+", "null"],
          "T": ["(", "id"],
          "Y": ["*", "null"], 
          "F": ["(", "id"]
        },
        """;
    json += """
        "follow": {
          "E": [")", "$"],
          "X": [")", "$"],
          "T": ["+", ")", "$"],
          "Y": ["+", ")", "$"],
          "F": ["*", "+", ")", "$"]
        },
        """;
    json += """ 
            "tableM": {
              "E": {
                "id": "TX",
                "(": "TX"
              },
              "X": {
                "+": "+TX",
                ")": "null",
                "$": "null"
              },
              "T": {
                "id": "FY",
                "(": "FY"
              },
              "Y": {
                "+": "null",
                "*": "*FY",
                ")": "null",
                "$": "null"},
              "F": {
                "id": "id", 
                "(": "(E)"}
              }
            }
            """;
            
    Gson gson = new Gson();
    atributos = gson.fromJson(json, Atributos.class);

    return atributos;
  }

  public ArrayList<String> readInput() {
    ArrayList<String> lines = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new java.io.FileReader(resources_dir + "input.txt"))) {
      String line;
      
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (java.io.IOException e) {
      System.err.println("Erro ao ler o arquivo: input.txt - " + e.getMessage());
    }
    
    return lines;
  }

  public void writeOutput(StringBuilder sb) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(resources_dir + "output.txt", true))) {
      writer.write(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}