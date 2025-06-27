package xadrez;

public class Dama extends Peca {

    public Dama(String cor) {
        super(cor);
        this.representacao = 'Q';
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);
        return (linhaO == linhaD || colunaO == colunaD || difLinha == difColuna);
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
