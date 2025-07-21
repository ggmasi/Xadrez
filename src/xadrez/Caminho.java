package xadrez;

public class Caminho {
    private final Tabuleiro tab;  // Referência ao tabuleiro onde o movimento ocorre
    private final String rota;    // String representando o caminho da peça (ex: "a2a4")
    private boolean situacao;     // Indica se o caminho está livre (true) ou não (false)

    public Caminho(Tabuleiro tabuleiro, String cam) {
        this.tab = tabuleiro;
        this.rota = cam;
        this.situacao = false; // Inicialmente, considera-se caminho não livre
    }

    /**
     * Verifica se o caminho da peça está livre (sem peças bloqueando entre origem e destino).
     * Importante: ignora a casa de origem e destino, verifica só casas intermediárias.
     * @return true se estiver livre, false caso contrário.
     */
    public boolean estaLivre() {
        // Se rota vazia, caminho considerado livre
        if(rota.isEmpty()){
            situacao = true;
            return true;
        }
        // Percorre o caminho, pulando origem e destino (por isso começa em i=2 e termina antes do final)
        for (int i = 2; i < rota.length() - 2; i += 2) {
            char letra = rota.charAt(i+1);  // coluna da casa no caminho
            char numeroChar = rota.charAt(i); // linha como caractere
            int numero = Character.getNumericValue(numeroChar); // linha convertida para int

            // Verifica se casa está dentro do tabuleiro
            if(!tab.noLimite(numero, letra)){
                situacao = false;
                return false;
            }
            Casa casaNoCaminho = tab.getCasa(numero, letra);
            if (casaNoCaminho == null) {
                situacao = false;
                return false;
            }
            
            // Se existe peça no caminho, então caminho não está livre
            if(casaNoCaminho.temPeca()){
                situacao = false;
                return false;
            }
        }
        // Se passou por todas as casas intermediárias sem bloqueios, caminho está livre
        situacao = true;
        return true;
    }

    /**
     * Retorna a casa inicial do caminho (origem do movimento)
     */
    public Casa casaInicial() {
        if (rota.length() < 2) return null;
        char letra = rota.charAt(0);
        char numeroChar = rota.charAt(1);
        int numero = Character.getNumericValue(numeroChar);
        if (!tab.noLimite(numero, letra)) return null; 
        return tab.getCasa(numero, letra);
    }

    /**
     * Retorna a casa final do caminho (destino do movimento)
     */
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
