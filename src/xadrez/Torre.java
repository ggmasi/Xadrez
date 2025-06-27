package xadrez;

public class Torre extends Peca {

    public Torre(String cor) {
        super(cor);
        this.representacao = 'R';
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (linhaO == linhaD || colunaO == colunaD);
    }

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

        while (linha != linhaD || coluna != colunaD) {
            caminho += "" + linha + coluna;
            linha += passoLinha;
            coluna += passoColuna;
        }

        caminho += "" + linhaD + colunaD;
        return caminho;
    }
}
