//
// Created by potra on 16.10.2018.
//

#include <fstream>
#include <vector>
#include <iostream>
#include "Matrix.h"
#include "utils.h"
#include <boost/asio/io_service.hpp>
#include <boost/bind.hpp>
#include <boost/thread/thread.hpp>
#include <boost/lockfree/queue.hpp>
#include <queue>
#include <future>

//#define USE_THREAD_POOL
//#define USE_QUEUE
#define USE_ASYNC
const int Threads = 200;
const int gap = 10000;

std::mutex queue_mutex;


Matrix::Matrix() {
    std::cin >> N >> M;
    data = std::vector<std::vector<long long>>(static_cast<unsigned long>(N), std::vector<long long>(M, 0));
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j)
            std::cin >> this->data[i][j];
    }
};

Matrix::Matrix(const std::string &fileName) {
    std::string filePath = "../testFiles/" + fileName;
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



void Matrix::SumExecutor(const Matrix* initial, const Matrix* other, Matrix* result, std::queue<int> *Q) {
    try {
        while (true) {
            queue_mutex.lock();
            if (Q->empty()){
                queue_mutex.unlock();
                break;
            }
            int x = Q->front();
            Q->pop();
            queue_mutex.unlock();
            int j = x % gap;
            int i = x / gap;
            makeSum(i, j, initial, other, result);
        }
    } catch (...) {
        return;
    }
}


void Matrix::ProdExecutor(const Matrix* initial, const Matrix* other, Matrix* result, std::queue<int> *Q) {
    try {
        while (true) {
            queue_mutex.lock();
            if (Q->empty()){
                queue_mutex.unlock();
                break;
            }
            int x = Q->front();
            Q->pop();
            queue_mutex.unlock();
            int j = x % gap;
            int i = x / gap;
            makeProd(i, j, initial, other, result);
        }
    } catch (...) {
        return;
    }
}

void asyncSum(int line, const Matrix *initial, const Matrix *other, Matrix *result) {
    for (int j = 0; j < initial->getM(); ++j) {
        result->setData(line, j, initial->getData(line, j) + other->getData(line, j));

    }
}

void asyncProd(int line, const Matrix *initial, const Matrix *other, Matrix *result) {
    for (int j = 0; j < initial->getM(); ++j) {
        long long accumulated = 0;
        int M = initial->getM();
        for (int k = 0; k < M; ++k)
            accumulated += (initial->getData(line, k) * other->getData(k, j));
        result->setData(line, j, accumulated);
    }
}

Matrix Matrix::operator+(const Matrix &other) {
    if (other.getN() != this->N || other.getM() != this->M)
        throw "Incompatible";
    Matrix result(this->N, this->M);
    #ifdef USE_THREAD_POOL
    boost::asio::io_service ioService;
    boost::thread_group threadPool;

    boost::asio::io_service::work work(ioService);
    for (int i = 0; i < 8; ++i) {
        threadPool.create_thread(boost::bind(&boost::asio::io_service::run, &ioService));
    }

    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j) {
            ioService.post(boost::bind(Matrix::makeSum, i, j, this, &other, &result));
        }
    ioService.poll();
    ioService.stop();
    threadPool.join_all();
    return result;
    #endif
    #ifdef USE_QUEUE
    std::queue<int> Q;
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j)
            Q.push(i * gap + j);

    boost::thread_group threads;
    for (int i = 0; i < Threads; ++i)
        threads.create_thread(boost::bind(Matrix::SumExecutor, this, &other, &result, &Q));
    threads.join_all();
    return result;
    #endif
    #ifdef USE_ASYNC
    std::vector<std::future<void> > futures;
    for (int i = 0; i < N; ++i) {
        futures.push_back(std::async(asyncSum, i, this, &other, &result));
    }
    for (auto& future : futures) {
        future.wait();
    }

    return result;
    #endif
    // this works correctly
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j)
            result.setData(i, j, this->data[i][j] + other.getData(i, j));
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
    #ifdef USE_THREAD_POOL
    boost::asio::io_service ioService;
    boost::thread_group threadPool;

    boost::asio::io_service::work work(ioService);
    for (int i = 0; i < 200; ++i) {
        threadPool.create_thread(boost::bind(&boost::asio::io_service::run, &ioService));
    }

    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j) {
            ioService.post(boost::bind(Matrix::makeProd, i, j, this, &other, &result));
        }
    ioService.poll();
    ioService.stop();
    threadPool.join_all();
    #endif
    #ifdef USE_QUEUE
    std::queue<int> Q;
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < M; ++j)
            Q.push(i * gap + j);

    boost::thread_group threads;
    for (int i = 0; i < Threads; ++i)
        threads.create_thread(boost::bind(Matrix::ProdExecutor, this, &other, &result, &Q));
    threads.join_all();
    return result;
    #endif
    #ifdef USE_ASYNC
    std::vector<std::future<void> > futures;
    for (int i = 0; i < N; ++i) {
        futures.push_back(std::async(asyncProd, i, this, &other, &result));
    }
    for (auto& future : futures) {
        future.wait();
    }

    return result;
    #endif
    // this works correctly
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < P; ++j) {
            long long accumulated = 0;
            for (int k = 0; k < M; ++k)
                accumulated += (this->data[i][k] * other.getData(k, j));
            result.setData(i, j, accumulated);
        }
    return result;
}


void Matrix::randomize(int left, int right) {
    for (auto &i : this->data) {
        for (auto &j : i) {
            j = generateRange(left, right);
        }
    }
}

void Matrix::makeSum(int i, int j, const Matrix* initial, const Matrix *other, Matrix *result) {
    result->setData(i, j, initial->getData(i, j) + other->getData(i, j));
}

void Matrix::makeProd(int i, int j, const Matrix* initial, const Matrix *other, Matrix *result) {
    long long accumulated = 0;
    int M = initial->getM();
    for (int k = 0; k < M; ++k)
        accumulated += (initial->data[i][k] * other->getData(k, j));
    result->setData(i, j, accumulated);
}


