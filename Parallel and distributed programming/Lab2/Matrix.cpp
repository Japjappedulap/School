//
// Created by potra on 16.10.2018.
//

#include <fstream>
#include <vector>
#include <iostream>
#include "Matrix.h"
#include "utils.h"

Matrix::Matrix() {
    std::cin >> N >> M;
    data = std::vector<std::vector<long long>>(static_cast<unsigned long>(N), std::vector<long long>(M, 0));
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j)
            std::cin >> this->data[i][j];
    }
};

Matrix::Matrix(const std::string &fileName) {
    std::string filePath = "../" + fileName;
    std::ifstream matrixFile(filePath);
    matrixFile >> N >> M;
    data = std::vector<std::vector<long long>>(static_cast<unsigned long>(N), std::vector<long long>(M, 0));
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j)
            matrixFile >> this->data[i][j];
    }
    matrixFile.close();
}

Matrix::Matrix(int _N, int _M)
    : N(_N), M(_M), data(std::vector<std::vector<long long>>(static_cast<unsigned long>(_N), std::vector<long long>(_M, 0))) {
}

Matrix::Matrix(const Matrix &other) {
    this->N = other.N;
    this->M = other.M;
    this->data = other.data;
}

void Matrix::pp() {
    for (const auto &i : data) {
        for (const auto &j : i)
            std::cout << j << ' ';
        std::cout << '\n';
    }
}

void Matrix::logToFile(const std::string &fileName) {
    std::string filePath = "../" + fileName;
    std::ofstream matrixFile(filePath);
    matrixFile << this->N << ' ' << this->M << '\n';
    for (const auto &i : this->data) {
        for (const auto &j : i)
            matrixFile << j << ' ';
        matrixFile << '\n';
    }
    matrixFile.close();
}

void Matrix::log() {
    for (const auto &i : this->data) {
        for (const auto &j : i)
            std::cout << j << ' ';
        std::cout << '\n';
    }
}

int Matrix::getN() const {
    return N;
}

void Matrix::setData(int i, int j, long long val) {
    this->data[i][j] = val;
}

int Matrix::getM() const {
    return M;

}

long long Matrix::getData(int i, int j) const {
    return this->data[i][j];
}


Matrix Matrix::operator+(const Matrix &other) {
    if (other.getN() != this->N || other.getM() != this->M)
        throw "Incompatible";
    Matrix result(this->N, this->M);
    #ifdef USE_THREADS

    #else
    // this works correctly
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j)
            result.setData(i, j, this->data[i][j] + other.getData(i, j));
    #endif
    return result;
}

Matrix Matrix::operator*(const Matrix &other) {
    /*
     * A(N, M)
     * B(M, P)
     *   =>
     * C(N, P)
     */
    const int &N = this->N;
    const int &M = this->M;
    const int &P = other.getM();
    if (M != other.getN())
        throw "Incompatible";
    Matrix result(N, P);
    #ifdef USE_THREADS

    #else
    // this works correctly
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < P; ++j) {
            long long accumulated = 0;
            for (int k = 0; k < M; ++k)
                accumulated += (this->data[i][k] * other.getData(k, j));
            result.setData(i, j, accumulated);
        }
    #endif
    return result;
}


void Matrix::randomize(int left, int right) {
    for (auto &i : this->data) {
        for (auto &j : i) {
            j = generateRange(left, right);
        }
    }
}
