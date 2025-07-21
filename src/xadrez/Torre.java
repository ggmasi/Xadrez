package xadrez;

public class Torre extends Peca {

    public Torre(String cor) {
        super(cor);
        // define a representação da torre conforme a cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'T';
        } else {
            this.representacao = 't';
        }
    }

    @Override
    public String desenho() {
        // representa a torre com dois caracteres
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        // impede que a torre fique parada
        if (linhaO == linhaD && colunaO == colunaD) return false;
        // movimento válido apenas em linha reta
        return (linhaO == linhaD || colunaO == colunaD);
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        // se o movimento não for válido, retorna caminho vazio
        if (!movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "";
        }

        String caminho = "" + linhaO + colunaO;

        int passoLinha = 0;
        int passoColuna = 0;

        // define o passo do movimento em linha e coluna
        if (linhaD > linhaO) passoLinha = 1;
        else if (linhaD < linhaO) passoLinha = -1;

        if (colunaD > colunaO) passoColuna = 1;
        else if (colunaD < colunaO) passoColuna = -1;

        // percorre o caminho até o destino
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
