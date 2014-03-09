package servidorweb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Servidor {

    public static final int LISTENING_PORT = 12345;
    private static ServerSocket ss;

    public static void main(String [] args) {
        try {
            ss = new ServerSocket(LISTENING_PORT);
            for (;;) {
                Socket s = ss.accept();
                new Peticion(s).start();
            }
        } catch (IOException ex) {
        }
    }
}
