package xadrez;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

public class Jogador {


    private final String nome;
    private final String cor;
    private final List<Peca> pecas;
    private final List<Peca> pecasCapturadas;
    
    
    public Jogador(String nome, String cor)
    {
        this.nome = nome;
        this.cor = cor;
        pecas = new ArrayList<>();
        pecasCapturadas = new ArrayList<>();
        
        for (int i = 0; i < 8; i++)
        {
            pecas.add(new Peao(this.cor));
        }
        pecas.add(new Torre(this.cor));
        pecas.add(new Torre(this.cor));
        
        pecas.add(new Bispo(this.cor));
        pecas.add(new Bispo(this.cor));
        
        pecas.add(new Cavalo(this.cor));
        pecas.add(new Cavalo(this.cor));
        
        pecas.add(new Rei(this.cor));
        pecas.add(new Dama(this.cor));
    }
    
    public String informaJogada()
    {
        Scanner scanner = new Scanner(System.in);
        
        String jogada = scanner.nextLine();
        
        return jogada;
    }
    
    public String pecasCapturadas()
    {
        StringBuilder sb = new StringBuilder();
        for (Peca peca : this.pecasCapturadas) 
        {
            sb.append(peca.desenho());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    
    public void adicionarCapturado(Peca peca)
    {
        this.pecasCapturadas.add(peca);
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public String getCor(){
        return cor;
    }

}
