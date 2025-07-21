package xadrez;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogo {
    private final Tabuleiro tabuleiro;
    private Jogador branco;
    private Jogador preto;
    private Jogador atual;
    private String estado;
    private final List<Jogada> historico;
    private final Scanner sc = new Scanner(System.in);
    
    public Jogo() throws CloneNotSupportedException{
        System.out.println("Nome do jogador branco: ");
        String nomeBranco = sc.nextLine();
        System.out.println("Nome do jogador preto: ");
        String nomePreto = sc.nextLine();
        
        this.branco = new Jogador(nomeBranco, "branco"); // inicia os jogadores
        this.preto = new Jogador(nomePreto, "preto");
        this.atual = branco;
        this.tabuleiro = new Tabuleiro();
        this.historico = new ArrayList<>();
        this.estado = "Inicio";
        
        tabuleiro.inicializar();
        atualizarTela();
    }
    
    public Jogo(String nomeBranco, String nomePreto) throws CloneNotSupportedException {
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
        
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        
        if (origem == null || destino == null || !tabuleiro.noLimite(linhaO, colunaO) || !tabuleiro.noLimite(linhaD, colunaD)) {
            return false;
        }
        
        Peca capturadaNoDestino = destino.temPeca() ? destino.getPeca() : null;
        
        Jogada jogadaParaValidar = new Jogada(atual, origem, destino, capturadaNoDestino);        

        
        try{
            if(!jogadaParaValidar.ehValida(tabuleiro)){
                return false;
            }
        }catch(CloneNotSupportedException e){
            System.err.println("Erro de clonagem durante validação da jogada: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD) throws CloneNotSupportedException{
        if(!jogadaValida(linhaO, colunaO, linhaD, colunaD)){
            System.out.println("Jogada inválida! Tente novamente!");
            return;
        }
        
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        Peca capturada = destino.getPeca();
        if (capturada != null) {
            atual.adicionarCapturado(capturada);
            destino.removePeca();
        }

        
        Jogada jogada = new Jogada(atual, origem, destino, capturada);
        Peca temp = origem.getPeca();
        origem.removePeca();
        destino.setPeca(temp);
        
        historico.add(jogada);
        trocarVez();
        atualizarTela();
        
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
    
    private String atualizarEstado() throws CloneNotSupportedException{
        if(!historico.isEmpty()){
            Jogada ultima = historico.get(historico.size() - 1);

            if (ultima.ehXequeMate(tabuleiro)) {
                estado = "Xeque-Mate";
                //System.out.println("Xeque-Mate! Vencedor: " + atual.getNome());
                //System.exit(0);
            } else if (ultima.ehXeque(tabuleiro)) {
                estado = "Xeque";
                //System.out.println("XEQUE!");
            } else {
                estado = "Em andamento";
            }
        }
        
        return estado;
    }
    
    private void atualizarTela() throws CloneNotSupportedException{
        System.out.println(tabuleiro.desenho());
        System.out.println(atualizarEstado());
        if(!"Xeque-Mate".equals(estado)){
            System.out.println("Brancas capturadas: " + branco.pecasCapturadas());
            System.out.println("Pretas capturadas: " + preto.pecasCapturadas());
            System.out.println("Vez de: " + atual.getNome() + " (" + atual.getCor() + ")");
        }else{
            System.out.println("FIM DE JOGO!");
            trocarVez();
            System.out.println("VENCEDOR: " + atual.getNome());
        }
        
    }
    
    @SuppressWarnings("empty-statement")
    public void iniciar() throws CloneNotSupportedException { 
    while (true) {
        String entrada = atual.informaJogada();

        if (entrada.equalsIgnoreCase("parar")) {
            System.out.println("Jogo interrompido.");
            break;
        }

        if (entrada.length() != 4) {
            System.out.println("Jogada inválida. Use o formato: a2a4.");
            continue;
        }

        try {
            int linhaO = Character.getNumericValue(entrada.charAt(1));
            char colunaO = entrada.charAt(0);
            int linhaD = Character.getNumericValue(entrada.charAt(3));
            char colunaD = entrada.charAt(2);

            realizarJogada(linhaO, colunaO, linhaD, colunaD);
        } catch (CloneNotSupportedException e) {;
            System.out.println("Erro de clonagem durante a jogada: " + e.getMessage());
        } catch (Exception e) { 
            System.out.println("Erro ao interpretar ou realizar jogada: " + e.getMessage());
        }
        
        if(estado.equals("Xeque-Mate")) return;
    }
}

    
    
    // Getters
    public String getEstado() { return estado; }
    public Jogador getJogadorDaVez() { return atual; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public List<Jogada> getHistorico() { return historico; }
    
}