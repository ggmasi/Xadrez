package xadrez;

// classe que representa a dama (rainha) no xadrez
public class Dama extends Peca {

    public Dama(String cor) {
        super(cor);
        // define a representação com base na cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'D';
        } else {
            this.representacao = 'd';
        }
    }

    // retorna o desenho da peça no tabuleiro (ex: "DD" ou "dd")
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    // verifica se o movimento é válido para uma dama (linha, coluna ou diagonal)
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);
        return (linhaO == linhaD || colunaO == colunaD || difLinha == difColuna);
    }

    // retorna o caminho entre a origem e o destino da peça (linha, coluna ou diagonal)
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (!movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "";
        }

        String caminho = "" + linhaO + colunaO;

        int passoLinha = 0;
        int passoColuna = 0;

        if (linhaD > linhaO) passoLinha = 1;
        else if (linhaD < linhaO) passoLinha = -1;

        if (colunaD > colunaO) passoColuna = 1;
        else if (colunaD < colunaO) passoColuna = -1;

        int linha = linhaO + passoLinha;
        char coluna = (char)(colunaO + passoColuna);

        // percorre o caminho até o destino
        while (linha != linhaD || coluna != colunaD) {
            caminho += "" + linha + coluna;
            linha += passoLinha;
            coluna += passoColuna;
        }

        caminho += "" + linhaD + colunaD;
        return caminho;
    }
}
