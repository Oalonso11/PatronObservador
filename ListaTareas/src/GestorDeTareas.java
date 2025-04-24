import java.sql.*;
import java.util.*;


/**
 * Esta clase actúa como el componente principal de gestión de tareas dentro del sistema.
 * Su responsabilidad es almacenar, ordenar, actualizar y notificar cambios sobre las tareas registradas.
 * Aplica el patrón Estrategia para permitir ordenamientos dinámicos según diferentes criterios.
 * También implementa el patrón Observador para notificar automáticamente a los observadores registrados cuando se agrega o modifica una tarea.
 * Adicionalmente, esta clase se encarga de la persistencia de datos, cargando y guardando tareas en una base de datos SQLite.
 */

public class GestorDeTareas {

    private List<Tarea> tareas = new ArrayList<>();

    private EstrategiaOrdenamiento estrategia;

    private Notificador notificador = new Notificador();

    public GestorDeTareas() {
        notificador.registrarObserver(new NotificacionUsuario("Usuario"));
        cargarTareasDesdeBD();
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        guardarEnBD(tarea);
        notificador.notificarObservers(tarea);
    }

    public void cambiarEstado(int idTarea, String nuevoEstado) {
        for (int i = 0; i < tareas.size(); i++) {
            Tarea t = tareas.get(i);
            if (t.getId() == idTarea && !t.getEstado().equals(nuevoEstado)) {
                Tarea actualizada = new Tarea(
                    t.getId(), t.getNombre(), t.getPrioridad(), nuevoEstado,
                    t.getDia(), t.getMes(), t.getAño()
                );
                
                tareas.set(i, actualizada);
                actualizarEstadoEnBD(idTarea, nuevoEstado);
                notificador.notificarObservers(actualizada);
                break;
            }
        }
    }

    public void setEstrategia(EstrategiaOrdenamiento estrategia) {
        this.estrategia = estrategia;
    }

    public void ordenarTareas() {
        if (estrategia != null) {
            estrategia.ordenar(tareas);
        }
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    private void guardarEnBD(Tarea tarea) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:ListaTareas.db")) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO tareas (id, nombre, prioridad, estado, dia, mes, año) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, tarea.getId());
            stmt.setString(2, tarea.getNombre());
            stmt.setInt(3, tarea.getPrioridad());
            stmt.setString(4, tarea.getEstado());
            stmt.setInt(5, tarea.getDia());
            stmt.setInt(6, tarea.getMes());
            stmt.setInt(7, tarea.getAño());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private void actualizarEstadoEnBD(int id, String nuevoEstado) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:ListaTareas.db")) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE tareas SET estado = ? WHERE id = ?");
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private void cargarTareasDesdeBD() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:ListaTareas.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tareas");
            while (rs.next()) {
                Tarea tarea = new Tarea(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("prioridad"),
                    rs.getString("estado"),
                    rs.getInt("dia"),
                    rs.getInt("mes"),
                    rs.getInt("año")
                );
                tareas.add(tarea); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}