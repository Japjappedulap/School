#pragma ide diagnostic ignored "hicpp-signed-bitwise"
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#include <stdio.h>
#include <unistd.h>
#include <sys/fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>

#define FIFO1 "p"  // we assume that p is already existing in the fs!
#define FIFO2 "q"  // we assume that q is already existing in the fs!

extern int errno;

//  The only difference between pipes and FIFOs is the manner in which
//  they are created and opened.  Once these tasks have been
//  accomplished, I/O on pipes and FIFOs has exactly the same semantics. (man page)

void OpenSingleFifoCorrectly() {
    // this illustrates how to properly open a fifo for read and write
    // comment one of the opens and the process will hang.
    // The child which has the open uncommented will hang,
    // as there is no other process attempting to open the other end
    //      The FIFO must be opened
    //      on both ends (reading and writing) before data can be passed.
    //      Normally, opening the FIFO blocks until the other end is opened also. (man page)
    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());
        open(FIFO1, O_RDONLY);
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        open(FIFO1, O_WRONLY);
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);
}

void OpenTwoFifoCorrectly1() {
    // Working with two named pipes will hang if the order isn't carefully managed.
    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());

        open(FIFO1, O_RDONLY);  // open FIFO1 for read, WAIT for FIFO1 to be opened for write
                                // if the other end is already open, the operation does not block
        open(FIFO2, O_RDONLY);  // open FIFO2 for read, WAIT for FIFO2 to be opened for write
                                // if the other end is already open, the operation does not block
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        open(FIFO1, O_WRONLY);  // open FIFO1 for write, WAIT for FIFO1 to be opened for read
                                // if the other end is already open, the operation does not block
        open(FIFO2, O_WRONLY);  // open FIFO2 for write, WAIT for FIFO2 to be opened for read
                                // if the other end is already open, the operation does not block
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);
    // Must be some kind of
    // Child 1 --- open/write 1, open/write 2
    // Child 2 --- write/open 1, write/open 2
    // the order must be the same for all children
}

void OpenTwoFifoCorrectly2() {
    // In OpenTwoFifoCorrectly1, a child was opening only read, and another only write
    // which isn't very useful.
    // This example correctly shows how to open one read and one write for each of the two
    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());

        open(FIFO1, O_RDONLY);  // open FIFO1 for read, WAIT for FIFO1 to be opened for write
        {}                      // if the other end is already open, the operation does not block
        open(FIFO2, O_WRONLY);  // open FIFO2 for write, WAIT for FIFO2 to be opened for read
                                // if the other end is already open, the operation does not block
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        open(FIFO1, O_WRONLY);  // open FIFO1 for write, WAIT for FIFO1 to be opened for read
                                // if the other end is already open, the operation does not block
        open(FIFO2, O_RDONLY);  // open FIFO2 for read, WAIT for FIFO2 to be opened for write
                                // if the other end is already open, the operation does not block
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);
    // This way, we can achieve bidirectional communication between the two children
    // in contrast to OpenTwoFifoCorrectly1, which offers unidirectional communication on 2 FIFOs
}

void OpenTwoFifoIncorrectly() {
    // In this example, a deadlock occurs.

    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());

        open(FIFO1, O_RDONLY);  // As child 1 opens FIFO1 for read, it blocks until FIFO1 is also opened for write
                                // In other words, child 1 will wait for child 2 to open FIFO1 for write
        open(FIFO2, O_RDONLY);  // This will never happen
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        open(FIFO2, O_WRONLY);  // Child 2 opens FIFO2 for write, it blocks until FIFO2 is also opened for read
                                // In other words, child 2 will wait for child 1 to open FIFO2 for write
        open(FIFO1, O_WRONLY);  // This will never happen
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);

    // both children will wait for each other to open the corresponding FIFO -> deadlock
}

void OpenTwoFifoIncorrectlyNonBlockingUndefined() {
    // In this example, a deadlock should occur, but won't because of
    // specifying that open should have a non blocking behaviour.

    // A process can open a FIFO in nonblocking mode.  In this case, opening
    //       for read-only succeeds even if no one has opened on the write side
    //       yet and opening for write-only fails with ENXIO (no such device or
    //       address) unless the other end has already been opened. (man-page)

    //  O_NONBLOCK or O_NDELAY as flags
    //              When possible, the file is opened in nonblocking mode.
    //              Neither the open() nor any subsequent I/O operations on the
    //              file descriptor which is returned will cause the calling
    //              process to wait. (man-page)

    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());

        int FIFO1_RDONLY_status = open(FIFO1, O_RDONLY | O_NONBLOCK);   // C1 opens F1 for read, the operation doesn't block
        if (FIFO1_RDONLY_status == -1) {                                // and will not fail, as its written in the manual
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        int FIFO2_RDONLY_status = open(FIFO2, O_RDONLY | O_NONBLOCK);   // C1 opens F2 for read, the operation doesn't block
        if (FIFO2_RDONLY_status == -1) {                                // and will not fail, as its written in the manual
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        int FIFO2_WRONLY_status = open(FIFO2, O_NONBLOCK | O_WRONLY);   // C2 opens F2 for write, the operation doesn't block but
        if (FIFO2_WRONLY_status == -1) {                                // may fail, if F2 wasn't open for read
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        int FIFO1_WRONLY_status = open(FIFO1, O_WRONLY | O_NONBLOCK);   // C2 opens F1 for write, the operation doesn't block but
        if (FIFO1_WRONLY_status == -1) {                                // may fail, if F1 wasn't open for read
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);

    // The program will not hang but the behaviour of the FIFOs will be undefined
    // As there is no guarantee that opening in read-only will occur before
    // opening in write-only

    // The only case when the operations don't fail is when C1 opens BOTH F1 and F2 for read
    // BEFORE C2 opens them for write
}

void OpenTwoFifoIncorrectlyNonBlockingDefined() {
    // This example orders the reads
    // explained in OpenTwoFifoIncorrectlyNonBlockingUndefined

    int pid1, pid2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());

        int FIFO1_RDONLY_status = open(FIFO1, O_RDONLY | O_NONBLOCK);
        if (FIFO1_RDONLY_status == -1) {
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        sleep(1); // adding "synchronization mechanism"
        int FIFO2_WRONLY_status = open(FIFO2, O_WRONLY | O_NONBLOCK);
        if (FIFO2_WRONLY_status == -1) {
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());

        int FIFO2_RDONLY_status = open(FIFO2, O_RDONLY | O_NONBLOCK);
        if (FIFO2_RDONLY_status == -1) {
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        sleep(1); // adding "synchronization mechanism"
        int FIFO1_WRONLY_status = open(FIFO1, O_WRONLY | O_NONBLOCK);
        if (FIFO1_WRONLY_status == -1) {
            fprintf(stderr, "ERROR: %s\n", strerror(errno));
        }
        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, NULL, 0);
    waitpid(pid2, NULL, 0);

    // The program will not hang and because of the "synchronization mechanism",
    // will work most of the times.

    // It may fail to open for write only if the read wasn't already open, but before attempting
    // to open for write, we wait for one second.

    // THIS IS NOT RECOMMENDED
}


int main(int n, char** a) {
    printf("\t\tPPID: %d\n", getpid());
    OpenTwoFifoCorrectly2();

    return 0;
}
