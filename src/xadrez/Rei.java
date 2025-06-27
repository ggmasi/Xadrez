package xadrez;

public class Rei extends Peca {
    
    public Rei(String cor) {
        super(cor);
        this.representacao = 'K';
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        if ((Math.abs(linhaO - linhaD) > 1) || (Math.abs(colunaO - colunaD) > 1)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "" + linhaO + colunaO + linhaD + colunaD;
        } else {
            return "";
        }
    }
    
}
