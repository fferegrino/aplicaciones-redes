/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito.servidor;

import carrito.compartido.Interaccion;
import carrito.compartido.Orden;
import carrito.compartido.Producto;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Servidor {

    static String photoFolder = "C:\\carrito\\servidor\\pic\\";
    public final static int SOLICITAR_IMAGENES = 1;
    static int bufferSize = 1024;
    static byte[] buffer = new byte[bufferSize];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(4040);
        int option = Interaccion.SALIR;



        // <editor-fold defaultstate="collapsed" desc="Productos">

        ArrayList<Producto> prods;
        prods = new ArrayList<Producto>();
        for (int x = 0; x < 7; x++) {
            Producto p = new Producto();
            p.setNombre("Producto " + x);
            p.setId(x);
            p.setImagePath("p" + (x + 1) + ".jpg");
            p.setPrecio(40.40);
            p.setDescripcion("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas suscipit eleifend urna, sit amet tempus elit viverra id. Duis ultricies eu augue eu consequat. Nunc lectus quam, mollis id augue.");
            p.setExistencia(20);
            prods.add(p);
        }
        // </editor-fold>

        while (true) {
            Socket s = ss.accept();
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            os.flush();

            System.out.println("Cliente aceptado " + s.getInetAddress());
            option = is.readInt();


            switch (option) {
                case Interaccion.SOLICITUD_CARRITO:
                    System.out.println("Solicitud carrito");
                    os.writeInt(prods.size());
                    for (int i = 0; i < prods.size(); i++) {
                        os.writeObject(prods.get(i));
                    }
                    os.flush();
                    if (is.readInt() == SOLICITAR_IMAGENES) {
                        System.out.println("Cliente solicita imagenes");
                        escribeImagenes(os, is);
                    }
                    break;
                case Interaccion.CONFIRMAR_COMPRA:
                    Orden o = (Orden) is.readObject();
                    Producto p = prods.get(o.getProductoId());
                    p.setExistencia(p.getExistencia() - o.getCantidad());
                    os.writeInt(Interaccion.TRANSACCION_CORRECTA);
                    break;
            }

            System.out.println("Solicitud de " + s.getInetAddress() + " terminada");

            os.close();
            is.close();
            s.close();
        }
    }

    private static void escribeImagenes(ObjectOutputStream os, ObjectInputStream is) {

        int i, resultadoLectura;
        File picFolder = new File(photoFolder);
        File[] files = picFolder.listFiles();
        try {
            os.writeInt(files.length);

            for (i = 0; i < files.length; i++) {
                // Conversión del nombre del archivo a bytes
                byte[] nombreArchivo = files[i].getName().getBytes();
                // Enviamos la longitud del nombre del archivo
                os.writeInt(nombreArchivo.length);
                    os.flush();
                // Enviamos el nombre del archivo
                os.write(nombreArchivo);
                    os.flush();
                // Enviamos el tamaño en bytes del archivo
                os.writeLong(files[i].length());
                    os.flush();
                // FileInputStream para leer del archivo
                FileInputStream fis = new FileInputStream(files[i]);
                // Leemos al buffer de bytes
                resultadoLectura = fis.read(buffer);
                // Escritura de los bytes leídos
                os.writeInt(resultadoLectura);
                    os.flush();
                // Ciclo para leer del archivo:
                while (resultadoLectura >= 0) {
                    // Escribimos al socket los bytes del archivo
                    os.write(buffer, 0, resultadoLectura);
                    os.flush();
                    int codigo = is.readInt();

                    if (codigo == 0) {
                        System.out.println("\tError");
                        break;
                    }
                    // Volvemos a leer
                    resultadoLectura = fis.read(buffer);
                    // Escritura de los bytes leídos
                    os.writeInt(resultadoLectura);
                    os.flush();
                }
                // Cerramos el FileInputStream
                fis.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
