package evaluacion2.common.io;

import evaluacion2.common.Host;
import evaluacion2.common.Juego;
import evaluacion2.common.Posicion;
import java.net.Socket;

/**
 *
 * @author Antonio
 */
public interface CommandListener {
    public void gameOver(String ganador);
    public void clientMove(Posicion p);
}
