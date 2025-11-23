class Main{
    public static void main(String[] args) {

        int opcao = -1;
        while (opcao != 6) {
            try {
                GerenciadorDeRotas.exibirMenu();

                String input = GerenciadorDeRotas.consoleScanner.nextLine().trim();

                if (input.isEmpty()) continue;

                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        GerenciadorDeRotas.lerArquivo("instancia.txt");
                        break;
                    case 2:
                        GerenciadorDeRotas.criarConexoes();
                        break;
                    case 3:
                        GerenciadorDeRotas.encontrarRotas();
                        break;
                    case 4:
                        //resolverTSP(); // CHAMADA DA FUNÇÃO TSP
                        break;
                    case 5:
                        GerenciadorDeRotas.analiseCombinatoria();
                        break;
                    case 6:
                        System.out.println("Saindo do simulador. Até logo!");
                        GerenciadorDeRotas.consoleScanner.close();
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
