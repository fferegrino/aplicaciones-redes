package chat.multicast.compartido;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author (at)fferegrino
 */
public class Usuario implements Serializable {

    public InetAddress getAddress() {
        return address;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getNombre() {
        return nombre;
    }
    private InetAddress address;
    int puerto;
    private String nombre;

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(InetAddress address, int puerto, String nombre) {
        this.address = address;
        this.puerto = puerto;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        Usuario u = (Usuario) o;
        return address.equals(u.address);
    }

    @Override
    public String toString() {
        return nombre + "@" + address.getHostAddress();
    }
}
