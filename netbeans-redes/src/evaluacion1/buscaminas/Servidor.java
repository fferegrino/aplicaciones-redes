package evaluacion1.buscaminas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tablero juego = null;
        try {
            // Socket de servidor
            ServerSocket ss = new ServerSocket(4040);
            System.out.println("Servidor escuchando");
            while (true) {
                // Aceptamos un cliente y abrimos los flujos de IO
                Socket s = ss.accept();
                System.out.println("Cliente conectado");
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                os.flush();

                System.out.println("wesperando leida");
                Jugada j = (Jugada) is.readObject();

                if (j != null) {
                    if (j.getX() < 0 && j.getY() < 0) {
                        System.out.println("Nuevo jeugo");
                        juego = new Tablero(j.getTipo());
                    } else {
                        juego.hazJugada(j.getX(), j.getY(), j.getTipo());
                        juego.setEstadoJuego(-1);
                    }
                } else {
                    System.out.println("Jugada vacia");
                }

                os.writeObject(juego);
                os.flush();
                is.close();
                os.close();
                s.close();
            }

        } catch (ClassNotFoundException ce) {
        } catch (IOException ex) {
        }
    }
}
