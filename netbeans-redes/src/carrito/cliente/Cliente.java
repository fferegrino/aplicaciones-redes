/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito.cliente;

import carrito.compartido.Interaccion;
import carrito.compartido.Producto;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author usuario
 */
public class Cliente {

    public final static int VER_CARRITO = 1;
    public final static int COMPRA = 3;
    public final static int ACTUALIZAR_CARRITO = 2;
    public final static int SALIR = 4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        System.out.println("Bienvenido al carrito de compras.\nEstos son los productos que existen disponibles:");
        ArrayList<Producto> productos = solicitaProductos();
        imprimeProductos(productos);
        int opt;

        do {
            System.out.println("Elija una opción:\n1. Ver carrito\t2. Actualizar carrito\t3. Realizar compra\t4. Salir");
            Scanner in = new Scanner(System.in);
            opt = in.nextInt();
            switch (opt) {
                case VER_CARRITO:
                    imprimeProductos(productos);
                    break;
                case ACTUALIZAR_CARRITO:
                    productos = solicitaProductos();
                    imprimeProductos(productos);
                    break;
                case COMPRA:
                    break;
            }
        } while (opt != SALIR);
    }

    private static void imprimeProductos(ArrayList<Producto> productos) {
        for (int i = 0; i < productos.size(); i++) {
            System.out.println(productos.get(i));
        }
    }

    private static ArrayList<Producto> solicitaProductos() throws Exception {
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

        os.close();
        is.close();
        s.close();
        return res;
    }
}
