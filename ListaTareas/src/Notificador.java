import java.util.*;
import java.lang.*;

/**
 * Esta clase implementa el rol de "Sujeto" dentro del patrón de diseño Observador.
 * Permite registrar, eliminar y notificar observadores cada vez que una tarea cambia.
 * Su objetivo es mantener sincronizados a todos los observadores con el estado más reciente
 * de una tarea, facilitando la reacción automática ante modificaciones.
 */

class Notificador implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public void registrarObserver(Observer o) {
        observers.add(o);
    }

    public void eliminarObserver(Observer o) {
        observers.remove(o);
    }
    public void notificarObservers(Tarea tarea) {
        for (Observer o : observers) {
            o.update(tarea); 
        }
    }
}
