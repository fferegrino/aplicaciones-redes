package buscaminas;

/**
 *
 * @author usuario
 */
public class Tablero {

    int nivel;
    int x;
    int y;
    int minas;
    int descubiertas;
    int estadoJuego;
    int[][] t;

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

        t = new int[x][y];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (char i = 0; i < x; i++) {
            char c = (char) ('A' + i);
            sb.append("  ").append(c).append(" ");
        }
        sb.append("\n");

        for (int i = 0; i < y; i++) {
            sb.append(String.format(" %2d ", i + 1));
            for (int j = 0; j < x; j++) {
                sb.append(String.format(" %2d ", t[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
