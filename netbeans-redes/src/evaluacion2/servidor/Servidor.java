package evaluacion2.servidor;

import chat.multicast.server.HandshakeListener;
import evaluacion2.common.Host;
import evaluacion2.common.Juego;
import evaluacion2.common.Jugador;
import evaluacion2.common.Match;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    static String[] palabras;

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InterruptedException {
        // <editor-fold defaultstate="collapsed" desc="Lectura de las palabras">
        FileInputStream fis = new FileInputStream("C:\\redes\\palabras.txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
        ArrayList<String> words = new ArrayList<String>();
        String word;
        while ((word = buffer.readLine()) != null) {
            words.add(word);
        }
        palabras = words.toArray(new String[words.size()]);
        // </editor-fold>
        // < editor-fold desc="Otros servidores">
        HandshakeListener hl = new HandshakeListener();
        // </editor-fold>
        Juego.init();

        Match match = null;
        ServerSocket ss = new ServerSocket(5500);
        while (true) {
            System.out.println("Servidor esperando");
            if (match == null || match.finalizado()) {
                System.out.println("Juego nuevo");
                match = new Match(palabras);
            }

            Socket posibleJugador = ss.accept();
            Jugador j = new Jugador(posibleJugador);

            System.out.println("Cliente aceptado " + j.getUsername());
            if (match.isAvailable()) {
                match.addJugador(j);
                if (match.availablePlaces() == 0) {
                    j.start();
                    match.start();
                } else {
                    j.hold();
                }
            } else {
                Host h = hl.getAvailableHost();
                j.reject();
            }
        }
    }
}
