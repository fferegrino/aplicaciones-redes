package evaluacion2.common.io;

import evaluacion2.common.Host;
import evaluacion2.common.Interaccion;
import evaluacion2.common.Juego;
import evaluacion2.common.Posicion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Reader extends Thread {

    ObjectInputStream is;
    Socket s;
    boolean listening;
    CommandListener listener;

    public Reader(Socket s, CommandListener c) throws IOException {
        this.s = s;
        this.listener = c;
        is = new ObjectInputStream(s.getInputStream());
    }

    /**
     * Síncrono
     */
    public int readRespuestaInicial() throws IOException {
        return is.readInt();
    }

    public String readString() throws IOException{
        return is.readUTF();
    }
    
    public int readInt() throws IOException {
        return is.readInt();
    }

    public Juego readJuego() throws IOException, ClassNotFoundException {
        Juego j = (Juego) is.readObject();
        return j;
    }

    private void readTiro() throws IOException, ClassNotFoundException {
        Posicion p = (Posicion) is.readObject();
        listener.clientMove(p);
    }

    private void readHost() throws IOException, ClassNotFoundException {
        Host h = (Host) is.readObject();
    }

    @Override
    public void run() {
        listening = true;
        while (listening) {
            try {
                int op = is.readInt();
                switch (op) {
                    case Interaccion.ENVIO_JUEGO:
                        readJuego();
                        break;
                    case Interaccion.ENVIO_TIRO:
                        readTiro();
                        break;
                    case Interaccion.ENVIO_HOST:
                        readHost();
                        break;
                    case Interaccion.FINALIZAR_JUEGO:
                        listening = false;
                        String ganador = readString();
                        listener.gameOver(ganador);
                        break;
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException ce) {
            }
        }
    }

}
