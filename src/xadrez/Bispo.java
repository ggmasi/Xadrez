package xadrez;

public class Bispo extends Peca {

    public Bispo(String cor) {
        super(cor);
        this.representacao = 'B';
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);
        return difLinha == difColuna;
    }

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

        while (linha != linhaD || coluna != colunaD) {
            caminho += "" + linha + coluna;
            linha += passoLinha;
            coluna += passoColuna;
        }

        caminho += "" + linhaD + colunaD;
        return caminho;
    }
}
