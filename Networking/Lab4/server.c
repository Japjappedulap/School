#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/ip.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#define MAX_LENGTH 1024

int main()
{
	char* string, *reverse;
	char c;
	int clientDescriptor, socketDescriptor;
	struct sockaddr_in server, client;
	socklen_t lenClient;

	socketDescriptor = socket(AF_INET, SOCK_STREAM, 0);

	if(socketDescriptor < 0)
	{
		printf("Error creating socket :(\n");
		return 1;
	}

	memset(&server, 0, sizeof(server));

	server.sin_family = AF_INET;
	server.sin_port = htons(12345);
	server.sin_addr.s_addr = INADDR_ANY;

	if(bind(socketDescriptor, (struct sockaddr*) &server, sizeof(server)) < 0)
	{
		printf("Error binding :(\n");
		return 1;
	}

	listen(socketDescriptor, 5);

	string = (char*)calloc(MAX_LENGTH * sizeof(char), 0);

	reverse = (char*)calloc(MAX_LENGTH * sizeof(char), 0);

	memset(&client, 0, sizeof(client));

	lenClient = sizeof(client);

	while(1)
	{
		clientDescriptor = accept(socketDescriptor, (struct sockaddr*) &client, &lenClient);

		int lenIP = 20;
		char buffer[lenIP];
		inet_ntop(AF_INET, &(client.sin_addr), buffer, lenIP);
		printf("Client %s connected\n", buffer);

		do
		{
			recv(clientDescriptor, &c, sizeof(char), MSG_WAITALL);
			strcat(string, &c);
		} while(c);

		printf("%s\n", string);
	}

	void strrev(char *p)
	{
	  char *q = p;
	  while(q && *q) ++q;
	  for(--q; p < q; ++p, --q)
	    *p = *p ^ *q,
	    *q = *p ^ *q,
	    *p = *p ^ *q;
	}

	close(socketDescriptor);
	free(string);
	free(reverse);

	return 0;
}