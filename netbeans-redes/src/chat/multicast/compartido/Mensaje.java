package chat.multicast.compartido;

import java.net.InetAddress;

/**
 *
 * @author (at)fferegrino
 */

public class Mensaje {
    InetAddress sender;
    String texto;

    public Mensaje(InetAddress sender, String texto) {
        this.sender = sender;
        this.texto = texto;
    }

    public InetAddress getSender() {
        return sender;
    }

    public String getTexto() {
        return texto;
    }
    
}
