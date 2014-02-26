package evaluacion1.buscaminas;

import java.io.Serializable;

/**
 *
 * @author usuario
 */

public class Tablero implements Serializable {

    private int nivel;
    private int x;
    private int y;
    private int minas;
    private int descubiertas;
    private int estadoJuego;
    int[][] tablero;

    public Tablero(int nivel) {
        descubiertas = 0;
        switch (nivel) {
            case 1:
                minas = 10;
                x = 9;
                y = 9;
                break;
            case 2:
                minas = 40;
                x = 16;
                y = 16;
                break;
            case 3:
                minas = 99;
                y = 30;
                x = 16;
                break;
        }
        estadoJuego = 0;
        tablero = new int[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (char i = 0; i < getX(); i++) {
            //char c = (char) ('A' + i);
            //sb.append("  ").append(c).append(" ");
            sb.append(String.format(" %2d ", i + 1));
        }
        sb.append("\n");

        for (int i = 0; i < getY(); i++) {
            sb.append(String.format(" %2d ", i + 1));
            for (int j = 0; j < getX(); j++) {
                sb.append(String.format(" %2d ", tablero[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the minas
     */
    public int getMinas() {
        return minas;
    }

    /**
     * @param minas the minas to set
     */
    public void setMinas(int minas) {
        this.minas = minas;
    }

    /**
     * @return the descubiertas
     */
    public int getDescubiertas() {
        return descubiertas;
    }

    /**
     * @param descubiertas the descubiertas to set
     */
    public void setDescubiertas(int descubiertas) {
        this.descubiertas = descubiertas;
    }

    /**
     * @return the estadoJuego
     */
    public int getEstadoJuego() {
        return estadoJuego;
    }

    /**
     * @param estadoJuego the estadoJuego to set
     */
    public void setEstadoJuego(int estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public void hazJugada(int x, int y, int t) {
        if (t == 0) // Tiro
        {
            tablero[x][y]= 1;
        }
    }
}
