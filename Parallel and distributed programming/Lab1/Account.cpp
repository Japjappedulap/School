//
// Created by potra on 02.10.2018.
//

#include "Account.h"

long long Account::getBalance() const {
    return balance;
}

Account::Account(int _balance, int _id) : balance(_balance), initialBalance(_balance), id(_id) {
    this->account_mutex = new std::mutex;
}

const long long int Account::getInitialBalance() const {
    return initialBalance;
}

void Account::recordSend(SendTransaction transaction){
    this->account_mutex->lock();
    sendTransactions.push_back(transaction);
    this->balance -= transaction.getAmount();
    this->account_mutex->unlock();
}

void Account::recordReceive(SendTransaction transaction) {
    this->account_mutex->lock();
    receiveTransactions.push_back(transaction);
    this->balance += transaction.getAmount();
    this->account_mutex->unlock();
}

const std::vector<SendTransaction> &Account::getSendTransactions() const {
    return sendTransactions;
}

const std::vector<SendTransaction> &Account::getReceiveTransactions() const {
    return receiveTransactions;
}

bool Account::corrupted() {
    long long computed_balance = this->initialBalance;
    for (const auto& i : sendTransactions)
        computed_balance -= i.getAmount();
    for (const auto& i : receiveTransactions)
        computed_balance += i.getAmount();
    return !computed_balance == balance;
}
