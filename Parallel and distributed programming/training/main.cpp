#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <atomic>
#include <future>
#include "util.hpp"
using namespace std;


class Matrix {
public:
    vector<vector<int> > data;
    int N;

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


    Matrix operator*(const Matrix &other) {
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




int main() {
    Matrix x(2, true), y(2, true);
    Matrix r1 = x.okProduct(y);
    Matrix r2 = x * y;
    r1.pp();
    r2.pp();

    return 0;
}