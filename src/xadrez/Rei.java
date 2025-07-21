package xadrez;

public class Rei extends Peca {

    public Rei(String cor) {
        super(cor);
        // define a representação do rei conforme a cor
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'R';
        } else {
            this.representacao = 'r';
        }
    }

    @Override
    public String desenho() {
        // representa o rei com dois caracteres
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }

    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        // impede que o rei fique parado
        if (linhaO == linhaD && colunaO == colunaD) return false;
        // o rei só pode andar 1 casa em qualquer direção
        return !((Math.abs(linhaO - linhaD) > 1) || (Math.abs(colunaO - colunaD) > 1));
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        // o caminho é direto, já que o rei anda apenas 1 casa
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "" + linhaO + colunaO + linhaD + colunaD;
        } else {
            return "";
        }
    }

}
