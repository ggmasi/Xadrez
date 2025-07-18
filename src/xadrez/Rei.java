package xadrez;

public class Rei extends Peca {
    
    public Rei(String cor) {
        super(cor);
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'R';
        } else {
            this.representacao = 'r';
        }
    }
    
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        return !((Math.abs(linhaO - linhaD) > 1) || (Math.abs(colunaO - colunaD) > 1));
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
