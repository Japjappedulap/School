
#include "utils.h"
#include "Matrix.h"

#define USE_THREADS


int main() {
    Matrix matrix1;
    Matrix matrix2(matrix1);
    Matrix res = matrix1 * matrix2;
    res.pp();
    return 0;
}
