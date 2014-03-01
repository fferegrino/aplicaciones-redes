package chat.multicast.cliente;

import chat.multicast.compartido.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 *
 * @author (at)fferegrino
 */
public class Cliente {

    private static final int PUERTO_MULTICAST = 4042;
    private static final int BufferSize = 1024;
    private static HashMap<String, InetAddress> usuarios;

    public static void main(String[] args) {
        try {

            DatagramPacket dp;
            MulticastSocket s = new MulticastSocket(Puertos.CLIENTE_MULTICAST);
            s.setTimeToLive(200);
            s.joinGroup(InetAddress.getByName("228.1.1.1"));
            byte[] data = new byte[BufferSize];

            // <editor-fold desc="Aviso de conexión">
            data[0] = Interaccion.SALUDO_CLIENTE;
            byte[] nombre = "Hola".getBytes();
            data[1] = (byte) nombre.length;
            for (int i = 0; i < nombre.length; i++) {
                data[i + 2] = nombre[i];
            }
            dp = new DatagramPacket(data, BufferSize, InetAddress.getByName("228.1.1.1"), Puertos.SERVIDOR_MULTICAST);
            s.send(dp);
            // </editor-fold>

            // <editor-fold desc="Recepción de la lista de usuarios conectados actualmente">
            ServerSocket ss = new ServerSocket(Puertos.CLIENTE_FLUJO);
            Socket scliente = ss.accept();
            DataInputStream bis = new DataInputStream(scliente.getInputStream());
            scliente.close();
            // </editor-fold>
            
            

        } catch (UnknownHostException uh) {
        } catch (IOException io) {
        }

    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
