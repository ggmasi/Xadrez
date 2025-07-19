package xadrez;

public class Caminho {
    private final Tabuleiro tab;
    private final String rota;
    private boolean situacao;

    public Caminho(Tabuleiro tabuleiro, String cam) {
        this.tab = tabuleiro;
        this.rota = cam;
        this.situacao = false;
    }

    public boolean estaLivre() {
        if(rota.isEmpty()){
            situacao = true;
            return true;
        }
        for (int i = 2; i < rota.length() - 2; i += 2) {
            char letra = rota.charAt(i+1);  
            char numeroChar = rota.charAt(i);
            int numero = Character.getNumericValue(numeroChar); 
            if(!tab.noLimite(numero, letra)){
                situacao = false;
                return false;
            }
            Casa casaNoCaminho = tab.getCasa(numero, letra);
            if (casaNoCaminho == null) {
                situacao = false;
                return false;
            }
            
            if(casaNoCaminho.temPeca()){
                situacao = false;
                return false;
            }
        }
        situacao = true;
        return true;
    }

    public Casa casaInicial() {
        if (rota.length() < 2) return null;
        char letra = rota.charAt(0);
        char numeroChar = rota.charAt(1);
        int numero = Character.getNumericValue(numeroChar);
        if (!tab.noLimite(numero, letra)) return null; 
        return tab.getCasa(numero, letra);
    }

    public Casa casaFinal() {
        if (rota.length() < 2) return null; 
        int ultimoIndice = rota.length() - 1;
        char letra = rota.charAt(ultimoIndice - 1);
        char numeroChar = rota.charAt(ultimoIndice);
        int numero = Character.getNumericValue(numeroChar);
        if (!tab.noLimite(numero, letra)) return null; 
        return tab.getCasa(numero, letra);
    }
    
    public boolean getSituacao()
    {
        return situacao;
    }
    
    public String getRota(){
        return rota;
    }
}
