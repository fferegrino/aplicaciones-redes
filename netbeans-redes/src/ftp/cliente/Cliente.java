package ftp.cliente;

/**
 *
 * @author (at)fferegrino
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;

/**
 *
 * @author Antonio
 */
public class Cliente {

    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Variables para contadores y auxiliares
        int i, resultadoLectura;

        JFileChooser fc = new JFileChooser();
        // Habilitamos la multiselección
        fc.setMultiSelectionEnabled(true);
        // Mostramos el FileChooser
        int seleccion = fc.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Recuperamos los archivos
            File[] files = fc.getSelectedFiles();
            try {
                // Apertura del socket y los flujos IO
                Socket s = new Socket(InetAddress.getByName("localhost"), 4040);
                DataInputStream is = new DataInputStream(s.getInputStream());
                DataOutputStream os = new DataOutputStream(s.getOutputStream());
s.setSoTimeout(3000);
                // Escribimos el total de archivos a copiar
                os.writeInt(files.length);

                for (i = 0; i < files.length; i++) {
                    // Conversión del nombre del archivo a bytes
                    byte[] nombreArchivo = files[i].getName().getBytes();
                    // Enviamos la longitud del nombre del archivo
                    os.writeInt(nombreArchivo.length);
                    // Enviamos el nombre del archivo
                    os.write(nombreArchivo);
                    // Enviamos el tamaño en bytes del archivo
                    os.writeLong(files[i].length());
                    // FileInputStream para leer del archivo
                    FileInputStream fis = new FileInputStream(files[i]);
                    // Leemos al buffer de bytes
                    resultadoLectura = fis.read(buffer);
                    // Escritura de los bytes leídos
                    os.writeInt(resultadoLectura);
                    // Ciclo para leer del archivo:
                    while (resultadoLectura != -1) {
                        // Escribimos al socket los bytes del archivo
                        os.write(buffer, 0, resultadoLectura);
                        
                        int codigo = is.readInt();
                        
                        if(codigo == 0){
                            System.out.println("\tError");
                            break;
                        }
                        // Volvemos a leer
                        resultadoLectura = fis.read(buffer);
                        // Escribimos el resultado de la lectura
                        os.writeInt(resultadoLectura);
                    }
                    // Cerramos el FileInputStream
                    fis.close();
                }
                // Cerramos los flujos de entrada y salida, después cerramos el socket
                is.close();
                os.close();
                s.close();
            } catch (UnknownHostException ex) {
            } catch (IOException ex) {
            }

        }
    }
}

