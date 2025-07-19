package xadrez;

/**
 *
 * @author ggmasi
 */
public class Casa implements Cloneable{
    
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
    
    @Override
    public Casa clone() throws CloneNotSupportedException {
        try {
            Casa copia = (Casa) super.clone();
            if (copia.peca != null) {
                copia.peca = copia.peca.clone();
            }
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String toString() {
        if (peca != null) {
            return peca.desenho(); 
        } else {
            return "--";
        }
    }
    
    public boolean setPeca(Peca peca){
        if(!temPeca){
            this.peca = peca;
            temPeca = true;
            return true;
        }
        return false; 
    }
    
    public Peca removePeca(){ 
        if(temPeca){
            Peca removida = peca;
            peca = null; 
            temPeca = false;
            return removida;
        }
        return null; 
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
