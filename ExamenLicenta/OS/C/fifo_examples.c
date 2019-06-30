//
// Created by potra on 30.06.2019.
//

#include <stdio.h>
#include <unistd.h>
#include <sys/fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/wait.h>

#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#define FIFO1 "p"  // we assume that p is already existing in the fs!
#define FIFO2 "q"  // we assume that q is already existing in the fs!

#define MSG1 "Hello world!"
#define MSG2 "CMZ PISI?"
#define MSG1_size strlen(MSG1)
#define MSG2_size strlen(MSG2)

#define BUFFER_SIZE 1<<12u


void Example1() {
    // child1 sends to child2

    int pid1, pid2, child_status_1, child_status_2;
    if ((pid1 = fork()) == 0) {
        // only child1 enters
        printf("PID: %d\n", getpid());
        int write_FD = open(FIFO1, O_WRONLY);
        if (write_FD == -1) {exit(errno);}

        printf("CHILD1: sending message...\n");
        write(write_FD, MSG1, MSG1_size);
        printf("CHILD1: message sent\n");

        close(write_FD); // correctly close pipe
        // If all file descriptors referring to the write end of a pipe have
        //       been closed, then an attempt to read(2) from the pipe will see end-
        //       of-file (read(2) will return 0).  If all file descriptors referring
        //       to the read end of a pipe have been closed, then a write(2) will
        //       cause a SIGPIPE signal to be generated for the calling process.
        exit(0);
    }
    if (pid1 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    if ((pid2 = fork()) == 0) {
        // only child2 enters
        printf("PID: %d\n", getpid());
        int read_FD = open(FIFO1, O_RDONLY);
        if (read_FD == -1) {exit(errno);}

        // prepare buffers
        char *buffer = malloc(sizeof(char) * BUFFER_SIZE);
        ssize_t bytesRead;

        printf("CHILD2: awaiting messages...\n");

        // act as a server
        while ((bytesRead = read(read_FD, buffer, (size_t) BUFFER_SIZE)) > 0) {
            printf("CHILD2: bytes read: %zd, message received: %s\n", bytesRead, buffer);
        }

        free(buffer); // release heap memory
        if (bytesRead == -1) {exit(errno);} // if something occurred,
        // exit with errno status
        sleep(2);

        printf("CHILD2: pipe closed, bytes read: %zd\n", bytesRead);
        int close_read_FD_status = close(read_FD);
        if (close_read_FD_status == -1) {exit(errno);} // if something occurred,

        exit(0);
    }
    if (pid2 < 0) {
        fprintf(stderr, "fork failed for C1");
    }
    waitpid(pid1, &child_status_1, 0);
    waitpid(pid2, &child_status_2, 0);
    printf("PARENT: child1 finished, status: %d\n", child_status_1);
    printf("PARENT: child2 finished, status: %d\n", child_status_2);
}

void Example2() {
    // multiple children send to child2
    // child2 acts as a server, the others act as senders
    const int clients_len = 10;
    int *clients_PID = malloc(clients_len * sizeof(int));
    int *clients_status = malloc(clients_len * sizeof(int));

    int server_PID;
    int server_status;

    // run server
    if ((server_PID = fork()) == 0) {
        // only server enters
        printf("PID: %d\n", getpid());

        // open for read and hang until parent opens for write
        int read_FD = open(FIFO1, O_RDONLY);
        if (read_FD == -1) {exit(errno);}

        // prepare buffers
        char *buffer = malloc(sizeof(char) * BUFFER_SIZE);
        ssize_t bytesRead;

        printf("SERVER: awaiting messages...\n");

        // read will return 0 when the write end is closed
        // It's crucial we open and close the write file descriptor
        // in the parent only once.
        while ((bytesRead = read(read_FD, buffer, (size_t) BUFFER_SIZE)) > 0) {
            printf("CHILD2: bytes read: %zd, message received: %s\n", bytesRead, buffer);
            memset(buffer, 0, sizeof(char) * BUFFER_SIZE);
        }

        free(buffer); // release heap memory
        if (bytesRead == -1) {exit(errno);} // if something occurred,
        // exit with errno status

        int close_read_FD_status = close(read_FD);
        if (close_read_FD_status == -1) {exit(errno);} // if something occurred,
        exit(0);
    }
    if (server_PID < 0) {
        fprintf(stderr, "fork failed for C1");
    }

    // open FIFO for writing ONLY ONCE
    int write_FD = open(FIFO1, O_WRONLY);
    if (write_FD == -1) {exit(errno);}

    for (int i = 0; i < clients_len; ++i) {

        if ((clients_PID[i] = fork()) == 0) {
            // only client enters
            printf("SPAWNED WITH PID: %d\n", getpid());
            // each client will write to FIFO
            write(write_FD, MSG1, MSG1_size);
            exit(0);
        }
        if (clients_PID[i] < 0) {
            fprintf(stderr, "fork failed for C1");
        }
    }

    for (int i = 0; i < clients_len; ++i) {
        waitpid(clients_PID[i], &clients_status[i], 0);
        printf("PARENT: client with PID: %d finished, status: %d\n", clients_PID[i], clients_status[i]);
    }
    // after every client has exited
    // close the write end of the FIFO
    printf("closing write_FD\n");
    close(write_FD); // correctly close pipe

    // wait for server to finish and get status
    waitpid(server_PID, &server_status, 0);
    printf("PARENT: server finished, status: %d\n", server_status);

    free(clients_PID);
    free(clients_status);

    //  Behaviour
    // The server will receive the message clients_len times.
    // How many times the read() from the server will return is undefined
    // as two clients may write at the same time onto the pipe, concatenating
    // their messages. read() may have buckets of messages instead of single
    // messages.
    //
    //       POSIX.1 says that write(2)s of less than PIPE_BUF bytes must be
    //       atomic: the output data is written to the pipe as a contiguous
    //       sequence.  Writes of more than PIPE_BUF bytes may be nonatomic: the
    //       kernel may interleave the data with data written by other processes.
    //       POSIX.1 requires PIPE_BUF to be at least 512 bytes.  (On Linux,
    //       PIPE_BUF is 4096 bytes.)  The precise semantics depend on whether the
    //       file descriptor is nonblocking (O_NONBLOCK), whether there are
    //       multiple writers to the pipe, and on n, the number of bytes to be
    //       written:
    //
    //       O_NONBLOCK disabled, n <= PIPE_BUF
    //              All n bytes are written atomically; write(2) may block if
    //              there is not room for n bytes to be written immediately
}

void Example3() {
    // child1 sends to child2 a message and receives it reversed

    int sender_pid, sender_status;
    int reverser_pid, reverser_status;

    if ((sender_pid = fork()) == 0) {
        // only sender enters
        printf("PID: %d\n", getpid());

        int write_FD = open(FIFO1, O_WRONLY);
        if (write_FD == -1) {exit(errno);}
        int read_FD = open(FIFO2, O_RDONLY);
        if (read_FD == -1) {exit(errno);}

        char *response = malloc(sizeof(char) * BUFFER_SIZE);

        write(write_FD, MSG1, MSG1_size);
        read(read_FD, response, (size_t) BUFFER_SIZE);
        printf("Sender received: %s\n", response);
        memset(response, 0, (size_t) BUFFER_SIZE);

        write(write_FD, MSG2, MSG2_size);
        read(read_FD, response, (size_t) BUFFER_SIZE);
        printf("Sender received: %s\n", response);

        free(response);

        close(write_FD);
        close(read_FD);
        exit(0);
    }
    if (sender_pid < 0) {fprintf(stderr, "fork failed for C1");}

    if ((reverser_pid = fork()) == 0) {
        // only reverser enters
        printf("PID: %d\n", getpid());

        int read_FD = open(FIFO1, O_RDONLY);
        if (read_FD == -1) {exit(errno);}
        int write_FD = open(FIFO2, O_WRONLY);
        if (write_FD == -1) {exit(errno);}


        char *buffer = malloc(sizeof(char) * BUFFER_SIZE);
        char *reverse_buffer = malloc(sizeof(char) * BUFFER_SIZE);
        int bytesRead;
        // read from sender
        while ((bytesRead = read(read_FD, buffer, (size_t) BUFFER_SIZE)) > 0) {
            printf("Reverser received: %s\n", buffer);

            // process sender's message
            for(int i = 0; i < bytesRead; ++i) {
                reverse_buffer[bytesRead-i-1] = buffer[i];
            }

            write(write_FD, reverse_buffer, bytesRead);

            memset(buffer, 0, (size_t) BUFFER_SIZE);
            memset(reverse_buffer, 0, (size_t) BUFFER_SIZE);
        }

        close(read_FD);
        close(write_FD);

        free(buffer);
        free(reverse_buffer);

        exit(0);
    }
    if (reverser_pid < 0) {fprintf(stderr, "fork failed for C1");}


    // wait for server to finish and get status
    waitpid(sender_pid, &sender_status, 0);
    printf("PARENT: sender finished, status: %d\n", sender_status);
    waitpid(reverser_pid, &reverser_status, 0);
    printf("PARENT: reverser finished, status: %d\n", reverser_status);

}

int main() {
    printf("\t\tPPID: %d\n", getpid());
    Example3();
    return 0;
}
