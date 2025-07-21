package xadrez;

import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

public class Jogador {

    private final String nome;                 // Nome do jogador
    private final String cor;                  // Cor das peças do jogador ("branco" ou "preto")
    private final List<Peca> pecas;            // Lista das peças que o jogador possui no início do jogo
    private final List<Peca> pecasCapturadas; // Lista das peças capturadas pelo jogador durante o jogo

    /**
     * Construtor que inicializa um jogador com nome, cor e todas as peças na configuração padrão.
     * Cria 8 peões, 2 torres, 2 bispos, 2 cavalos, 1 rei e 1 dama da cor do jogador.
     */
    public Jogador(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
        pecas = new ArrayList<>();
        pecasCapturadas = new ArrayList<>();
        
        // Cria 8 peões para o jogador
        for (int i = 0; i < 8; i++) {
            pecas.add(new Peao(this.cor));
        }
        
        // Adiciona as torres (2 unidades)
        pecas.add(new Torre(this.cor));
        pecas.add(new Torre(this.cor));
        
        // Adiciona os bispos (2 unidades)
        pecas.add(new Bispo(this.cor));
        pecas.add(new Bispo(this.cor));
        
        // Adiciona os cavalos (2 unidades)
        pecas.add(new Cavalo(this.cor));
        pecas.add(new Cavalo(this.cor));
        
        // Adiciona o rei (1 unidade)
        pecas.add(new Rei(this.cor));
        
        // Adiciona a dama (1 unidade)
        pecas.add(new Dama(this.cor));
    }
    
    /**
     * Método para ler a jogada do jogador a partir da entrada do console.
     * Utiliza Scanner para capturar o input do usuário.
     * @return String representando a jogada informada pelo jogador.
     */
    public String informaJogada() {
        Scanner scanner = new Scanner(System.in);
        String jogada = scanner.nextLine();
        return jogada;
    }
    
    /**
     * Retorna uma string com a representação das peças capturadas pelo jogador.
     * Cada peça capturada é representada pelo seu desenho (caractere ou símbolo).
     * @return String contendo os desenhos das peças capturadas separados por espaço.
     */
    public String pecasCapturadas() {
        StringBuilder sb = new StringBuilder();
        for (Peca peca : this.pecasCapturadas) {
            sb.append(peca.desenho());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    
    /**
     * Adiciona uma peça capturada à lista de peças capturadas do jogador.
     * @param peca Peça capturada a ser adicionada.
     */
    public void adicionarCapturado(Peca peca) {
        this.pecasCapturadas.add(peca);
    }
    
    /**
     * Getter para obter o nome do jogador.
     * @return Nome do jogador.
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Getter para obter a cor das peças do jogador.
     * @return Cor das peças ("branco" ou "preto").
     */
    public String getCor() {
        return cor;
    }

}
