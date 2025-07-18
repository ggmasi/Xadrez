package xadrez;

public class Peao extends Peca {
    
    public Peao(String cor) {
        super(cor);
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'P';
        } else {
            this.representacao = 'p';
        }
    }
    
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); // Using two characters for empty spaces (e.g., "PP")
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        if((linhaO == 2 && cor.equals("branco")) || (linhaO == 7 && cor.equals("preto"))){
            return (Math.abs(linhaO - linhaD) <= 2);
        }
        return (Math.abs(linhaO - linhaD) <= 1);
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
