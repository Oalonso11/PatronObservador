import java.util.*;
import java.lang.*;


/*
 * Esta clase representa a un observador que reacciona a los cambios en el estado de una tarea.
 * Cada instancia está asociada a un nombre de usuario que recibirá notificaciones personalizadas.
 * Cuando una tarea cambia de estado, se genera un mensaje notificando dicho cambio.
 * Forma parte de la implementación del patrón de diseño Observador,
 * permitiendo la comunicación automática entre objetos cuando ocurre una actualización.
 */


class NotificacionUsuario implements Observer {

    private String nombre; 


    public NotificacionUsuario(String nombre) {
        this.nombre = nombre;
    }

    public void update(Tarea tarea) {
        System.out.println("[" + nombre + "] Notificación: La tarea '" + tarea.getNombre() + "' cambió a estado: " + tarea.getEstado());
    }
}
