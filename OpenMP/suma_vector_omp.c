#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
int main(){
    int n=1000000;//Tamaño del array
    int *array=(int *)malloc(n*sizeof(int));
    int sum=0;
    int i;
    // Inicializar el array con valores
    for(i=0;i<n;i++){
        array[i]=i+1;
    }
    //Suma en paralelo con OpenMP
    #pragma omp parallel for reduction(+:sum)
    for(i=0;i<n;i++){
        sum += array[i];
    }
    printf("La suma total es: %d\n", sum);
    free(array);
    return 0;
}