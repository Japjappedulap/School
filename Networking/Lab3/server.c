#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int main() {
	int s;
	struct sockaddr_in server, client;
	int c, l;
	
	s = socket(AF_INET, SOCK_DGRAM, 0);
	if (s < 0) {
		printf("Eroare la crearea socketului...\n");
		return 1;
	}
	
	memset(&server, 0, sizeof(server));
	server.sin_port = htons(1234);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	
	if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
		printf("Eroare la bind...\n");
		return 1;
	}
	
	l = sizeof(client);
	memset(&client, 0, sizeof(client));
	
	int k = 0;
	while(1) {
		char msg[100];
		recvfrom(s, msg, sizeof(msg), 0, (struct sockaddr *) &client, &l);
		int rec = atoi(msg);
		if (rec != k)
		{
			fprintf(stderr, "DIFF: %d %d\n", k, rec);
			sleep(999999);
		}
		++k;
		//fprintf(stderr, "%s\t", msg);
	}
	close(s);
	
}