import java.util.*;

/**
 * Esta clase implementa una estrategia de ordenamiento que organiza una lista de tareas
 * alfabéticamente según el nombre de cada tarea (de la A a la Z).
 * Forma parte del patrón de diseño Estrategia, permitiendo cambiar el criterio de
 * ordenamiento en el sistema de gestión de tareas.
 */

class OrdenarPorNombre implements EstrategiaOrdenamiento {

    public void ordenar(List<Tarea> tareas) {
        
        tareas.sort(Comparator.comparing(Tarea::getNombre));
    }
}
