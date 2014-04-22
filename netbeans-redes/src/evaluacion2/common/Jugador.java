package evaluacion2.common;

import evaluacion2.common.io.CommandListener;
import evaluacion2.common.io.Writer;
import evaluacion2.common.io.Reader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Jugador implements CommandListener {

    Match matchAsociado;
    int puntos;
    Socket cliente;
    Writer ec;
    Reader lc;
    Juego j;
    String username;

    public Jugador(Socket s) throws IOException {
        ec = new Writer(s);
        lc = new Reader(s, this);
        username = lc.readString();
    }

    public void redirecHost(Host h) throws IOException, InterruptedException {
        ec.writeHost(h);
        this.finalizarJuego("");
    }

    public void comenzarJuego() throws IOException {
        ec.writeJuego(matchAsociado.j);
        lc.start();
    }

    public void asociarMatch(Match m) {
        if (matchAsociado == null) {
            matchAsociado = m;
        }
    }

    public void finalizarJuego(String userGanador) throws IOException, InterruptedException {
        ec.writeInt(Interaccion.FINALIZAR_JUEGO);
        ec.writeString(userGanador);
    }

    public void reject() throws IOException {
        ec.writeRespuestaInicial(Interaccion.CONEXION_RECHAZADA);
    }

    public void hold() throws IOException {
        ec.writeRespuestaInicial(Interaccion.CONEXION_ESPERA);
    }

    public void start() throws IOException {
        ec.writeRespuestaInicial(Interaccion.CONEXION_ACEPTADA);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public void gameOver(String ganador) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void clientMove(Posicion p) {
        try {
            matchAsociado.move(this, p);
        } catch (IOException ex) {
        } catch (InterruptedException ex) {
        }
    }
}
