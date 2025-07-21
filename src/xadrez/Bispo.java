package xadrez;

// classe que representa o bispo no xadrez
public class Bispo extends Peca {

    public Bispo(String cor) {
        super(cor);
        // define a representação com base na cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'B';
        } else {
            this.representacao = 'b';
        }
    }

    // retorna o desenho da peça no tabuleiro (ex: "BB" ou "bb")
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    // verifica se o movimento é válido para um bispo (diagonal)
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);
        return difLinha == difColuna;
    }

    // retorna o caminho entre a origem e o destino da peça (em formato de string)
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (!movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "";
        }

        String caminho = "" + linhaO + colunaO;

        int passoLinha = linhaD > linhaO ? 1 : -1;
        int passoColuna = colunaD > colunaO ? 1 : -1;

        int linha = linhaO + passoLinha;
        char coluna = (char)(colunaO + passoColuna);

        // percorre o caminho diagonal até a casa de destino
        while (linha != linhaD || coluna != colunaD) {
            caminho += "" + linha + coluna;
            linha += passoLinha;
            coluna += passoColuna;
        }

        caminho += "" + linhaD + colunaD;
        return caminho;
    }
}
