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

    /*Verifica se uma jogada é válida
        - Origem e destino sao casas validas no tabuleiro
        - Cria o objeto "Jogada" e verifica se a jogada eh valida
    */
    
    public boolean jogadaValida(int linhaO, char colunaO, int linhaD, char colunaD){
        
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        
        //Verifica se origem e destino sao casas validas no tabuleiro
        if (origem == null || destino == null || !tabuleiro.noLimite(linhaO, colunaO) || !tabuleiro.noLimite(linhaD, colunaD)) {
            return false;
        }
        
        //Atribui peça capturada e instancia nova Jogada
        
        Peca capturadaNoDestino = destino.temPeca() ? destino.getPeca() : null;
        
        Jogada jogadaParaValidar = new Jogada(atual, origem, destino, capturadaNoDestino);        

        /*  
            Verifica se a Jogada eh valida a partir de um tratamento de exceçao
            Caso haja erro de clonagem, a exceçao "CloneNotSuppoprtedException" eh disparada
        */
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
    
    /*
        Metodo responsavel por realizar as jogadas
    */
    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD) throws CloneNotSupportedException{
        //Verifica se a jogada pretendida eh valida
        if(!jogadaValida(linhaO, colunaO, linhaD, colunaD)){
            System.out.println("Jogada inválida! Tente novamente!");
            return;
        }
        
        Casa origem = tabuleiro.getCasa(linhaO, colunaO);
        Casa destino = tabuleiro.getCasa(linhaD, colunaD);
        Peca capturada = destino.getPeca();
        //Caso haja uma peça a ser capturada, adiciona essa peça as capturadas e remove do tabuleiro
        if (capturada != null) {
            atual.adicionarCapturado(capturada);
            destino.removePeca();
        }

        //Movimenta a peça no tabuleiro
        Jogada jogada = new Jogada(atual, origem, destino, capturada);
        Peca temp = origem.getPeca();
        origem.removePeca();
        destino.setPeca(temp);
        
        //Adiciona a jogada ao historico e atualiza o jogo
        historico.add(jogada);
        trocarVez();
        atualizarTela();
        
    }
    
    /*
        Metodo responsavel por registrar o jogo
        Salva em uma string o nome do jogador branco e preto, respectivamente
        Para cada Jogada da lista "historico", adiciona-se o codigo do da jogada a string
    */
    public String registroJogo(){
        StringBuilder sb = new StringBuilder();
        sb.append(branco.getNome()).append("\n");
        sb.append(preto.getNome()).append("\n");
        for(Jogada j : historico){
            sb.append(j.getCodigo()).append("\n");
        }
        return sb.toString();
    }
    
    //Alterna os turnos
    private void trocarVez(){
        atual = atual == branco ? preto : branco;    
    }
    
    //Atualiza o estado em que a partida esta
    private String atualizarEstado() throws CloneNotSupportedException{
        if(!historico.isEmpty()){
            Jogada ultima = historico.get(historico.size() - 1);

            if (ultima.ehXequeMate(tabuleiro)) {
                estado = "XEQUE-MATE";
            } else if (ultima.ehXeque(tabuleiro)) {
                estado = "XEQUE";
            } else {
                estado = "EM ANDAMENTO";
            }
        }
        
        return estado;
    }
    
    //Atualiza a tela para que o jogo ocorra
    private void atualizarTela() throws CloneNotSupportedException{
        System.out.println(tabuleiro.desenho());
        System.out.println(atualizarEstado());
        if(!"XEQUE-MATE".equals(estado)){
            System.out.println("Brancas capturadas: " + branco.pecasCapturadas());
            System.out.println("Pretas capturadas: " + preto.pecasCapturadas());
            System.out.println("Vez de: " + atual.getNome() + " (" + atual.getCor() + ")");
        }else{
            System.out.println("FIM DE JOGO!");
            trocarVez();
            System.out.println("VENCEDOR: " + atual.getNome() + " (" + atual.getCor() + ")");
        }
        
    }
    
    //Inicia o jogo
    @SuppressWarnings("empty-statement")
    public void iniciar() throws CloneNotSupportedException { 
    while (true) {
        String entrada = atual.informaJogada();
        //Quando escrito "parar", o jogo eh interrompido
        if (entrada.equalsIgnoreCase("parar")) {
            System.out.println("Jogo interrompido.");
            break;
        }
        //Verifica se a jogada solicitada possui formato valido
        if (    entrada.length() != 4 
                || (entrada.charAt(0) >= '1' && entrada.charAt(0) <= '8')
                || (entrada.charAt(2) >= '1' && entrada.charAt(2) <= '8')
                || (entrada.charAt(1) >= 'a' && entrada.charAt(1) <= 'h')
                || (entrada.charAt(3) >= 'a' && entrada.charAt(3) <= 'h')) {
            System.out.println("Jogada inválida. Use o formato: a2a4.");
            continue;
        }
        
        //Le a jogada e a executa
        try {
            int linhaO = Character.getNumericValue(entrada.charAt(1));
            char colunaO = entrada.charAt(0);
            int linhaD = Character.getNumericValue(entrada.charAt(3));
            char colunaD = entrada.charAt(2);

            realizarJogada(linhaO, colunaO, linhaD, colunaD);
        } catch (CloneNotSupportedException e) {; //Tratamento de excecao para erro de clonagem
            System.out.println("Erro de clonagem durante a jogada: " + e.getMessage());
        } catch (Exception e) { //Tratamento de exceçao para erros logicos
            System.out.println("Erro ao interpretar ou realizar jogada: " + e.getMessage());
        }
        //Caso haja xeque-mate o jogo se encerra
        if(estado.equals("XEQUE-MATE")) return;
    }
}

    
    
    // Getters
    public String getEstado() { return estado; }
    public Jogador getJogadorDaVez() { return atual; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public List<Jogada> getHistorico() { return historico; }
    
}