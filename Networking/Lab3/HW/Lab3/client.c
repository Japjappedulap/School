#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <time.h>
#include <errno.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h> 
#include <time.h>

#define MAX_LENGTH 1024+1

int clientfd, cod;
struct sockaddr_in server;
char string[MAX_LENGTH];
int correct;
int correct_packets;
int sent_packets;
char recv_string[MAX_LENGTH];

void time_out(int semnal)
{
	printf("\n%d packets sent, %d accurate packets, %f\n", 
		sent_packets, 
		correct_packets,
		(1.0 * correct_packets / sent_packets) * 100
		);
	exit(0);
} 

int main(int argc, char *argv[]) {
	if (argc != 3)
	{
		printf("invalid arguments\n");
		exit(0);
	}
	int port = atoi(argv[2]);
	signal(SIGALRM, time_out);
	signal(SIGINT, time_out);
	clientfd = socket(AF_INET, SOCK_DGRAM, 0);
	if (clientfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client. @ %s\n", strerror(errno));
	    return 1;
	}
	srand(time(NULL));
	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
	server.sin_port = htons(port);
	server.sin_addr.s_addr = inet_addr(argv[1]);
	
 // 	sendto(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, sizeof(server));
	// memset(string, 0, sizeof(string));

 // 	recv_stringfrom(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, &l);
	struct timespec start, end;
 	while (1) {
 		int l = sizeof(server);
		memset(string, 0, sizeof(string));
		memset(recv_string, 0, sizeof(recv_string));
		for (int i = 0; i < MAX_LENGTH-1; ++i)
			string[i] = rand() % ('z' - 'a') + 'a';
		//printf("%s\n", string);

		sent_packets++;
	 	alarm(5);
		clock_gettime(CLOCK_MONOTONIC_RAW, &start);
		sendto(clientfd, string, sizeof(char) * MAX_LENGTH, 0, (struct sockaddr *) &server, sizeof(server));		
		recvfrom(clientfd, recv_string, sizeof(char) * MAX_LENGTH, MSG_WAITALL, (struct sockaddr *) &server, &l);
		clock_gettime(CLOCK_MONOTONIC_RAW, &end);
		
		correct = 0;
		for (int i = 0; i < MAX_LENGTH-1; ++i)
			if (string[i] == recv_string[i])
				correct++;
		if (correct == (int)strlen(string))
			correct_packets++;
		//printf("%s\n", recv_string);

		//do stuff

		uint64_t delta_us = (end.tv_sec - start.tv_sec) * 1000000 + (end.tv_nsec - start.tv_nsec) / 1000;

		printf("%d/%d bytes sent/received, accuracy: %.2f, in: %ld ms \n", 
			(int)strlen(string), 
			(int)strlen(recv_string), 
			(1.0 * correct / (int)strlen(string)) * 100,
			delta_us/1000
			);
		usleep(1300000);
 	}
 	close(clientfd);
}
 
