package evaluacion2.common;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.swing.text.Position;
import sun.swing.PrintColorUIResource;

/**
 *
 * @author (at)fferegrino
 */
public class Juego implements Serializable {

    final static int PALABRA_HORIZONTAL = 0;
    final static int PALABRA_VERTICAL = 1;
    final static int PALABRA_DIAGONAL = 2;
    final static int PALABRA_POSICIONES = 3;
    final static int INTENTOS = 50;
    int anchura;
    int altura;
    int chars;
    String[] palabras;
    ArrayList<Posicion> posiciones;
    char[][] tablero;

    public Juego(String[] palabras) {

        this.anchura = 35;
        this.altura = 35;
        chars = anchura * altura;
        this.palabras = palabras;
        posiciones = new ArrayList<Posicion>();
        this.tablero = new char[altura][anchura];
    }

    private void setPalabras() {
        for (int i = 0; i < palabras.length; i++) {
            String current = palabras[i];
            int colocado = -1;
            do {
                int estado = r.nextInt(PALABRA_POSICIONES);
                switch (estado) {
                    case PALABRA_HORIZONTAL:
                        //System.out.println("Horizontal: " + current);
                        colocado = setPalabraHorizontal(current);
                        break;
                    case PALABRA_VERTICAL:
                        //System.out.println("Vertical: " + current);
                        colocado = setPalabraVertical(current);
                        break;
                    case PALABRA_DIAGONAL:
                        //System.out.println("Diagonal: " + current);
                        colocado = setPalabraDiagonal(current);
                        break;
                }
            } while (colocado == -1);
            chars -= (current.length() - colocado);
        }
    }

    private int setPalabraDiagonal(String palabra) {
        int x = 0;
        int y = 0;
        int intento = 0;
        int cruces = 0;
        do {
            x = r.nextInt(anchura - palabra.length());
            y = r.nextInt(altura - palabra.length());
            cruces = posible(palabra, x, y, PALABRA_DIAGONAL);
        } while (cruces == -1 && intento++ != INTENTOS);
        if (intento < INTENTOS) {
            for (int i = 0; i < palabra.length(); i++) {
                tablero[x + i][y + i] = palabra.toCharArray()[i];
            }
            Posicion p = new Posicion(x, x + palabra.length() - 1, y, y + palabra.length() - 1);
            posiciones.add(p);
            return cruces;
        } else {
            return -1;
        }
    }

    private int setPalabraHorizontal(String palabra) {
        int x = 0;
        int y = 0;
        int intento = 0;
        int cruces = 0;
        do {
            x = r.nextInt(anchura - palabra.length());
            y = r.nextInt(altura);
            cruces = posible(palabra, x, y, PALABRA_HORIZONTAL);
        } while (cruces == -1 && intento++ != INTENTOS);
        if (intento < INTENTOS) {
            for (int i = 0; i < palabra.length(); i++) {
                tablero[x + i][y] = palabra.toCharArray()[i];
            }
            Posicion p = new Posicion(x, x + palabra.length() - 1, y, y);
            posiciones.add(p);
            return cruces;
        } else {
            return -1;
        }
    }

    private int setPalabraVertical(String palabra) {
        int x = 0;
        int y = 0;
        int intento = 0;
        int cruces = 0;
        do {
            x = r.nextInt(anchura);
            y = r.nextInt(altura - palabra.length());
            cruces = posible(palabra, x, y, PALABRA_VERTICAL);
        } while (cruces == -1 && intento++ != INTENTOS);
        if (intento < INTENTOS) {
            for (int i = 0; i < palabra.length(); i++) {
                tablero[x][y + i] = palabra.toCharArray()[i];
            }
            Posicion p = new Posicion(x, x, y, y + palabra.length() - 1);
            posiciones.add(p);
            return cruces;
        } else {
            return -1;
        }
    }

    private int posible(String palabra, int x, int y, int posicion) {
        int cruces = 0;
        boolean res = true;
        switch (posicion) {
            case PALABRA_HORIZONTAL:
                for (int i = 0; i < palabra.length(); i++) {
                    cruces += tablero[x + i][y] == palabra.toCharArray()[i] ? 1 : 0;
                    res = res && (tablero[x + i][y] == 0 || tablero[x + i][y] == palabra.toCharArray()[i]);
                }
                break;
            case PALABRA_VERTICAL:
                for (int i = 0; i < palabra.length(); i++) {
                    cruces += tablero[x][y + i] == palabra.toCharArray()[i] ? 1 : 0;
                    res = res && (tablero[x][y + i] == 0 || tablero[x][y + i] == palabra.toCharArray()[i]);
                }
                break;
            case PALABRA_DIAGONAL:
                for (int i = 0; i < palabra.length(); i++) {
                    cruces += tablero[x + i][y + i] == palabra.toCharArray()[i] ? 1 : 0;
                    res = res && (tablero[x + i][y + i] == 0 || tablero[x + i][y + i] == palabra.toCharArray()[i]);
                }
                break;
        }
        if (res) {
            return cruces;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int i = 1; i <= anchura; i++) {
            sb.append(" ").append(String.format("%2d", i)).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < altura; i++) {
            sb.append(" ").append(String.format("%2d", i + 1)).append(" ");
            for (int j = 0; j < anchura; j++) {
                sb.append("  ").append(tablero[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void fill() {
        setPalabras();
        randomFill();
    }

    public void randomFill() {
        while (chars != 0) {
            int x, y;
            do {
                x = r.nextInt(anchura);
                y = r.nextInt(altura);
            } while (tablero[x][y] != 0);
            char ch = (char) (r.nextInt(26));
            tablero[x][y] = (char) (ch + 65);
            chars--;
        }
    }
    // <editor-fold desc="Statics">
    static SecureRandom r;
    static boolean isInit;

    public static void init() throws NoSuchAlgorithmException {
        if (!isInit) {
            isInit = true;
            r = SecureRandom.getInstance("SHA1PRNG");
        }
    }
    // </editor-fold>
}
