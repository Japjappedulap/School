//
// Created by potra on 02.10.2018.
//

#ifndef LAB1_SENDTRANSACTION_H
#define LAB1_SENDTRANSACTION_H


class SendTransaction{
private:
    static int auto_id;
private:
  const int id;
    int from, to;
    int amount;
public:
    SendTransaction(int, int, int);
    int getFrom() const;
    int getTo() const;
    int getAmount() const;
    const int getId() const;
    static int getAuto_id();
};

#endif //LAB1_SENDTRANSACTION_H
