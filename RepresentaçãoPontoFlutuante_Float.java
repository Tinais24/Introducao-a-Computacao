import java.math.BigDecimal;
import java.util.Scanner;

public class RepresentaçãoPontoFlutuante_Float {
 

    public static void teste1() {
        // Problema de Representação

        float a = 1000.43f;
    	float b = 1000.0f;
        float c = a +b;
        System.out.printf("%f",c);     
    }   

    public static void teste2() {
        // Valores que esperamos que somem 0.3
        double a = 0.1;
        double b = 0.2;
        double soma = a + b;

        double esperado = 0.3;

        System.out.println("=== Erro de Arredondamento na Soma (Double) ===");
        System.out.println("Valor de a (0.1): " + a);
        System.out.println("Valor de b (0.2): " + b);
        System.out.println("Resultado da soma (a + b): " + soma);
        System.out.println("Resultado Esperado: " + esperado);
        
        // A comparação direta falha
        System.out.println("\nsoma == esperado? " + (soma == esperado));

        // Diferença (o erro de arredondamento)
        double erro = soma - esperado;
        System.out.println("Diferença (Erro): " + erro);
        System.out.println("\nIsso ocorre porque 0.1 e 0.2 não têm representação binária exata no ponto flutuante!\n\n");
                
    }

    public static void teste3() {
        // Exemplo de erro acumulado em loop
        double somaAcumulada = 0.0;
        for (int i = 0; i < 1000; i++) {
            somaAcumulada += 0.1;
        }
        System.out.println("\n--- Erro Acumulado (10 x 0.1) ---");
        System.out.println("Soma Acumulada: " + somaAcumulada);
        System.out.println("Esperado: 1.0");
        System.out.println("Diferença (Erro): " + (somaAcumulada - 1.0));
    
    }

    public static void teste4() {
        // Erro de Arredondamento na Multiplicação
        // Exemplo: 0.7 * 10
        double a = 0.7;
        double b = 10.0;
        double multiplicacao = a * b;

        double esperado = 7.0;

        System.out.println("=== Erro de Arredondamento na Multiplicação (Double) ===");
        System.out.println("Valor de a (0.7): " + a);
        System.out.println("Valor de b (10.0): " + b);
        System.out.println("Resultado da Multiplicação (a * b): " + multiplicacao);
        System.out.println("Resultado Esperado: " + esperado);
        
        // A comparação direta pode falhar
        System.out.println("multiplicacao == esperado? " + (multiplicacao == esperado));

        // Exemplo de erro na multiplicação de valores pequenos
        double x = 0.3;
        double y = 3.0;
        double produto = x * y;
        double produtoEsperado = 0.9;
        
        System.out.println("\n--- Outro Exemplo: 0.3 * 3 ---");
        System.out.println("Resultado: " + produto);
        System.out.println("Esperado: " + produtoEsperado);
        System.out.println("Diferença: " + (produto - produtoEsperado));
    }

    public  static void teste5() {
        System.out.println("=== Calculando o Épsilon de Máquina em Java ===");
        System.out.println("O Épsilon é o menor número 'e' > 0 tal que 1.0 + e > 1.0");
        

        float epsilon = 1.0f;
        int iteracoes = 0;
        
        // Loop: enquanto 1.0 + (epsilon / 2) for diferente de 1.0, 
        // significa que epsilon / 2 ainda está "visível" na soma.
        // O loop para quando o valor é perdido por arredondamento.
        do {
            epsilon /= 2.0f;
            iteracoes++;
        } while ((1.0f + (epsilon / 2.0f)) != 1.0f);
        
        System.out.println("\n--- Tipo float (Precisão Simples) ---");
        System.out.println("Épsilon Calculado: " + epsilon);
        // O valor teórico é 2^(-23)
        System.out.printf("Em notação científica: %.9e\n", epsilon);
        System.out.println("Verificação (1.0 + e): " + (1.0f + epsilon));
        System.out.println("Verificação (1.0 + e/2): " + (1.0f + (epsilon / 2.0f)));
        System.out.println("Número de iterações: " + iteracoes);

        /*
        Aplicações: Este é o motivo pelo qual, ao comparar números de ponto flutuante, 
        nunca se deve usar a == b. Em vez disso, a comparação correta é verificar se a 
        diferença absoluta entre eles está dentro da tolerância do Épsilon de Máquina 
        (ou de um Épsilon definido pelo usuário): Math.abs(a - b) < epsilon.
        */

    }

    public  static void teste6() {
        // Representação Binária de valores Float

        // 1. NÚMERO NORMAL (Exemplo Padrão)
        // 12.5 = 1.1001 x 2^3. Expoente Bias: 3 + 127 = 130 (10000010)
        exibirFloatBinario(12.5f);

        // 2. ZERO POSITIVO E NEGATIVO
        // 0.0 é um caso especial (denormalizado). E = 0, M = 0.
        exibirFloatBinario(0.0f);
        exibirFloatBinario(-0.0f); 

        // 3. INFINITO POSITIVO E NEGATIVO
        // Infinity é um caso especial: E = 255, M = 0.
        exibirFloatBinario(Float.POSITIVE_INFINITY);
        exibirFloatBinario(Float.NEGATIVE_INFINITY);
        
        // (BÔNUS) 4. NaN (Not a Number)
        // NaN é um caso especial: E = 255, M != 0.
        exibirFloatBinario(Float.NaN);
        
        // (BÔNUS) 5. Erro de Arredondamento
        // O valor armazenado na mantissa é a representação truncada/arredondada dessa sequência infinita
        exibirFloatBinario(0.1f);        
        exibirFloatBinario(0.4f);
    }

    public  static void teste7() {
        System.out.println("=== Reconstruindo Floats a Partir da Representação Binária ===");
        System.out.println();
        
        // Exemplo 1: 12.5 (0 | 10000010 | 10010000000000000000000)
        System.out.println("Exemplo 1: 12.5 (0 | 10000010 | 10010000000000000000000)");
        String binario12_5 = "0 10000010 10010000000000000000000";
        float valor1 = binarioParaFloat(binario12_5);
        System.out.printf("Binário: %s -> Valor: %.2f\n", binario12_5, valor1);
        System.out.println();
        
        // Exemplo 2: -1.0
        // (-1)^1 * (1.0) * 2^(127 - 127) = -1. Expoente Bias: 127 (01111111)
        System.out.println("Exemplo 2: -1.0");
        System.out.println("(-1)^1 * (1.0) * 2^(127 - 127) = -1. Expoente Bias: 127 (01111111)");
        String binarioMenosUm = "1 01111111 00000000000000000000000";
        float valor2 = binarioParaFloat(binarioMenosUm);
        System.out.printf("Binário: %s -> Valor: %.2f\n", binarioMenosUm, valor2);
        System.out.println();

        // Exemplo 3: Zero Positivo (Caso Especial)
        System.out.println("Exemplo 3: Zero Positivo (Caso Especial)");
        String binarioZeroPositivo = "0 00000000 00000000000000000000000";
        float valor3 = binarioParaFloat(binarioZeroPositivo);
        System.out.printf("Binário: %s -> Valor: %.1f\n", binarioZeroPositivo, valor3);
        System.out.println();

        // Exemplo 4: Infinito Negativo (Caso Especial)
        // Sinal 1, Expoente 255 (11111111), Mantissa 0
        System.out.println("Exemplo 4: Infinito Negativo (Caso Especial)");
        System.out.println("Sinal 1, Expoente 255 (11111111), Mantissa 0");
        String binarioInfinitoNegativo = "1 11111111 00000000000000000000000";
        float valor4 = binarioParaFloat(binarioInfinitoNegativo);
        System.out.printf("Binário: %s -> Valor: %s\n", binarioInfinitoNegativo, valor4);
        System.out.println();
        
        // Exemplo 5: NaN (Not a Number) (Caso Especial)
        // Sinal 0/1, Expoente 255 (11111111), Mantissa NÃO-ZERO
        System.out.println("Exemplo 5: NaN (Not a Number) (Caso Especial)");
        System.out.println("Sinal 0/1, Expoente 255 (11111111), Mantissa NÃO-ZERO");
        String binarioNaN = "0 11111111 00000000000000000000001";
        float valor5 = binarioParaFloat(binarioNaN);
        System.out.printf("Binário: %s -> Valor: %s\n", binarioNaN, valor5);
        System.out.println();        
    }


        // Função para extrair e exibir os componentes IEEE 754
    public static void exibirFloatBinario(float valor) {
        // 1. Converte o float de 32 bits para um int de 32 bits (mantendo o padrão de bits)
        int bits = Float.floatToIntBits(valor);
        
        // 2. Extrai as partes usando operações bit a bit
        
        // Bit 31: Sinal (1 bit)
        int sinal = (bits >>> 31); // Desloca 31 bits para a direita (pega o bit mais significativo)
        
        // Bits 30-23: Expoente (8 bits)
        int expoenteBits = (bits >>> 23) & 0xFF; // Desloca 23, depois aplica máscara 0xFF (binário 11111111)
        
        // Bits 22-0: Mantissa/Fração (23 bits)
        int mantissaBits = bits & 0x7FFFFF; // Aplica máscara 0x7FFFFF (binário 0111... - 23 uns)

        // 3. Formata as strings binárias
        String binarioCompleto = String.format("%32s", Integer.toBinaryString(bits)).replace(' ', '0');
        String binSinal = binarioCompleto.substring(0, 1);
        String binExpoente = binarioCompleto.substring(1, 9);
        String binMantissa = binarioCompleto.substring(9, 32);

        System.out.printf("Valor: %s\n", valor);
        System.out.println("------------------------------------");
        System.out.println("IEEE 754 (32 bits): " + binSinal + " | " + binExpoente + " | " + binMantissa);
        System.out.println("                    Sinal | Expoente | Mantissa (Fração)");
        System.out.println("------------------------------------");
        System.out.printf("Sinal: %d (%s)\n", sinal, (sinal == 0 ? "Positivo" : "Negativo"));
        System.out.printf("Expoente (Bias): %d (Exp. Real: %d)\n", expoenteBits, (expoenteBits - 127));
        if (expoenteBits != 0 && expoenteBits != 255) {
             System.out.printf("Mantissa: 1.%s (o 1 é implícito)\n", binMantissa);
        } else {
             System.out.printf("Mantissa: %s (não tem 1 implícito)\n", binMantissa);
        }
        System.out.println("\n");
    }

    public static float binarioParaFloat(String binario) {
        // Remove quaisquer espaços ou separadores para garantir que a string tenha 32 bits
        String bitsLimpos = binario.replaceAll("\\s", "");
        // Converte a string binária (Base 2) para um inteiro de 32 bits.
        // Usamos parseUnsignedInt para tratar corretamente o bit de sinal (posição 31)
        // como parte do valor, e não como um sinal para o valor Int.
        int bitsInt = Integer.parseUnsignedInt(bitsLimpos, 2);

        // Usa o método nativo de Java para interpretar esses 32 bits
        // como o formato de ponto flutuante IEEE 754.
        return Float.intBitsToFloat(bitsInt);
    }


    public  static void teste8() {

       System.out.println("==================================================================================");
        System.out.println(" Comparação entre Ponto Flutuantes com Tolerância (Epsilon) em Java (IEEE 754) ");
        System.out.println("==================================================================================");

        // --- PARTE 1: ERROS CLÁSSICOS E FALHA DA COMPARAÇÃO DIRETA (double) ---
        System.out.println("\n--- PARTE 1: FALHA DA COMPARAÇÃO (UTILIZANDO DOUBLE) ---");
        System.out.println("O PROBLEMA: Decimais (Base 10) não são exatos em Binário (Base 2).");
        System.out.println("Usamos double (64 bits) pois ele REVELA o erro na comparação direta.");
        
        // **VALORES DOUBLE PARA FORÇAR O ERRO**
        double resultadoCalculado = 0.1 + 0.2;
        double valorEsperado = 0.3;

        System.out.printf("\nResultado Calculado (0.1 + 0.2): %.17f\n", resultadoCalculado);
        System.out.printf("Valor Esperado (0.3):          %.17f\n", valorEsperado);

        // A. O erro que o operador '==' comete (AGORA VAI DAR FALSE)
        boolean comparacaoDireta = (resultadoCalculado == valorEsperado);
        System.out.printf("\nComparação Direta (==): %b\n", comparacaoDireta);
        System.out.println("MOTIVO CORRETO: O resultado calculado é 0.30000000000000004 e o esperado é 0.3.");
        System.out.println("Os padrões de bits são diferentes, por isso '==' retorna FALSE.");
        
        System.out.println("\n----------------------------------------------------");

        // --- PARTE 2: A Solução com Epsilon (Tolerância) ---
        System.out.println("=== 2. A SOLUÇÃO CORRETA: Comparação com Tolerância (Epsilon) ===");
        System.out.printf("Tolerância (Epsilon) Usada: %.17f (1e-15)\n", TOLERANCIA_PADRAO_DOUBLE);
        
        // B. A comparação correta usando a função
        boolean comparacaoAproximada = saoQuaseIguais(resultadoCalculado, valorEsperado, TOLERANCIA_PADRAO_DOUBLE);
        System.out.printf("\nComparação com Tolerância: %b\n", comparacaoAproximada);
        System.out.printf("Diferença Absoluta: %.17e\n", Math.abs(resultadoCalculado - valorEsperado));
        System.out.println("Conclusão: A diferença é menor que o Epsilon, logo os valores são equivalentes.");
        
        // ----------------------------------------------------
        
        // --- PARTE 3: O Perigo de Usar um Epsilon MUITO GRANDE ---
        System.out.println("\n----------------------------------------------------");
        System.out.println("=== 3. O Perigo do Epsilon Inapropriado ===");
        double valorA = 1000.0;
        double valorB = 1000.00001; // Diferença de 1e-5
        double epsilonGrande = 0.01; // Epsilon obviamente muito grande para um erro de máquina

        boolean comparacaoErrada = saoQuaseIguais(valorA, valorB, epsilonGrande);
        
        System.out.printf("Valor A: %.5f | Valor B: %.5f\n", valorA, valorB);
        System.out.printf("Tolerância Grande: %.2f\n", epsilonGrande);
        System.out.printf("Diferença real: %.10e\n", Math.abs(valorA - valorB));
        System.out.printf("Comparação (A e B): %b\n", comparacaoErrada);
        System.out.println("MOTIVO: A função retornou 'true' porque a diferença de 1e-5 é menor que a tolerância de 0.01.");
        System.out.println("Isto é um ERRO lógico: a tolerância deve ser pequena o suficiente para capturar apenas erros de máquina.");

        // --- PARTE 4: SOLUÇÃO PARA PRECISÃO EXATA E BINÁRIO ---
        System.out.println("\n----------------------------------------------------");
        System.out.println("=== 4. SOLUÇÃO E CONTEXTO (BigDecimal e Binário) ===");
        
        // SOLUÇÃO PARA CÁLCULOS EXATOS
        // O java.math.BigDecimal é uma classe em Java utilizada para realizar cálculos com precisão decimal exata, eliminando 
        // os erros de arredondamento inerentes aos tipos primitivos de ponto flutuante (float e double). 
        // - Uso Principal: Cálculos Financeiros,monetários, tributários e qualquer aplicação onde erros de arredondamento não são inaceitáveis.
        // - Desvantagem: Performance.
        // - Base 10
        System.out.println("\n[A] Solução para Precisão EXATA (Ex: Dinheiro):");
        BigDecimal bdA = new BigDecimal("0.1"); 
        BigDecimal bdB = new BigDecimal("0.2");
        BigDecimal bdSoma = bdA.add(bdB);
        System.out.printf("BigDecimal (0.1 + 0.2): %s\n", bdSoma);
        System.out.printf("Comparação exata (equals): %b\n", bdSoma.equals(new BigDecimal("0.3")));
        
               
        System.out.println("\n==================================================================================");
    }
    
    
    // Epsilon de Máquina para Double (Aproximado) - usado para comparação com double.
    // O valor teórico (2^-52) é aproximadamente 2.22e-16. Usamos 1e-15 como tolerância segura.
    private static final double TOLERANCIA_PADRAO_DOUBLE = 0.000000000000001; // 1e-15

    /**
     * Função CORRETA para comparar dois doubles usando uma tolerância (Epsilon).
     */
    public static boolean saoQuaseIguais(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }




    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Resumo dos testes disponíveis:");
            System.out.println("1: Demonstra uma operação aritmética simples com 'float'.");
            System.out.println("2: Mostra o erro de arredondamento ao somar 0.1 e 0.2, que não têm representação binária exata.");
            System.out.println("3: Ilustra como pequenos erros de precisão se acumulam ao somar 0.1 repetidamente em um loop.");
            System.out.println("4: Demonstra o erro de arredondamento em operações de multiplicação, como 0.7 * 10.");
            System.out.println("5: Calcula e explica o 'Épsilon de Máquina', o menor valor que, somado a 1.0, ainda é distinguível de 1.0.");
            System.out.println("6: Análise de Representação de Ponto Flutuante em Binário.");
            System.out.println("7: Reconstruir Float a Partir de Binário");
            System.out.println("8: Comparação entre Ponto Flutuantes");
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("Escolha o teste para executar (1-8)");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    teste1();
                    break;
                case 2:
                    teste2();
                    break;
                case 3:
                    teste3();
                    break;
                case 4:
                    teste4();
                    break;
                case 5:
                    teste5();
                    break;      
                case 6:
                    teste6();
                    break;  
                case 7:
                    teste7();
                    break;                
                case 8:
                    teste8();
                    break;      
                default:
                    System.out.println("Opção inválida. Nenhum teste foi executado.");
                    break;
            }
        }
    }   
}