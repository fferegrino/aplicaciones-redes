package chat.multicast.cliente;

import chat.multicast.compartido.Interaccion;
import chat.multicast.compartido.Usuario;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 *
 * @author (at)fferegrino
 */
public class ReceptorMensajes extends Thread {

    private MulticastSocket multicastSocket;
    NuevoMensajeListener listener;

    void addListener(NuevoMensajeListener listener) {
        this.listener = listener;
    }

    void removeListener() {
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
                switch (data[0]) {
                    case Interaccion.MENSAJE_GRUPAL:
                    //case Interaccion.MENSAJE_PRIVADO:
                        NuevoMensajeEvent e = new NuevoMensajeEvent(this, dp.getAddress(), new String(data), dp.getPort());
                        if (listener != null) {
                            listener.nuevoMensajeHandler(e);
                        }
                        break;
                    case Interaccion.DESPEDIDA_USUARIO:
                        Usuario u = new Usuario(dp.getAddress(), dp.getPort(), null);
                        if (listener != null) {
                            listener.userRemoved(u);
                        }
                        break;

                }
            } catch (IOException ex) {
            }
        }
    }
}
