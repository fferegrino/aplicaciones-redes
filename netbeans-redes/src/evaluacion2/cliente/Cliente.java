package evaluacion2.cliente;

import evaluacion2.common.Host;
import evaluacion2.common.Interaccion;
import evaluacion2.common.Juego;
import evaluacion2.common.Posicion;
import evaluacion2.common.io.CommandListener;
import evaluacion2.common.io.Writer;
import evaluacion2.common.io.Reader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author (at)fferegrino
 */
public class Cliente implements CommandListener {

    String username;
    String ipHost;
    int port;
    boolean playing;
    Socket client;
    Reader lc;
    Writer ec;
    Juego juego;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Escribe tu nombre de usuario");
        String username = in.nextLine();
        Cliente c = new Cliente(username);
        c.setHost("localhost", 5500);
        c.connect();
        System.out.println("Conectado");
        int ans = c.serverAnswer();
        if (ans == Interaccion.CONEXION_ACEPTADA || ans == Interaccion.CONEXION_ESPERA) {
            System.out.println("Esperando por el juego");
            c.waitForGame();
            System.out.println("Juego recibido");
            System.out.println(c.getJuego());
            c.start();
            while (true) {
                System.out.print("Escribe las coordenadas del tiro (x1 y1 x2 y2): ");
                String tiro = in.nextLine();
                if (c.isPlaying()) {
                    String[] t = tiro.split(" ");
                    Posicion p = new Posicion(
                            Integer.parseInt(t[0]),
                            Integer.parseInt(t[2]),
                            Integer.parseInt(t[1]),
                            Integer.parseInt(t[3]));
                    c.move(p);
                } else {
                    break;
                }
            }
            c.waitForEnd();
        }
    }

    public void start() {
        lc.start();
        playing = true;
    }

    public void waitForEnd() throws InterruptedException {
        lc.join();
    }

    public void connect() throws UnknownHostException, IOException {
        client = new Socket(ipHost, port);
        ec = new Writer(client);
        lc = new Reader(client, this);
        ec.writeString(username);
    }

    public void setHost(String ipHost, int puerto) {
        this.ipHost = ipHost;
        this.port = puerto;
    }

    public int serverAnswer() throws IOException {
        return lc.readRespuestaInicial();
    }

    public void waitForGame() throws IOException, ClassNotFoundException {
        lc.readInt();
        juego = lc.readJuego();
    }

    // Getters & setters
    public Juego getJuego() {
        return juego;
    }

    public void move(Posicion p) throws IOException {
        ec.writeTiro(p);
    }

    public boolean isPlaying() {
        return playing;
    }

    public Cliente(String username) {
        this.username = username;
    }
    
    

    @Override
    public void gameOver(String ganador) {
        playing = false;
        System.out.println();
        System.out.println("||      Juego terminado      ||");
        System.out.println("||      Ganador: " + ganador+ "      ||");
        System.out.println("|| Presiona enter para salir ||");
    }

    @Override
    public void clientMove(Posicion p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
