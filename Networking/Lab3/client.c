#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int main() {
	int c;
	struct sockaddr_in server;
	char msg[100];
	
	c = socket(AF_INET, SOCK_DGRAM, 0);
	
	if (c < 0) {
		printf("Eroare la crearea socketului...\n");;
		return 1;
	}
	
	memset(&server, 0, sizeof(server));
	server.sin_port = htons(1234);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	
	
	int i;
	for (i = 0; i <= 30000000; i++) {
		sprintf(msg, "%d", i);		
		sendto(c, msg, sizeof(msg), 0, (struct sockaddr *) &server, (socklen_t) sizeof(server));
		// usleep(1000);
		// printf("%d\n", i);
	}
	close(c);
}