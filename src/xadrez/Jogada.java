/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xadrez;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author ggmasi
 */
public class Jogada {
    private final Jogador jogador;
    private final Casa origem;
    private final Casa destino;
    private final String caminhoSeq;
    private final Peca capturada;

    public Jogada(Jogador jogador, Casa origem, Casa destino, Peca capturada) {
        this.jogador = jogador;
        this.origem = origem;
        this.destino = destino;
        this.capturada = capturada;
        Peca peca = origem.getPeca();
        this.caminhoSeq = peca.caminho(origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna());
    }
    
    public boolean ehValida(Tabuleiro tabuleiro) {
        if (!tabuleiro.noLimite(origem.getLinha(), origem.getColuna()) || !tabuleiro.noLimite(destino.getLinha(), destino.getColuna())) return false;
        if (!origem.temPeca() || !Objects.equals(origem.getPeca().getCor(), jogador.getCor())) return false;
        if (!origem.getPeca().movimentoValido(
                origem.getLinha(), origem.getColuna(), destino.getLinha(), destino.getColuna())) return false;
        Caminho cam = new Caminho(tabuleiro, caminhoSeq);
        if (!cam.estaLivre()) return false;
        if (!destino.temPeca() && Objects.equals(destino.getPeca().getCor(), jogador.getCor())) return false;
        if (causaXequeProprio(tabuleiro)) return false;
        return true;
    }
    
    public boolean ehXeque(Tabuleiro tabuleiro){
        Tabuleiro t = simularMovimento(tabuleiro);
        String corOponente = oposto(jogador.getCor());
        return detectaXeque(t, corOponente);
    }
    
    public boolean ehXequeMate(Tabuleiro tabuleiro){
        Tabuleiro t = simularMovimento(tabuleiro);
        String corOponente = oposto(jogador.getCor());
        if(!detectaXeque(t, corOponente)) return false;
        List<Casa> pecasAdversarias = t.getCasasComPecaDaCor(corOponente);
        for(Casa c : pecasAdversarias){
            Peca peca = c.getPeca();
            List<Casa> movimentos = peca.movimentosPossiveis(t, c.getLinha(), c.getColuna());
            for(Casa dest : movimentos){
                Jogada hipot = new Jogada(new Jogador("temp", corOponente), c, dest, dest.getPeca());
                if(hipot.ehValida(t)){
                    Tabuleiro t2 = hipot.simularMovimento(t);
                    if(!detectaXeque(t2, corOponente)){
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
    
    private Tabuleiro simularMovimento(Tabuleiro tab){
        Tabuleiro copia = tab.clone();
        copia.getCasa(origem.getLinha(), origem.getColuna()).removePeca();
        copia.getCasa(destino.getLinha(), destino.getColuna()).setPeca(origem.getPeca());
        return copia;
    }
    
    private boolean causaXequeProprio(Tabuleiro tabuleiro){
        Tabuleiro t = simularMovimento(tabuleiro);
        return detectaXeque(t, jogador.getCor());
    }
    
    private boolean detectaXeque(Tabuleiro tab, String corRei){
        Casa posRei = tab.encontraRei(corRei);
        String corAtacante = oposto(corRei);
        List<Casa> atacantes = tab.getCasasComPecaDaCor(corAtacante);
        for(Casa c : atacantes){
            Peca peca = c.getPeca();
            if(peca.movimentoValido(c.getLinha(), c.getColuna(), posRei.getLinha(), posRei.getColuna())){
                Caminho cam = new Caminho(tab, peca.caminho(c.getLinha(), c.getColuna(), posRei.getLinha(), posRei.getColuna()));
                if(cam.estaLivre()) return true;
            }
        }
        return false;
    }
    
    
    private String oposto(String cor) {
        return "branco".equalsIgnoreCase(cor) ? "preto" : "branco";
    }
    
    public Jogador getJogador()   { return jogador; }
    public Casa     getOrigem()    { return origem; }
    public Casa     getDestino()   { return destino; }
    public String   getCaminhoSeq(){ return caminhoSeq; }
    public Peca     getCapturada() { return capturada; }
}
