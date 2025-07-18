package xadrez;

import java.util.List;
import java.util.Objects;

public class Jogada {
    private final Jogador jogador;
    private final Casa origem;
    private final Casa destino;
    private final String caminhoSeq;
    private final Peca capturada;
    private final Peca pecaMovida;

    public Jogada(Jogador jogador, Casa origem, Casa destino, Peca capturada) {
        this.jogador = jogador;
        this.origem = origem;
        this.destino = destino;
        this.capturada = capturada;
        this.pecaMovida = origem.getPeca();
        Peca peca = this.pecaMovida;
        this.caminhoSeq = (peca != null) ? peca.caminho(origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna()) : "";
    }
    
    public boolean ehValida(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        
        if (!tabuleiro.noLimite(origem.getLinha(), origem.getColuna()) || !tabuleiro.noLimite(destino.getLinha(), destino.getColuna())) {
            return false;
        }
        
        
        if (!origem.temPeca() || !Objects.equals(origem.getPeca().getCor(), jogador.getCor())) {
            return false;
        }

       
        if (!origem.getPeca().movimentoValido(origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna())) {
            return false;
        }

        
        Caminho cam = new Caminho(tabuleiro, caminhoSeq);
        if(origem.getPeca().getRepresentacao() != 'C' && origem.getPeca().getRepresentacao() != 'c'){
            if (!cam.estaLivre()) {
                System.out.println(caminhoSeq);
                System.out.println(cam.getRota());
                return false;
            }
        }
        
        if (destino.temPeca() && Objects.equals(destino.getPeca().getCor(), jogador.getCor())) {
            return false;
        }

        if (causaXequeProprio(tabuleiro)) {
            return false;
        }
        
        return true;
    }
    

    public boolean ehXeque(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        Tabuleiro tAposMovimento = simularMovimento(tabuleiro);
        String corOponente = oposto(jogador.getCor());
        return detectaXeque(tAposMovimento, corOponente);
    }
    
    public boolean ehXequeMate(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        String corReiEmXeque = this.jogador.getCor(); 

        if (!detectaXeque(tabuleiro, corReiEmXeque)) { 
            return false; 
        }

        List<Casa> pecasAdversarias = tabuleiro.getCasasComPecaDaCor(corReiEmXeque);
        for (Casa c : pecasAdversarias) {
            Peca peca = c.getPeca();
            if (peca == null) continue;

            List<Casa> movimentosPossiveisDaPeca = peca.movimentosPossiveis(tabuleiro, c.getLinha(), c.getColuna());
            
            for (Casa dest : movimentosPossiveisDaPeca) {
                Jogada hipoteticaJogada = new Jogada(
                    new Jogador("temp", corReiEmXeque), // Cria um Jogador temporário para a jogada hipotética
                    c,                                 // Casa de origem da peça adversária
                    dest,                              // Casa de destino da peça adversária
                    dest.temPeca() ? dest.getPeca() : null // Peça que seria capturada (pode ser null)
                );

                if (hipoteticaJogada.ehValida(tabuleiro)) {
                    Tabuleiro tabuleiroAposHipotetica = hipoteticaJogada.simularMovimento(tabuleiro);
                    
                    if (!detectaXeque(tabuleiroAposHipotetica, corReiEmXeque)) {
                        return false; 
                    }
                }
            }
        }
        return true; 
    }
    
    public String getCodigo(){
        return String.format("%s%d%s%d", origem.getColuna(), origem.getLinha(), destino.getColuna(), destino.getLinha());
    }
    
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

        origemNaCopia.removePeca(); 
        if (this.pecaMovida != null) {
            destinoNaCopia.removePeca();
            destinoNaCopia.setPeca(this.pecaMovida.clone()); 
        } else {
             System.err.println("AVISO: Tentando simular movimento sem peça na origem da jogada.");
        }

        return copia;
    }
    
    private boolean causaXequeProprio(Tabuleiro tabuleiro) throws CloneNotSupportedException {
        Tabuleiro tAposMovimento = simularMovimento(tabuleiro);
        return detectaXeque(tAposMovimento, jogador.getCor()); 
    }
    
    private boolean detectaXeque(Tabuleiro tab, String corRei) {
        Casa posRei = tab.encontraRei(corRei);
        if (posRei == null) {
            return false; 
        }

        String corAtacante = oposto(corRei);
        List<Casa> atacantes = tab.getCasasComPecaDaCor(corAtacante);

        for (Casa c : atacantes) {
            Peca pecaAtacante = c.getPeca();
            if (pecaAtacante == null) continue;

            List<Casa> movimentosDoAtacante = pecaAtacante.movimentosPossiveis(tab, c.getLinha(), c.getColuna());
            
            for (Casa possivelCasaDeAtaque : movimentosDoAtacante) {
                if (Objects.equals(possivelCasaDeAtaque.getLinha(), posRei.getLinha()) &&
                    Objects.equals(possivelCasaDeAtaque.getColuna(), posRei.getColuna())) {
                    
                    String caminhoParaRei = pecaAtacante.caminho(c.getLinha(), c.getColuna(), posRei.getLinha(), posRei.getColuna());
                    Caminho camParaRei = new Caminho(tab, caminhoParaRei);
                    if (camParaRei.estaLivre()) {
                        return true; 
                    }
                }
            }
        }
        return false; 
    }
    
    private String oposto(String cor) {
        return "branco".equalsIgnoreCase(cor) ? "preto" : "branco";
    }
    
    public Jogador getJogador()      { return jogador; }
    public Casa        getOrigem()       { return origem; }
    public Casa        getDestino()      { return destino; }
    public String      getCaminhoSeq(){ return caminhoSeq; }
    public Peca        getCapturada()    { return capturada; }
}