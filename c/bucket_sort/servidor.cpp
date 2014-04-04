#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <random>
#include <vector>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <algorithm>
#include "mirandom.h"

void atiende(int sd);

int main(){
	int serverSocket = socket (AF_INET, SOCK_STREAM, 0);
	
	struct sockaddr_in addr; 
    bzero(&addr,sizeof(addr)); 
	addr.sin_family = AF_INET; 
	addr.sin_port = htons(5551); 
	addr.sin_addr.s_addr = INADDR_ANY; 

	if (bind (serverSocket, (struct sockaddr *)&addr, sizeof (addr)) == -1) { 
		printf ("Error\n"); 
	}
	
	if (listen (serverSocket, 1) == -1) { 
		printf ("Error\n"); 
	}
	
	while(true){
			printf("Esperando conexiones...\n");
			atiende(serverSocket);
	}	
	return 0;
}

void atiende(int sd){
	std::vector<int> numeros;
	struct sockaddr_in cliente; 
	int dc; 
	unsigned int cl_tam = sizeof(cliente);
	dc = accept(sd, (struct sockaddr *)&cliente, &cl_tam); 
	if (dc == -1) 
	{ 
		printf ("Error\n"); 
	}
	printf("Cliente conectado %s:%d\n",inet_ntoa(cliente.sin_addr),ntohs(cliente.sin_port));
	
	
	int nums;
	int n;
	int recibidos = read(dc, &nums, sizeof(nums));  // Error checking missing
	int enviados;
	nums = ntohl(nums);
	printf("Vector de %d numeros\n",nums);
	
	for(int i = 0; i < nums; i++){
		recibidos = read(dc, &n, sizeof(n)); // Error checking missing
		numeros.push_back(ntohl(n));
	}
	
	std::sort(numeros.begin(), numeros.end());
	
	for(int i = 0; i < nums; i++){
		n = htonl(numeros[i]);
		enviados = write(dc, &n, sizeof(n)); // Error checking missing
	}
	
}
