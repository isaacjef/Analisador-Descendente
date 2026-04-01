package com.example;

import com.google.gson.Gson;

public class JSONReader {
  Atributos atributos;

  /*
   * Converte os atributos JSON em objetos Java.
   *
   *
   */
  public Atributos ler_atributos() {
    // O nome de cada objeto JSON deve ser igual ao nome do atributo da Classe.
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
}