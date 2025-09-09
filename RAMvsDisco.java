/*
 
Experimento RAM vs. Disco:

Vamos fazer um programa faz o seguinte:
- Cria um arquivo grande (ex: 512 MB) no disco com dados aleatórios.
- Mede o tempo para ler todo o conteúdo do arquivo do disco. 
  - Faremos isso duas vezes para observar um efeito interessante.
- Carrega todo o arquivo para um array de bytes na memória RAM.
- Mede o tempo para "ler" (percorrer) esse array de bytes que já está na RAM.
- Compara os tempos e observa a enorme diferença.

*/

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class RAMvsDisco {
    

    private static final String NOME_ARQUIVO = "arquivo_temporario.dat";
    // Arquivo com 512 MB. Grande o suficiente para a medição ser significativa.
    private static final int TAMANHO_ARQUIVO_BYTES = 512 * 1024 * 1024;
    private static final File arquivo = new File(NOME_ARQUIVO);


    public static void criarArquivoTemporario() throws IOException {
        System.out.printf("Criando arquivo temporário de %d MB...\n", 
                          TAMANHO_ARQUIVO_BYTES / (1024 * 1024));
        long inicio = System.currentTimeMillis();
        
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {
            byte[] buffer = new byte[8192]; // Buffer de 8 KB
            Random random = new Random();
            for (int i = 0; i < TAMANHO_ARQUIVO_BYTES / buffer.length; i++) {
                random.nextBytes(buffer);
                fos.write(buffer);
            }
        }
        long fim = System.currentTimeMillis();
        System.out.printf("Arquivo criado em %d ms.\n\n", (fim - inicio));
    }

    public static long testeLeituraDiscoCacheFrio() throws IOException {
        System.out.println("--- Teste 1: Lendo do Disco (Cache Frio) ---");
        long inicio = System.currentTimeMillis();
        long bytesLidos = 0;

        try (FileInputStream fis = new FileInputStream(arquivo);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bytesLidos += bytesRead;
            }
        }
        
        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;
        System.out.printf("Lidos %d MB do disco em %d ms.\n\n", 
                          bytesLidos / (1024 * 1024), tempo);
        return tempo;
    }

    public static long testeLeituraDiscoCacheQuente() throws IOException {
        System.out.println("--- Teste 2: Lendo do Disco (Cache Quente do S.O.) ---");
        long inicio = System.currentTimeMillis();
        long bytesLidos = 0;

        try (FileInputStream fis = new FileInputStream(arquivo);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bytesLidos += bytesRead;
            }
        }
        
        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;
        System.out.printf("Lidos %d MB (do cache do S.O.) em %d ms.\n\n", 
                          bytesLidos / (1024 * 1024), tempo);
        return tempo;
    }
    
    public static long testeLeituraRAM() throws IOException {
        System.out.println("--- Teste 3: Lendo da Memória RAM ---");
        
        // Primeiro, carregamos o arquivo para a RAM. Essa parte AINDA usa o disco.
        byte[] dadosEmMemoria = Files.readAllBytes(arquivo.toPath());
        
        long inicio = System.currentTimeMillis();
        long soma = 0; // Usamos uma soma para garantir que o compilador não otimize o loop
        
        // Agora, percorremos o array que já está na RAM.
        for (byte b : dadosEmMemoria) {
            soma += b;
        }

        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;
        System.out.printf("Lidos %d MB da RAM em %d ms. (Soma de verificação: %d)\n\n", 
                          dadosEmMemoria.length / (1024 * 1024), (fim - inicio), soma);
        return tempo;
    }
    
    public static void deletarArquivoTemporario() {
        if (arquivo.exists()) {
            if (arquivo.delete()) {
                System.out.println("Arquivo temporário deletado com sucesso.");
            } else {
                System.err.println("Falha ao deletar o arquivo temporário.");
            }
        }
    }
    
    public static void main(String[] args) {
        long tempoDisco, tempoRAM;
        try {
            // Etapa 1: Preparação
            if (!arquivo.exists()) {
                criarArquivoTemporario();
                return;
            }            

            // Etapa 2: Teste de Leitura do Disco (1ª vez - "Cache Frio")
            tempoDisco = testeLeituraDiscoCacheFrio();

            // Etapa 3: Teste de Leitura do Disco (2ª vez - "Cache Quente")
            testeLeituraDiscoCacheQuente();

            // Etapa 4: Teste de Leitura da RAM
            tempoRAM = testeLeituraRAM();

            System.out.printf("Acessar dados no disco foi cerca de %.2f vezes mais lento do que acessá-los na RAM!\n\n",
                              (float)tempoDisco/(float)tempoRAM);

        } catch (IOException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
        } finally {
            // Etapa 5: Limpeza
            // deletarArquivoTemporario();
        }
    }
}