package xadrez;

/**
 *
 * @author ggmasi
 */
public class Casa {
    
    private final String cor;
    private final int linha;
    private final char coluna;
    private Peca peca;
    private boolean temPeca;
    
    public Casa(String cor, int linha, char coluna){
        this.cor = cor;
        this.linha = linha;
        this.coluna = coluna;
        temPeca = false; // ao criar uma casa nao deve ter peca
    }
    
    public boolean setPeca(Peca peca){
        if(!temPeca){
            this.peca = peca;
            temPeca = true;
            return true;
        }
        return false; 
    }
    
    public Peca removePeca(){ //remove a peça da casa e retorna ela
        if(temPeca){
            Peca removida = peca;
            peca = null; 
            temPeca = false;
            return removida;
        }
        return null; // caso nao tenha, retorna null
    } 

    public String getCor(){
        return cor;
    }

    public int getLinha(){
        return linha;
    }

    public char getColuna(){
        return coluna;
    }

    public Peca getPeca(){
        if(temPeca)
            return this.peca;
        return null;       
    }
    
    public boolean temPeca(){
        return temPeca;
    }

}
