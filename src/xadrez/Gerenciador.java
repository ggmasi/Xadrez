package xadrez;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// classe principal que gerencia o jogo de xadrez
public class Gerenciador {

    public static void main(String[] args) throws CloneNotSupportedException {
        // cria uma instância do Gerenciador e inicia o menu principal
        Gerenciador g = new Gerenciador();
        g.menu();
    }

    // exibe o menu principal do jogo
    private void menu() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== JOGO DE XADREZ ===");
        System.out.println("1. Novo jogo");
        System.out.println("2. Carregar jogo");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        // trata a opção escolhida pelo usuário
        switch (opcao) {
            case "1" -> novoJogo();         // inicia um novo jogo do zero
            case "2" -> carregarJogo();     // carrega um jogo salvo anteriormente
            case "3" -> System.out.println("Encerrando o programa...");
            default -> {
                // caso o usuário digite uma opção inválida, exibe mensagem e reexibe o menu
                System.out.println("Opção inválida.");
                menu();
            }
        }
    }

    // inicia um novo jogo e pergunta se o usuário deseja salvar após terminar
    private void novoJogo() throws CloneNotSupportedException {
        Jogo jogo = new Jogo();
        jogo.iniciar();                    // começa a partida
        encerrarOuSalvar(jogo);           // ao fim, pergunta se quer salvar
    }

    // carrega um jogo salvo a partir de um arquivo
    private void carregarJogo() throws CloneNotSupportedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo para carregar: ");
        String nomeArquivo = scanner.nextLine();

        try {
            File arquivo = new File(nomeArquivo);
            Scanner leitor = new Scanner(arquivo);

            // lê os nomes dos jogadores
            String nomeBranco = leitor.nextLine();
            String nomePreto = leitor.nextLine();

            // cria o jogo com os nomes carregados
            Jogo jogo = new Jogo(nomeBranco, nomePreto);

            // lê e executa cada jogada registrada no arquivo
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (linha.equalsIgnoreCase("parar")) break;

                if (linha.length() != 4) continue; // ignora linhas mal formatadas

                int linhaO = Character.getNumericValue(linha.charAt(0));
                char colunaO = linha.charAt(1);
                int linhaD = Character.getNumericValue(linha.charAt(2));
                char colunaD = linha.charAt(3);

                // valida e realiza a jogada, se possível
                if (jogo.jogadaValida(linhaO, colunaO, linhaD, colunaD)) {
                    jogo.realizarJogada(linhaO, colunaO, linhaD, colunaD);
                } else {
                    System.out.println("Jogada inválida encontrada no arquivo: " + linha);
                }
            }

            leitor.close();
            jogo.iniciar();                // continua o jogo após as jogadas lidas
            encerrarOuSalvar(jogo);       // pergunta se quer salvar depois

        } catch (IOException e) {
            System.out.println("Erro ao carregar jogo: " + e.getMessage());
        }
    }

    // após o fim do jogo, oferece a opção de salvar o progresso
    private void encerrarOuSalvar(Jogo jogo) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Deseja salvar o jogo? (s/n): ");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("Digite o nome do arquivo para salvar: ");
            String nomeArquivo = scanner.nextLine();

            try {
                FileWriter escritor = new FileWriter(nomeArquivo);
                escritor.write(jogo.registroJogo());  // escreve o conteúdo formatado do jogo
                escritor.close();
                System.out.println("Jogo salvo com sucesso em: " + nomeArquivo);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o jogo: " + e.getMessage());
            }
        }
    }

    // método de testes manuais — não é chamado no programa principal
    public void teste() throws CloneNotSupportedException {
        System.out.println("=== Iniciando testes ===");

        Tabuleiro t = new Tabuleiro();
        t.inicializar();                   // prepara o tabuleiro com as peças iniciais
        System.out.println(t.desenho());   // mostra o estado do tabuleiro

        Jogador j1 = new Jogador("ggmasi", "branco");
        Jogador j2 = new Jogador("Nickao", "preto");

        Casa origem = t.getCasa(2, 'a');   // exemplo: peão na posição 2a
        Casa destino = t.getCasa(3, 'a');  // exemplo: destino 3a

        Jogada jogada = new Jogada(j1, origem, destino, destino.getPeca());
        System.out.println("Jogada válida? " + jogada.ehValida(t));

        System.out.println("=== Fim dos testes ===");
    }
}
