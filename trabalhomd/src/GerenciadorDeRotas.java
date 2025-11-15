import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeRotas {
    private static List<Cidade> cidades = new ArrayList<>();
    private static int numCidades = 0;
    private static int[][] matrizAdj; //MATRIZ DE ADJACÊNCIA
    static Scanner consoleScanner = new Scanner(System.in); //SCANNER PARA INTERAÇÕES DE CONSOLE

    public static void lerArquivo(String nomeArquivo){
        System.out.println("LENDO O ARQUIVO: " + nomeArquivo + "...");
        cidades.clear(); //LIMPA DADOS ANTERIORES

        try(Scanner input = new Scanner(new File(nomeArquivo))){
            //LEITURA DO NÚMERO DE CIDADES
            if(input.hasNextInt()){
                numCidades = input.nextInt();
                System.out.println("Total de cidades: " + numCidades);
                matrizAdj = new int[numCidades][numCidades];
            } else {
                System.err.println("ERRO: O arquivo fornecido não possui o número de cidades a serem manipuladas...");
                return;
            }
            for(int i = 0; i < numCidades; i++){
                if(input.hasNextInt()){
                    int x = input.nextInt();

                    if(input.hasNextInt()){
                        int y = input.nextInt();
                        cidades.add(new Cidade(i + 1, x, y));
                    } else {
                        throw new InputMismatchException("COORDENADA Y AUSENTE NA LINHA " + (i + 2));
                    }
                } else {
                    throw new InputMismatchException("COORDENADA X AUSENTE NA LINHA " + (i + 2));
                }
            }
            System.out.println("LEITURA CONCLUÍDA. CIDADES CARREGADAS:");
            for(Cidade c : cidades){
                System.out.println(" - " + c);
            }
        } catch(FileNotFoundException e){
            System.err.println("ERRO: Arquivo '" + nomeArquivo + "' não encontrado.");
        } catch(InputMismatchException e){
            System.err.println("ERRO: inconsistencias no formato do arquivo - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void criarConexoes(){
        if(numCidades == 0) {
            System.err.println("ERRO: PRIMEIRO CARREGUE AS CIDADES (OPCAO 1).");
            return;
        }
        System.out.println("\n === Definição de conexoões ===");
        System.out.println("Cidades disponíveis (IDs): 1 a " + numCidades);
        System.out.println("Digite as conexões no formado 'Origem Destino' (ex: 15).");
        System.out.println("Digite FIM para finalizar.");

        //LOOP PARA ENTRADA DE CONEXÕES
        while(true){
            System.out.print("> Conexão (Origem Destino / FIM): ");
            String linha = consoleScanner.nextLine().trim();

            if(linha.equalsIgnoreCase("FIM")){break;}

            try{ //TENTA LER DOIS INTEIROS
                String[] partes = linha.split("\\s+");
                if(partes.length != 2){
                    System.err.println("Formato inválido. Use 'ID_Origem ID_Destino'.");
                    continue;
                }

                int origemID = Integer.parseInt(partes[0]);
                int destinoID = Integer.parseInt(partes[1]);

                //VALIDAÇÃO DE IDs
                if(origemID < 1 || origemID > numCidades || destinoID < 1 || destinoID > numCidades){
                    System.err.println("ID(s) inválido(s). Use IDs entre 1 e " + numCidades + ".");
                    continue;
                }

                //INDICES PARA A MATRIZ
                int origemIndex = origemID - 1;
                int destinoIndex = destinoID - 1;

                //DEFINE A CONEXÃO NA MATRIZ DE ADJACÊNCIA
                //GRAFO DIRIGIDO
                matrizAdj[origemIndex][destinoIndex] = 1;

                System.out.println("CONEXÃO C" + origemID + " -> C" + destinoID + " criada.");
            } catch (NumberFormatException e){
                System.err.println("ENTRADA INVÁLIDA: IDs devem ser números inteiros.");
            }
        }
        System.out.println("\nConexões Finalizadas. Matriz de adjacência atualizada.");
    }

    public static void exibirMenu() {
        System.out.println("\n--- Simulador de Gerenciamento de Rotas (MD) ---");
        System.out.println("1.  Carregar Pontos de Interesse (instancia.txt)");
        System.out.println("2.  Definir Conexões (Matriz de Adjacência)");
        System.out.println("3.  Encontrar e Exibir Rotas (Recursão)");
        System.out.println("4.  Resolver Caixeiro Viajante (TSP)");
        System.out.println("5.  Análise Combinatória");
        System.out.println("6.  Sair");
        System.out.print("Escolha uma opção: ");
    }

}


