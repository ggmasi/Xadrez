package xadrez;

public class Caminho {
    private final Tabuleiro tab;
    private final String rota;
    private boolean situacao;

    public Caminho(Tabuleiro tabuleiro, String cam) {
        this.tab = tabuleiro;
        this.rota = cam;
    }

    public boolean estaLivre() {
        for (int i = 2; i < rota.length() - 2; i += 2) {
            char letra = rota.charAt(i);
            char numeroChar = rota.charAt(i + 1);
            int numero = Character.getNumericValue(numeroChar); 
            if (tab.getCasa(numero, letra).temPeca()) {
                situacao = false;
                return false;
            }
        }
        situacao = true;
        return true;
    }

    public Casa casaInicial() {
        char letra = rota.charAt(0);
        char numeroChar = rota.charAt(1);
        int numero = Character.getNumericValue(numeroChar);

        return tab.getCasa(numero, letra);
    }

    public Casa casaFinal() {
        int ultimoIndice = rota.length() - 1;
        char letra = rota.charAt(ultimoIndice - 1);
        char numeroChar = rota.charAt(ultimoIndice);
        int numero = Character.getNumericValue(numeroChar);

        return tab.getCasa(numero, letra);
    }
    
    public boolean getSituacao()
    {
        return situacao;
    }
}
