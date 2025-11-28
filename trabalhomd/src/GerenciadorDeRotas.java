import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeRotas { //PERSISTENCIA
    private static List<Cidade> cidades = new ArrayList<>(); //ARRAY QUE ARMAZENA TODAS AS CIDADES
    private static int numCidades = 0; //NÚMERO DE CIDADES
    private static int[][] matrizAdj; //MATRIZ DE ADJACÊNCIA (Usada apenas para Opções 3 e 6)
    
    private static String Entrada(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static void lerArquivo(String nomeArquivo){
        System.out.println("LENDO O ARQUIVO: " + nomeArquivo + "...");
        cidades.clear(); //LIMPA DADOS ANTERIORES

        try(Scanner input = new Scanner(new File(nomeArquivo))){
            if(input.hasNextInt()){ //LEITURA DO NÚMERO DE CIDADES
                numCidades = input.nextInt();
                System.out.println("Total de cidades: " + numCidades);
                // Inicializa a matriz de adjacência (usada nas Opções 2, 3 e 6)
                matrizAdj = new int[numCidades][numCidades]; 
            } else { 
                System.err.println("ERRO: O arquivo fornecido não possui o número de cidades a serem manipuladas...");
                return;
            }
            for(int i = 0; i < numCidades; i++){ //LOOP QUE ADICIONA TODAS CIDADES CRIADAS AO ARRAYLIST
                if(input.hasNextInt()){ 
                    int x = input.nextInt();

                    //VERIFICA SE HÁ ALGUMA COORDENADA OU INFORMAÇÃO FALTANDO NO ARQUIVO
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
        } catch(FileNotFoundException e){  //ERRO -> ARQUIVO NÃO ENCONTRADO
            System.err.println("ERRO: Arquivo '" + nomeArquivo + "' não encontrado.");
        } catch(InputMismatchException e){  //ERRO -> FORMATO DO ARQUIVO INCORRETO
            System.err.println("ERRO: inconsistencias no formato do arquivo - " + e.getMessage());
        } catch (Exception e) { 
            System.err.println("ERRO: " + e.getMessage());
        }
    }

    public static void criarConexoes(Scanner consoleScanner){
        if(numCidades == 0) { //ERRO -> CRIE AS CONEXÕES ANTES DE REALIZAR QUALQUER OPERAÇÃO
            System.err.println("ERRO: PRIMEIRO CARREGUE AS CIDADES (OPCAO 1).");
            return;
        }
        System.out.println("\n === Definição de conexões ===");
        System.out.println("Cidades disponíveis (IDs): 1 a " + numCidades);
        System.out.println("Digite as conexões no formado 'Origem Destino' (ex: 1 5).");
        System.out.println("Digite FIM para finalizar.");

        while(true){
            String linha = Entrada(consoleScanner, "> Conexão (Origem Destino / FIM): ");

            if(linha.equalsIgnoreCase("FIM")){break;} 

            try{ 
                String[] partes = linha.split("\\s+");
                if(partes.length != 2){ //SE A STRING FORNECIDA TIVER TAMANHO MAIOR QUE DOIS É RETORNADO UM ERRO
                    System.err.println("Formato inválido. Use 'ID_Origem ID_Destino'.");
                    continue;
                }

                //CASTING DE VALORES CAPTURADOS ANTERIORMENTE
                int origemID = Integer.parseInt(partes[0]);
                int destinoID = Integer.parseInt(partes[1]);

                //VERIFICA SE AS CIDADES INFORMADAS PARA A CRIAÇÃO DA CONEXÃO EXISTEM
                if(origemID < 1 || origemID > numCidades || destinoID < 1 || destinoID > numCidades){
                    System.err.println("ID(s) inválido(s). Use IDs entre 1 e " + numCidades + ".");
                    continue;
                }

                int origemIndex = origemID - 1;
                int destinoIndex = destinoID - 1;

                // DEFINE A CONEXÃO NA MATRIZ DE ADJACÊNCIA
                matrizAdj[origemIndex][destinoIndex] = 1;

                System.out.println("CONEXÃO C" + origemID + " -> C" + destinoID + " criada.");
            } catch (NumberFormatException e){
                System.err.println("ENTRADA INVÁLIDA: IDs devem ser números inteiros.");
            }
        }
        System.out.println("\nConexões Finalizadas. Matriz de adjacência atualizada.");
    }

    public static long fatorial(int n){ // MÉTODO ATUALIZADO COM VALIDAÇÃO DE OVERFLOW
        if(n < 0){return 0;} //FATORIAL NÃO DEFINIDO PARA NÚMEROS NEGATIVOS
        
        if (n > 20) {
            System.err.println("ATENÇÃO: O fatorial de " + n + " excede o limite do tipo 'long'. Retornando 0.");
            return 0; 
        }
        
        if(n == 0 || n == 1) return 1; //0! = 1! = 1
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static long permutacao(int n, int k){
        if(n < 0 || k < 0 || k > n) return 0; //CONDIÇÕES INVÁLIDAS PARA PERMUTAÇÃO
        long nFatorial = fatorial(n);
        long nMenosKFatorial = fatorial(n - k);

        //VERIFICA SE O FATORIAL DO NUMERADOR OU DENOMINADOR EXCEDEU O LIMITE
        if(nFatorial == 0 || nMenosKFatorial == 0) return 0;

        return nFatorial / nMenosKFatorial;
    }

    public static long combinacao(int n, int k){
        if(n < 0 || k < 0 || k > n) return 0; //CONDIÇÕES INVÁLIDAS PARA COMBINAÇÃO
        long numerador = permutacao(n, k);
        long kFatorial = fatorial(k);

        if(kFatorial == 0) return 0;

        return numerador / kFatorial;
    }
    
    public static void analiseCombinatoria(Scanner consoleScanner) { //INTERAGE COM O USUÁRIO PARA CALCULAR PERMUTAÇÕES E COMBINAÇÕES
        System.out.println("\n--- Análise Combinatória de Eventos ---");

        try { //CAPTURA OS DADOS PARA A FUNÇÃO
            int n = Integer.parseInt(Entrada(consoleScanner, "Digite o número total de eventos (n): "));
            int k = Integer.parseInt(Entrada(consoleScanner, "Digite o número de eventos a serem visitados (k): "));

            if (n < 0 || k < 0 || k > n) { 
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

    public static void encontrarRotas(Scanner consoleScanner){ // MÉTODO ATUALIZADO PARA RECEBER O SCANNER
        if(numCidades == 0){ //ENCERRA A EXECUÇÃO DA FUNÇÃO CASO AS CIDADES AINDA NÃO TENHAM SIDO CARREGADAS
            System.err.println("ERRO: Primeiro carregue as cidades (OPÇÃO 1).");
            return;
        }

        System.out.println("\n--- BUSCA RECURSIVA DE ROTAS ---");
        try{
            int inicioID = Integer.parseInt(Entrada(consoleScanner, "Digite o ID da cidade de Partida (1 a " + numCidades + "): "));
            int fimID = Integer.parseInt(Entrada(consoleScanner, "Digite o ID da cidade de Chegada (1 a" + numCidades + "): "));

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

    //FAZER CAIXEIRO VIAJANTE

    public static void imprimirGrafo(){
        if(numCidades == 0){
            System.out.println("GRAFO VAZIO - Carregue as cidades (Opção 1) e crie as conexões (Opção 2)");
            return;
        }

        System.out.println("#== ESTADO ATUAL DO GRAFO (Cidades e Ligações) ==#");

        System.out.println("=> Cidades Carregadas:"); 
        for(Cidade c : cidades){System.out.println(" - " + c);} 

        System.out.println("\n=> Ligações (Relação binária R - Baseada em matrizAdj):");
        boolean temLigacao = false;

        for(int i = 0; i < numCidades; i++){
            List<String> vizinhos = new ArrayList<>();

            for(int j = 0; j < numCidades; j++){
                // Verifica a matriz de adjacência
                if(matrizAdj[i][j] == 1){ 
                    vizinhos.add("C" + (j + 1)); 
                    temLigacao = true;
                }
            }
            if (!vizinhos.isEmpty()) {
                System.out.printf("C%d -> { %s }\n", (i + 1), String.join(", ", vizinhos));
            }
        }
        if (!temLigacao) {
            System.out.println("Não há conexões definidas no grafo.");
        }
    }

    public static void exibirMenu() {
        System.out.println("\n--- Simulador de Gerenciamento de Rotas (MD) ---");
        System.out.println("1. 📥 Carregar Pontos de Interesse (instancia.txt)");
        System.out.println("2. 🔗 Definir Conexões (Matriz de Adjacência)");
        System.out.println("3. 🔎 Encontrar e Exibir Rotas (Recursão)");
        System.out.println("4. 💰 Resolver Caixeiro Viajante (TSP)");
        System.out.println("5. 📊 Análise Combinatória");
        System.out.println("6. 📋 Imprimir Cidades e Ligações"); 
        System.out.println("7. ❌ Sair");
        System.out.print("Escolha uma opção: ");
    }
}