/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.multicast.cliente;

import java.util.EventListener;

/**
 *
 * @author Antonio
 */
public interface NuevoMensajeListener extends EventListener{
    void nuevoMensajeHandler(NuevoMensajeEvent e);
}
