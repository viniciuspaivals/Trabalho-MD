import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeRotas { //PERSISTENCIA
    private static List<Cidade> cidades = new ArrayList<>(); //ARRAY QUE ARMAZENA TODAS AS CIDADES CRIADAS E MANIPULADAS DURANTE A EXECUÇÃO DO PROGRAMA
    private static int numCidades = 0; //CONTADOR QUE ARMAZENA O NÚMERO DE CIDADES
    private static int[][] matrizAdj; //MATRIZ DE ADJACÊNCIA
    static Scanner consoleScanner = new Scanner(System.in); //SCANNER PARA INTERAÇÕES DE CONSOLE

    public static void lerArquivo(String nomeArquivo){
        System.out.println("LENDO O ARQUIVO: " + nomeArquivo + "...");
        cidades.clear(); //LIMPA DADOS ANTERIORES

        try(Scanner input = new Scanner(new File(nomeArquivo))){
            if(input.hasNextInt()){ //LEITURA DO NÚMERO DE CIDADES
                numCidades = input.nextInt();
                System.out.println("Total de cidades: " + numCidades);
                matrizAdj = new int[numCidades][numCidades];
            } else { //CASO O FORMATO DO ARQUIVO ESTEJA INADEQUADO O PROGRAMA RETORNA UM ERRO...
                System.err.println("ERRO: O arquivo fornecido não possui o número de cidades a serem manipuladas...");
                return;
            }
            for(int i = 0; i < numCidades; i++){ //LOOP QUE ADICIONA TODAS CIDADES CRIADAS AO ARRAYLIST
                if(input.hasNextInt()){ //LÊ AS COORDENADAS DIGITADAS E VERIFICA SE ESTA FALTANDO ALGUM DADO
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
            for(Cidade c : cidades){ //AO FINAL DA LEITURA IMPRIME TODAS AS CIDADES ADICIONADAS AO ARRAYLIST
                System.out.println(" - " + c);
            }
        } catch(FileNotFoundException e){ //EXCEÇÃO PARA CASO O ARQUIVO instancia.txt NÃO SEJA ENCONTRADO
            System.err.println("ERRO: Arquivo '" + nomeArquivo + "' não encontrado.");
        } catch(InputMismatchException e){ //EXCEÇÃO QUE VERIFICA INCONSISTÊNCIAS NA FORMATAÇÃO DO ARQUIVO
            System.err.println("ERRO: inconsistencias no formato do arquivo - " + e.getMessage());
        } catch (Exception e) { //EXCEÇÃO BASE
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void criarConexoes(){
        if(numCidades == 0) { //CASO O NÚMERO DE CIDADES SEJA IGUAL A ZERO, NÃO É POSSÍVEL CRIAR CONEXÕES
            System.err.println("ERRO: PRIMEIRO CARREGUE AS CIDADES (OPCAO 1).");
            return;
        }
        System.out.println("\n === Definição de conexões ===");
        System.out.println("Cidades disponíveis (IDs): 1 a " + numCidades);
        System.out.println("Digite as conexões no formado 'Origem Destino' (ex: 15).");
        System.out.println("Digite FIM para finalizar.");

        //LOOP PARA ENTRADA DE CONEXÕES
        while(true){
            System.out.print("> Conexão (Origem Destino / FIM): ");
            String linha = consoleScanner.nextLine().trim();

            if(linha.equalsIgnoreCase("FIM")){break;} //SE O USUÁRIO DIGITAR 'FIM' É ENCERRADA A CRIAÇÃO DE CONEXÕES

            try{ //TENTA LER DOIS INTEIROS
                String[] partes = linha.split("\\s+");
                if(partes.length != 2){
                    System.err.println("Formato inválido. Use 'ID_Origem ID_Destino'.");
                    continue;
                }
                //O PROGRAMA LÊ UMA STRING E A PARTE EM PEDAÇOS, FAZENDO O CASTING DESTES, VERIFICANDO OS IDs E ADICIONANDO A CONEXÃO À MATRIZ
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

    public static long fatorial(int n){
        if(n < 0){return 0;} //FATORIAL NÃO DEFINIDO PARA NÚMEROS NEGATIVOS
        if(n == 0 || n == 1) return 1; //0! = 1! = 1
        return n * fatorial(n - 1); //PASSO RECURSIVO: n! = n * (n - 1)!
    }

    public static long permutacao(int n, int k){
        if(n < 0 || k < 0 || k > n) return 0; //CONDIÇÕES INVÁLIDAS PARA PERMUTAÇÃO
        long nFatorial = fatorial(n);
        long nMenosKFatorial = fatorial(n - k);

        if(nMenosKFatorial == 0) return 0;

        return nFatorial / nMenosKFatorial;
    }

    public static long combinacao(int n, int k){
        if(n < 0 || k < 0 || k > n) return 0; //CONDIÇÕES INVÁLIDAS PARA COMBINAÇÃO
        long numerador = permutacao(n, k);
        long kFatorial = fatorial(k);

        if(kFatorial == 0) return 0;

        return numerador / kFatorial;
    }
    
    public static void analiseCombinatoria() { //INTERAGE COM O USUÁRIO PARA CALCULAR PERMUTAÇÕES E COMBINAÇÕES
        System.out.println("\n--- Análise Combinatória de Eventos ---");
        System.out.println("Cálculo do número de maneiras de organizar eventos em uma rota.");

        try { //CAPTURA OS DADOS PARA A FUNÇÃO
            System.out.print("Digite o número total de eventos (n): ");
            int n = Integer.parseInt(consoleScanner.nextLine().trim());

            System.out.print("Digite o número de eventos a serem visitados (k): ");
            int k = Integer.parseInt(consoleScanner.nextLine().trim());

            if (n < 0 || k < 0 || k > n) { //
                System.err.println("PARÂMETROS INVÁLIDOS: n e k devem ser não negativos, e k não pode ser maior que n.");
                return;
            }

            long numPermutacoes = permutacao(n, k); //PERMUTAÇÃO: QUANTAS ORDENS DE VISITAÇÃO EXISTEM DE k eventos de n
            System.out.println("\nRESULTADOS:");
            System.out.println("PERMUTAÇÕES (Ordem importa):");
            System.out.printf("P(%d, %d) = %d maneiras de visitar %d cidades em uma ordem definida.\n", n, k, numPermutacoes, k);

            long numCombinacoes = combinacao(n, k); //COMBINAÇÃO: QUANTOS GRUPOS DIFERENTES DE k eventos podem ser formados de n
            System.out.println("Combinações (Ordem não importa):");
            System.out.printf("C(%d, %d) = %d grupos diferentes de %d cidades podem ser formados.\n", n, k, numCombinacoes, k);
        } catch(NumberFormatException e){ //CASO OS DADOS FORNECIDOS FUJAM DO PADRÃO A FUNÇÃO RETORNA A MENSAGEM DE ERRO
            System.err.println("ENTRADA INVÁLIDA. Por favor, digite números inteiros positivos para n e k.");
        }
    }

    public static void encontrarRotas(){
        if(numCidades == 0){ //ENCERRA A EXECUÇÃO DA FUNÇÃO CASO AS CIDADES AINDA NÃO TENHAM SIDO CARREGADAS
            System.err.println("ERRO: Primeiro carregue as cidades (OPÇÃO 1).");
            return;
        }

        System.out.println("\n--- BUSCA RECURSIVA DE ROTAS ---");
        try{
            System.out.println("Digite o ID da cidade de Partida (1 a " + numCidades + "): ");
            int inicioID = Integer.parseInt(consoleScanner.nextLine().trim());

            System.out.print("Digite o ID da cidade de Chegada (1 a" + numCidades + "): ");
            int fimID = Integer.parseInt(consoleScanner.nextLine().trim());

            if (inicioID < 1 || inicioID > numCidades || fimID < 1 || fimID > numCidades){ //CASO O ID FORNECIDO NÃO ESTEJA ENTRE AS CIDADES CRIADAS ELE RETORNA A FUNÇÃO...
                System.err.println("IDs Inválidos. Por favor, digite IDs entre 1 e " + numCidades + ".");
                return;
            }

            //MAPEAMENTO ID (1 a N) PARA ÍNDICE DA MATRIZ (0 a N-1)
            int inicioIndex = inicioID - 1;
            int fimIndex = fimID - 1;

            boolean[] visitado = new boolean[numCidades]; //ARRAY PARA QUE NÃO OCORRAM LOOPS
            List<Integer> rotaAtual = new ArrayList<>(); //LISTA QUE ARMAZENA O CAMINHO SENDO TRAÇADO

            System.out.println("\n ROTAS POSSÍVEIS DE C" + inicioID + " para C" + fimID + ":");
            buscarRotas(inicioIndex, fimIndex, rotaAtual, visitado); //CHAMADA RECURSIVA
        } catch (NumberFormatException e){ //CASO A ENTRADA SEJA INVÁLIDA, RETORNA O ERRO AO USUÁRIO
            System.err.println("ENTRADA INVÁLIDA. Por favor, digite números inteiros.");
        }
    }

    private static void buscarRotas(int atualIndex, int fimIndex, List<Integer> rotaAtual, boolean[] visitado) {
        visitado[atualIndex] = true; //MARCA A CIDADE ATUAL COMO VISITADA E ADIICONA SEU ID A ROTA
        rotaAtual.add(atualIndex + 1); //ADICIONA O ID REAL (index + 1)

        if (atualIndex == fimIndex) { //CASO BASE - CHEGOU AO DESTINO
            //IMPRIME A ROTA COMPLETA
            System.out.println("-> Rota encontrada: " + rotaAtual.toString().replace("[", "").replace("]", "").replace(", ", " -> "));
        } else { //CASO NÃO TENHA ENCONTRADO UMA ROTA, CONTINUA A BUSCA
            for (int v = 0; v < numCidades; v++) {
                if (matrizAdj[atualIndex][v] == 1 && !visitado[v]) {

                    buscarRotas(v, fimIndex, rotaAtual, visitado);
                }
            }
        }

        //DESFAZ O ESTADO DA CIDADE PARA QUE ELA POSSA SER USADA EM OUTRAS ROTAS
        //REMOVE A CIDADE ATUAL DA ROTA E MARCA A CIDADE COMO NÃO VISITADA
        rotaAtual.remove(rotaAtual.size() - 1);
        visitado[atualIndex] = false;
    }

    private static double calcularDistancia(int index1, int index2){ //CALCULA A DISTÂNCIA EUCLIDIANA ENTRE 2 CIDADES PELOS SEUS ÍNDICES
        Cidade c1 = cidades.get(index1);
        Cidade c2 = cidades.get(index2);

        int dx = c1.getX() - c2.getX();
        int dy = c1.getY() - c2.getY();

        return Math.sqrt(dx * dx + dy * dy);
    }

    //FUNÇÃO QUE EXIBE O MENU INICIAL
    public static void exibirMenu() {
        System.out.println("\n--- Simulador de Gerenciamento de Rotas (MD) ---");
        System.out.println("1. 📥 Carregar Pontos de Interesse (instancia.txt)");
        System.out.println("2. 🔗 Definir Conexões (Matriz de Adjacência)");
        System.out.println("3. 🔎 Encontrar e Exibir Rotas (Recursão)");
        System.out.println("4. 💰 Resolver Caixeiro Viajante (TSP)");
        System.out.println("5. 📊 Análise Combinatória");
        System.out.println("6. ❌ Sair");
        System.out.print("Escolha uma opção: ");
    }

}


