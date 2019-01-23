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

int main() {
    Matrix x(2, true), y(2, true);
    Matrix r1 = x.okProduct(y);
    Matrix r2 = x * y;
    r1.pp();
    r2.pp();

    return 0;
}
