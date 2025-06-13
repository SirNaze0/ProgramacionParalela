#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/time.h>

typedef struct {
    double *A, *B;
    long start, end;
    pthread_mutex_t *mutex;
    double *global_sum;
} ThreadData;

void *thread_func(void *arg) {
    ThreadData *td = (ThreadData*)arg;
    double sum = 0.0;
    for(long i = td->start; i < td->end; ++i)
        sum += td->A[i] * td->B[i];
    pthread_mutex_lock(td->mutex);
    *(td->global_sum) += sum;
    pthread_mutex_unlock(td->mutex);
    return NULL;
}

double sequential_dot(double *A, double *B, long N) {
    double sum = 0.0;
    for(long i = 0; i < N; ++i)
        sum += A[i] * B[i];
    return sum;
}

int main(int argc, char *argv[]) {
    if(argc != 4) {
        fprintf(stderr, "Uso: %s N hilos modo\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    long N = atol(argv[1]);
    int num_threads = atoi(argv[2]);
    int modo = atoi(argv[3]); // 0=secuencial, 1=paralelo

    double *A = malloc(N * sizeof(double));
    double *B = malloc(N * sizeof(double));
    for(long i = 0; i < N; i++) {
        A[i] = i + 1;
        B[i] = N - i;
    }

    struct timeval t0, t1;
    double elapsed;

    if(modo == 0) {
        gettimeofday(&t0, NULL);
        double res = sequential_dot(A, B, N);
        gettimeofday(&t1, NULL);
        elapsed = (t1.tv_sec - t0.tv_sec) + (t1.tv_usec - t0.tv_usec) / 1e6;
        printf("Secuencial: %f en %f s\n", res, elapsed);
        free(A); free(B);
        return 0;
    }

    // Modo paralelo
    pthread_t threads[num_threads];
    ThreadData td[num_threads];
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
    double global_sum = 0.0;

    long chunk = N / num_threads;

    gettimeofday(&t0, NULL);
    for(int i = 0; i < num_threads; i++) {
        td[i].A = A;
        td[i].B = B;
        td[i].start = i * chunk;
        td[i].end = (i == num_threads - 1) ? N : (i + 1) * chunk;
        td[i].mutex = &mutex;
        td[i].global_sum = &global_sum;
        pthread_create(&threads[i], NULL, thread_func, &td[i]);
    }
    for(int i = 0; i < num_threads; i++)
        pthread_join(threads[i], NULL);
    gettimeofday(&t1, NULL);
    elapsed = (t1.tv_sec - t0.tv_sec) + (t1.tv_usec - t0.tv_usec) / 1e6;

    printf("Paralelo (%d hilos): %f en %f s\n", num_threads, global_sum, elapsed);

    pthread_mutex_destroy(&mutex);
    free(A); free(B);
    return 0;
}
