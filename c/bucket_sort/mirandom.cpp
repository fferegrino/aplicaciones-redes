#include <random>
#include <cstdlib>
#include <ctime>
#include "mirandom.h"
#define C11
using namespace std;
#ifdef C11
mt19937 mt;
uniform_int_distribution<int32_t> intDist(MIN,MAX);

void init(){
	mt.seed( time(NULL) );
}

int next(){
	return intDist(mt);
}
#endif

#ifndef C11
void init(){
  srand (time(NULL));
}

int next(){
	return ((rand() % (MAX - MIN))) + MIN ;
}	
#endif
