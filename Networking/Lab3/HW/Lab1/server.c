#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>

#define MAX_LENGTH 1024

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
	
	while (1)
	{
		char string[MAX_LENGTH];
		memset(string, 0, sizeof(MAX_LENGTH));
		
		recvfrom(s, string, sizeof(char) * MAX_LENGTH, 0, (struct sockaddr *) &client, &l);
		printf("original: %s , strlen= %d\n", string, strlen(string));
		int string_len = strlen(string);
		char aux;
		for (int i = 0; i < string_len/2; ++i)
			aux = string[i],
			string[i] = string[string_len - 2 - i],
			string[string_len - 2 - i] = aux;
		printf("reversed: %s , strlen= %d\n\n", string, strlen(string));
		sendto(s, string, sizeof(char) * string_len, 0, (struct sockaddr *) &client, sizeof(server));
 		
	}
	close(s);
	
}