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


        int dir = "branco".equalsIgnoreCase(this.cor) ? 1 : -1;


        int deltaLinha = linhaD - linhaO;
        int deltaColuna = colunaD - colunaO;


        if (deltaColuna == 0) {

            if (deltaLinha == dir) {
                return true;
            }
            boolean primeiroMovimentoBranco = (linhaO == 2 && dir ==  1);
            boolean primeiroMovimentoPreto   = (linhaO == 7 && dir == -1);
            return (primeiroMovimentoBranco || primeiroMovimentoPreto) && deltaLinha == 2 * dir;
        }

        if (Math.abs(deltaColuna) == 1 && deltaLinha == dir) {
            return true;
        }

        return false;
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
