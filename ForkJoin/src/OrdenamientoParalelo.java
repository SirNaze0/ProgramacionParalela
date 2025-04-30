import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class OrdenamientoParalelo extends RecursiveAction {

    private int[] arreglo;
    private int inicio;
    private int fin;
    private int umbral;

    public OrdenamientoParalelo(int[] arreglo, int inicio, int fin, int umbral) {
        this.arreglo = arreglo;
        this.inicio = inicio;
        this.fin = fin;
        this.umbral = umbral;
    }

    @Override
    protected void compute() {
        if (fin - inicio <= umbral) {
            ordenamientoSecuencial(arreglo, inicio, fin);
        } else {
            int medio = (inicio + fin) / 2;
            OrdenamientoParalelo tareaIzquierda = new OrdenamientoParalelo(arreglo, inicio, medio, umbral);
            OrdenamientoParalelo tareaDerecha = new OrdenamientoParalelo(arreglo, medio + 1, fin, umbral);

            invokeAll(tareaIzquierda, tareaDerecha);
            merge(arreglo, inicio, medio, fin);
        }
    }

    private void ordenamientoSecuencial(int[] arreglo, int inicio, int fin) {
        Arrays.sort(arreglo, inicio, fin + 1);
    }

    private void merge(int[] arreglo, int inicio, int medio, int fin) {
        int[] temp = new int[fin - inicio + 1];
        int i = inicio, j = medio + 1, k = 0;

        while (i <= medio && j <= fin) {
            if (arreglo[i] <= arreglo[j]) {
                temp[k++] = arreglo[i++];
            } else {
                temp[k++] = arreglo[j++];
            }
        }

        while (i <= medio) temp[k++] = arreglo[i++];
        while (j <= fin) temp[k++] = arreglo[j++];

        for (i = 0; i < temp.length; i++) {
            arreglo[inicio + i] = temp[i];
        }
    }
}
