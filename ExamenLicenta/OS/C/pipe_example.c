#include <stdio.h>
#include <unistd.h>
#include <sys/fcntl.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/wait.h>

#define MSG "Hello world!"
#define MSG_size 13

#define MSG2 "Pipes are awesome!"
#define MSG2_size 19


void child1(int* fd1, int* fd2){
    sleep(1);                       // emulate work
    printf("child1: writing...\n"); // notify work is complete
    write(fd1[1], MSG, MSG_size);   // write message
    close(fd1[1]);                  // close write pipe 1
    char *buffer = malloc(100);
    read(fd2[0], buffer, 100);      // block and wait for content
    close(fd2[0]);                  // close read pipe 2
    printf("child1: read: %s\n", buffer);
    printf("child1: done\n");       // notify finish
}

void child2(int* fd1, int* fd2){
    char *buffer = malloc(100);
    read(fd1[0], buffer, 100);      // block and wait for content
    close(fd1[0]);                  // close read pipe 1
    printf("child2: read: %s\n", buffer);
    sleep(1);                       // emulate work
    printf("child2: writing...\n"); // notify work is complete
    write(fd2[0], MSG2, MSG2_size); // write message
    close(fd2[1]);                  // close write pipe 2
    printf("child2: done\n");       // notify finish
}

int main(int n, char** a) {
    int pid1, pid2, status1, status2;
    int fd1[2]; // child1 writes to child2
    int fd2[2]; // child2 writes to child1
    if (pipe(fd1) < 0) {
        fprintf(stderr, "pipe() failed");
    }
    if (pipe(fd2) < 0) {
        fprintf(stderr, "pipe() failed");
    }

    if ((pid1 = fork()) == 0) {
        printf("CHILD PID: %d\n", getpid());
        child2(fd1, fd2);
        exit(0);
    }
    if ((pid2 = fork()) == 0) {
        printf("CHILD PID: %d\n", getpid());
        child1(fd1, fd2);
        exit(0);
    }
    waitpid(pid1, &status1, 0);
    waitpid(pid2, &status2, 0);

    return 0;
}