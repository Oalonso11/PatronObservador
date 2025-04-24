import java.util.*;
import java.lang.*;

/**
 * Esta interfaz representa al "observador" dentro del patrón de diseño Observer.
 * Define un método `update` que se invoca cada vez que una tarea cambia de estado.
 * De esta forma, cualquier clase que implemente
 * esta interfaz puede recibir notificaciones automáticas cuando una tarea sea modificada.
 */

interface Observer {
    
    void update(Tarea tarea);
}
