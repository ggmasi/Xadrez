package xadrez;
import java.util.ArrayList;
import java.util.List;    

public abstract class Peca implements Cloneable{
    protected String cor;
    protected char representacao;
    
    public Peca (String cor) {
        this.cor = cor;
    }
    
    @Override
    public Peca clone() throws CloneNotSupportedException {
        try {   
            return (Peca) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
  
    
    public String getCor(){
        return this.cor;
    }
    
    public char getRepresentacao(){
        return representacao;
    }
    
    public List<Casa> movimentosPossiveis(Tabuleiro tabuleiro, int linhaAtual, char colunaAtual) {
    List<Casa> validas = new ArrayList<>();

    for (int linha = 1; linha <= 8; linha++) {
        for (char coluna = 'a'; coluna <= 'h'; coluna++) {
            if (linha == linhaAtual && coluna == colunaAtual)
                continue;

            if (this.movimentoValido(linhaAtual, colunaAtual, linha, coluna)) {
                Casa destino = tabuleiro.getCasa(linha, coluna);

                if (destino == null) continue;

                if(!destino.temPeca()){
                    validas.add(destino);
                }else if(!destino.getPeca().getCor().equals(this.cor)){
                    validas.add(destino);
                }
            }
        }
    }

    return validas;
}

    public abstract String desenho();
    
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);
    
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
    
    
    
}
