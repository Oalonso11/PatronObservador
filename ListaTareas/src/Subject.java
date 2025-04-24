import java.util.*;
import java.lang.*;


/**
 *Esta interfaz define las funciones del patrón de diseño Observador (Observer),
 *permitiendo que uno o varios objetos (observadores) se registren para recibir
 *notificaciones cuando ocurra un cambio relevante en el sujeto (Subject).
 *En este caso, el sujeto es responsable de notificar a los observadores
 *cuando una tarea es modificada, agregada o actualizada.
 */

interface Subject {


    void registrarObserver(Observer o);

    void eliminarObserver(Observer o);

    void notificarObservers(Tarea tarea);
}
