import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        int opcao = -1;

        // CRIAÇÃO E CENTRALIZAÇÃO DO SCANNER NA CLASSE MAIN
        Scanner consoleScanner = new Scanner(System.in); 

        while (opcao != 7) {
            try {
                GerenciadorDeRotas.exibirMenu();

                // LÊ A ENTRADA USANDO O SCANNER CENTRALIZADO
                String input = consoleScanner.nextLine().trim();

                if (input.isEmpty()) continue;

                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        GerenciadorDeRotas.lerArquivo("instancia.txt");
                        break;
                    case 2:
                        // PASSA O SCANNER COMO PARÂMETRO
                        GerenciadorDeRotas.criarConexoes(consoleScanner);
                        break;
                    case 3:
                        // PASSA O SCANNER COMO PARÂMETRO
                        GerenciadorDeRotas.encontrarRotas(consoleScanner);
                        break;
                    case 4:
                        // PASSA O SCANNER COMO PARÂMETRO
                        //CAIXEIRO VIAJANTE
                        break;
                    case 5:
                        // PASSA O SCANNER COMO PARÂMETRO
                        GerenciadorDeRotas.analiseCombinatoria(consoleScanner);
                        break;
                    case 6: 
                        GerenciadorDeRotas.imprimirGrafo();
                        break;
                    case 7: 
                        System.out.println("Saindo do simulador. Até logo!");
                        // FECHA O SCANNER QUANDO O PROGRAMA É ENCERRADO
                        consoleScanner.close(); 
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, digite um número de 1 a 7.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }
}