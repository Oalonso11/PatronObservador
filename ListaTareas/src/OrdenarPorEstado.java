import java.util.*;


/**
 * Esta clase implementa una estrategia de ordenamiento que organiza una lista de tareas
 * según su estado, siguiendo un orden lógico definido: Pendiente, En progreso y Completada.
 * Para lograrlo, se asigna una prioridad numérica a cada estado y se utiliza como criterio de comparación.
 * Forma parte del patrón de diseño Estrategia, facilitando el cambio dinámico
 * del criterio de ordenamiento de tareas en función del estado en el que se encuentran.
 */

class OrdenarPorEstado implements EstrategiaOrdenamiento {

    public void ordenar(List<Tarea> tareas) {
        

        Map<String, Integer> prioridadEstado = new HashMap<>();
        prioridadEstado.put("Pendiente", 1);
        prioridadEstado.put("En progreso", 2);
        prioridadEstado.put("Completada", 3);

        tareas.sort(Comparator.comparingInt(t -> 
            prioridadEstado.getOrDefault(t.getEstado(), Integer.MAX_VALUE) 
        ));
    }
}
