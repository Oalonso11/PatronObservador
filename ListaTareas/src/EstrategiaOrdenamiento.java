import java.util.*;

/*  Se define la interfaz que representa el uso del patrón de diseño Estrategia.
*   Su objetivo es permitir que una lista de tareas pueda ordenarse mediante diferentes criterios,
*   en este caso por fecha, nombre, prioridad o estado. Cada estrategia
*   implementará esta interfaz y definirá su propio método de ordenación.
*/

interface EstrategiaOrdenamiento {
   
    void ordenar(List<Tarea> tareas);
}
