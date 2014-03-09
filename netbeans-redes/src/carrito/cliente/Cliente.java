/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito.cliente;

import carrito.compartido.Interaccion;
import carrito.compartido.Orden;
import carrito.compartido.Producto;
import chat.multicast.cliente.ClienteGUI;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Cliente {
    
    public final static int VER_CARRITO = 1;
    public final static int COMPRA = 3;
    public final static int ACTUALIZAR_CARRITO = 2;
    public final static int SALIR = 4;
    public final static int SOLICITAR_IMAGENES = 1;
    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];
    static String photoFolder = "C:\\carrito\\cliente\\pic\\";

    /**
     * @param args the command line arguments
     */
    public ArrayList<Producto> solicitaProductos(boolean solicitaImagenes) throws Exception {
        ArrayList<Producto> res = new ArrayList<Producto>();
        Socket s = new Socket(InetAddress.getByName("localhost"), 4040);
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        os.flush();

        // Solicitud de carrito
        os.writeInt(Interaccion.SOLICITUD_CARRITO);
        os.flush();
        int cantidadProductos = is.readInt();
        while (cantidadProductos-- > 0) {
            res.add((Producto) is.readObject());
        }
        if (solicitaImagenes) {
            os.writeInt(SOLICITAR_IMAGENES);
        } else {
            os.writeInt(0);
        }
        os.flush();
        if (solicitaImagenes) {
            leeImagenes(os, is);
        }
        
        os.close();
        is.close();
        s.close();
        return res;
    }
    
    public int realizaOrden(int productoId, int cantidad) throws Exception {
        Orden o = new Orden(cantidad, productoId);
        Socket s = new Socket(InetAddress.getByName("localhost"), 4040);
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        os.flush();
        
        os.writeInt(Interaccion.CONFIRMAR_COMPRA);
        os.writeObject(o);
        int resultado = is.readInt();
        os.close();
        is.close();
        s.close();
        return resultado;
    }
    
    private void leeImagenes(ObjectOutputStream os, ObjectInputStream is) {
        try {
            int totalArchivos = is.readInt(), resultadoLectura;
            for (int i = 0; i < totalArchivos; i++) {
                int longNombreArchivo = is.readInt();
                byte[] nombreArchivo = new byte[longNombreArchivo];
                // Leemos el nombre del archivo
                is.read(nombreArchivo);
                String nombre = new String(nombreArchivo);
                // Leemos la longitud en bytes del archivo
                long longArchivo = is.readLong();
                // FileOutputStream para escribir el archivo
                FileOutputStream fos = new FileOutputStream(photoFolder + nombre);
                System.out.println("Archivo " + nombre + " " + longArchivo);
                // Leemos la cantidad de bytes que se recibirán del servidor
                resultadoLectura = is.readInt();
                // Ciclo para leer del cliente y escribir al archivo
                while (resultadoLectura >= 0) {
                    System.out.println("\tBytes leídos " + resultadoLectura);
                    // Leemos del socket los bytes
                    try {
                        is.read(buffer, 0, resultadoLectura);
                        os.writeInt(1);
                        os.flush();
                    } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                        os.writeInt(0);
                        os.flush();
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
        } catch (IOException ex) {
        }
    }
}
