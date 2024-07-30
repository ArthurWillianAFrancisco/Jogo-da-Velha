import java.util.Random;
import java.util.Scanner;

public class jogoCompleto {
    private static Tabuleiro tabuleiro = new Tabuleiro();
    private static String jogador1;
    private static String jogador2;
    private static int placarJogador1 = 0;
    private static int placarJogador2 = 0;
    private static int totalPartidas = 0;
    private static Random random;

    public static void main(String[] args) {
        System.out.println("###########################");
        System.out.println("Bem-vindo ao Jogo da Velha!");
        System.out.println("###########################");
        Scanner iniciar = new Scanner(System.in);
        System.out.println("\nAperte qualquer tecla para inciciar o game ou 'q' para sair: ");
        String inicia = iniciar.nextLine();
        if (inicia.equals("q")) {
            System.out.println("saindo");
            System.exit(0);
        }else {
            System.out.println("""
                    Como funciona o jogo
                       A entrada de dados deve ser recebida da seguinte forma
                       Linha Coluna X (xis maiúsculo) ou 0 (zero)
                       Exemplos de entradas válidas: 01X, 000, 110, 220, 12X
                       Exemplos de entradas inválidas: X01, 111, 0X1, 012, 221 """);
            iniciarJogo();
        }
    }

    private static void iniciarJogo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("###########################");
        System.out.println("Bem-vindo ao Jogo da Velha!");
        System.out.println("###########################");
        System.out.print("Nome do Jogador 1 (X): ");
        jogador1 = scanner.nextLine();
        System.out.print("Nome do Jogador 2 (O): ");
        jogador2 = scanner.nextLine();
        char jogadorAtual;
        String nomeAtual;
        Random random = new Random();
        int escolido = random.nextInt(2);

        while (true) {
            tabuleiro.inicializarTabuleiro();
            boolean jogoEmAndamento = true;
            if (escolido == 1){
                System.out.println(escolido);
                jogadorAtual = 'X';
                nomeAtual = jogador1;
            }else {
                System.out.println(escolido);
                jogadorAtual = '0';
                nomeAtual = jogador2;
            }

            while (jogoEmAndamento) {
                tabuleiro.mostrarTabuleiro();
                System.out.println("É a vez de " + nomeAtual + " (" + jogadorAtual + ")");
                System.out.print("Informe sua jogada (Formato LinhaColunaX ou LinhaColunaO, ou 'q' para sair): ");
                String entrada = scanner.nextLine();

                if (entrada.equalsIgnoreCase("q")) {
                    System.out.println("Saindo do jogo...");
                    scanner.close();
                    return;
                }

                if (entrada.length() < 3) {
                    System.out.println("Entrada inválida. Tente novamente.");
                    continue;
                }

                int linha = entrada.charAt(0) - '0';
                int coluna = entrada.charAt(1) - '0';
                char simbolo = entrada.charAt(2);

                if (tabuleiro.fazerJogada(linha, coluna, simbolo)) {
                    if (tabuleiro.verificarVencedor(simbolo)) {
                        tabuleiro.mostrarTabuleiro();
                        System.out.println(nomeAtual + " venceu!");
                        if (simbolo == 'X') {
                            placarJogador1++;
                        } else {
                            placarJogador2++;
                        }
                        totalPartidas++;
                        jogoEmAndamento = false;
                    } else if (tabuleiro.tabuleiroCheio()) {
                        tabuleiro.mostrarTabuleiro();
                        System.out.println("Empate!");
                        totalPartidas++;
                        jogoEmAndamento = false;
                    } else {
                        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
                        nomeAtual = (jogadorAtual == 'X') ? jogador1 : jogador2;
                    }
                } else {
                    System.out.println("Jogada inválida. Tente novamente.");
                }
            }

            mostrarPlacar();
        }
    }

    private static void mostrarPlacar() {
        System.out.println("\nPlacar Atual:");
        System.out.println(jogador1 + ": " + placarJogador1);
        System.out.println(jogador2 + ": " + placarJogador2);
        System.out.println("Total de partidas: " + totalPartidas);
        System.out.println();
    }

    private static class Tabuleiro {
        private char[][] tabuleiro;

        public Tabuleiro() {
            tabuleiro = new char[3][3];
            inicializarTabuleiro();
        }

        public void inicializarTabuleiro() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tabuleiro[i][j] = '_';
                }
            }
        }

        public void mostrarTabuleiro() {
            System.out.println("  1 2 3");
            for (int i = 0; i < 3; i++) {
                System.out.print((i + 1) + " ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(tabuleiro[i][j] + " ");
                }
                System.out.println();
            }
        }

        public boolean fazerJogada(int linha, int coluna, char simbolo) {
            if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == '_') {
                tabuleiro[linha][coluna] = simbolo;
                return true;
            }
            return false;
        }

        public boolean verificarVencedor(char simbolo) {
            // Checar linhas e colunas
            for (int i = 0; i < 3; i++) {
                if ((tabuleiro[i][0] == simbolo && tabuleiro[i][1] == simbolo && tabuleiro[i][2] == simbolo) ||
                        (tabuleiro[0][i] == simbolo && tabuleiro[1][i] == simbolo && tabuleiro[2][i] == simbolo)) {
                    return true;
                }
            }
            // Checar diagonais
            if ((tabuleiro[0][0] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][2] == simbolo) ||
                    (tabuleiro[0][2] == simbolo && tabuleiro[1][1] == simbolo && tabuleiro[2][0] == simbolo)) {
                return true;
            }
            return false;
        }

        public boolean tabuleiroCheio() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro[i][j] == '_') {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
