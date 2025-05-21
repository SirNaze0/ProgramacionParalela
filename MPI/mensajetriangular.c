#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

int main(int argc, char *argv[]) {
    int rank, size;
    int n;
    int *matrix = NULL;
    int *recvbuf = NULL;
    MPI_Datatype upper_type;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (size != 2) {
        if (rank == 0)
            printf("Este programa requiere exactamente 2 procesos.\n");
        MPI_Finalize();
        return 1;
    }

    if (rank == 0) {
        if (argc < 2) {
            printf("Uso: %s <n> <matriz>\n", argv[0]);
            MPI_Abort(MPI_COMM_WORLD, 1);
        }

        n = atoi(argv[1]);
        if (argc != 2 + n * n) {
            printf("Debe ingresar %d elementos para una matriz %dx%d\n", n*n, n, n);
            MPI_Abort(MPI_COMM_WORLD, 1);
        }

        matrix = malloc(n * n * sizeof(int));
        for (int i = 0; i < n * n; i++) {
            matrix[i] = atoi(argv[2 + i]);
        }
    }

    // Difundir el valor de n a todos los procesos
    MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // Crear tipo MPI para la parte triangular superior
    int *block_lengths = malloc(n * sizeof(int));
    int *displacements = malloc(n * sizeof(int));
    int disp = 0;

    for (int i = 0; i < n; i++) {
        block_lengths[i] = n - i;
        displacements[i] = i * n + i;
    }

    MPI_Type_indexed(n, block_lengths, displacements, MPI_INT, &upper_type);
    MPI_Type_commit(&upper_type);

    int total_elements = n * (n + 1) / 2;
    if (rank == 1) {
        recvbuf = malloc(total_elements * sizeof(int));
    }

    if (rank == 0) {
        MPI_Send(matrix, 1, upper_type, 1, 0, MPI_COMM_WORLD);
    } else {
        MPI_Recv(recvbuf, total_elements, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        printf("Proceso 1 recibió los siguientes elementos de la parte triangular superior:\n");
        for (int i = 0; i < total_elements; i++) {
            printf("%d ", recvbuf[i]);
        }
        printf("\n");
    }

    MPI_Type_free(&upper_type);
    free(block_lengths);
    free(displacements);
    if (matrix) free(matrix);
    if (recvbuf) free(recvbuf);

    MPI_Finalize();
    return 0;
}
