package chat.multicast;

import chat.multicast.compartido.*;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    
    private static HashMap<String, InetAddress> usuarios;

    public static void main(String[] args) {
        usuarios = new HashMap<String, InetAddress>();
        try {
            MulticastSocket socket = new MulticastSocket(Puertos.SERVIDOR_MULTICAST);
            socket.setTimeToLive(50);
            socket.joinGroup(InetAddress.getByName("228.1.1.1"));

            while (true) {log("Escuchando");
                DatagramPacket paquete = new DatagramPacket(new byte[1024], 1024);
                socket.receive(paquete);
                byte[] data = paquete.getData();
                log("Conexion");
                if (data[0] == Interaccion.SALUDO_CLIENTE) {
                    InetAddress cliente = paquete.getAddress();
                    int nameLen = data[1];//ByteBuffer.allocateDirect(4).wrap(data, 1, 4).getInt();
                    String usuario = new String(data, 2, nameLen);
                    log(usuario);
                    Socket s = new Socket(cliente, Puertos.CLIENTE_FLUJO);
                    DataOutputStream bos = new DataOutputStream(s.getOutputStream());
                    bos.writeInt(12);
                    s.close();;
                }
            }

        } catch (UnknownHostException uh) {
        } catch (IOException io) {
        }

    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
