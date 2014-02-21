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

public class Cliente {
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
                DatagramPacket dp = new DatagramPacket(new byte[1024],1024);
                s.receive(dp);
                System.out.println("Datagrama recibido de: " + dp.getAddress());
            }
        } catch (IOException ex) {
            
        }
    }

}
