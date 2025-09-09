/*

Experimento para analisarmos a influência da Cache!

Enquanto a localidade espacial diz que "se você acessou o endereço X, provavelmente acessará 
o X+1 em breve" (o caso da matriz por linha), a localidade temporal diz que "se você acessou
 o endereço X, provavelmente acessará o mesmo endereço X novamente em breve".

O cache da CPU explora isso mantendo os dados usados recentemente por perto, na expectativa
de que sejam necessários de novo.

Comparações neste experimento:
- Soma Espalhada: Somar elementos de um array que estão distantes um do outro. Isso terá uma
  péssima localidade espacial e temporal.
- Soma Repetida: Somar o valor de um único índice do array repetidamente. Este é um exemplo
  puro de localidade temporal.

 
*/
 

public class CacheLocalidadeTemporal {
    
    private static final int TAMANHO_ARRAY = 10_000; // Um array grande
    private static final int NUM_OPERACOES = 50_000_000; // Muitas operações
    private static final int[] dados = new int[TAMANHO_ARRAY];

    static {
        for (int i = 0; i < TAMANHO_ARRAY; i++) {
            dados[i] = (int) (Math.random() * 10);
        }
    }

    /**
     * Acessa elementos muito distantes no array.
     * A cada iteração, é provável que ocorra um "cache miss".
     */
    public static long somaEspalhada() {
        long soma = 0;
        int indice = 0; 
        // O salto garante que cada acesso esteja em uma página de memória diferente,
        // maximizando os cache misses.
        int salto = 4096; 
        for (int i = 0; i < NUM_OPERACOES; i++) {
            soma += dados[indice];       
            indice = (indice + salto) % TAMANHO_ARRAY; 
        }
        return soma;
    }

    /**
     * Acessa o MESMO elemento do array repetidamente.
     * Após o primeiro acesso (provável "miss"), os N-1 acessos seguintes
     * serão "cache hits" extremamente rápidos no cache L1.
     */
    public static long somaRepetida() {
        long soma = 0;
        for (int i = 0; i < NUM_OPERACOES; i++) {
            soma += dados[42];
        }
        return soma;
    }

    public static void main(String[] args) {
        System.out.println("Iniciando análise de localidade temporal...");

        System.out.println("Aquecendo a JVM...");      
        somaEspalhada();
        somaRepetida();        
        System.out.println("----------------------------------------------------------");
       
        // --- Teste 1: Péssima Localidade Temporal e Espacial ---
        long inicioEspalhada = System.nanoTime();
        long resEspalhada = somaEspalhada();
        long fimEspalhada = System.nanoTime();
        double duracaoEspalhada = (fimEspalhada - inicioEspalhada) / 1_000_000.0;
        System.out.printf("Tempo com acesso ESPALHADO: %.2f ms\n", duracaoEspalhada);

        // --- Teste 2: Excelente Localidade Temporal ---
        long inicioRepetida = System.nanoTime();
        long resRepetida = somaRepetida();
        long fimRepetida = System.nanoTime();
        double duracaoRepetida = (fimRepetida - inicioRepetida) / 1_000_000.0;
        System.out.printf("Tempo com acesso REPETIDO:  %.2f ms\n", duracaoRepetida);

        // Usamos as somas para garantir que o compilador não otimize os loops
        // removendo-os completamente.
        if (resEspalhada == resRepetida) System.out.println("Resultados iguais.");

        System.out.printf("A soma repetida foi %.2f vezes mais rápida!\n\n", 
                          duracaoEspalhada/duracaoRepetida);

    }

}