#include <cstdio>
#include <cstdlib>
#include <random>
#include <vector>
#include <pthread.h>
#include <algorithm>
#include "mirandom.h"
#define DEFNUMS 50
using namespace std;

struct AuxHiloBucket{
	int superior;
	int inferior;
	int size;
	vector<int> numeros;
	
	void agregaNumero(int n){
		size++;
		numeros.push_back(n);
	}
};

void * procesaCubeta(void * auxiliar);

int main(int argc, char** argv){
	init();
	int cubetas, nums, rango;
	AuxHiloBucket * auxiliares;
	pthread_t * hilos;
	if(argc > 1){
		cubetas = atoi(argv[1]);
		nums = DEFNUMS;
		
		rango = ceil(((double)MAX) / ((double)cubetas));
		auxiliares = (AuxHiloBucket *)malloc(cubetas * sizeof(AuxHiloBucket));
		hilos = (pthread_t *)malloc(cubetas * sizeof(pthread_t));
		
		// Creamos las cubetas
		for(int j = 0; j < cubetas; j++){
			auxiliares[j].inferior = j * rango;
			auxiliares[j].superior = (j+1) * rango;
		}
		
		// Generamos los numeros aleatorios y los insertamos en la cubeta correspondiente
		for(int i = 0; i < nums; i++){
			int nn = next();
			printf("%d ", nn);
			for(int j = 0; j < cubetas; j++){
				if(nn > j * rango &&  nn <= (j+1) * rango){
					auxiliares[j].agregaNumero(nn);
				}
			}
		}
		printf("\n");
		
		// Creamos los hilos
		for(int i = 0; i < cubetas; i++){
			if(pthread_create(&hilos[i], NULL, procesaCubeta, (void *)&auxiliares[i]) != 0){
				
			}
		}
		
		for (int i = 0; i < cubetas; i++) 
			pthread_join (hilos[i], NULL);
		
		for (int i = 0; i < cubetas; i++){
			for(int j = 0; j < auxiliares[i].size; j++){				
				printf("%d ", auxiliares[i].numeros[j]);
			}
		}
		printf("\n");
	}
	return 0;
}


void * procesaCubeta(void * auxiliar){
	AuxHiloBucket * a = (AuxHiloBucket *) auxiliar;
	sort(a->numeros.begin(),a->numeros.end()); // Ordenacion
}

