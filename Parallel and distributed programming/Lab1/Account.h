//
// Created by potra on 02.10.2018.
//

#ifndef LAB1_ACCOUNT_H
#define LAB1_ACCOUNT_H

#include <vector>
#include <mutex>
#include "SendTransaction.h"

class Account {
private:
    int id;
    long long balance;
    const long long initialBalance;
    std::vector <SendTransaction> sendTransactions;
    std::vector <SendTransaction> receiveTransactions;
    std::mutex* account_mutex;

public:
    explicit Account(int _balance = 0, int _id = 0);
    void recordSend(SendTransaction);
    void recordReceive(SendTransaction);

    bool corrupted();
    const std::vector<SendTransaction> &getSendTransactions() const;
    const std::vector<SendTransaction> &getReceiveTransactions() const;
    const long long int getInitialBalance() const;
    long long getBalance() const;
};



#endif //LAB1_ACCOUNT_H
