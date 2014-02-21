package ftp.datagrama.cliente;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.swing.JFileChooser;

/**
 *
 * @author (at)fferegrino
 */
public class Cliente {

    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];

    public static void main(String[] args) throws Exception {

        JFileChooser fc = new JFileChooser();
        // Habilitamos la multiselección
        fc.setMultiSelectionEnabled(true);
        // Mostramos el FileChooser
        int seleccion = fc.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Recuperamos los archivos
            File[] files = fc.getSelectedFiles();
            try {
                DatagramSocket cl = new DatagramSocket();
                int resultadoLectura;

                int pto_dst = 9000;
                InetAddress dst = InetAddress.getByName("localhost");
                byte[] byteSize = intToBytes(files.length);
                SocketAddress addr = new InetSocketAddress(dst, pto_dst);
                // Se envia la cantidad de archivos que se van a enviar
                DatagramPacket dp = new DatagramPacket(byteSize, byteSize.length, addr);
                cl.send(dp);

                for (int i = 0; i < files.length; i++) {
                    // Se envia el nombre del archivo   
                    byte[] flieName = files[i].getName().getBytes();
                    dp.setData(flieName, 0, flieName.length);
                    cl.send(dp);

                    // Se envia la longitud del archivo:
                    dp.setData(longToBytes(files[i].length()), 0, 8);
                    cl.send(dp);

                    // Iniciamos la lectura
                    FileInputStream fis = new FileInputStream(files[i]);
                    resultadoLectura = fis.read(buffer);
                    // Escritura de los bytes leídos
                    byte[] resultadoLec = intToBytes(resultadoLectura);
                    dp.setData(resultadoLec, 0, 4);
                    cl.send(dp);
                    // Ciclo para leer del archivo:
                    while (resultadoLectura != -1) {
                        // Escribimos al socket los bytes del archivo
                        dp.setData(buffer, 0, resultadoLectura);
                        cl.send(dp);
                        // Volvemos a leer
                        resultadoLectura = fis.read(buffer);
                        // Escribimos el resultado de la lecturaresultadoLec = intToBytes(resultadoLectura);
                        resultadoLec = intToBytes(resultadoLectura);
                        dp.setData(resultadoLec, 0, 4);
                        cl.send(dp);
                    }
                    fis.close();
                }

            } catch (UnknownHostException ex) {
            } catch (IOException ex) {
            }

        }



//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
////        oos.writeUTF(nombre+":"+new String(tam));
//        oos.flush();
//        DatagramPacket p = new DatagramPacket(baos.toByteArray(), (baos.toByteArray()).length, dst, pto_dst);
//        cl.send(p);
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
//        int tam_bloque = (ois.available() >= 1024) ? 1024 : ois.available();
//        byte[] buf = new byte[tam_bloque];
//        while ((leidos = ois.read(buf, 0, tam_bloque)) != -1) {
//            DatagramPacket dp = new DatagramPacket(buf, leidos, dst, pto_dst);
//            cl.send(dp);
//            tam_bloque = (ois.available() >= 1024) ? 1024 : ois.available();
//            b_leidos += (long) leidos;
//            completados = (int) ((b_leidos * 100) / tam/*Tamaño archivo*/);
//        }
    }

    public static byte[] intToBytes(final int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
    }

    public static byte[] longToBytes(final long i) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(i).array();
    }
}
