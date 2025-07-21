package xadrez;

import java.util.ArrayList;
import java.util.List;

// classe abstrata que representa uma peça de xadrez
public abstract class Peca implements Cloneable {
    protected String cor;               // cor da peça: "branco" ou "preto"
    protected char representacao;       // caractere usado para desenhar a peça no tabuleiro

    // construtor que define a cor da peça
    public Peca(String cor) {
        this.cor = cor;
    }

    // método para clonar uma peça (necessário para copiar o estado do jogo)
    @Override
    public Peca clone() throws CloneNotSupportedException {
        try {
            return (Peca) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // retorna a cor da peça
    public String getCor() {
        return this.cor;
    }

    // retorna o caractere que representa a peça
    public char getRepresentacao() {
        return representacao;
    }

    // retorna uma lista com todas as casas válidas para onde a peça pode se mover
    public List<Casa> movimentosPossiveis(Tabuleiro tabuleiro, int linhaAtual, char colunaAtual) {
        List<Casa> validas = new ArrayList<>();

        // percorre todas as casas do tabuleiro
        for (int linha = 1; linha <= 8; linha++) {
            for (char coluna = 'a'; coluna <= 'h'; coluna++) {

                // ignora a casa atual da peça
                if (linha == linhaAtual && coluna == colunaAtual)
                    continue;

                // verifica se o movimento é válido de acordo com o tipo da peça
                if (this.movimentoValido(linhaAtual, colunaAtual, linha, coluna)) {
                    Casa destino = tabuleiro.getCasa(linha, coluna);

                    if (destino == null) continue;

                    // se a casa estiver vazia ou com peça inimiga, é um movimento permitido
                    if (!destino.temPeca()) {
                        validas.add(destino);
                    } else if (!destino.getPeca().getCor().equals(this.cor)) {
                        validas.add(destino);
                    }
                }
            }
        }

        return validas;
    }

    // método abstrato que retorna a forma visual da peça
    public abstract String desenho();

    // método abstrato que verifica se um movimento é válido para a peça
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);

    // método abstrato que define o caminho que a peça percorre entre origem e destino
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
}
