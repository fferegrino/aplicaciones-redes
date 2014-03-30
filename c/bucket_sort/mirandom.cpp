#include <random>
#include "mirandom.h"
using namespace std;
mt19937 mt;
uniform_int_distribution<int32_t> intDist(MIN,MAX);

void init(){
	mt.seed( time(NULL) );
}

int next(){
	return intDist(mt);
}
