package chat.multicast.server;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    private static ArrayList<Usuario> usuarios;
    //private static HashMap<String, InetAddress> usuarios;

    public static void main(String[] args) {
        usuarios = new ArrayList<Usuario>(); //new HashMap<String, InetAddress>();
        try {
            MulticastSocket socket = new MulticastSocket(Puertos.SERVIDOR_MULTICAST);
            socket.setTimeToLive(50);
            socket.joinGroup(InetAddress.getByName("229.2.2.2"));
             
            while (true) {
                log("Escuchando " + socket.getNetworkInterface().getDisplayName());
                DatagramPacket paquete = new DatagramPacket(new byte[1024], 1024);
                socket.receive(paquete);
                byte[] data = paquete.getData();
                log("Conexion " + paquete.getSocketAddress());
                switch (data[0]) {
                    case Interaccion.SALUDO_CLIENTE:
                        InetAddress cliente = paquete.getAddress();
                        int nameLen = data[1];//ByteBuffer.allocateDirect(4).wrap(data, 1, 4).getInt();
                        String usuario = new String(data, 2, nameLen);
                        log(usuario);

//                    Socket s = new Socket(cliente, Puertos.CLIENTE_FLUJO);
//                    DataOutputStream bos = new DataOutputStream(s.getOutputStream());
//                    
//                    s.close();;
                        break;
                    default:
                        System.out.println("Mensaje de " + paquete.getSocketAddress() + " no procesado") ;
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
