//
// Created by potra on 23.01.2019.
//

#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <atomic>
#include <future>
#include "util.hpp"
#include "Matrix.hpp"
using namespace std;

Matrix MegaPower(const Matrix& matrix, int power) {
    if (power == 0) return Matrix(matrix.N);
    if (power == 1) return matrix;
    if (power % 2 == 0) {
        return MegaPower(matrix * matrix, power / 2);
    }
    return matrix * MegaPower(matrix * matrix, power / 2);
}

Matrix justPower(const Matrix& matrix, int power) {
    Matrix result(matrix.N);
    while (power) {
        result = result * matrix;
        --power;
    }
    return  result;
}

int main() {
    const Matrix x(2, true);
    Matrix r1 = justPower(x, 4);
    Matrix r2 = MegaPower(x, 4);
    r1.pp();
    r2.pp();
    return 0;
}