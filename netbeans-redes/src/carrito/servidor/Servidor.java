/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito.servidor;

import carrito.compartido.Interaccion;
import carrito.compartido.Orden;
import carrito.compartido.Producto;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(4040);
        int option = Interaccion.SALIR;



        // <editor-fold defaultstate="collapsed" desc="Productos">
        
        ArrayList<Producto> prods;
        prods = new ArrayList<Producto>();
        for (int x = 0; x < 5; x++) {
            Producto p = new Producto();
            p.setNombre("Producto " + x);
            p.setId(x);
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
}
