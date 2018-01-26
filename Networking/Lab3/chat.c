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
	server.sin_port = htons(12345);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr("172.30.113.222");
	
	while (1)
	{
		printf("baga mesaj: \n");
		fgets(msg, sizeof(msg), stdin);
		sendto(c, (char*)msg, sizeof(msg), 0, (struct sockaddr *) &server, (socklen_t) sizeof(server));	
		memset(msg, 0, sizeof(msg));

		recvfrom(c, (char*)msg, sizeof(msg), 0, (struct sockaddr *) &server, (socklen_t) sizeof(server));	
		printf("Message received: %s\n", msg);
		memset(msg, 0, sizeof(msg));
	}	

	close(c);

}