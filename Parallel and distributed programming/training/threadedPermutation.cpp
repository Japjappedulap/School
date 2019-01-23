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
 * Calculates all the permutations of N elements
 */
void ThreadedPermutations(const vector<int> &current_permutation) {
    if (current_permutation.size() == N && predicate(current_permutation))
        count++;
    vector<thread> threads;
    for (int i = 1; i <= N; ++i) {
        if (!alreadyIn(current_permutation, i)) {
            // should create a new thread and add the value i
            vector<int> new_permutation(current_permutation);
            new_permutation.push_back(i);
            threads.emplace_back([new_permutation](){
                ThreadedPermutations(new_permutation);
                });
        }
    }
    for (auto& i : threads) {
        i.join();
    }
}


/**
 * Calculates all the permutations of N elements
 */
void permutations(const vector<int> &current_permutation) {
    if (current_permutation.size() == N && predicate(current_permutation))
        count++;
    for (int i = 1; i <= N; ++i) {
        if (!alreadyIn(current_permutation, i)) {
            // should create a new thread and add the value i
            vector<int> new_permutation(current_permutation);
            new_permutation.push_back(i);
            ThreadedPermutations(new_permutation);
        }
    }
}


int main() {
    vector<int> x;
    ThreadedPermutations(x);
    cout << count << '\n';
    return 0;
}