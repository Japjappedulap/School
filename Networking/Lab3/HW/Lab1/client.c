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
	int clientfd, cod;
	struct sockaddr_in server;
	  
	clientfd = socket(AF_INET, SOCK_DGRAM, 0);
	if (clientfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client. @ %s\n", strerror(errno));
	    return 1;
	}
	  
	memset(&server, 0, sizeof(server));
	server.sin_family = AF_INET;
	server.sin_port = htons(1234);
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	
	char string[MAX_LENGTH];
	memset(string, 0, sizeof(string));
	fgets(string, MAX_LENGTH, stdin);
	

	int string_len = strlen(string);


 	sendto(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, sizeof(server));
	memset(string, 0, sizeof(string));
 	sleep(1);
 	int l = sizeof(server);
 	recvfrom(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, &l);
 	
	printf("%s", string);
 	
 	close(clientfd);
}
 
 