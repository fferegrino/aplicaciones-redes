package ftp.datagrama.servidor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];
    static String serverPath = "C:/ftp/";

    public static void main(String[] args) {
        try {

            int pto_dst = 9000;
            InetAddress dst = InetAddress.getByName("localhost");
            SocketAddress addr = new InetSocketAddress(dst, pto_dst);
            DatagramSocket cl = new DatagramSocket(addr);

            while (true) {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                cl.receive(dp);
                int files = bytesToInt(dp.getData());
                for (int i = 0; i < files; i++) {
                    // Recepcion del nombre de archivo
                    cl.receive(dp);
                    String nombre = new String(dp.getData());
                    // Recepcion de la longitud del archivo
                    cl.receive(dp);
                    long longArchivo = bytesToLong(dp.getData());
                    FileOutputStream fos = new FileOutputStream(serverPath + nombre);
                    System.out.println("Archivo " + nombre + " " + longArchivo);
                    // Comienzo de la lectura:
                    cl.receive(dp);
                    int resultadoLectura = bytesToInt(dp.getData());
                    while (resultadoLectura != -1) {
                        System.out.println("\tBytes leídos " + resultadoLectura);
                        cl.receive(dp);
                        // Escribimos los bytes al archivo
                        fos.write(dp.getData(), 0, resultadoLectura);
                        cl.receive(dp);
                        resultadoLectura = bytesToInt(dp.getData());
                    }
                    System.out.println("Escritura terminada");
                    // Cerramos el FileOutputStream
                    fos.close();
                }
            }


        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }


    }

    public static int bytesToInt(byte[] byteBarray) {
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static long bytesToLong(byte[] byteBarray) {
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }
}
