package carrito.compartido;

import java.io.Serializable;

/**
 *
 * @author Antonio
 */
public class Orden implements Serializable {

    private int productoId;
    private int cantidad;

    public Orden(int cantidad, int productoId) {
        this.cantidad = cantidad;
        this.productoId = productoId;
    }

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
