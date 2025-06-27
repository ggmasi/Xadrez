package xadrez;

/**
 *
 * @author ggmasi
 */

import java.util.ArrayList;
import java.util.List;

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
        
        Cavalo cavalo1b = new Cavalo("branco");
        
        casas[0][1].setPeca(cavalo1b);
        
        Bispo bispo1b = new Bispo("branco");
        
        casas[0][2].setPeca(bispo1b);
        
        Dama rainhab = new Dama("branco");
        
        casas[0][3].setPeca(rainhab);
        
        Rei reib = new Rei("branco");
        
        casas[0][4].setPeca(reib);
        
        Bispo bispo2b = new Bispo("branco");
        
        casas[0][5].setPeca(bispo2b);
        
        Cavalo cavalo2b = new Cavalo("branco");
        
        casas[0][6].setPeca(cavalo2b);
        
        Torre torre2b = new Torre("branco");
        
        for(int i = 0; i < 7; i++){
            Peao peaob = new Peao("branco");
            casas[1][i].setPeca(peaob);
        }
        
        // inicia lado preto
        
        Torre torre1p = new Torre("preto");
        
        casas[7][0].setPeca(torre1p);
        
        Cavalo cavalo1p = new Cavalo("preto");
        
        casas[7][1].setPeca(cavalo1p);
        
        Bispo bispo1p = new Bispo("preto");
        
        casas[7][2].setPeca(bispo1p);
        
        Dama rainhap = new Dama("preto");
        
        casas[7][3].setPeca(rainhap);
        
        Rei reip = new Rei("preto");
        
        casas[7][4].setPeca(reip);
        
        Bispo bispo2p = new Bispo("preto");
        
        casas[7][5].setPeca(bispo2p);
        
        Cavalo cavalo2p = new Cavalo("preto");
        
        casas[7][6].setPeca(cavalo2p);
        
        Torre torre2p = new Torre("preto");
        
        for(int i = 0; i < 7; i++){
            Peao peaop = new Peao("preto");
            casas[6][i].setPeca(peaop);
        }  
    } // tabuleiro iniciado
    
    
   /* public boolean setPeca(int linha, char coluna, Peca peca){
        if(!noLimite(linha, coluna)) return false;
        
        casas[linha][coluna - 'a'].setPeca(peca); 
        
        return true;    
    }*/

    public List<Casa> getCasasComPecaDaCor(String cor) {
    List<Casa> casasFiltradas = new ArrayList<>();

    for (int linha = 0; linha < 8; linha++) {
        for (int coluna = 0; coluna < 8; coluna++) {
            Casa casa = casas[linha][coluna];

            if (casa.temPeca() && casa.getPeca().getCor().equalsIgnoreCase(cor)) {
                casasFiltradas.add(casa);
            }
        }
    }

    return casasFiltradas;
}






}