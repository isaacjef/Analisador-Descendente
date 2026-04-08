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
    if (pilha_transicoes.isEmpty()) {
      sb.append("Status: true\n");
    } else {
      sb.append("Status: false\n");
    }
    sb.append("Stack: [\n").append(sb2).append("\n]\n\n");

    System.out.println(sb.toString());
    return sb;
  }

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
          sb.setLength(sb.length() - 2);
        }

        break;
      }

      if (this.tokens.getFirst().getValue().equals(pilha_transicoes.peek())) {
        // Se o token for igual ao símbolo no topo da pilha de transições, desempilha o símbolo e remove o token da lista de tokens.
        pilha_transicoes.pop();

        sb.append(pilha_transicoes);
        tokens.remove(0);

        
      } else if (pilha_transicoes.peek().equals("null")) {
        // Quanto a transição for o vazio ("null"):
        pilha_transicoes.pop();
        
        sb.append(pilha_transicoes);
        
        
      } else if (atributos.getNonTerminal().contains(pilha_transicoes.peek())) {
        // Caso o elemento no topo da pilha de transições seja um não-terminal
        tabela_transicao = tableM.get(pilha_transicoes.pop()).get(this.tokens.getFirst().getValue());
        
        if (tabela_transicao == null) {
          // Se não há símbolo para a transição entre os elementos (caractere não pertence à gramática), a tabela de transição é nula.
          sb.append(pilha_transicoes);
          sb.append(" Erro na análise sintática!\nCaractere não pertence à gramática ou transição inválida: ").append(this.tokens.getFirst().getValue());
          sb.append("\nPilha de transições: ").append(pilha_transicoes);
          sb.append("\nLista de Tokens restantes: ").append(tokens.stream().map(Token::getValue).toList());

          tokens.clear();
        
        } else if (!tabela_transicao.equals("null") && !tabela_transicao.equals("id")) {
          // Considerar outras opções, (E), null, "null", id
          int i = tabela_transicao.length();

          while(i > 0) {
            char topo = tabela_transicao.charAt(i-1);
            pilha_transicoes.push(topo + "");
            i--;
          }

          sb.append(pilha_transicoes);
        } else {
          // Armazena os terminais no topo da pilha de transicoes
          pilha_transicoes.push(tabela_transicao);

          sb.append(pilha_transicoes);
        }
      }

      sb.append(", ");
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
    ArrayList<Token> tks = this.tokens;

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

    if (!finalEntrada.equals("")) {
      tks.add(new Token(finalEntrada, entrada.indexOf(finalEntrada)));
    }
    tks.sort(Comparator.comparingInt(t -> t.getPosition()));

    return tks;
  }

  public void setPilha() {
    pilha_transicoes = new Stack<>();
    //pilha_transicoes.push("$");
    pilha_transicoes.push(atributos.startElement());
  }
  
}