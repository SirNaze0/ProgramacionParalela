public class SecuencialPrimos {
    public static void main(String[] args) {
        int N = 1000;
        long inicioTiempo = System.nanoTime();//Tiempo inicial

        System.out.print("Números primos hasta " + N + ": ");
        for (int i = 0; i <= N; i++) {
            if (esPrimo(i)) {
                System.out.print(i + " ");
            }
        }
        long finTiempo = System.nanoTime(); //Tiempo final

        System.out.println("\n Tiempo de ejecución secuencial: " + (finTiempo - inicioTiempo) / 1_000_000.0 + " ms");
    }

    private static boolean esPrimo(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}