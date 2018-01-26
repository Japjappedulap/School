#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
             
#define MAX_LENGTH 1024
 
int main() {
	int serverfd, cod;
	struct sockaddr_in server;
	  
	serverfd = socket(AF_INET, SOCK_STREAM, 0);
	if (serverfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client. @ %s\n", strerror(errno));
	    return 1;
	}
	  
	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
	server.sin_port = htons(12345);
	server.sin_addr.s_addr = inet_addr("172.30.112.203");
	  
	if (connect(serverfd, (struct sockaddr *) &server, sizeof(server)) < 0) {
	    fprintf(stderr, "Eroare la conectarea la server. @ %s\n", strerror(errno));
		return 1;
	}
	
	printf("Connected to server\n");
	char *string = malloc(sizeof(char) * MAX_LENGTH);
	memset(string, 0, sizeof(char) * MAX_LENGTH);
	fgets(string, MAX_LENGTH, stdin);
	string[strlen(string) - 1] = 0;
	
	int string_len = strlen(string);

 	for (int i = 0; i <= string_len; ++i) {
 		
 		if (send(serverfd, string + i, sizeof(char), 0) < 0) {
 			printf("Eroare la send\n");
 			exit(1);
 		}	
 	}
 	char c[2] = "\0\0";
 	char result[MAX_LENGTH];
 	memset(result, 0, sizeof(char) * MAX_LENGTH);
 	for (int i = 0; i  <string_len; ++i)
 	{
 		recv(serverfd, result+i, sizeof(char), MSG_WAITALL);
 	}
 	printf("%s\n", result);
 	// if (recv(serverfd, result, sizeof(result), MSG_WAITALL) < 0)
 	// {
 	// 	printf("Eroare la recv! %s !! %d\n", strerror(errno), errno);
 	// 	return 1;
 	// }

 	free(string);
 	close(serverfd);
}
 
 