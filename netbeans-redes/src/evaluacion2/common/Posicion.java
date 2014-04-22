package evaluacion2.common;

import java.io.Serializable;

/**
 *
 * @author (at)fferegrino
 */
public class Posicion implements Serializable{

    public Posicion(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    private int x1, x2, y1, y2;

    public boolean esValido() {
        return (x1 >= 0 && x2 >= 0 && y1 >= 0 && x2 >= 0)
                && (x1 <= x2 && y2 <= y1);
    }

    @Override
    public String toString() {
        return String.format("[%d, %d] - [%d, %d]", x1, y1, x2, y2);
    }

    @Override
    public boolean equals(Object o) {
        Posicion p = (Posicion) o;
        return this.x1 == p.x1
                && this.x2 == p.x2
                && this.y1 == p.y1
                && this.y2 == p.y2;
    }
}
