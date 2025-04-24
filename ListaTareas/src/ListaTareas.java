import javax.swing.*;
import java.sql.*;

/**
 * Este archivo representa el punto de entrada principal para el sistema de gestión de tareas.
 * Al iniciar el programa, se asegura que la base de datos contenga la tabla necesaria para almacenar tareas,
 * y posteriormente se lanza la interfaz gráfica del usuario.
 * Cada tarea incluye un nombre, una prioridad, un estado y una fecha de entrega.
 * Este diseño permite registrar, visualizar y gestionar tareas a través de una GUI en Java.
 */

public class ListaTareas {

    public static void main(String[] args) {
        crearTabla(); 
        GestorDeTareas gestor = new GestorDeTareas();

        SwingUtilities.invokeLater(() -> new InterfazTareas(gestor));
    }

    private static void crearTabla() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:ListaTareas.db")) {
            Statement stmt = conn.createStatement();
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS tareas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, -- Identificador único de cada tarea
                    nombre TEXT NOT NULL, -- Nombre de la tarea
                    prioridad INTEGER CHECK(prioridad BETWEEN 1 AND 3), -- Prioridad entre 1 (alta) y 3 (baja)
                    estado TEXT CHECK(estado IN ('Pendiente', 'En progreso', 'Completada')), -- Estado válido de la tarea
                    dia INTEGER CHECK(dia BETWEEN 1 AND 31), -- Día de entrega
                    mes INTEGER CHECK(mes BETWEEN 1 AND 12), -- Mes de entrega
                    año INTEGER CHECK(año BETWEEN 1900 AND 2100) -- Año de entrega
                );
            """); 
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}
