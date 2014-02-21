package multicast.clase;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

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
                DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, gpo, 1234);
                System.out.println("Enviando datagrama");
                s.send(dp);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {}
            }
        } catch (IOException ex) {
            
        }
        
    }
}
