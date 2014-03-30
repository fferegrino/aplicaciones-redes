run: a.out
	./a.out 10
a.out: mirandom.o
	g++ main.cpp mirandom.o -lpthread -std=c++11
mirandom.o:
	g++ -c mirandom.cpp -std=c++11
clean:
	rm mirandom.o
	rm a.out
