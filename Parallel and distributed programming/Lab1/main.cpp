#include <iostream>
#include <random>
#include <vector>
#include <thread>
#include <mutex>
#include <cassert>
#include "Account.h"
#include "SendTransaction.h"
#include "utils.h"
#include "Bank.h"

const int NumberAccounts = 10000;
const int NumberThreads = 2000;
const int TransactionsPerThread = 1000;


int main() {
    Bank bank(NumberAccounts, NumberThreads, TransactionsPerThread);
    bank.run();
    return 0;
}