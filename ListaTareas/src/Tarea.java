import java.util.*;


/**
 * Esta clase representa el modelo de una tarea dentro de un sistema de gestión de actividades.
 * Cada tarea contiene un identificador único, un nombre descriptivo, un nivel de prioridad (1,2,3; donde 
 * el número 1 representa una mayor urgencia, y el número 3 una menor urgencia),
 * un estado (como pendiente, en progreso o completada), y una fecha de entrega compuesta por día, mes y año.
 * Además, la clase proporciona métodos para acceder a esta información y para generar una descripción legible
 * de la tarea mediante el método `toString`.
 */

public class Tarea {


    private int id;             
    private String nombre;      
    private int prioridad;      
    private String estado;     
    private int dia, mes, año;  

    public Tarea(int id, String nombre, int prioridad, String estado, int dia, int mes, int año) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.estado = estado;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAño() {
        return año;
    }

    public String getFechaEntrega() {
        return String.format("%02d/%02d/%04d", dia, mes, año);
    }

    @Override
    public String toString() {
        return nombre + " (Prioridad: " + prioridad + ", Estado: " + estado + ", Entrega: " + getFechaEntrega() + ")";
    }
}
