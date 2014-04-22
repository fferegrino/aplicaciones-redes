package evaluacion2.common;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author (at)fferegrino
 */
public class Host implements Serializable {

    private InetAddress ip;
    private int port;
    private boolean available;

    public Host() {
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        Host h = (Host)o;
        return port == h.port && ip.equals(h.ip);
    }
}
