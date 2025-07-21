package xadrez;

// classe que representa o cavalo no xadrez
public class Cavalo extends Peca {

    public Cavalo(String cor) {
        super(cor);
        // define a representação com base na cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'C';
        } else {
            this.representacao = 'c';
        }
    }

    // retorna o desenho da peça no tabuleiro (ex: "CC" ou "cc")
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    // verifica se o movimento é válido para um cavalo (em L)
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);

        return (difLinha == 2 && difColuna == 1) || (difLinha == 1 && difColuna == 2);
    }

    // cavalo "salta" sobre as peças, então o caminho é vazio
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        return "";
    }
}
