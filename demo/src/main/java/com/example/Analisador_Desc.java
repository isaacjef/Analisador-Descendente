package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Analisador_Desc {

  private Atributos atributos_gramatica;
  private ArrayList<Token> tokens;
  private Stack<String> pilha_transicoes;

  JSONReader reader = new JSONReader();

  public Analisador_Desc() {
    setAtributos();
    setTokens();
    setPilha();
  }

  public void parser_preditivo(String entrada) {
    //getPilha && tokens se refere à entrada, entretanto, um arraylist com todos os terminais (tokens) da linguagem
    System.out.println(pilha_transicoes.toString());
    this.tokens = this.analisar_entrada(entrada);

    Map<String, Map<String, String>> tableM = getAtributos().getTableM();
    String tabela_transicao;

    while(!tokens.isEmpty()) {
      if (this.tokens.getFirst().getValue().equals(pilha_transicoes.peek())) {
        pilha_transicoes.pop();
        System.out.println(pilha_transicoes.toString());
        tokens.remove(0);

        // A ordem de condição deve ser essa, para que a string "null" possa aparecer na pilha, e assim,
        // na string de saída, que indicará todas as variações/transições ocorridas na pilha.
        // A situação "|| pilha_transicoes.peek().equals("id")" é capturada na primeira condição
      } else if (pilha_transicoes.peek().equals("null")) {
        pilha_transicoes.pop();
        System.out.println(pilha_transicoes.toString());
        
        // Caso o elemento no topo da pilha de transições seja um não-terminal
      } else if (getAtributos().getNonTerminal().contains(pilha_transicoes.peek())) {
        
        // Consultar tabela M
        tabela_transicao = tableM.get(pilha_transicoes.pop()).get(this.tokens.getFirst().getValue());
        // System.out.println(pilha_transicoes.toString());
        
        // Se cair nesta condição, a análise descendente deu erro.
        if (tabela_transicao == null) {
          tokens.clear();
        
          // Considerar outra opção, (E), null, "null", id
        } else if (!tabela_transicao.equals("null") && !tabela_transicao.equals("id")) {
          int i = tabela_transicao.length();

          while(i > 0) {
            char topo = tabela_transicao.charAt(i-1);
            pilha_transicoes.push(topo + "");
            i--;
          }

          System.out.println(pilha_transicoes.toString());
        } else {
          // Código repetido, ja acessamos a tabela M acima.
          // String topo_pi = tableM.get(pilha_transicoes.peek()).get(this.tokens.getFirst().getValue());

          // Armazena os terminais no topo da pilha de transicoes
          pilha_transicoes.push(tabela_transicao);
          System.out.println(pilha_transicoes.toString());
        }

      }
      
    }
    // System.out.println(pilha_transicoes.toString());
  }

  // Adicionar símbolo "$" ao final da entrada.
  public ArrayList<Token> analisar_entrada(String entrada) {
    ArrayList<String> terminal = getAtributos().getTerminal();
    ArrayList<Token> tks = getTokens();

    terminal.forEach(t -> {
      Pattern pattern = Pattern.compile(Pattern.quote(t));
      Matcher matcher = pattern.matcher(entrada);

      while (matcher.find()) {
        Token tok = new Token(matcher.group(), matcher.end());
        tks.add(tok);
      }
    });

    // Pesquisar sobre comparator
    tks.sort(Comparator.comparingInt(t -> t.getEnd()));
    tks.add(new Token("$", entrada.length() + 1));
    return tks;
  }

  public Atributos getAtributos() {
    return this.atributos_gramatica;
  }

  public void setAtributos() {
    this.atributos_gramatica = reader.ler_atributos();
  }

  public ArrayList<Token> getTokens() {
    return tokens;
  }

  public void setTokens() {
    this.tokens = new ArrayList<>();
  }

  public Stack<String> getPilha() {
    
    return pilha_transicoes;
  }

  public void setPilha() {
    pilha_transicoes = new Stack<>();
    pilha_transicoes.push("$");
    pilha_transicoes.push(getAtributos().startElement());
  }
  
}
