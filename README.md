# ProgramacionParalela

**JDK 21**

Este proyecto demuestra el uso de programación paralela en Java utilizando hilos y el framework `ForkJoinPool`. Se divide en dos partes:

---

## 📁 Carpeta: `Pro_Paralela`

### 🧠 Descripción

En esta sección se realiza la **búsqueda de números primos** desde cero (0) hasta un valor `N`, de dos maneras:

- Deseamos encontrar los números primos es de cero (0) hasta un valor N de forma secuencial y
realizar la operación de búsqueda de números primos en hilos de ejecución de los N en forma
paralelo para utilizar múltiples hilos de ejecución y obtener un mejor tiempo de respuesta.


---

## 📁 Carpeta: `ForkJoin`

### 🧠 Descripción

En esta sección se realiza el **ordenamiento de un conjunto de `N` elementos**, tanto de forma secuencial como paralela, usando la clase `RecursiveAction` del framework `ForkJoin`.

- Deseamos ordenar un conjunto de N elementos de forma secuencial y de forma paralela para
utilizar múltiples hilos de ejecución y obtener un mejor tiempo de respuesta.


---

## 📁 Carpeta: `MPI`

### 🧠 Descripción

En esta sección se desarrolla una **aplicación en C usando la biblioteca MPI**, enfocada en enviar la **parte triangular superior de una matriz cuadrada** mediante tipos de datos derivados.

- Se utiliza la función `MPI_Type_indexed` para crear un tipo de dato derivado que representa los elementos de la parte triangular superior.
- El proceso 0 recibe como argumentos una matriz `n x n`, construye el tipo derivado y envía los datos con una sola llamada a `MPI_Send`.
- El proceso 1 recibe los datos con una sola llamada a `MPI_Recv` y los imprime por pantalla.