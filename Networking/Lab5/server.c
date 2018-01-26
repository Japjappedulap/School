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

#define MAX_LENGTH 1024

int serverfd;
int clientfd;
char result[MAX_LENGTH];
char aux[MAX_LENGTH];
int registered_clients;

 
int main(){
	
	struct sockaddr_in client, server;
	serverfd = socket(AF_INET, SOCK_STREAM, 0); 

	if (serverfd < 0)
	{
		fprintf(stderr, "Eroare la creare socket server.\n");
		return 1;
	}

	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_port = htons(54321);
	server.sin_addr.s_addr = INADDR_ANY;

	if (bind(serverfd, (struct sockaddr *) &server, sizeof(struct sockaddr_in)) < 0)
	{
		fprintf(stderr, "Eroare la bind.\n");
		return 1;
	}
	listen(serverfd, 5);
	

	char clientans[MAX_LENGTH];

	while (1)
	{
		fprintf(stderr, "Waiting for client to connect\n");
			
		int length = sizeof(clientfd);
		clientfd = accept(serverfd, (struct sockaddr*) &client, &length);
		fprintf(stderr, "client connected\n");

		
		memset(result, 0, sizeof(result));
		recv(clientfd, result, sizeof(result), MSG_WAITALL);
		printf("received: %s\n", result);

		recv(clientfd, aux, sizeof(aux), MSG_WAITALL);

		char c = aux[0];
		printf("received: %c\n", c);

		int i;
		for (i = 0; i < strlen(result); ++i)
		{
			printf("%d : %c\n", i, result[i]);
			if (result[i] == c)
				break;
		}
		++i;
		printf("%s %d\n",result+i, strlen(result+i));
		send(clientfd, result+i, strlen(result+i), 0);		

		printf("%s\n", result);		
		close(clientfd);
	}
	close(serverfd);
}