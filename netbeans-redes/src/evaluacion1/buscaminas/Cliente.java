package evaluacion1.buscaminas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author usuario
 */

public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tablero tab = Tiro(-1, -1, 2);
        System.out.println(tab);
        while (tab.getEstadoJuego() == 0) {
            tab = Tiro(1, 2, 0);
            System.out.println(tab);
        }
        if(tab.getEstadoJuego()==1){
            System.out.println("Champion");
        }
        else{
            System.out.println("Looser");
        }
           
    }
    
    

    public static Tablero Tiro(int x, int y, int jugada) {
        Tablero t = null;
        try {
            // Apertura del socket y los flujos IO
            Socket s = new Socket(InetAddress.getByName("8.25.100.94"), 4040);
            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            os.flush();

            Jugada j1 = new Jugada();

            j1.setTipo(jugada);
            j1.setX(x);
            j1.setY(y);
            os.writeObject(j1);
            os.flush();

            t = (Tablero) is.readObject();


            //Cerramos los flujos y el socket
            is.close();
            os.close();
            s.close();
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return t;
    }
}

