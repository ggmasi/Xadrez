package xadrez;

/**
 *
 * @author ggmasi
 */
public class Tabuleiro {
    
    private Casa[][] casas;
    
    public Tabuleiro(){
        casas = new Casa[8][8];
        
        for(int linha = 0; linha < 8; linha++){
            for(int coluna = 0; coluna < 8; coluna++){
                String cor = (coluna + linha) % 2 == 0? "branco": "preto";
                char letraColuna = (char) ('a' + coluna);
                casas[linha][coluna] = new Casa(cor, linha, letraColuna);
            }      
        }
    }
    
   public boolean noLimite(int linha, char coluna){
       return linha >= 1 && linha <= 8 && coluna >= 'a' && coluna <= 'h';
   }
    
   public Casa getCasa(int linha, char coluna){
       if(noLimite(linha,coluna))
           return casas[linha - 1][coluna - 'a'];
        return null; 
   }

    public String desenho() {
        StringBuilder sb = new StringBuilder();

        for (int linha = 7; linha >= 0; linha--) {
            sb.append(linha + 1).append(" "); // número da linha

            for (int coluna = 0; coluna < 8; coluna++) {
                sb.append(casas[linha][coluna].toString()).append(" ");
            }

            sb.append("\n");
        }

            sb.append("   a  b  c  d  e  f  g  h\n");
            return sb.toString();
    }

    public void inicializar(){
        
        // inicia lado branco
        
        Torre torre1b = new Torre("branco");
        
        casas[0][0].setPeca(torre1b);
        
        
        
        
        
        
        
    }
















}

