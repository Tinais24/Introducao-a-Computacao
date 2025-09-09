/*
 
Experimento: O Efeito do Tamanho da Linha de Cache (Cache Line Size)

Já sabemos que a memória é trazida para o cache em "linhas" (geralmente de 64 bytes). 
Isso significa que, se você acessa um int (4 bytes), na verdade os próximos 15 ints 
(totalizando 16 ints = 64 bytes) também são carregados para o cache.

Neste experimento, vamos varrer um array grande com diferentes "saltos" (strides). 
A teoria é (o que esperamos...):
- Saltos pequenos (de 1 a 15) deveriam ter um desempenho semelhante, pois cada novo 
  acesso provavelmente já estará na linha de cache carregada pelo acesso anterior.
- Quando o salto ultrapassar o tamanho da linha de cache (ex: 16 ints), cada acesso
  forçará o carregamento de uma nova linha de cache, e o desempenho deve cair drasticamente.

*/

public class CacheTamanhoLinha {

    private static final int TAMANHO_ARRAY = 128 * 1024 * 1024; // Array grande (512MB)
    private static final int[] dados = new int[TAMANHO_ARRAY];

    // Número de execuções para medir e tirar a média
    private static final int ITERACOES_MEDICAO = 20;

    // Variável somente para consumir o resultado e evitar que o compilador otimize o código
    public static volatile long buracoNegro;

    // Bloco estático para inicializar o vetor com dado
    static {
        for (int i = 0; i < dados.length; i++) {
            // Usamos o módulo para evitar que a soma estoure facilmente
            dados[i] = (int) (Math.random() * 256);
        }
    }

    public static long acessarComSalto(int salto) {
        long soma = 0;
        for (int i = 0; i < dados.length; i += salto) {
            soma += dados[i];
        }
        return soma;
    }

    public static void executarTeste(int salto) {
        // Aquecimento (Warm-up) 
        // Executa o código várias vezes para que o compilador JIT possa otimizá-lo
        for (int i = 0; i < 10; i++) {
            buracoNegro = acessarComSalto(salto); // Consome o resultado
        }

        // Medição 
        long nanosTotais = 0;
        // Executa o teste várias vezes e acumula o tempo
        for (int i = 0; i < ITERACOES_MEDICAO; i++) {
            long inicio = System.nanoTime();
            buracoNegro = acessarComSalto(salto); // Consome o resultado
            long fim = System.nanoTime();
            nanosTotais += (fim - inicio);
        }

        // Apresentação dos Resultados
        long nanosMedios = nanosTotais / ITERACOES_MEDICAO;
        
        long bytesAcessados = (long) (TAMANHO_ARRAY / salto) * Integer.BYTES;
        
        // A conversão de (bytes / nanossegundos) para (GB / segundos) é direta,
        // pois ambos os prefixos (giga e nano) representam 10^9.
        double gbs = (double) bytesAcessados / nanosMedios;

        System.out.printf("Salto: %2d ints | Tempo médio: %6.2f ms | Largura de Banda: %.2f GB/s (Soma: %d)\n",
                salto, nanosMedios / 1_000_000.0, gbs, buracoNegro);
    }
   
    public static void main(String[] args) {
      

        System.out.println("Analisando o impacto do salto (stride) no acesso à memória.");
        System.out.println("O desempenho deve cair quando o salto cruzar a fronteira da linha de cache (geralmente 16 ints = 64 bytes).");
        System.out.println("-------------------------------------------------------------------------");

        // Testa diferentes tamanhos de salto
        for (int salto = 1; salto <= 128; salto *= 2) {
            executarTeste(salto);
        }
    }
    
}