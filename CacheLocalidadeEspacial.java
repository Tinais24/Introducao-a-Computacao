/*
 
Experimento para analisarmos a influência da Cache!

CPU Cache
A CPU não lê dados diretamente da memória RAM, que é relativamente lenta. Em vez disso, 
ela possui pequenas áreas de memória super-rápidas chamadas caches (L1, L2, L3). 
Quando a CPU precisa de um dado, ela primeiro procura no cache.

Cache Hit (Acerto): O dado já está no cache. Acesso quase instantâneo.
Cache Miss (Falta): O dado não está no cache. A CPU precisa buscá-lo na lenta memória RAM. 
Isso causa uma penalidade de tempo significativa.

Linhas de Cache (Cache Lines)
Quando a CPU busca dados da RAM, ela não busca apenas o byte que você pediu. Ela busca um 
bloco contíguo de memória, chamado linha de cache (geralmente 64 bytes). A ideia é que, 
se você precisou de um dado, provavelmente precisará dos dados que estão logo ao lado dele 
em breve.


Experimento: Localidade Espacial e o Caso da Matriz
Em Java (e na maioria das linguagens C-like), uma matriz 2D é armazenada na memória de 
forma "linha-major" (row-major order). Isso significa que a segunda linha inteira vem logo
após a primeira linha na memória, e assim por diante.

Exemplo: matriz[0][0], matriz[0][1], ..., matriz[0][N], matriz[1][0], matriz[1][1], ...


*/


public class CacheLocalidadeEspacial {

    // Vamos definir um tamanho grande o suficiente para exceder o cache da CPU   
    static final int LINHAS = 10_000;
    static final int COLUNAS = 10_000;
    static final int[][] matriz = new int[LINHAS][COLUNAS];

    // Inicializa a matriz com valores aleatórios para que o compilador não otimize tudo.
    static {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                matriz[i][j] = (int) (Math.random() * 100);
            }
        }
    }

    /**
     * Percorre a matriz da forma mais eficiente, seguindo a ordem da memória.
     * Isso maximiza os "cache hits".
     */
    public static long percorrerPorLinha() {
        long soma = 0; // Usamos a soma para forçar a leitura do valor
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                soma += matriz[i][j];
            }
        }
        return soma;
    }

    /**
     * Percorre a matriz "pulando" na memória.
     * Isso causa um grande número de "cache misses".
     */
    public static long percorrerPorColuna() {
        long soma = 0; // Usamos a soma para forçar a leitura do valor
        for (int j = 0; j < COLUNAS; j++) {
            for (int i = 0; i < LINHAS; i++) {
                soma += matriz[i][j];
            }
        }
        return soma;
    }

    public static void main(String[] args) {
        System.out.println("Iniciando análise de tempo de execução para uma matriz " + 
                            LINHAS + " x"  + COLUNAS);
        System.out.println("----------------------------------------------------------");

        // --- Aquecimento da JVM ---
        // As primeiras execuções de um código em Java podem ser mais lentas
        // devido à compilação Just-In-Time (JIT). Executamos ambos os métodos
        // uma vez antes para "aquecer" e obter medições mais consistentes.
        System.out.println("Aquecendo a JVM...");
        percorrerPorLinha();
        percorrerPorColuna();        
        System.out.println("----------------------------------------------------------");


        // --- Medição do Tempo de Execução ---

        // Teste 1: Varredura por Linha (Cache-Friendly)
        long inicioLinha = System.nanoTime();
        long somaLinha = percorrerPorLinha();
        long fimLinha = System.nanoTime();
        double duracaoLinhaMs = (fimLinha - inicioLinha) / 1_000_000.0;
        System.out.printf("Tempo para percorrer por LINHA: %.2f ms\n", duracaoLinhaMs);


        // Teste 2: Varredura por Coluna (Cache-Unfriendly)
        long inicioColuna = System.nanoTime();
        long somaColuna = percorrerPorColuna();
        long fimColuna = System.nanoTime();
        double duracaoColunaMs = (fimColuna - inicioColuna) / 1_000_000.0;
        System.out.printf("Tempo para percorrer por COLUNA: %.2f ms\n", duracaoColunaMs);

        // Usamos as somas para garantir que o compilador não otimize os loops
        // removendo-os completamente.
        if (somaLinha != somaColuna) {
             System.out.println("As somas não batem, algo está muito errado!");
        }

        System.out.printf("Percorrendo por coluna demorou %.2f vezes mais!\n\n", 
                          duracaoColunaMs/duracaoLinhaMs);


    }

    
    
}