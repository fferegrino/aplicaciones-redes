package evaluacion2.common.io;

import com.sun.xml.internal.ws.resources.SoapMessages;
import evaluacion2.common.Host;
import evaluacion2.common.Interaccion;
import evaluacion2.common.Juego;
import evaluacion2.common.Posicion;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author (at)fferegrino
 */
public class Writer /*extends Thread*/ {

    ObjectOutputStream os;
    Socket s;

    public Writer(Socket s) throws IOException {
        this.s = s;
        os = new ObjectOutputStream(s.getOutputStream());
        os.flush();
    }

    public void writeRespuestaInicial(int respuesta) throws IOException {
        os.writeInt(respuesta);
        os.flush();
    }

    public void writeString(String s) throws IOException {
        os.writeUTF(s);
        os.flush();
        
    }

    public void writeInt(int i) throws IOException {
        os.writeInt(i);
        os.flush();
    }

    public void writeJuego(Juego j) throws IOException {
        os.writeInt(Interaccion.ENVIO_JUEGO);
        os.writeObject(j);
    }

    public void writeTiro(Posicion p) throws IOException {
        os.writeInt(Interaccion.ENVIO_TIRO);
        os.writeObject(p);
    }

    public void writeHost(Host h) throws IOException {
        os.writeInt(Interaccion.ENVIO_HOST);
        os.writeObject(h);
    }

    public void close() throws IOException {
        os.close();
    }
}
