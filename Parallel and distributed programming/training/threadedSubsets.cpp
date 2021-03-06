//
// Created by potra on 23.01.2019.
//

#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <atomic>
#include "util.hpp"
using namespace std;

const int N = 7;

atomic<int> count(0);

bool predicate(const vector<int>& perm) {
    return true;
}

/**
 * Calculates all the subsets of k elements from the given set
 * We ASSUME that all the elements of the given set are distinct
 */
void Subset(const vector<int>& given_set, const int& k, const vector<int> &current_set) {
    if (current_set.size() == k && predicate(current_set)) {
        count++;
        printVector(current_set);
        return;
    }
    vector<thread> threads;
    for (const auto& i : given_set) {
        if (!alreadyIn(current_set, i)) {
            vector<int> next_set(current_set);
            next_set.push_back(i);
            threads.emplace_back([given_set, k, next_set](){
                Subset(given_set, k, next_set);
            });
        }
    }
    for (auto& i : threads) {
        i.join();
    }
}

int main() {
    vector<int> given_set = {1, 2, 3, 4, 5, 6};
    vector<int> empty;
    Subset(given_set, 2, empty);
    return 0;
}