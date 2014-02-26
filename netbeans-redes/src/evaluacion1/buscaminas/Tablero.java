package evaluacion1.buscaminas;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

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
    boolean[][] visibles;
    boolean[][] marcas;
    private static Random random = new Random(new Date().getTime());

    public Tablero(int nivel) {
        descubiertas = 0;
        switch (nivel) {
            case 0:
                minas = 4;
                x = 3;
                y = 4;
                break;
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
                x = 16;
                y = 30;
                break;
        }
        estadoJuego = 0;
        tablero = new int[x][y];
        visibles = new boolean[x][y];
        marcas = new boolean[x][y];
//        for (int i = 0; i < x; i++) {
//            Arrays.fill(visibles[i], true);
//        }
        rellenaMinas();
        procesaTablero();
    }

    private void rellenaMinas() {
        int m = minas;
        int xx, yy;
        while (m != 0) {
            do {
                xx = (int) (random.nextDouble() * this.x);
                yy = (int) (random.nextDouble() * this.y);
            } while (tablero[xx][yy] == -1);
            tablero[xx][yy] = -1;
            m--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (char i = 0; i < getY(); i++) {
            //char c = (char) ('A' + i);
            //sb.append("  ").append(c).append(" ");
            sb.append(String.format(" %2d ", i + 1));
        }
        sb.append("\n");

        for (int i = 0; i < getX(); i++) {
            sb.append(String.format(" %2d ", i + 1));
            for (int j = 0; j < getY(); j++) {
                String val;
                if (marcas[i][j]) {
                    val = "  X ";
                } else {
                    if (visibles[i][j]) {
                        val = String.format(" %2d ", tablero[i][j]);
                    } else {
                        val = "  - ";
                    }
                }
                sb.append(val);
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

    public void ponMarca(int x, int y) {
        hazJugada(x, y, 1);
    }

    public void destapa(int i, int j) {
        if (tablero[i][j] == -1 || visibles[i][j]) {
            return;
        }
        visibles[i][j] = true;

        if (tablero[i][j] > 0) {
            return;
        }
        if (i > 0) {
            destapa(i - 1, j);
        }
        if (j > 0) {
            destapa(i, j - 1);
        }
        if (j != y - 1) {
            destapa(i, j + 1);
        }
        if (i != x - 1) {
            destapa(i + 1, j);
        }
        if (i > 0 && j > 0) {
            destapa(i - 1, j - 1);
        }
        if (j != y - 1 && i != x - 1) {
            destapa(i + 1, j + 1);
        }
        if (j != y - 1 && i > 0) {
            destapa(i - 1, j + 1);
        }
        if (i != x - 1 && j > 0) {
            destapa(i + 1, j - 1);
        }
    }

    private void procesaTablero() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int n = 0;
                if (tablero[i][j] >= 0) {
                    if (i > 0) {
                        tablero[i][j] += tablero[i - 1][j] == -1 ? 1 : 0;
                    }
                    if (j > 0) {
                        tablero[i][j] += tablero[i][j - 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1) {
                        tablero[i][j] += tablero[i][j + 1] == -1 ? 1 : 0;
                    }
                    if (i != x - 1) {
                        tablero[i][j] += tablero[i + 1][j] == -1 ? 1 : 0;
                    }
                    if (i > 0 && j > 0) {
                        tablero[i][j] += tablero[i - 1][j - 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1 && i != x - 1) {
                        tablero[i][j] += tablero[i + 1][j + 1] == -1 ? 1 : 0;
                    }
                    if (j != y - 1 && i > 0) {
                        tablero[i][j] += tablero[i - 1][j + 1] == -1 ? 1 : 0;
                    }
                    if (i != x - 1 && j > 0) {
                        tablero[i][j] += tablero[i + 1][j - 1] == -1 ? 1 : 0;
                    }
                }
            }
        }
    }

    public void hazJugada(int x, int y, int t) {
        if (t == 0) // Tiro
        {
            if (tablero[x][y] == -1) {
                for (int i = 0; i < x; i++) {
                    Arrays.fill(visibles, true);
                }
                estadoJuego = -1;
            } else {
                destapa(x, y);
            }
        } else {
            marcas[x][y] = !marcas[x][y];
            if (tablero[x][y] == -1) {
                minas += marcas[x][y] ? -1 : 1;
            }
            if (minas == 0) {
                estadoJuego = 1;
            }
        }
    }
}
