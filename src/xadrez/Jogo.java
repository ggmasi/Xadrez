/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xadrez;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ggmasi
 */
public class Jogo {
    private final Tabuleiro tabuleiro;
    private Jogador branco;
    private Jogador preto;
    private Jogador atual;
    private String estado;
    private final List<Jogada> historico;
    private final Scanner sc = new Scanner(System.in);
    
    public Jogo(){
        System.out.println("Nome do jogador branco: ");
        String nomeBranco = sc.nextLine();
        System.out.println("Nome do jogador branco: ");
        String nomePreto = sc.nextLine();
        
        this.branco = new Jogador(nomeBranco, "branco");
        this.preto = new Jogador(nomePreto, "preto");
        this.atual = branco;
        this.tabuleiro = new Tabuleiro();
        this.historico = new ArrayList<>();
        this.estado = "Inicio";
        
        
        tabuleiro.inicializar();
        atualizarTela();
    }
    
    public boolean jogadaValida(int linhaO, char colunaO, int linhaD, char colunaD){
        if(!tabuleiro.noLimite(linhaO, colunaO) || !tabuleiro.noLimite(linhaD, colunaD));
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        
        if(!origem.temPeca()) return false;
        Peca peca = origem.getPeca();
        if(!peca.getCor().equals(atual.getCor())) return false;
        
        if(!peca.movimentoValido(linhaO, colunaO, linhaD, colunaD)) return false;
        
        String seq = peca.caminho(linhaO, colunaO, linhaD, colunaD);
        if(seq.isEmpty()) return false;
        
        Caminho caminho = new Caminho(tabuleiro, seq);
        if(!caminho.estaLivre()) return false;
        
        if(destino.temPeca() && destino.getPeca().getCor().equals(atual.getCor())) return false;
        
        /*VERIFICAR SE NAO DEIXA PROPRIO REI EM XEQUE*/
        
        return true;
    }
    
    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD){
        if(!jogadaValida(linhaO, colunaO, linhaD, colunaD)){
            System.out.println("Jogada inválida! Tente novamente!");
            return;
        }
        
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        Peca capturada = destino.getPeca();
        if(capturada != null){
            trocarVez();
            atual.adicionarCapturado(capturada);
            destino.removePeca();
            trocarVez();
        }
        
        Jogada jogada = new Jogada(atual, origem, destino, capturada);
        Peca temp = origem.getPeca();
        origem.removePeca();
        destino.setPeca(temp);
        
        historico.add(jogada);
        atualizarEstado();
        atualizarTela();
        trocarVez();
        
    }
    
    public String registroJogo(){
        StringBuilder sb = new StringBuilder();
        sb.append(branco.getNome()).append("\n");
        sb.append(preto.getNome()).append("\n");
        for(Jogada j : historico){
            sb.append(j.getCodigo()).append("\n");
        }
        return sb.toString();
    }
    
    private void trocarVez(){
        atual = atual == branco ? preto : branco; 
    }
    
    private void atualizarEstado(){
        
    }
    
    private void atualizarTela(){
        System.out.println(tabuleiro.desenho());
        System.out.println("Brancas capturadas: " + branco.pecasCapturadas());
        System.out.println("Pretas capturadas: " + preto.pecasCapturadas());
        System.out.println("Vez de: " + atual.getNome() + " (" + atual.getCor() + ")");
    }
    
    // Getters
    public String getEstado() { return estado; }
    public Jogador getJogadorDaVez() { return atual; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public List<Jogada> getHistorico() { return historico; }
    
}
