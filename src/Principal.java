/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 114 da aula do dia 15/09/2017 
 *
 * Página 142 Thomas H. Cormen 3 ed
 *
 * Algoritmo RadixSort/Ordenação Digital
 *
 * Atenção:
 * Vetor em java inicia em 0, os algoritmos consideram início em 1.
 * A subtração de -1 ocorre somente no local de acesso ao vetor ou matriz 
 * para manter a compatibilidade entre os algoritmos.


 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
public class Principal {

    /**
     * Retorna o maior elemento do vetor.
     * @param A vetor com os dados 
     * @return Um valor inteiro com o maior elemento do vetor
     */
    public static int maiorElemento(int[] A) {
        int maior = A[1-1];
        for (int i = 1; i <= A.length; i++) {
            if (A[i-1] > maior) {
                maior = A[i-1];
            }
        }
        return maior;
    }

    /**
     * Counting Sort.
     * Algoritmos de ordenação podem ser ou não in-place ou estáveis.
     * Um método de ordenação é estável se elementos iguais ocorrem no 
     * vetor ordenado na mesma ordem em que são passados na entrada.
     * O CountingSort é estável. 
     *
     * Complexidade para o pior caso Theta(k+n).
     * Complexidade para o caso médio/esperado Theta(k+n).
     * k pertence a O(n) portanto O(n)
     * 
     * @param A Vetor com os dados desordenados
     * @param B Vetor com os dados ordenados
     * @param n Quantidade de elementos do vetor
     * @param k Maior elemento do vetor A
     */
    public static void countingSort(int[] A, int[] B, int n, int exp) {

        //Maior elemento do vetor A na posição d        
        //Os indices vão de 0 a 9
        int k = 9;
        
        //Cria o vetor auxiliar C com base do maior elemento de A
        int C[] = new int[k+1];
        
        //Inicializar com zero o vetor auxiliar C
        for (int i = 0; i <= k; i++) {
            C[i] = 0;
        }

        //Realiza a contagem das ocorrencias
        //C[j] É o número de A[j] tais que A[j] = i
        //(102/1)%10=2 / (102/10)%10=0 / (102/100)%10=1
        //(121/1)%10=1 / (121/10)%10=2 / (121/100)%10=1
        //(221/1)%10=1 / (221/10)%10=2 / (221/100)%10=1
        for (int j = 1; j <= n; j++) {
            C[(A[j-1]/exp)%10] = C[(A[j-1]/exp)%10] + 1;
        }
        //Orrdenando os indices do vetor auxiliar C
        //C[i] é o número de js tais que A[j] 
        for (int i = 1; i <= k; i++) {
            C[i] = C[i] + C[i - 1];
        }
        // Classifica o vetor da direita para a esquerda
        // Procure no vetor de ocorrências a última ocorrência do valor dado
        // Coloca no vetor ordenado
        // Decrementa o índice da última ocorrência do valor dado
        // Continue com o valor anterior do vetor de entrada, termina se todos os valores já foram classificados
        // n - 1 pois vetor em java começa em 0 zero
        for (int j = n; j >= 1; j--) {
            B[C[(A[j-1]/exp)%10] - 1] = A[j-1];
            C[(A[j-1]/exp)%10] = C[(A[j-1]/exp)%10] - 1;            
        }
    }
    
    /**
     * RadixSort ou Ordenação Digital.
     * A complexidade depende do algoritmo estável usado para ordenar cada dígito.
     * Se essa complexidade for Theta(f(n)), obtemos uma complexidade total de Theta(d*f(n)).
     * Como d é uma constante a complexidade é Theta(f(n))
     * Para que Radix Sort funcione corretamente, ele deve usar um método de ordenação estável. 
     * A complexidade do CountingSort é Theta(n+k)
     * Complexidade para o pior caso Theta(d(n+k)).
     * Complexidade para o caso médio/esperado Theta(d(n+k)).
     * k pertence a O(n) portanto O(n)
     * 
     * @param A Vetor com os dados a serem ordenados
     * @param n Quantidade de elementos do vetor A
     */
    public static void radixSort(int[] A, int n){
         //Maior valor do vetor A
        int k = maiorElemento(A);
        //Vetor para receber os dados ordenados, com tamanho igual ao vetor A
        int B[] = new int[A.length];
        
        // Maior 713
        // Exp=1, 10 e 100        
        for (int exp = 1; k/exp >0; exp *= 10) {
            System.out.println("exp:"+exp);
            //Realiza o countsort para cada digito
            //Ao invés de passar cada dígito, é passado exp.
            ///Exp é 10^i onde i é digito atual
            countingSort(A,B,n,exp);
        }
        //Copia o vetor B para A
        System.arraycopy(B, 0, A, 0, n);
    }    

    public static void main(String args[]) {
        
        //Vetor dos dados    
        int A[] = {512, 713, 625, 102, 121, 328, 221, 449};

        //Tamanho do vetor
        int n = A.length;

        System.out.println(">>> Algoritmo RadixSort/Ordenação Digital <<<");
        System.out.println("Original: ");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + A[i]);
        }
     
        radixSort(A, n);

        System.out.println("Depois: ");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + A[i]);
        }
    }
}