run: cliente.out servidor.out
test: 
	clear
	./cliente.out < in.txtcliente.out servidor.out
cliente.out: mirandom.o comunicacion.o
	g++ -o cliente.out main.cpp mirandom.o comunicacion.o -lpthread -std=c++11
servidor.out:
	g++ -o servidor.out servidor.cpp -lpthread -std=c++11
mirandom.o:
	g++ -c mirandom.cpp -std=c++11
comunicacion.o:
	g++ -c comunicacion.cpp -std=c++11
clean:
	rm mirandom.o
	rm comunicacion.o
	rm cliente.out
	rm servidor.out	
	clear
