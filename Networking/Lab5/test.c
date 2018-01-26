#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
             
#define max 512
char result[max];
char clientans[max];
	int clientfd, cod;
	int32_t r;
	struct sockaddr_in server;

void receive(){
	memset(result, 0, sizeof(result));
	char c[2];
	do
	{
		recv(clientfd, &c, sizeof(char), MSG_WAITALL);
		c[1] = '\0';
		strcat(result, &c);
		if (c[0] == '\n')
			break;
	} while(c);
	printf("received: %s\n", result);
}
 
int main() {
	  
	clientfd = socket(AF_INET, SOCK_STREAM, 0);
	if (clientfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client.\n");
	    return 1;
	}
	  
	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_port = htons(54321);
	server.sin_addr.s_addr = inet_addr("172.30.113.3");
	  
	if (connect(clientfd, (struct sockaddr *) &server, sizeof(struct sockaddr_in)) < 0) {
	    fprintf(stderr, "Eroare la conectarea la server.\n");
		return 1;
	}
	  
	//fprintf(stderr, "%lf\n", clientans);
	receive();
	memset(clientans, 0, sizeof(clientans));
	sprintf(clientans, "pv\n");

	send(clientfd, clientans, strlen(clientans), 0);
	receive();

	memset(clientans, 0, sizeof(clientans));
	
	fgets(clientans, max, stdin);
	send(clientfd, clientans, strlen(clientans), 0);


	receive();


 	close(clientfd);
}
 
 