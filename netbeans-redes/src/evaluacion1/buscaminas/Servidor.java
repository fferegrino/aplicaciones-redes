package evaluacion1.buscaminas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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

        Scanner in = new Scanner(System.in);
        juego = new Tablero(0);
        do {
            System.out.println(juego);
            int x = in.nextInt();
            int y = in.nextInt();
            int j = in.nextInt();
            juego.hazJugada(x, y, j);
        } while (juego.getEstadoJuego() == 0);
        if (juego.getEstadoJuego() > 0) {
            System.out.println("Ganaste!");
        } else {
            System.out.println("Perdiste");
        }
//         <editor-fold desc="Servidor">
//        try {
//            // Socket de servidor
//            ServerSocket ss = new ServerSocket(4040);
//            System.out.println("Servidor escuchando.");
//            while (true) {
//                // Aceptamos un cliente y abrimos los flujos de IO
//                Socket s = ss.accept();
//                System.out.println("Cliente conectado");
//                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
//                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
//                os.flush();
//
//                Jugada j = (Jugada) is.readObject();
//
//                if (j != null) {
//                    if (j.getX() < 0 && j.getY() < 0) {
//                        System.out.println("Nuevo jeugo");
//                        juego = new Tablero(j.getTipo());
//                    } else {
//                        juego.hazJugada(j.getX(), j.getY(), j.getTipo());
//                        if (juego.getEstadoJuego() == -1) {
//                            System.out.println("El jugador perdió");
//                        }
//                    }
//                }
//                os.writeObject(juego);
//                os.flush();
//                is.close();
//                os.close();
//                s.close();
//            }
//
//        } catch (ClassNotFoundException ce) {
//        } catch (IOException ex) {
//        }
        // </editor-fold>
    }
}
