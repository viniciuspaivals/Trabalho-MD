public class Main {
    public static void main(String[] args) {

        int opcao = -1;
        while (opcao != 6) {
            try {
                GerenciadorDeRotas.exibirMenu();
                if (GerenciadorDeRotas.consoleScanner.hasNextLine()) {
                    String input = GerenciadorDeRotas.consoleScanner.nextLine();
                    if (input.isEmpty()) continue;
                    opcao = Integer.parseInt(input);
                } else {
                    break; // Fim da entrada
                }

                switch (opcao) {
                    case 1:
                        GerenciadorDeRotas.lerArquivo("instancia.txt");
                        break;
                    case 2:
                        GerenciadorDeRotas.criarConexoes();
                        break;
                    case 3:
                        System.out.println("Funcionalidade 3 em desenvolvimento...");
                        // chamar encontrarRotas();
                        break;
                    case 4:
                        System.out.println("Funcionalidade 4 em desenvolvimento...");
                        // chamar tspRecursivo();
                        break;
                    case 5:
                        System.out.println("Funcionalidade 5 em desenvolvimento...");
                        // chamar funcoesCombinatorias();
                        break;
                    case 6:
                        System.out.println("Saindo do simulador. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, digite um número de 1 a 6.");
            }
        }
    }
}
