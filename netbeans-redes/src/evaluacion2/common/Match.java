package evaluacion2.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author (at)fferegrino
 */
public class Match extends Thread {

    static int MAXIMO_JUGADORES = 2;
    static int MINUTOS_JUEGO = 1;
    private TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            segundosJuego++;
            if (segundosJuego / 30 == MINUTOS_JUEGO) {
                try {
                    finalizarJuego();
                } catch (IOException ex) {
                } catch (InterruptedException ex) {
                }
            }
        }
    };
    boolean terminado;
    int segundosJuego;
    int puntosMax;
    String userMax;
    Juego j;
    Timer timer;
    ArrayList<Jugador> jugadores;

    public Match(String[] palabras) {
        jugadores = new ArrayList<Jugador>();
        timer = new Timer();
        j = new Juego(palabras);
        j.fill();
        for (Posicion p : j.posiciones) {
            System.out.println(p);
        }
        System.out.println(j);
    }

    @Override
    public void run() {
        try {
            comenzar();
            while (!terminado) {
                Thread.sleep(100);
            }
        } catch (IOException ex) {
        } catch (InterruptedException iex) {
        }
    }

    public boolean isAvailable() {
        return this.jugadores.size() < MAXIMO_JUGADORES;
    }

    public int availablePlaces() {
        return MAXIMO_JUGADORES - this.jugadores.size();
    }

    public void comenzar() throws IOException {
        System.out.println("Juego comenzado");
        puntosMax = 0;
        userMax = "";
        this.terminado = false;
        for (Iterator<Jugador> it = jugadores.iterator(); it.hasNext();) {
            Jugador jugador = it.next();
            jugador.asociarMatch(this);
        }
        for (Iterator<Jugador> it = jugadores.iterator(); it.hasNext();) {
            Jugador jugador = it.next();
            jugador.comenzarJuego();
        }
        timer.schedule(tt, 0, 1000);
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }

    public void move(Jugador j, Posicion p) throws IOException, InterruptedException {
        System.out.println("Tiro de " + j + " " + p);
        if (this.j.posiciones.contains(p)) {
            j.setPuntos(j.getPuntos() +1);
            if(j.getPuntos() > puntosMax){
                puntosMax = j.getPuntos();
                userMax = j.getUsername();
            }
            this.j.posiciones.remove(p);
            if (this.j.posiciones.isEmpty()) {
                finalizarJuego();
            }
        }
    }

    void finalizarJuego() throws IOException, InterruptedException {
        for (Iterator<Jugador> it = jugadores.iterator(); it.hasNext();) {
            Jugador jugador = it.next();
            jugador.finalizarJuego(userMax);
        }
        timer.cancel();
        terminado = true;
        System.out.println("Juego terminado");
    }

    public boolean finalizado() {
        return terminado;
    }
}
