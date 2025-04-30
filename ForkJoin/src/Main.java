import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int n = 10;
        int[] arregloOriginal = new int[n];
        Random rand = new Random();

        // Generar arreglo aleatorio
        for (int i = 0; i < n; i++) {
            arregloOriginal[i] = rand.nextInt(100);
        }

        int[] arregloSecuencial = Arrays.copyOf(arregloOriginal, n);
        int[] arregloParalelo = Arrays.copyOf(arregloOriginal, n);

        System.out.println("Antes de ordenar:");
        imprimir(arregloOriginal);

        // Ordenamiento secuencial
        long inicioSec = System.nanoTime();
        Arrays.sort(arregloSecuencial);
        long finSec = System.nanoTime();
        System.out.println("\nDespués de ordenar secuencialmente:");
        imprimir(arregloSecuencial);
        System.out.println("Tiempo secuencial: " + (finSec - inicioSec) + " ns");

        // Ordenamiento paralelo
        ForkJoinPool pool = new ForkJoinPool();
        OrdenamientoParalelo tarea = new OrdenamientoParalelo(arregloParalelo, 0, arregloParalelo.length - 1, 2);

        long inicioPar = System.nanoTime();
        pool.invoke(tarea);
        long finPar = System.nanoTime();

        System.out.println("\nDespués de ordenar en paralelo:");
        imprimir(arregloParalelo);
        System.out.println("Tiempo paralelo: " + (finPar - inicioPar) + " ns");
    }

    public static void imprimir(int[] arreglo) {
        for (int valor : arreglo) {
            System.out.print(valor + " ");
        }
        System.out.println();
        for (int i = 1; i <= arreglo.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
