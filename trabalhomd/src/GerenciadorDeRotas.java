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
            for(cidade c : cidades){
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
}


