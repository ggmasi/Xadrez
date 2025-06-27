package xadrez;

public abstract class Peca {
    protected String cor;
    protected char representacao;
    
    public Peca (String cor) {
        this.cor = cor;
    }
    
    public String desenho(){
        char corletra;
        if(this.cor.equals("preto"))
            corletra = 'p';
        else
            corletra = 'b';
        return "" + this.representacao + corletra;
    }
    
    public String getCor(){
        return this.cor;
    }
    
    
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);
    
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
    
}
