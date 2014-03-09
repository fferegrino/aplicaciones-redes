package servidorweb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author (at)fferegrino
 */
public /**
         * Clase interna para manejar la petición web
         */
        class Peticion extends Thread {

    private Socket socket;
    private PrintWriter pw;
    protected BufferedOutputStream bos;
    protected BufferedReader br;

    public Peticion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bos = new BufferedOutputStream(socket.getOutputStream());
            pw = new PrintWriter(new OutputStreamWriter(bos));

            String peticion = br.readLine();
            // Archivo solicitado
            String file = peticion.substring(peticion.indexOf(" ") + 1, peticion.lastIndexOf(" "));
            log(file);
            pw.write("Hola mundo");
            pw.flush();
            socket.shutdownInput();
            socket.close();
        } catch (IOException ex) {
        }
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
