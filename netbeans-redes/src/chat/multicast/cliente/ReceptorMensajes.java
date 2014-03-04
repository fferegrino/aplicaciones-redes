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
public class ReceptorMensajes extends Thread {

    private MulticastSocket multicastSocket;
    NuevoMensajeListener listener;
    
    void addListener(NuevoMensajeListener listener){
        this.listener = listener;
    }
    
    void removeListener(){
        this.listener = null;
    }

    public ReceptorMensajes(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket dp = new DatagramPacket(new byte[Interaccion.MAX_BUFFER_SIZE], Interaccion.MAX_BUFFER_SIZE);
                multicastSocket.receive(dp);
                byte[] data = dp.getData();
                if (data[0] == Interaccion.MENSAJE_GRUPAL) {
                    NuevoMensajeEvent e = new NuevoMensajeEvent(this, dp.getAddress(), new String(data), dp.getPort());
                    if(listener != null) listener.nuevoMensajeHandler(e);
                }
            } catch (IOException ex) {
            }
        }
    }
}
