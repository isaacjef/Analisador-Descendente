package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Analisador_Desc {

  private final Atributos atributos;
  private ArrayList<Token> tokens;
  private Stack<String> pilha_transicoes;
  
  Tratador reader = new Tratador();

  public Analisador_Desc() {
    this.atributos = reader.ler_atributos();
    this.tokens = new ArrayList<>();
    setPilha();
  }

  public StringBuilder exibir_saida(String entrada) {
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = this.parser(entrada);

    sb.append("Input: ").append(entrada).append("\n");
    if (getPilha().isEmpty()) {
      sb.append("Status: true\n");
    } else {
      sb.append("Status: false\n");
    }
    sb.append("Stack: [\n").append(sb2).append("\n]\n\n");

    System.out.println(sb.toString());
    return sb;
  }

  // algoritmo que recebendo como entrada uma sentença x, emite como saída:
  public StringBuilder parser(String entrada) {
    StringBuilder sb = new StringBuilder();

    sb.append(pilha_transicoes).append(", ");
    this.tokens = this.analisar_entrada(entrada);

    Map<String, Map<String, String>> tableM = atributos.getTableM();
    String tabela_transicao;

    while(!tokens.isEmpty()) {
      // Verificação caso o símbolo "$" de fato não possa aparecer na pilha de transições.
      // Caso contrário, comente a condição abaixo, e descomente a linha "pilha_transicoes.push();" no método setPilha().
      if (pilha_transicoes.isEmpty()) {
        if (tokens.getFirst().getValue().equals("$")) {
          // Por que -2? --> remove uma vírgula, procurar solução melhor para isso.
          // this.tokens.remove(0);
          sb.deleteCharAt(sb.length() - 2);
        }

        break;
      }

      if (this.tokens.getFirst().getValue().equals(pilha_transicoes.peek())) {
        pilha_transicoes.pop();

        sb.append(pilha_transicoes).append(", ");
        tokens.remove(0);

        // Quanto a transição for vazia:
      } else if (pilha_transicoes.peek().equals("null")) {
        pilha_transicoes.pop();
        
        sb.append(pilha_transicoes).append(", ");
        
        // Caso o elemento no topo da pilha de transições seja um não-terminal
      } else if (atributos.getNonTerminal().contains(pilha_transicoes.peek())) {
        
        tabela_transicao = tableM.get(pilha_transicoes.pop()).get(this.tokens.getFirst().getValue());
        
        if (tabela_transicao == null) {
          sb.append(pilha_transicoes);
          sb.append(" Erro na análise sintática!\nCaractere não pertence à gramática: ").append(this.tokens.getFirst().getValue());
          sb.append("\nLista de Tokens restantes: ").append(tokens.stream().map(Token::getValue).toList());

          tokens.clear();
        
          // Considerar outra opção, (E), null, "null", id
        } else if (!tabela_transicao.equals("null") && !tabela_transicao.equals("id")) {
          int i = tabela_transicao.length();

          while(i > 0) {
            char topo = tabela_transicao.charAt(i-1);
            pilha_transicoes.push(topo + "");
            i--;
          }

          sb.append(pilha_transicoes).append(", ");
        } else {
          // Armazena os terminais no topo da pilha de transicoes
          pilha_transicoes.push(tabela_transicao);

          sb.append(pilha_transicoes).append(", ");
        }
      } 
    }

    return sb;
  }

  /**
   * Analisa a string de entrada, e armazena os terminais encontrados em um ArrayList de Tokens.
   * O ArrayList de Tokens é ordenado de acordo com o índice final do token na string de entrada,
   * para que a ordem dos tokens seja a mesma da string de entrada.
   * 
   * @param entrada String de entrada a ser analisada.
   * @return ArrayList de Tokens encontrados na string de entrada,
   * ordenados de acordo com a posição na string de entrada.
   */
  public ArrayList<Token> analisar_entrada(String entrada) {
    ArrayList<String> terminal = atributos.getTerminal();
    ArrayList<Token> tks = getTokens();

    entrada += "$";
    String finalEntrada = entrada;

    for (String t : terminal) {
      Pattern pattern = Pattern.compile(Pattern.quote(t));
      Matcher matcher = pattern.matcher(entrada);

      while (matcher.find()) {
        Token tok = new Token(matcher.group(), matcher.end());
        tks.add(tok);
      }

      finalEntrada = finalEntrada.replaceAll(Pattern.quote(t), "");
    }

    tks.add(new Token(finalEntrada, entrada.indexOf(finalEntrada)));
    tks.sort(Comparator.comparingInt(t -> t.getPosition()));

    return tks;
  }

  public ArrayList<Token> getTokens() {
    return tokens;
  }

  public Stack<String> getPilha() {
    return pilha_transicoes;
  }

  public void setPilha() {
    pilha_transicoes = new Stack<>();
    //pilha_transicoes.push("$");
    pilha_transicoes.push(atributos.startElement());
  }
  
}
