package com.example;

// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Atributos {
  ArrayList<String> terminal;
  ArrayList<String> nonterminal;
  Map<String, List<String>> grammar;
  Map<String, List<String>> first;
  Map<String, List<String>> follow;
  Map<String, Map<String, String>> tableM;

  public Atributos(ArrayList<String>  terminal, ArrayList<String> nonterminal, Map<String, List<String>> grammar,
     Map<String, List<String>> first, Map<String, List<String>> follow, Map<String, Map<String, String>> tableM) {
    this.terminal = terminal;
    this.nonterminal = nonterminal;
    this.grammar = grammar;
    this.first = first;
    this.follow = follow;
    this.tableM = tableM;
  }

  public String startElement() {
    //String start = this.getNonTerminal().getFirst();
    return this.getNonTerminal().getFirst();
  }

  public ArrayList<String> getTerminal() {
    return terminal;
  }

  public List<String> getNonTerminal() {
    return nonterminal;
  }

  public void setTerminal(ArrayList<String>  terminal) {
    this.terminal = terminal;
  }

  public Map<String, List<String>> getGrammar() {
    return this.grammar;
  }

  public Map<String, List<String>> getFirst() {
    return this.first;
  }

  public Map<String, List<String>> getFollow() {
    return this.follow;
  }

  public Map<String, Map<String, String>> getTableM() {
    return this.tableM;
  }
}
