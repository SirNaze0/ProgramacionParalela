import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParaleloPrimos {
    static final int N = 1000;
    static final int NUM_HILOS = 4;

    static List<Integer> primos = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        long inicioTiempo = System.nanoTime(); //Tiempo inicial
        int rangoPorHilo = (N + NUM_HILOS - 1) / NUM_HILOS; //Redondeo hacia arriba

        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < NUM_HILOS; i++) {
            int inicio = i * rangoPorHilo;
            int fin = Math.min(inicio + rangoPorHilo, N + 1);
            Thread hilo = new Thread(new BuscadorPrimos(inicio, fin));
            hilos.add(hilo);
            hilo.start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }
        long finTiempo = System.nanoTime(); //Tiempo final
        Collections.sort(primos); // Ordenamos los resultados
        System.out.println("Números primos hasta " + N + ": " + primos);
        System.out.println("Tiempo de ejecución paralelo: " + (finTiempo - inicioTiempo) / 1_000_000.0 + " ms");
    }

    static class BuscadorPrimos implements Runnable {
        int inicio, fin;

        BuscadorPrimos(int inicio, int fin) {
            this.inicio = inicio;
            this.fin = fin;
        }

        @Override
        public void run() {
            for (int i = inicio; i < fin; i++) {
                if (esPrimo(i)) {
                    primos.add(i);
                }
            }
        }

        private boolean esPrimo(int num) {
            if (num < 2) return false;
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) return false;
            }
            return true;
        }
    }
}
