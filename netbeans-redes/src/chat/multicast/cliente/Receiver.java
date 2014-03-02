package chat.multicast.cliente;

import chat.multicast.compartido.Interaccion;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;

/**
 *
 * @author (at)fferegrino
 */
public class Receiver extends Thread{

    private MulticastSocket multicastSocket;

    public Receiver(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket dp = new DatagramPacket(new byte[Interaccion.MAX_BUFFER_SIZE], Interaccion.MAX_BUFFER_SIZE);
                multicastSocket.receive(dp);
                byte[] data = dp.getData();
                if(data[0] == Interaccion.PING){
                    System.out.println("Ping");
                }
            } catch (IOException ex) {
                
            }
        }
    }


}
