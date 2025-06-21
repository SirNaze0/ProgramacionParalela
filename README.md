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

---

## 📁 Carpeta: Pthreads
### 🧠 Descripción
En esta sección se desarrolla una **aplicación en C utilizando hilos POSIX (pthreads)**, enfocada en **calcular el producto punto de dos vectores de manera secuencial y paralela**.

 - Se implementa una función que calcula el producto punto de forma secuencial como referencia.
 - Se utiliza la biblioteca pthread.h para dividir la tarea en varios hilos que trabajan en paralelo, optimizando el tiempo de ejecución cuando los vectores son grandes.
 - Cada hilo se encarga de una porción del cálculo, y los resultados se acumulan utilizando mutexes para asegurar la sincronización y evitar condiciones de carrera.
 - Se mide el tiempo de ejecución de ambas versiones para comparar el rendimiento.

### 📌 Modo de uso:
    ./dotprod N num_hilos modo
        N: tamaño del vector (entero positivo)
        num_hilos: cantidad de hilos a usar en modo paralelo
        modo: 0 para ejecución secuencial, 1 para ejecución paralela

## 📁 Carpeta: OpenMP
### Descripción
En esta sección se desarrolla una **aplicación en C utilizando Open MP**, enfocada en **calcular la suma de N números aleatorios de forma paralela** para utilizar múltiples hilos de ejecución y obtener un mejor tiempo de respuesta. Además, se quiere determinar porque no funciona la suma paralela con valores mayores e iguales a 100,000
 - Se divide el conjunto de datos en porciones más pequeñas
 - Esas partes se a un hilo diferente para calcular una suma parcial
 - Se combinan las sumas parciales para obtener la suma total
### La suma no funcion correctamente ya que el tipo de la variable sum es int, lo que causa overflow y hace que el resultado sea incorrecto. Se modificó el codigo con el tipo long y ahora el resultado es correcto.