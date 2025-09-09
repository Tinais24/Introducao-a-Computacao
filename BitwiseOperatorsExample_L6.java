public class BitwiseOperatorsExample_L6 {
    
    public static byte setBit(byte value, int bitPosition) {
        return (byte) (value | (1 << bitPosition));
    }
    
    public static byte resetBit(byte value, int bitPosition) {
        return (byte) (value & ~(1 << bitPosition));
    }
    
    public static byte twosComplement(byte value) {
        return (byte) (~value + 1);
    }
    
    public static int getBit(byte value, int position) {
        // Desloca o valor para a direita até o bit na posição desejada e faz uma
        // máscara com 1.

        return (value >> position) & 1;
    }

    /**
     * Converte um valor byte em sua representação binária (String).
     * Mostra todos os 8 bits do byte, incluindo os zeros à esquerda.
     */
    public static String byteToBinaryString(byte value) {

        // Usamos uma StringBuilder para construir a representação binária
        StringBuilder binary = new StringBuilder();

        // Itera sobre cada bit do byte, da posição mais alta (7) até a mais baixa (0)
        for (int i = 7; i >= 0; i--) {
            // Usa a função getBit para obter o valor do bit na posição 'i'
            int bit = getBit(value, i);
            binary.append(bit);
        }

        return binary.toString();
    }
        

    public static void main(String[] args) {

        /*
         * Operações bitwise
         * 
         * Operações bitwise são operações realizadas diretamente nos bits individuais
         * de números inteiros. Em vez de operar em números inteiros como um todo, as
         * operações bitwise manipulam cada bit na representação binária dos números.
         * Elas são fundamentais na programação de baixo nível, em otimização de código,
         * criptografia, manipulação de dados em sistemas embarcados, entre outros.
         */  

         /* 
         * Principais Operações Bitwise:
         * 
         * AND (&): Compara cada bit de dois números; o bit no resultado será 1 apenas
         * se ambos os bits correspondentes nos operandos forem 1. Caso contrário, será
         * 0.
         * Exemplo: 5 & 3 resulta em 1 (0101 & 0011 = 0001).
         * 
         * OR (|): Compara cada bit de dois números; o bit no resultado será 1 se
         * qualquer um dos bits correspondentes for 1.
         * Exemplo: 5 | 3 resulta em 7 (0101 | 0011 = 0111).
         * 
         * XOR (^): Compara cada bit de dois números; o bit no resultado será 1 se os
         * bits correspondentes forem diferentes.
         * Exemplo: 5 ^ 3 resulta em 6 (0101 ^ 0011 = 0110).
         * 
         * NOT (~): Inverte todos os bits do número, transformando 1 em 0 e vice-versa.
         * Exemplo: ~5 inverte 0101 para 1010 (representação binária de -6 em
         * complemento de 2).
         */

        byte a = 5; // 0000 0101 em binário                 
        byte b = 3; // 0000 0011 em binário
        byte c = (byte)0b11110000; // -16 em decimal (Complemento de 2)
        byte r;    // para armazenar os resultados 

        // As variáveis também poderia ser inicializadas no formato bináro
        /*
         * byte a = (byte)0b00000101; // 5
         * byte b = (byte)0b00000011; // 3 
        */

        System.out.println();
        System.out.println("a: " + "0b"+ byteToBinaryString(a) + " (" + a +" em decimal)" );
        System.out.println("b: " + "0b"+ byteToBinaryString(b) + " (" + b +" em decimal)" );
        System.out.println("c: " + "0b"+ byteToBinaryString(b) + " (" + c +" em decimal)" );
        System.out.println();
      
        // Demonstração de operações bitwise

        /*
         * Operador "E" bitwise (&)
         * Realiza uma operação AND bit a bit entre os dois bytes.
         * Cada bit na posição correspondente nos dois operandos precisa ser 1 para que
         * o bit no resultado seja 1.
         * Exemplo: 5 & 3
         * 5: 0000 0101
         * 3: 0000 0011
         * Resultado: 0000 0001 (1 em decimal)
         */

        r = (byte)(a & b);    
        System.out.println("a & b: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" ); 

         /*
         * Operador "OU" bitwise (|)
         * Realiza uma operação OR bit a bit entre os dois bytes.
         * Se qualquer bit em uma posição correspondente nos dois operandos for 1, o bit
         * no resultado será 1.
         * Exemplo: 5 | 3
         * 5: 0000 0101
         * 3: 0000 0011
         * Resultado: 0000 0111 (7 em decimal)
         */

        r = (byte)(a | b);  
        System.out.println("a | b: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        /*
        * Operador "XOR" bitwise (^)
        * Realiza uma operação XOR bit a bit entre os dois bytes.
        * O bit no resultado será 1 apenas se os bits correspondentes dos operandos
        * forem diferentes.
        * Exemplo: 5 ^ 3
        * 5: 0000 0101
        * 3: 0000 0011
        * Resultado: 0000 0110 (6 em decimal)
        */

        r = (byte)(a ^ b);  
        System.out.println("a ^ b: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        /*
        * Operador "NOT" bitwise (~)
        * Realiza uma operação NOT bit a bit, invertendo todos os bits         
        * Exemplo: ~5
        * 5: 0000 0101         
        * Resultado: 1111 1010  (-6 em decimal) (Complemento de 2)
        */

        r = (byte)(~a);  
        System.out.println("~a: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        
        // Demonstração de operações de deslocamento de bits
        System.out.println("\nOperações de deslocamento de bits\n");
       
        /*
        * Shift Left (<<): Desloca todos os bits de um número para a esquerda por um
        * determinado número de posições, preenchendo com 0 à direita.
        * 
        * Exemplo: 5 << 1 resulta em 0000 1010 (10 em decimal) 
        * Exemplo: 3 << 6 resulta em 1000 0000 (-128 em decimal) 
        */
        r = (byte)(a << 1);  
        System.out.println("a << 1: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        r = (byte)(b << 7);  
        System.out.println("b << 7: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );
      
        /* 
        * Shift Right (>>): Desloca todos os bits de um número para a direita por um
        * determinado número de posições, preenchendo com 0 ou 1 à esquerda, dependendo
        * do sinal.
        * 
        * Exemplo: 5 >> 1 resulta em 0000 0010 (2 em decimal)
        * Exemplo: 3 >> 3 resulta em 0000 0000 (0 em decimal)
        */

        r = (byte)(a >> 1);  
        System.out.println("a >> 1: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        r = (byte)(b >> 3);  
        System.out.println("b >> 3: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        r = (byte)(c >> 2);  
        System.out.println("c >> 1: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );


        // Demonstração de operações de set e reset de bits
        System.out.println("\nDemonstração de operações de set e reset de bits\n");

        /*
        * public static byte setBit(byte value, int bitPosition) {
        *     return (byte) (value | (1 << bitPosition));
        * }
        *
        * Define (seta) um bit específico em um valor.
        * Usa uma máscara que tem um 1 na posição desejada e faz uma operação OR  bitwise.
        * Isso garante que o bit na posição especificada será 1, sem alterar os outros
        * bits.
        * Exemplo: setBit(5, 1)
        * 5: 0000 0101
        * Posição 1: 0000 0010
        * Resultado: 0000 0111 (7 em decimal)
        */
        int bitPosition = 1;
        r = setBit(a, bitPosition);  
        System.out.println("setando o bit 1 de a: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        
        /*
        * public static byte resetBit(byte value, int bitPosition) {
        *    return (byte) (value & ~(1 << bitPosition));
        * }
        *
        * Reseta (limpa) um bit específico em um valor.
        * Usa uma máscara que tem um 0 na posição desejada (1 << bitPosition) e faz uma operação AND bitwise 
        * com o complemento (~) da máscara.
        * Isso garante que o bit na posição especificada será 0, sem alterar os outros bits.
        * Exemplo: resetBit(5, 0)
        * 5: 0000 0101
        * Posição 0: 0000 0001
        * Máscara invertida: 1111 1110
        * Resultado: 0000 0100 (4 em decimal)
        */
        bitPosition = 0;
        r = resetBit(a, bitPosition);  
        System.out.println("resetando o bit 0 de a: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );

        
        // Demonstração de complemento de 2
        System.out.println("\nDemonstração de complemento de 2\n");
        
        /*
        * public static byte twosComplement(byte value) {
        *     return (byte) (~value + 1);
        * }
        *
        * Calcula o complemento de 2 de um número byte.
        * Inverte todos os bits e adiciona 1. Usado para representar números negativos
        * em binário.
        * Exemplo: twosComplement(5)
        * 5: 0000 0101
        * ~5: 1111 1010
        * +1: 1111 1011 (valor -5 em complemento de 2) 
        */

        r = twosComplement(a);  
        System.out.println("complemento de 2 de a: " + "0b"+ byteToBinaryString(r) + " (" + r +" em decimal)" );
 
        // Demonstração de incremento até overflow
        
        System.out.println("\nDemonstração de incremento até overflow\n");
        
        /*
        * Incrementa um número byte até que ele se torne negativo devido a overflow.
        * Inicia próximo ao valor máximo de um byte e continua incrementando até que o
        * valor passe do limite e "transborde" para o negativo.
        * Exemplo: 127 (0111 1111) + 1 -> -128 (overflow)
        */
       
        byte value = 120; // Inicia próximo ao limite positivo de um byte

        System.out.print("Iniciando incremento a partir de: " + value);
        System.out.println(" (0b"+ byteToBinaryString(value) + ")");

        for (int i = 0; i < 10; i++) { // Limite o loop para evitar overflow infinito
            value++;
            System.out.print("Valor atual: " + value);
            System.out.println(" (0b"+ byteToBinaryString(value) + ")");
            if (value < 0) {
                System.out.println("Overflow detectado! Valor se tornou negativo: " + value);
                break;
            }
        }
        
    }

}