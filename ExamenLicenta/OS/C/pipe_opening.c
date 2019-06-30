#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

#include <stdio.h>
#include <unistd.h>
#include <sys/fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/wait.h>

// opening pipes rarely fails, as not much can go wrong.

void OpenSuccessfully() {
    int pipe_fd[2];

    if (pipe(pipe_fd) < 0) {
        fprintf(stderr, "pipe() failed with message: %s\n", strerror(errno));
    }
}

void OpenError() {
    int *pipe_fd; // as no memory is allocated here, error EFAULT is raised ("bad address")

    if (pipe(pipe_fd) < 0){
        fprintf(stderr, "pipe() failed with message: %s\n", strerror(errno));
    }
}

int main(int n, char** a) {
    OpenError();

    return 0;
}
