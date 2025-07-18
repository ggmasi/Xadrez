package xadrez;

public class Cavalo extends Peca {
    
    public Cavalo(String cor) {
        super(cor);
        if (cor.equalsIgnoreCase("branco")) {
            this.representacao = 'C';
        } else {
            this.representacao = 'c';
        }
    }
    
    @Override
    public String desenho() {
        return String.valueOf(this.representacao) + String.valueOf(this.representacao); 
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (linhaO == linhaD && colunaO == colunaD) return false;
        int difLinha = Math.abs(linhaO - linhaD);
        int difColuna = Math.abs(colunaO - colunaD);

        return (difLinha == 2 && difColuna == 1) || (difLinha == 1 && difColuna == 2);
    }

    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        return "";
    }

}
