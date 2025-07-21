package xadrez;

public class Peao extends Peca {

    public Peao(String cor) {
        super(cor);
        // define a representação da peça conforme a cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'P';
        } else {
            this.representacao = 'p';
        }
    }

    @Override
    public String desenho() {
        // retorna dois caracteres para representar o peão visualmente
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        // impede que a peça "se mova para o mesmo lugar"
        if (linhaO == linhaD && colunaO == colunaD) return false;

        // define a direção do movimento (branco sobe, preto desce)
        int dir = "branco".equalsIgnoreCase(this.cor) ? 1 : -1;

        int deltaLinha = linhaD - linhaO;
        int deltaColuna = colunaD - colunaO;

        // movimento vertical simples (1 casa à frente)
        if (deltaColuna == 0) {
            if (deltaLinha == dir) {
                return true;
            }
            // verifica se é o primeiro movimento do peão (2 casas)
            boolean primeiroMovimentoBranco = (linhaO == 2 && dir ==  1);
            boolean primeiroMovimentoPreto   = (linhaO == 7 && dir == -1);
            return (primeiroMovimentoBranco || primeiroMovimentoPreto) && deltaLinha == 2 * dir;
        }

        // movimento de captura (diagonal)
        if (Math.abs(deltaColuna) == 1 && deltaLinha == dir) {
            return true;
        }

        return false;
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        // retorna a string com o caminho apenas se o movimento for válido
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "" + linhaO + colunaO + linhaD + colunaD;
        } else {
            return "";
        }
    }

}
