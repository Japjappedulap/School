#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <errno.h>
             
#define max 100
char result[64];
 
int main() {
	int clientfd, cod;
	int32_t r;
	struct sockaddr_in server;
	  
	clientfd = socket(PF_INET, SOCK_STREAM, 0);
	if (clientfd < 0) {
	    fprintf(stderr, "Eroare la creare socket client.\n");
	    return 1;
	}
	  
	memset(&server, 0, sizeof(struct sockaddr_in));
	server.sin_family = AF_INET;
	server.sin_port = htons(1234);
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	  
	if (connect(clientfd, (struct sockaddr *) &server, sizeof(struct sockaddr_in)) < 0) {
	    fprintf(stderr, "Eroare la conectarea la server.\n");
		return 1;
	}
	  
	double clientans;
	printf("Input a float in [0, 20]: ");
	scanf("%lf", &clientans);
	//fprintf(stderr, "%lf\n", clientans);
 	if (send(clientfd, &clientans, sizeof(clientans), 0) < 0)
 	{
 		fprintf(stderr, "Eroare la trimiterea datelor la server.\n");
  		return 1;
 	}


 	if (recv(clientfd, result, sizeof(result), MSG_WAITALL) < 0)
 	{
 		printf("Eroare in pl! %s !! %d\n", strerror(errno), errno);
 		return 1;
 	}
 	printf("%s\n", result);
 	close(clientfd);
}
 
 