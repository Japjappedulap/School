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
using namespace std;

int calculate(const vector<int>& a, const vector<int>& b, const int& i) {
    int x = 0;
    for (int j = 0; j < a.size() && j <= i; ++j) {
        x += a[j] * b[i - j];
    }
    return x;
}

/**
 * Calculate the ?convolution? for vectors a and b with respect to the formula from
 * subject 2, 2018 PDP
 * The result is the same as the dummy non-threaded convolution
 * This uses async and futures, to calculate the result for each i in the resulting vector
 * the threads do NOT have the same amount of work to do, the first spawned threads have much
 * lighter tasks compared to the later spawned threads
 * but f**k it, it's still threaded
 */
vector<int> threadedConvolution(const vector<int>& a, const vector<int>& b) {
    const int N = static_cast<const int>(a.size());
    vector<int> r(N, 0);
    vector<future<int> > futures;
    futures.reserve((unsigned long) N);
    for (int i = 0; i < N; ++i)
        futures.push_back(async(calculate, a, b, i));

    for (int i = 0; i < N; ++i)
        r[i] = futures[i].get();

    return r;
}

vector<int> convolution(const vector<int>& a, const vector<int>& b) {
    const int N = static_cast<const int>(a.size());
    vector<int> r(N, 0);
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N && j <= i; ++j)
            r[i] += a[j] * b[i - j];
    }
    return r;
}

int main() {
    vector<int> a = {1,6,3,5,7,4,9};
    vector<int> b = {7,4,8,3,1,4,5};
    vector<int> r1 = convolution(a, b);
    vector<int> r2 = threadedConvolution(a, b);
    printVector(a);
    printVector(b);
    printVector(r1);
    printVector(r2);
    return 0;
}