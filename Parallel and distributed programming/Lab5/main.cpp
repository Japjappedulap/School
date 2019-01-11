#include <utility>
#include <iostream>
#include "utils.h"
#include <vector>
#include <thread>
#include <mutex>


//#define SIMPLE
//#define THREADED
#define KARATSUBA

const int NumberThread = 8;

class Polynomial{
    std::vector<int> coefficients;
    std::vector<std::mutex> mutexes;
    size_t size;
public:
    explicit Polynomial(std::vector<int> _coefficients) : coefficients(std::move(_coefficients)){
        size = coefficients.size() - 1;
        mutexes = std::vector<std::mutex> (size + 1);
    };

    explicit Polynomial(size_t N, bool randomize) {
        if (randomize) {
            for (size_t i = 0; i <= N; ++i) {
                this->coefficients.push_back(generateRange(1, 6));
            }
        } else {
            this->coefficients = std::vector<int> (N+1, 0);
        }
        size = N;
        mutexes = std::vector<std::mutex> (size + 1);
    }

    Polynomial operator* (Polynomial& other) {
        Polynomial result(this->size + other.size, false);
        #ifdef SIMPLE
        for (int k = 0; k <= this->size + other.size; ++k)
            for (int i = 0; i <= k; ++i)
                result.coefficients[k] += this->getCoefficient(i)*other.getCoefficient(k-i);
        return result;
        #endif
        #ifdef THREADED
        std::vector<std::thread> threads;

        for (size_t K = 0; K < NumberThread; ++K) {
            threads.emplace_back([&](){
                for (size_t k = K; k <= this->size + other.size; k += K) {
                    int accumulator = 0;
                    for (int i = 0; i <= k; ++i) {
                        accumulator += this->getCoefficient(i)*other.getCoefficient(static_cast<int>(k-i));
                    }
                    result.setCoefficient(static_cast<int>(k), accumulator);
                }
            });
        }

        for (auto& i : threads) {
            i.join();
        }

        return result;
        #endif

        #ifdef KARATSUBA
        if(this->coefficients.size() == 1 && other.coefficients.size() == 1) {
            result.coefficients[0] = this->coefficients[0] * other.coefficients[0];
            return result;
        }
        int half = static_cast<int>(this->coefficients.size() / 2 + this->coefficients.size() % 2);

        std::vector<int> a_lo(this->coefficients.begin(), this->coefficients.begin() + half);
        std::vector<int> a_hi(this->coefficients.begin() + half, this->coefficients.end());
        std::vector<int> b_lo(other.coefficients.begin(), other.coefficients.begin() + half);
        std::vector<int> b_hi(other.coefficients.begin() + half, other.coefficients.end());
        std::vector<int> lo(a_lo.size() + b_lo.size() - 1);
        std::vector<int> hi(a_hi.size() + b_hi.size() - 1);
        std::vector<std::thread> threads;
        T += 2;
        if (T < MAXT) {
            threads.push_back(std::thread([&a_lo, &b_lo, &lo](){mult(a_lo, b_lo, lo);}));
            threads.push_back(std::thread([&a_hi, &b_hi, &hi](){mult(a_hi, b_hi, hi);}));
        } else {
            mult(a_lo, b_lo, lo);
            mult(a_hi, b_hi, hi);
        }

        return result;

        #endif
    }

    int getCoefficient(int i) const {
        return i > size ? 0 : coefficients[i];
    }

    void setCoefficient(int index, int val) {
        mutexes[index].lock();
        coefficients[index] = val;
        mutexes[index].unlock();
    }

    void pp() {
        std::cout << size << " : ";
        for (const auto& i : coefficients)
            std::cout << i << ' ';
        std::cout << '\n';
    }
};

int main() {
//    std::vector<int> xv{1, 1, 3, 2, 3, 4, 5};
//    std::vector<int> yv{2, 2, 3, 3};
//    Polynomial x(xv), y(yv);

    Polynomial x(10000, true), y(10000, true);
//    x.pp();
//    y.pp();
    Polynomial z = x * y;
//    z.pp();
    return 0;
}