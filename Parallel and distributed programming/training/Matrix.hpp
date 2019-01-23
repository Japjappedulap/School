//
// Created by potra on 23.01.2019.
//

#ifndef TRAINING_MATRIX_HPP
#define TRAINING_MATRIX_HPP

#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <atomic>
#include <future>
#include "util.hpp"

class Matrix {
public:
    vector<vector<int> > data;
    int N;

    /**
     * Ctor for matrix, if randomly... == true,
     * will put random numbers in the matrix
     */
    Matrix(int N, bool randomly_generated) {
        this->data = std::vector<std::vector<int>>(static_cast<unsigned long>(N), std::vector<int>(N, 0));
        this->N = N;
        if (randomly_generated) {
            for (auto &i : this->data) {
                for (auto &j : i) {
                    j = generateRange(1, 4);
                }
            }
        }
    }

    /**
     * Ctor for matrix filled with element on each cell
     */
    Matrix(int N, int element) {
        this->data = std::vector<std::vector<int>>(static_cast<unsigned long>(N), std::vector<int>(N, element));
        this->N = N;
    }

    /**
     * Ctor for identity matrix
     */
    explicit Matrix(int N) {
        this->data = std::vector<std::vector<int>>(static_cast<unsigned long>(N), std::vector<int>(N, 0));
        this->N = N;
        for (int i = 0; i < N; ++i) {
            this->data[i][i] = 1;
        }
    }

    int get(int i, int j) const {
        return this->data[i][j];
    }

    void set(int i, int j, int val) {
        this->data[i][j] = val;
    }

    Matrix okProduct(const Matrix &other) {
        Matrix result(N, true);
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                int accumulated = 0;
                for (int k = 0; k < N; ++k)
                    accumulated += (this->data[i][k] * other.get(k, j));
                result.set(i, j, accumulated);
            }
        return result;
    }

    /**
     * Matrix multiplication on threads, using futures cuz it's shorter code
     */
    Matrix operator*(const Matrix &other) const {
        Matrix result(N, true);
        std::vector<std::future<void> > futures;
        futures.reserve(static_cast<unsigned long>(N));
        for (int i = 0; i < N; ++i) {
            futures.push_back(std::async(asyncProd, i, this, &other, &result));
        }
        for (auto& future : futures) {
            future.wait();
        }
        return result;
    }

    /**
     * Technique ultra barosana calculating a whole line
     */
    static void asyncProd(int line, const Matrix *initial, const Matrix *other, Matrix *result) {
        for (int j = 0; j < initial->N; ++j) {
            int accumulated = 0;
            int M = initial->N;
            for (int k = 0; k < M; ++k)
                accumulated += (initial->get(line, k) * other->get(k, j));
            result->set(line, j, accumulated);
        }
    }


    void pp() {
        for (const auto &i : data) {
            for (const auto &j : i)
                std::cout << j << ' ';
            std::cout << '\n';
        }
    }
};


#endif //TRAINING_MATRIX_HPP
