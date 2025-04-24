import java.util.*;

/**
 * Esta clase define una estrategia de ordenamiento que organiza una lista de tareas
 * según su fecha de entrega, ordenándolas cronológicamente de la más próxima a la más lejana.
 * Forma parte del patrón de diseño Estrategia, lo cual permite aplicar distintos criterios
 * de ordenamiento a las tareas.
 */

class OrdenarPorFecha implements EstrategiaOrdenamiento {

    public void ordenar(List<Tarea> tareas) {
       
        tareas.sort(Comparator.comparing(Tarea::getFechaEntrega));
    }
}
