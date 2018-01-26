#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
#include <arpa/inet.h>
             
#define MAX_LENGTH 1024
char result[MAX_LENGTH];
char clientans[MAX_LENGTH];
int clientfd, cod;
int32_t r;
struct sockaddr_in server;

int main() {
	  
	clientfd = socket(AF_INET, SOCK_STREAM, 0);
	if (clientfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client.\n");
	    return 1;
	}
	  
	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_port = htons(54321);
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	  
	if (connect(clientfd, (struct sockaddr *) &server, sizeof(struct sockaddr_in)) < 0) {
	    fprintf(stderr, "Eroare la conectarea la server.\n");
		return 1;
	}
	  
	//fprintf(stderr, "%lf\n", clientans);
	printf("input string: ");
	fgets(clientans, MAX_LENGTH, stdin);
	clientans[strlen(clientans)-1] = 0;	

	send(clientfd, clientans, sizeof(clientans), 0);

	printf("input char: ");
	fgets(clientans, MAX_LENGTH, stdin);
	clientans[strlen(clientans)-1] = 0;

	send(clientfd, clientans, sizeof(clientans), 0);

	memset(result, 0, sizeof(result));
	recv(clientfd, result, sizeof(result), MSG_WAITALL);
	printf("result: \"%s\"\n", result);

 	close(clientfd);
}
 
 