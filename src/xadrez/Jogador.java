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
            pecas[i] = new Peao(this.cor);
        }
        pecas[8] = new Torre(this.cor);
        pecas[9] = new Torre(this.cor);
        
        pecas[10] = new Bispo(this.cor);
        pecas[11] = new Bispo(this.cor);
        
        pecas[12] = new Cavalo(this.cor);
        pecas[13] = new Cavalo(this.cor);
        
        pecas[14] = new Torre(this.cor);
        pecas[15] = new Torre(this.cor);
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
}
