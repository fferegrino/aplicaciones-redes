package chat.multicast.cliente;

import java.net.InetAddress;
import java.util.EventObject;

/**
 *
 * @author Antonio
 */
public class NuevoMensajeEvent extends EventObject{

    public NuevoMensajeEvent(Object source, InetAddress sender, String texto, int puerto) {
        super(source);
        this.sender = sender;
        this.puerto = puerto;
        this.texto = texto;
    }
    
    
    private int puerto;
    boolean privado;

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    public boolean isPrivado() {
        return privado;
    }
    private InetAddress sender;
    private String texto;

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public InetAddress getSender() {
        return sender;
    }

    public void setSender(InetAddress sender) {
        this.sender = sender;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
