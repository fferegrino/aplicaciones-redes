#include <sys/types.h>	
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <cstdio>
#include <cstring>
#include "comunicacion.h"

int comunicacion(){
	return socket(AF_INET,SOCK_STREAM,0); 
}

bool enviaNumeros(std::vector<int> numeros, int sd, char * server, int port){
	struct hostent *host; 
    host=gethostbyname(server); 
    if(host==NULL){ 
        perror("Direccion no valida"); 
        //exit(1); 
    }
    
	struct sockaddr_in dst; 
    bzero(&dst,sizeof(dst)); 
    dst.sin_family=AF_INET; 
    dst.sin_port=htons(port);
    //memcpy((char *)& dst.sin_addr.s_addr, dst.h.addr,dst.h_length); 
    if(connect(sd,(struct sockaddr *)&dst,sizeof(dst))<0){ 
        perror("Error en la funcion connect\n"); 
    } 
    
	int nums = numeros.size();
	int dato_red = htonl(nums);
	int enviados = write(sd, &dato_red, sizeof(dato_red)); // Error checking missing
	for(int i = 0; i < nums; i++){
		dato_red = htonl(numeros[i]);
		enviados = write(sd, &dato_red, sizeof(dato_red)); // Error checking missing
	}
	
	return true;
}
