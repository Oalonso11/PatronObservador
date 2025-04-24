import java.util.*;

/**
 * Esta clase define una estrategia de ordenamiento que organiza una lista de tareas
 * en orden ascendente según su nivel de prioridad.
 * Cuanto menor es el valor numérico de la prioridad, mayor es la urgencia de la tarea.
 * Esta lógica forma parte de la implementación del patrón de diseño Estrategia,
 * permitiendo cambiar dinámicamente el criterio con el cual se ordenan las tareas.
 */


class OrdenarPorPrioridad implements EstrategiaOrdenamiento {

    public void ordenar(List<Tarea> tareas) {
       
        tareas.sort(Comparator.comparingInt(Tarea::getPrioridad));
    }
}
