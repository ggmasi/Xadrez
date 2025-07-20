package xadrez;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Gerenciador {

    public static void main(String[] args) throws CloneNotSupportedException {
        Gerenciador g = new Gerenciador();
        g.menu();
    }

    private void menu() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== JOGO DE XADREZ ===");
        System.out.println("1. Novo jogo");
        System.out.println("2. Carregar jogo");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1" -> novoJogo();
            case "2" -> carregarJogo();
            case "3" -> System.out.println("Encerrando o programa...");
            default -> {
                System.out.println("Opção inválida.");
                menu();
            }
        }
    }

    private void novoJogo() throws CloneNotSupportedException {
        Jogo jogo = new Jogo();
        jogo.iniciar(); 
        encerrarOuSalvar(jogo);
    }

    private void carregarJogo() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo para carregar: ");
        String nomeArquivo = scanner.nextLine();

        try {
            File arquivo = new File(nomeArquivo);
            Scanner leitor = new Scanner(arquivo);

            String nomeBranco = leitor.nextLine();
            String nomePreto = leitor.nextLine();

            Jogo jogo = new Jogo(nomeBranco, nomePreto);

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                if (linha.equalsIgnoreCase("parar")) break;

                if (linha.length() != 4) continue; // ignora jogadas inválidas
                int linhaO = Character.getNumericValue(linha.charAt(0));
                char colunaO = linha.charAt(1);
                int linhaD = Character.getNumericValue(linha.charAt(2));
                char colunaD = linha.charAt(3);

                if (jogo.jogadaValida(linhaO, colunaO, linhaD, colunaD)) {
                    jogo.realizarJogada(linhaO, colunaO, linhaD, colunaD);
                } else {
                    System.out.println("Jogada inválida encontrada no arquivo: " + linha);
                }
            }

            leitor.close();
            jogo.iniciar(); 
            encerrarOuSalvar(jogo);

        } catch (IOException e) {
            System.out.println("Erro ao carregar jogo: " + e.getMessage());
        }
    }

    private void encerrarOuSalvar(Jogo jogo) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Deseja salvar o jogo? (s/n): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite o nome do arquivo para salvar: ");
            String nomeArquivo = scanner.nextLine();

            try {
                FileWriter escritor = new FileWriter(nomeArquivo);
                escritor.write(jogo.registroJogo());
                escritor.close();
                System.out.println("Jogo salvo com sucesso em: " + nomeArquivo);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o jogo: " + e.getMessage());
            }
        }
    }

    
    public void teste() throws CloneNotSupportedException {
        System.out.println("=== Iniciando testes ===");

        Tabuleiro t = new Tabuleiro();
        t.inicializar();
        System.out.println(t.desenho());

        Jogador j1 = new Jogador("ggmasi", "branco");
        Jogador j2 = new Jogador("Nickao", "preto");

        Casa origem = t.getCasa(2, 'a');    
        Casa destino = t.getCasa(3, 'a');

        Jogada jogada = new Jogada(j1, origem, destino, destino.getPeca());
        System.out.println("Jogada válida? " + jogada.ehValida(t));

        System.out.println("=== Fim dos testes ===");
    }
}
