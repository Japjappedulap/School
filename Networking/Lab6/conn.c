// #include <sys/socket.h>
// #include <netinet/in.h>
// #include <stdio.h>
// #include <string.h>
// #include <stdint.h>
// #include <errno.h>
// #include <stdlib.h>
// #include <arpa/inet.h>
// #include <unistd.h>
             
// #define MAX_LENGTH 1024
 
// int main() {
// 	int clientfd, cod;
// 	struct sockaddr_in server;
	  
// 	clientfd = socket(AF_INET, SOCK_DGRAM, 0);
// 	if (clientfd < 0) {
// 	    fprintf(stderr, "Eroare la creare socket client. @ %s\n", strerror(errno));
// 	    return 1;
// 	}
	  
// 	memset(&server, 0, sizeof(server));
// 	server.sin_family = AF_INET;
// 	server.sin_port = htons();
// 	server.sin_addr.s_addr = inet_addr("172");
	
// 	char string[MAX_LENGTH];
// 	memset(string, 0, sizeof(string));
// 	fgets(string, MAX_LENGTH, stdin);
	

// 	int string_len = strlen(string);


//  	sendto(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, sizeof(server));
// 	memset(string, 0, sizeof(string));
//  	sleep(1);
//  	int l = sizeof(server);
//  	recvfrom(clientfd, string, sizeof(string), 0, (struct sockaddr *) &server, &l);
 	
// 	printf("%s", string);
 	
//  	close(clientfd);
// }
 
//  

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
	int c;
	struct sockaddr_in server;
	char msg[256];

	char ccc;

	c = socket(AF_INET, SOCK_STREAM, 0);

	if (c < 0) {
		printf("Eroare la crearea socketului...\n");;
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_port = htons(5432);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr("172.30.113.3");

	if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0 ) {
		printf("Eroare la conectare...\n");
		return 1;
	}

    while (1) {

        char backMsg[256];
        bzero(backMsg, 256);
        recv(c, &backMsg, sizeof(backMsg), 0);
        printf("Raspuns: %s\n", backMsg);

        printf("Mesaj: ");
        bzero(msg, 256);
        fgets(msg, sizeof(msg), stdin);
        send(c, msg, strlen(msg) * sizeof(char) , 0);
	}

	close(c);
}