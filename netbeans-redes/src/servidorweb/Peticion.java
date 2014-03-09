package servidorweb;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clase para manejar la petición web
 *
 * @author (at)fferegrino
 */
public class Peticion extends Thread {

    public static final String SERVER_ROUTE = "C:\\server";
    public static final String INDEX = "\\index.html";
    public static final int BUFFER_SIZE = 1024;
    private char[] buffer = new char[BUFFER_SIZE];
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
            file = file.equals("/") ? INDEX : file;

            File seleccionado = new File(SERVER_ROUTE + file);
            long length = seleccionado.length();
            int leidos = 0;
            FileReader fr = new FileReader(seleccionado);

            leidos = fr.read(buffer);
            while (leidos != -1) {
                pw.write(buffer, 0, leidos);
                pw.flush();
                leidos = fr.read(buffer);
            }

            fr.close();
            bos.close();
            br.close();
            pw.close();


            socket.shutdownInput();
            socket.close();
        } catch (IOException ex) {
        }
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
