# Lab2

### Task: Matrix multiplication and matrix addition

Divide a simple task between threads. The task can easily be divided in sub-tasks requiring no cooperation at all. See the effects of false sharing, and the costs of creating threads and of switching between threads.

Requirement: write two problems: one for computing the sum of two matrices, the other for computing the product of two matrices.

Divide the task between a configured number of threads (going from 1 to the number of elements in the resulting matrix). See the effects on the execution time.

### Times:

| Threads | Matrix N | Matrix M | Left Bound | Right Bound |           Time(1)      | Time(2) | 
|---      |    ---   |      --- | --- |    ---  |    ---|   ---|
|200      |1000       | 1000    |  2 | 16|                          `0m0,422s`  |`0m0,422s` |
|200      |1000      |1000       | 10 | 1,000|                      `0m0,422s`  |`0m6,676s` |
| 200     |1000      |1000       | 10,000 | 1,000,000 |             `0m0,422s`  |`0m6,738s` |
| 200   |1000      |1000         | 1,000,000 | 2,000,000,000 |      `0m0,422s`  |`0m5,098s` |
| 200    |5000       |5000  | 1,000,000 | 2,000,000,000    |        `0m0,422s`  |`1m15,657s` |

Time(1) = time using a single thread - classic approach

Time(2) = time using a multi-threading
