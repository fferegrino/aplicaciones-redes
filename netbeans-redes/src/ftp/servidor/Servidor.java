package ftp.servidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.swing.PrintColorUIResource;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];
    static String serverPath = "C:/ftp/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Variables para contadores y auxiliares
        int i, resultadoLectura = 0;
        try {
            // Socket de servidor
            ServerSocket ss = new ServerSocket(4040);

            while (true) {
                // Aceptamos un cliente y abrimos los flujos de IO
                Socket s = ss.accept();
                DataInputStream is = new DataInputStream(s.getInputStream());
                DataOutputStream os = new DataOutputStream(s.getOutputStream());
                // Leemos la cantidad de archivos a copiar
                int archivos = is.readInt();
                s.setSoTimeout(3000);
                for (i = 0; i < archivos; i++) {
                    // Leemos la longitud del nombre del archivo
                    int longNombreArchivo = is.readInt();

                    byte[] nombreArchivo = new byte[longNombreArchivo];
                    // Leemos el nombre del archivo
                    is.read(nombreArchivo);
                    String nombre = new String(nombreArchivo);
                    // Leemos la longitud en bytes del archivo
                    long longArchivo = is.readLong();
                    // FileOutputStream para escribir el archivo
                    FileOutputStream fos = new FileOutputStream(serverPath + nombre);
                    System.out.println("Archivo " + nombre + " " + longArchivo);
                    // Leemos la cantidad de bytes que se recibirán del servidor
                    resultadoLectura = is.readInt();
                    // Ciclo para leer del cliente y escribir al archivo
                    System.out.println(" " + i);
                    while (resultadoLectura != -1) {
                        System.out.println("\tBytes leídos " + resultadoLectura);
                        // Leemos del socket los bytes
                        try {
                            is.read(buffer, 0, resultadoLectura);
                            os.writeInt(1);
                        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                            os.writeInt(0);
                            break;
                        }
                        // Escribimos los bytes al archivo
                        fos.write(buffer, 0, resultadoLectura);
                        // Leemos la cantidad de bytes que se recibirán del servidor
                        resultadoLectura = is.readInt();
                    }
                    System.out.println("Escritura terminada");
                    // Cerramos el FileOutputStream
                    fos.close();
                }
                // Cerramos los flujos de entrada y salida, después cerramos el socket
                is.close();
                os.close();
                s.close();
            }

        } catch (IOException ex) {
        }
    }
}
