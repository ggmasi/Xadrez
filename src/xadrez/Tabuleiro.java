package xadrez;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro implements Cloneable {
    
    private Casa[][] casas;  // matriz 8x8 representando as casas do tabuleiro
    
    public Tabuleiro(){
        casas = new Casa[8][8];
        
        // inicializa as casas com as cores alternadas e posições corretas
        for (int i = 0; i < 8; i++) { 
            for (int j = 0; j < 8; j++) { 
                int linhaTabuleiro = i + 1; 
                char colunaTabuleiro = (char) ('a' + j); 
                
                String cor = ((i + j) % 2 == 0) ? "branca" : "preta";
                casas[i][j] = new Casa(cor, linhaTabuleiro, colunaTabuleiro);
            }
        }
    }
    
    @Override
    public Tabuleiro clone() throws CloneNotSupportedException {
        try {
            Tabuleiro copia = (Tabuleiro) super.clone();

            // clona a matriz de casas para evitar referência compartilhada
            copia.casas = new Casa[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    copia.casas[i][j] = this.casas[i][j].clone();
                }
            }

            return copia;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean noLimite(int linha, char coluna){
        // verifica se uma posição está dentro dos limites do tabuleiro
        return linha >= 1 && linha <= 8 && coluna >= 'a' && coluna <= 'h';
    }
    
    public Casa getCasa(int linha, char coluna){
        // retorna a casa correspondente, se estiver no limite; senão null
        if(noLimite(linha,coluna))
            return casas[linha - 1][coluna - 'a'];
        return null; 
    }

    public String desenho() {
        // monta uma string com o desenho do tabuleiro e suas peças para exibição
        StringBuilder sb = new StringBuilder();

        for (int linha = 7; linha >= 0; linha--) {
            sb.append(linha + 1).append(" ");

            for (int coluna = 0; coluna < 8; coluna++) {
                sb.append(casas[linha][coluna].toString()).append(" ");
            }

            sb.append("\n");
        }

        sb.append("   a  b  c  d  e  f  g  h\n");
        return sb.toString();
    }

    public void inicializar(){
        // coloca as peças na posição inicial padrão para ambos os lados

        // lado branco (linha 1 e 2)
        casas[0][0].setPeca(new Torre("branco"));
        casas[0][1].setPeca(new Cavalo("branco"));
        casas[0][2].setPeca(new Bispo("branco"));
        casas[0][3].setPeca(new Dama("branco"));
        casas[0][4].setPeca(new Rei("branco"));
        casas[0][5].setPeca(new Bispo("branco"));
        casas[0][6].setPeca(new Cavalo("branco"));
        casas[0][7].setPeca(new Torre("branco"));
        
        for(int i = 0; i < 8; i++){
            casas[1][i].setPeca(new Peao("branco"));
        }
        
        // lado preto (linha 8 e 7)
        casas[7][0].setPeca(new Torre("preto"));
        casas[7][1].setPeca(new Cavalo("preto"));
        casas[7][2].setPeca(new Bispo("preto"));
        casas[7][3].setPeca(new Dama("preto"));
        casas[7][4].setPeca(new Rei("preto"));
        casas[7][5].setPeca(new Bispo("preto"));
        casas[7][6].setPeca(new Cavalo("preto"));
        casas[7][7].setPeca(new Torre("preto"));
        
        for(int i = 0; i < 8; i++){
            casas[6][i].setPeca(new Peao("preto"));
        }  
    } 
    
    public List<Casa> getCasasComPecaDaCor(String cor) {
        // retorna a lista de casas que possuem peças da cor informada
        List<Casa> casasFiltradas = new ArrayList<>();

        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Casa casa = casas[linha][coluna];
                if(casa.getPeca() != null){
                    if (casa.temPeca() && casa.getPeca().getCor().equalsIgnoreCase(cor)) {
                        casasFiltradas.add(casa);
                    }
                }
            }
        }
        return casasFiltradas;
    }

    public Casa encontraRei(String corRei){
        // procura e retorna a casa onde está o rei da cor especificada
        for(int linha = 0; linha < 8; linha++){
            for(int coluna = 0; coluna < 8; coluna++){
                Casa casa = casas[linha][coluna];
                if(casa.getPeca() != null) { 
                    // verifica se é o rei (representação 'R' ou 'r')
                    if(casa.getPeca().getRepresentacao() == 'R' || casa.getPeca().getRepresentacao() == 'r'){
                        if(corRei.equals(casa.getPeca().getCor())){
                            return casa;
                        }
                    }
                }
            }
        }
        return null;
    }
}
