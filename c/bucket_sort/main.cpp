#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <random>
#include <vector>
#include <pthread.h>
#include <algorithm>
#include "mirandom.h"
#include "comunicacion.h"
#define DEFNUMS 3500
//using namespace std;
char ip[16];
int localPort = 5432;
struct AuxHiloBucket{
	char ip[16];
	int puerto;
	int superior;
	int inferior;
	int size;
	std::vector<int> numeros;
	
	void agregaNumero(int n){
		size++;
		numeros.push_back(n);
	}
};

void * procesaCubeta(void * auxiliar);
void rellenaCubetas(AuxHiloBucket * arr, int cubetas);

int main(int argc, char** argv){
	init();
	int cubetas;
	AuxHiloBucket * auxiliares;
	pthread_t * hilos;
	scanf("%d",&cubetas);

	auxiliares = (AuxHiloBucket *)malloc(cubetas * sizeof(AuxHiloBucket));
	hilos = (pthread_t *)malloc(cubetas * sizeof(pthread_t));
	
	// Rellenamos las cubetas
	rellenaCubetas(auxiliares, cubetas);

	for(int j = 0; j < cubetas; j++){
		scanf("%s",auxiliares[j].ip);
		scanf("%d",&auxiliares[j].puerto);
	}
	
	// Creamos los hilos
	for(int i = 0; i < cubetas; i++){
		if(pthread_create(&hilos[i], NULL, procesaCubeta, (void *)&auxiliares[i]) != 0){
			
		}
	}
	
	for (int i = 0; i < cubetas; i++) 
		pthread_join (hilos[i], NULL);
	return 0;
}


void * procesaCubeta(void * auxiliar){
	AuxHiloBucket * a = (AuxHiloBucket *) auxiliar;
	int vsize = sizeof(std::vector<int>) + (sizeof(int) * a->numeros.size());
	printf("Cubeta se va a %s:%d con %d bytes\n",a->ip,a->puerto,vsize);
	int sd = comunicacion();
	bool verdadero = enviaNumeros(a->numeros,sd,a->ip,a->puerto);
}

void rellenaCubetas(AuxHiloBucket * arr, int cubetas){	
	int rango = ceil(((double)MAX) / ((double)cubetas));

	// Generamos los numeros aleatorios y los insertamos en la cubeta correspondiente
	for(int i = 0; i < DEFNUMS; i++){
		int nn = next();
		for(int j = 0; j < cubetas; j++){	
			arr[j].inferior = j * rango;
			arr[j].superior = (j+1) * rango;
			if(nn > arr[j].inferior &&  nn <= arr[j].superior){
				arr[j].agregaNumero(nn);
			}
		}
	}
}

