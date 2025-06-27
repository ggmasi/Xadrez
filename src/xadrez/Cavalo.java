package xadrez;

public class Cavalo extends Peca {
    
    public Cavalo(String cor) {
        super(cor);
        this.representacao = 'N';
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);

        if ((difLinha == 2 && difColuna == 1) || (difLinha == 1 && difColuna == 2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            String caminho = "" + linhaO + colunaO;

            if (Math.abs(linhaD - linhaO) == 2) {
                caminho += "" + (linhaO + ((linhaD - linhaO) / 2)) + colunaO;
                caminho += "" + linhaD + (char)(colunaO + (colunaD - colunaO));
            } else {
                caminho += "" + linhaO + (char)(colunaO + ((colunaD - colunaO) / 2));
                caminho += "" + (linhaO + (linhaD - linhaO)) + colunaD;
            }

            caminho += "" + linhaD + colunaD;
            return caminho;
        }

        return "";
    }

}
