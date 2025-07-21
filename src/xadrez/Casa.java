package xadrez;

/**
 *
 * @author ggmasi
 */
public class Casa implements Cloneable {
    
    private final String cor;      // cor da casa (branca ou preta)
    private final int linha;       // número da linha (1 a 8)
    private final char coluna;     // letra da coluna ('a' a 'h')
    private Peca peca;             // peça que está na casa, se houver
    private boolean temPeca;       // flag para indicar se tem peça na casa
    
    public Casa(String cor, int linha, char coluna){
        this.cor = cor;
        this.linha = linha;
        this.coluna = coluna;
        temPeca = false; // ao criar a casa, não deve ter peça inicialmente
    }
    
    @Override
    public Casa clone() throws CloneNotSupportedException {
        try {
            Casa copia = (Casa) super.clone();
            // clona a peça para evitar referência compartilhada
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
        // se tiver peça, retorna o desenho dela; se não, retorna "--"
        if (peca != null) {
            return peca.desenho(); 
        } else {
            return "--";
        }
    }
    
    public boolean setPeca(Peca peca){
        // tenta colocar uma peça na casa, só se não houver peça ainda
        if(!temPeca){
            this.peca = peca;
            temPeca = true;
            return true;
        }
        return false; 
    }
    
    public Peca removePeca(){ 
        // remove a peça da casa, se houver, e retorna ela
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
        // retorna a peça se houver, senão null
        if(temPeca)
            return this.peca;
        return null;       
    }
    
    public boolean temPeca(){
        return temPeca;
    }
}
