package chat.multicast.compartido;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author (at)fferegrino
 */

public class Usuario implements Serializable{
    private InetAddress address;
    private String nombre;

    /**
     * @return the address
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public boolean equals(Object o){
        Usuario u = (Usuario)o;
        return address.equals(u.address);
    }
}
