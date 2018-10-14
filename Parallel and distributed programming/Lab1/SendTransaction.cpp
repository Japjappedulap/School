//
// Created by potra on 02.10.2018.
//

#include "SendTransaction.h"

int SendTransaction::auto_id = 0;

SendTransaction::SendTransaction(int _from, int _to, int _amount) : from(_from), to(_to), id(auto_id++), amount(_amount) {}

int SendTransaction::getFrom() const {
    return from;
}

int SendTransaction::getTo() const {
    return to;
}

int SendTransaction::getAmount() const {
    return amount;
}

const int SendTransaction::getId() const {
    return id;

}
int SendTransaction::getAuto_id() {
    return auto_id;
}
