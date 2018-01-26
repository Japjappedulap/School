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

#define MAX_CLIENTS 128

int serverfd;
int clientfd[MAX_CLIENTS];
int clients = 0;
pthread_t threads[MAX_CLIENTS];
double clientans[MAX_CLIENTS];
double serverans;

int registered_clients;
pthread_mutex_t mtx;

double absolute(double x) {return x > 0 ? x : -x;}

void time_out(int semnal)
{
	fprintf(stderr, "Executing time_out\n");
	int winner = -1;
	double diff = 999999999;
	for (int i = 0; i < clients; ++i)
	{
		printf("%lf ", clientans[i]);
		if (absolute(clientans[i] - serverans) < diff)
			winner = i, diff = absolute(clientans[i] - serverans);
	}
	printf("\n");
	for (int i = 0; i < clients; ++i)
	{
		char result[100];
		memset(result, 0, sizeof(result));
		if (i == winner)
		{
			strcpy(result, "You have the best guess with an error of ");
			sprintf(result + strlen(result), "%lf", diff);
			printf("%s\n", result);
		}
		else
		{
			strcpy(result, "You lost!");
			printf("%s\n", result);
		}
		if (send(clientfd[i], result, strlen(result), 0) < 0)
		{
			printf("Eroare in pl! %s !! %d\n", strerror(errno), errno);
			exit(1);
		}

	}	

	close(serverfd);
	exit(0);
}

void* treat_client(void* p){
	fprintf(stderr, "\t\t\tEntered treat_client\n");
	int i = (int)p;
	printf("\t\t\tindex: %d\n", i);
	fprintf(stderr, "ALARM SET TO 60\n");
	alarm(60);
	recv(clientfd[i], clientans+i, sizeof(double), MSG_WAITALL);
	pthread_mutex_lock(&mtx);
	registered_clients++;
	pthread_mutex_unlock(&mtx);
	if (registered_clients < clients)
		printf("ALARM SET TO 60\n"),
		alarm(60);
	else 
		printf("ALARM SET TO 10\n"),
		alarm(10);
	printf("\t\t\tclientans[%d]: %f\n", i, clientans[i]);
} 

int main(){
	signal(SIGALRM, time_out);
	srand(time(NULL));
    pthread_mutex_init(&mtx, NULL);

	serverans = (double)rand() / (double) 100000000;
	fprintf(stderr, "Server answer: %f\n", serverans);
	struct sockaddr_in client, server;
	serverfd = socket(AF_INET, SOCK_STREAM, 0); 

	if (serverfd < 0)
	{
		fprintf(stderr, "Eroare la creare socket server.\n");
		return 1;
	}

	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_port = htons(1234);
	server.sin_addr.s_addr = INADDR_ANY;

	if (bind(serverfd, (struct sockaddr *) &server, sizeof(struct sockaddr_in)) < 0)
	{
		fprintf(stderr, "Eroare la bind.\n");
		return 1;
	}
	listen(serverfd, 5);
	
	while (1)
	{
		fprintf(stderr, "ALARM SET TO 10\n");
		alarm(10);
		fprintf(stderr, "Waiting for client to connect\n");
		
		
		int length = sizeof(clientfd[clients]);
		clientfd[clients] = accept(serverfd, (struct sockaddr*) &client, &length);
		pthread_create(&threads[clients], NULL, treat_client, (int*)clients);
		

		printf("Thread created!\n");
		if (clients >= 127)
			break;
		clients++;
	}
	close(serverfd);
}