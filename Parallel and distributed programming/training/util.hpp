//
// Created by potra on 23.01.2019.
//

#ifndef TRAINING_UTIL_HPP
#define TRAINING_UTIL_HPP

#include <vector>
#include <mutex>
#include <iostream>

using namespace std;

mutex mtx;

void printVector(const vector<int>& x) {
    lock_guard<mutex> guard(mtx);
    for (const auto& i : x) {
        cout << i << ' ';
    }
    cout << '\n';
}

bool alreadyIn(const vector<int>& perm, int value) {
    for (const auto& i : perm)
        if (i == value)
            return true;
    return false;
}

bool diffVectors(const vector<int>& a, const vector<int>& b) {
    if (a.size() != b.size())
        return false;
    for (int i = 0; i < b.size(); ++i)
        if (a[i] != b[i])
            return false;
    return true;
}


#include <random>

int generateRange(int x, int y) {
    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> uid(x, y);
    return uid(rng);
}

#endif //TRAINING_UTIL_HPP
