/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrito.compartido;

import java.io.Serializable;

/**
 *
 * @author Antonio
 */
public class Orden implements Serializable{
    private int productoId;
    private int cantidad;

    /**
     * @return the productoId
     */
    public int getProductoId() {
        return productoId;
    }

    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
