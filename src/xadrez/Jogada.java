package xadrez;

import java.util.List;
import java.util.Objects;

public class Jogada {
    private final Jogador jogador;      // Jogador que executa a jogada
    private final Casa origem;           // Casa de onde a peça sai
    private final Casa destino;          // Casa para onde a peça vai
    private final String caminhoSeq;     // Sequência que representa o caminho da peça
    private final Peca capturada;        // Peça capturada, caso haja
    private final Peca pecaMovida;       // Peça que está sendo movida

    public Jogada(Jogador jogador, Casa origem, Casa destino, Peca capturada) {
        this.jogador = jogador;
        this.origem = origem;
        this.destino = destino;
        this.capturada = capturada;
        this.pecaMovida = origem.getPeca();
        // Gera o caminho da peça com base nas coordenadas de origem e destino
        Peca peca = this.pecaMovida;
        this.caminhoSeq = (peca != null) ? peca.caminho(origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna()) : "";
    }
    
    /**
     * Verifica se a jogada é válida:
     * - Origem e destino dentro do tabuleiro
     * - Origem possui peça da cor do jogador
     * - Movimento é válido para a peça
     * - Caminho está livre (sem peças bloqueando)
     * - Destino não possui peça da mesma cor
     * - Jogada não deixa o próprio rei em xeque
     */
    public boolean ehValida(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        
        if (!tabuleiro.noLimite(origem.getLinha(), origem.getColuna()) || !tabuleiro.noLimite(destino.getLinha(), destino.getColuna())) {
            return false;
        }
        
        // Origem deve ter peça do jogador atual
        if (!origem.temPeca() || !Objects.equals(origem.getPeca().getCor(), jogador.getCor())) {
            return false;
        }

        // Movimento válido para a peça
        if (!origem.getPeca().movimentoValido(origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna())) {
            return false;
        }

        // Verifica se caminho está livre (exceto para cavalo)
        Caminho cam = new Caminho(tabuleiro, caminhoSeq);
        if(origem.getPeca().getRepresentacao() != 'C' && origem.getPeca().getRepresentacao() != 'c'){
            if (!cam.estaLivre()) {
                return false;
            }
        }
        
        // Destino não pode ter peça da mesma cor
        if (destino.temPeca() && Objects.equals(destino.getPeca().getCor(), jogador.getCor())) {
            return false;
        }

        // Jogada não pode deixar o próprio rei em xeque
        if (causaXequeProprio(tabuleiro)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Verifica se a jogada causa xeque no adversário.
     */
    public boolean ehXeque(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        Tabuleiro tAposMovimento = simularMovimento(tabuleiro);
        String corOponente = oposto(jogador.getCor());
        return detectaXeque(tAposMovimento, corOponente);
    }
    
    /**
     * Verifica se a jogada resulta em xeque-mate.
     */
    public boolean ehXequeMate(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        String corReiEmXeque = oposto(this.jogador.getCor()); 

        // Se não está em xeque, não é xeque mate
        if (!detectaXeque(tabuleiro, corReiEmXeque)) { 
            return false; 
        }

        // Para cada peça adversária, tenta todos os movimentos possíveis
        List<Casa> pecasAdversarias = tabuleiro.getCasasComPecaDaCor(corReiEmXeque);
        for (Casa c : pecasAdversarias) {
            Peca peca = c.getPeca();
            if (peca == null) continue;

            List<Casa> movimentosPossiveisDaPeca = peca.movimentosPossiveis(tabuleiro, c.getLinha(), c.getColuna());
            
            for (Casa dest : movimentosPossiveisDaPeca) {
                // Cria uma jogada hipotética para cada movimento possível
                Jogada hipoteticaJogada = new Jogada(
                    new Jogador("temp", corReiEmXeque),
                    c,                                 
                    dest,                             
                    dest.temPeca() ? dest.getPeca() : null 
                );

                // Se a jogada hipotética for válida e remove o xeque, então não é xeque mate
                if (hipoteticaJogada.ehValida(tabuleiro)) {
                    Tabuleiro tabuleiroAposHipotetica = hipoteticaJogada.simularMovimento(tabuleiro);
                    
                    if (!detectaXeque(tabuleiroAposHipotetica, corReiEmXeque)) {
                        return false; 
                    }
                }
            }
        }
        return true; // Se nenhuma jogada remove o xeque, é xeque mate
    }
    
    /**
     * Gera o código da jogada no formato "2a4a" (linha e coluna origem + destino)
     */
    public String getCodigo(){
        return String.format("%d%s%d%s", origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna());
    }
    
    /**
     * Simula o movimento no tabuleiro clonando o estado atual,
     * para análise sem modificar o tabuleiro real.
     */
    private Tabuleiro simularMovimento(Tabuleiro tab) throws CloneNotSupportedException {
        Tabuleiro copia = tab.clone();    

        Casa origemNaCopia = copia.getCasa(origem.getLinha(), origem.getColuna());
        if (origemNaCopia == null) {
            System.err.println("ERRO: simularMovimento - Não foi possível obter a casa de origem na cópia do tabuleiro. Coordenadas: " + origem.getColuna() + origem.getLinha());
            throw new RuntimeException("Falha crítica na simulação de movimento: Casa de origem nula na cópia do tabuleiro.");
        }

        Casa destinoNaCopia = copia.getCasa(destino.getLinha(), destino.getColuna());
        if (destinoNaCopia == null) {
            System.err.println("ERRO: simularMovimento - Não foi possível obter a casa de destino na cópia do tabuleiro. Coordenadas: " + destino.getColuna() + destino.getLinha());
            throw new RuntimeException("Falha crítica na simulação de movimento: Casa de destino nula na cópia do tabuleiro.");
        }

        // Remove peça da origem na cópia
        origemNaCopia.removePeca(); 
        if (this.pecaMovida != null) {
            // Remove peça do destino na cópia (captura) e coloca a peça movida
            destinoNaCopia.removePeca();
            destinoNaCopia.setPeca(this.pecaMovida.clone()); 
        } else {
             System.err.println("AVISO: Tentando simular movimento sem peça na origem da jogada.");
        }

        return copia;
    }
    
    /**
     * Verifica se a jogada deixa o próprio rei em xeque (não permitido).
     */
    private boolean causaXequeProprio(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        Tabuleiro tAposMovimento = simularMovimento(tabuleiro);
        return detectaXeque(tAposMovimento, jogador.getCor()); 
    }
    
    /**
     * Detecta se um rei de determinada cor está em xeque no tabuleiro dado.
     * Verifica se alguma peça adversária pode atacar o rei.
     */
    private boolean detectaXeque(Tabuleiro tab, String corRei) {
        Casa posRei = tab.encontraRei(corRei);
        if (posRei == null) {
            return false; // Rei não encontrado, teoricamente não está em xeque
        }

        String corAtacante = oposto(corRei);
        List<Casa> atacantes = tab.getCasasComPecaDaCor(corAtacante);

        // Para cada peça adversária, verifica se ela pode atacar o rei
        for (Casa c : atacantes) {
            Peca pecaAtacante = c.getPeca();
            if (pecaAtacante == null) continue;

            List<Casa> movimentosDoAtacante = pecaAtacante.movimentosPossiveis(tab, c.getLinha(), c.getColuna());
            
            for (Casa possivelCasaDeAtaque : movimentosDoAtacante) {
                // Se alguma das casas possíveis da peça adversária é a casa do rei, verifica o caminho
                if (Objects.equals(possivelCasaDeAtaque.getLinha(), posRei.getLinha()) &&
                    Objects.equals(possivelCasaDeAtaque.getColuna(), posRei.getColuna())) {
                    
                    String caminhoParaRei = pecaAtacante.caminho(c.getLinha(), c.getColuna(), posRei.getLinha(), posRei.getColuna());
                    Caminho camParaRei = new Caminho(tab, caminhoParaRei);
                    if (camParaRei.estaLivre()) {
                        return true; // Rei está em xeque
                    }
                }
            }
        }
        return false; // Rei não está em xeque
    }
    
    /**
     * Retorna a cor oposta à dada.
     */
    private String oposto(String cor) {
        return "branco".equalsIgnoreCase(cor) ? "preto" : "branco";
    }
    
    // Getters para acessar atributos da jogada
    public Jogador getJogador()      { return jogador; }
    public Casa    getOrigem()       { return origem; }
    public Casa    getDestino()      { return destino; }
    public String  getCaminhoSeq()   { return caminhoSeq; }
    public Peca    getCapturada()    { return capturada; }
}
