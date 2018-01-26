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
		uint16_t app[128];
		memset(app, 0, sizeof(app));
		for (int i = 0; i < string_len; ++i)
			app[string[i]]++;
		int ivipchar = -1;
		uint16_t vipapp = 0;
		for (int i = 0; i < 128; ++i)
			if (vipapp < app[i]){
				vipapp = app[i]; 
				ivipchar = i;
			}
		char vipchar[2];
		memset(vipchar, 0, sizeof(vipchar));
		vipchar[0] = (char)ivipchar;
		printf("%s\n", vipchar);
		sendto(s, vipchar, sizeof(vipchar), 0, (struct sockaddr *) &client, sizeof(server));
		vipapp = htons(vipapp);
		sendto(s, &vipapp, sizeof(vipapp), 0, (struct sockaddr *) &client, sizeof(server));
		vipapp = ntohs(vipapp);

		for (uint16_t i = 0; i < string_len; ++i){
			if (ivipchar == string[i]){
				i = htons(i);
				sendto(s, &i, sizeof(i), 0, (struct sockaddr *) &client, sizeof(server));
				i = ntohs(i);
			}
		}
	}
	close(s);
	
}