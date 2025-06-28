package xadrez;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private final String nome;
    private final String cor;
    private final List<Peca> pecas;
    private List<Peca> pecasCapturadas;
    
    
    public Jogador(String nome, String cor)
    {
        this.nome = nome;
        this.cor = cor;
        pecas = new ArrayList<>();
        pecasCapturadas = new ArrayList<>();
        
        for (int i = 0; i < 8; i++)
        {
            pecas.set(i, new Peao(this.cor));
        }
        pecas.set(8, new Torre(this.cor));
        pecas.set(9, new Torre(this.cor));
        
        pecas.set(10, new Bispo(this.cor));
        pecas.set(11, new Bispo(this.cor));
        
        pecas.set(12, new Cavalo(this.cor));
        pecas.set(13, new Cavalo(this.cor));
        
        pecas.set(14, new Torre(this.cor));
        pecas.set(15, new Torre(this.cor));
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
