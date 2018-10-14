# Lab1

### Task: Bank accounts

At a bank, we have to keep track of the balance of some accounts. Also, each account has an associated log (the list of records of operations performed on that account). Each operation record shall have a unique serial number, that is incremented for each operation performed in the bank.

We have concurrently run transfer operations, to be executer on multiple threads. Each operation transfers a given amount of money from one account to someother account, and also appends the information about the transfer to the logs of both accounts.

From time to time, as well as at the end of the program, a consistency check shall be executed. It shall verify that the amount of money in each account corresponds with the operations records associated to that account, and also that all operations on each account appear also in the logs of the source or destination of the transfer.

### Times:

| Threads | Operations/thread | Bank Account | Time | 
|---      |                ---|           ---|   ---|
|200      |100                |           100|`0m0,422s` |
|200      |1000                |           100|`0m6,676s` |
| 200     |1000                |           10|`0m6,738s` |
| 20000     |10                |           1000|`0m5,098s` |
| 2000     |1000                |           10000|`1m15,657s` |
