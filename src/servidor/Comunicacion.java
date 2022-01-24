
package servidor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 *
 * @author Jesus
 */
public class Comunicacion extends Observable{
    
    private String mensaje;
    private int jugadores = 0;
    private int jugadoresListos = 0;
    private ArrayList<String> lista = fichas();
    
    public Comunicacion(){
       
    }
    
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
        this.setChanged();
        this.notifyObservers(getMensaje());
    }
    
    public String getMensaje(){
        return mensaje;
    }
    
    public void setJugadores(int jugadores){
        this.jugadores = jugadores;
    }
    
    public int getJugadores(){
        return jugadores;
    }

    public int getJugadoresListos() {
        return jugadoresListos;
    }

    public void setJugadoresListos(int jugadoresListos) {
        this.jugadoresListos = jugadoresListos;
    }
    
    public String repartirFichas(int id) {
        String fichas = "";
        if (id == 1) {
            for (int i = 0; i < 14; i++) {
                fichas += ";" + lista.get(i);
            }

            for (int i = 0; i < 14; i++) {
                lista.remove(0);
            }
        } else if (id == 2) {
            for (int i = 0; i < 14; i++) {
                fichas += ";" + lista.get(i);
            }

            for (int i = 0; i < 14; i++) {
                lista.remove(0);
            }
        }

        return fichas;
    }
    
    public ArrayList<String> fichas(){
        lista = new ArrayList();
        lista.add("0-0");
        lista.add("1-0");
        lista.add("1-1");
        lista.add("2-0");
        lista.add("2-1");
        lista.add("2-2");
        lista.add("3-0");
        lista.add("3-1");
        lista.add("3-2");
        lista.add("3-3");
        lista.add("4-0");
        lista.add("4-1");
        lista.add("4-2");
        lista.add("4-3");
        lista.add("4-4");
        lista.add("5-0");
        lista.add("5-1");
        lista.add("5-2");
        lista.add("5-3");
        lista.add("5-4");
        lista.add("5-5");
        lista.add("6-0");
        lista.add("6-1");
        lista.add("6-2");
        lista.add("6-3");
        lista.add("6-4");
        lista.add("6-5");
        lista.add("6-6");

        Collections.shuffle(lista);

        return lista;
    }
    
}
