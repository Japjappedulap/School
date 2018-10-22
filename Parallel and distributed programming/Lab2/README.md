# Lab2 & Lab3

### Task: Matrix multiplication and matrix addition

Divide a simple task between threads. The task can easily be divided in sub-tasks requiring no cooperation at all. See the effects of false sharing, and the costs of creating threads and of switching between threads.

Requirement: write two problems: one for computing the sum of two matrices, the other for computing the product of two matrices.

Divide the task between a configured number of threads (going from 1 to the number of elements in the resulting matrix). See the effects on the execution time.

### Times:

| Threads | Matrix N | Matrix M | Left Bound | Right Bound |       Time(1) Sum| Time(2) Sum | Time(3) Sum | Time(1) Product | Time(2) Product | Time(3) Product | 
|---      |    ---   |      --- | --- |    ---  |    ---|   ---| ---| ---|---|---|
|200      |1000       | 1000    |  2 | 16|                          `0m0,247s`  |`0m2,532s` |`0m0,539s` |  `0m16,684s` | `0m8,286s` |`0m4,671s` |
|200      |1000      |1000       | 10 | 1,000|                      `0m0,256s`  |`0m2,588s` |`0m0,575s` | `0m17.833s` | `0m5,954s` | `0m4,693s` |
| 200     |1000      |1000       | 10,000 | 1,000,000 |             `0m0,342s`  |`0m2,687s` |`0m0,640s` | `0m17,243` | `0m5,991s` | `0m4,845s` |
| 200   |1000      |1000         | 1,000,000 | 2,000,000,000 |      `0m0,427s`  |`0m2,749s` |`0m0,752s`| `0m17,317s`| `0m8,039s` | `0m4,890s` |
| 200    |5000       |5000  | 1,000,000 | 2,000,000,000    |        `0m10,651s`  |`1m8,377s` |`0m17,787s` | `46m3,076s` | `11m35,040s` | `10m38,219s` |

* Time(1) = time using a single thread - classic approach

* Time(2) = time using a thread pool - boost library (Lab3)

* Time(3) = time using threads and a queue of *tasks*
