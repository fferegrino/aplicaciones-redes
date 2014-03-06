package chat.multicast.cliente;

import chat.multicast.compartido.Interaccion;
import chat.multicast.compartido.Usuario;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
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
    
    public static Usuario aceptaUsuario(byte[] data, InetAddress cliente, int puerto) {
        int nameLen = data[1];//ByteBuffer.allocateDirect(4).wrap(data, 1, 4).getInt();
        String nombreUsuario = new String(data, 2, nameLen);
        Usuario u = new Usuario(cliente, puerto, nombreUsuario);
        return u;
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
                        
                        
                        NuevoMensajeEvent e = new NuevoMensajeEvent(this, dp.getAddress(), new String(data, 1, Interaccion.MAX_BUFFER_SIZE - 1), dp.getPort());
                        if (listener != null) {
                            listener.nuevoMensajeHandler(e);
                        }
                        break;
                    case Interaccion.MENSAJE_PRIVADO:
                        String msg = new String(data, 1, Interaccion.MAX_BUFFER_SIZE - 1);
                        NuevoMensajeEvent event = new NuevoMensajeEvent(this, dp.getAddress(), msg, dp.getPort());
                        event.setPrivado(true);
                        if (listener != null) {
                            listener.nuevoMensajeHandler(event);
                        }
                        break;
                    case Interaccion.DESPEDIDA_USUARIO:
                        Usuario u = new Usuario(dp.getAddress(), dp.getPort(), null);
                        if (listener != null) {
                            listener.userRemoved(u);
                        }
                        break;
                    
                    case Interaccion.SALUDO_CLIENTE:
                        Usuario nuevo = aceptaUsuario(data, dp.getAddress(), dp.getPort());
                        if (listener != null) {
                            listener.userAdded(nuevo);
                        }
                        break;
                    
                }
            } catch (IOException ex) {
            }
        }
    }
}
