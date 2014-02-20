package ftp.datagrama.cliente;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author (at)fferegrino
 */
public class Cliente {

    public static void main(String[] args) throws Exception {
        DatagramSocket cl = new DatagramSocket();
        int leidos = 0,
                completados = 0;
        long b_leidos = 0;
        // int tam_bloque =  tam >= 1024 ? 1024 : tam;
        InetAddress dst = null;
        try {
            dst = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.err.println("Direccion no valida");
            System.exit(1);
        }
        int pto_dst = 9000;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeUTF(nombre+":"+new String(tam));
        oos.flush();
        DatagramPacket p = new DatagramPacket(baos.toByteArray(), (baos.toByteArray()).length, dst, pto_dst);
        cl.send(p);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        int tam_bloque = (ois.available() >= 1024) ? 1024 : ois.available();
        byte[] buf = new byte[tam_bloque];
        while ((leidos = ois.read(buf, 0, tam_bloque)) != -1) {
            DatagramPacket dp = new DatagramPacket(buf, leidos, dst, pto_dst);
            cl.send(dp);
            tam_bloque = (ois.available() >= 1024) ? 1024 : ois.available();
            b_leidos += (long) leidos;
            completados = (int) ((b_leidos * 100) / tam/*Tamaño archivo*/);
        }
    }
}
