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
#include <pthread.h>
#include "mirandom.h"

void atiende(int sd);
char oaiosdmioasmd[20];
struct Server{
	int sd, puerto;
};
void * procesaServidor(void * auxiliar);
int main(){
	pthread_t * hilos;
	
	int sockets;
	scanf("%d",&sockets);
	Server * servidores = (Server *)malloc(sockets * sizeof(Server));
	hilos =  (pthread_t *)malloc(sockets * sizeof(pthread_t));
	
	for(int i = 0; i < sockets; i++){
		servidores[i].sd = socket (AF_INET, SOCK_STREAM, 0); 
		scanf("%s",oaiosdmioasmd);
		scanf("%d",&servidores[i].puerto);
		if(pthread_create(&hilos[i], NULL, procesaServidor, (void *)&servidores[i]) != 0){
			
		}
	}
	
	for (int i = 0; i < sockets; i++) 
		pthread_join (hilos[i], NULL);
	return 0;
}

void * procesaServidor(void * auxiliar){
	Server * s = (Server *)auxiliar;
	
	struct sockaddr_in addr; 
    bzero(&addr,sizeof(addr)); 
	addr.sin_family = AF_INET; 
	addr.sin_port = htons(s->puerto); 
	addr.sin_addr.s_addr = INADDR_ANY; 
	
	if (bind (s->sd, (struct sockaddr *)&addr, sizeof (addr)) == -1) { 
		printf ("Error\n"); 
	}
	
	if (listen (s->sd, 1) == -1) { 
		printf ("Error\n"); 
	}
	
	atiende(s->sd);
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
	
	int nums;
	int n;
	int recibidos = read(dc, &nums, sizeof(nums));  // Error checking missing
	int enviados;
	nums = ntohl(nums);
	printf("Vector de %d numeros recibido de %s:%d\n",nums,inet_ntoa(cliente.sin_addr),ntohs(cliente.sin_port));
	
	// Leemos los numeros del cliente
	for(int i = 0; i < nums; i++){
		recibidos = read(dc, &n, sizeof(n)); // Error checking missing
		numeros.push_back(ntohl(n));
	}
	
	std::sort(numeros.begin(), numeros.end());
	
	// Reescribimos los numeros al cliente
	for(int i = 0; i < nums; i++){
		n = htonl(numeros[i]);
		enviados = write(dc, &n, sizeof(n)); // Error checking missing
	}
	
}
