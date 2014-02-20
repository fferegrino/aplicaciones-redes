package multicast.clase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */

public class Anuncio {
    public static void main(String []args){
        try {
            MulticastSocket s = new MulticastSocket(1234);
            s.setTimeToLive(200);
            InetAddress gpo = null;
            try{
            // Dirección de grupo
                gpo = InetAddress.getByName("228.1.1.1");
            }catch(UnknownHostException u){
                
            }
            s.joinGroup(gpo);
            while(true){
                String msg = "Hola";
                DatagramPacket dp = new DatagramPacket(buf, length, gpo, 1234);
                s.send(dp);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {}
            }
        } catch (IOException ex) {
            
        }
        
    }
}
