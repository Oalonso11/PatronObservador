import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;

/**
 * Esta clase define la interfaz gráfica principal del sistema de gestión de tareas.
 * Permite al usuario visualizar todas las tareas registradas en una tabla,
 * ordenarlas dinámicamente por prioridad, fecha de entrega o estado, y modificar
 * el estado de cada tarea en tiempo real.
 *
 * Se utiliza el patrón de diseño Estrategia para cambiar el criterio de
 * ordenamiento según la selección del usuario en la lista desplegable "Ordenar por".
 * Al seleccionar una opción del comboBox "Ordenar por", se actualiza la vista de la tabla
 * con el criterio correspondiente usando una estrategia de ordenamiento.
 * Además, el usuario puede cambiar el estado de cada tarea desde la tabla,
 * y se muestra una notificación indicando el nuevo estado.
 * El botón "Agregar Tarea" abre una nueva ventana que permite ingresar una nueva tarea
 * al sistema, especificando su nombre, prioridad, estado y fecha de entrega.
 */

public class InterfazTareas extends JFrame {

    private JPanel panelTabla; 
    private JComboBox<String> filtroOrdenamiento; 
    private GestorDeTareas gestor; 

    public InterfazTareas(GestorDeTareas gestor) {
        this.gestor = gestor;
        setTitle("Tareas"); 
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setLayout(new GridBagLayout()); 
        setLocationRelativeTo(null); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel titulo = new JLabel("Lista de Tareas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JButton btnAgregar = new JButton("Agregar Tarea");
        add(btnAgregar, gbc);

        gbc.gridx = 1;
        add(new JLabel("Ordenar por:"), gbc);

        gbc.gridx = 2;
        filtroOrdenamiento = new JComboBox<>(new String[]{"Prioridad", "Fecha de Entrega", "Estado"});
        add(filtroOrdenamiento, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        panelTabla = new JPanel(); 
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS)); 
        JScrollPane scrollPane = new JScrollPane(panelTabla); 
        add(scrollPane, gbc);

        btnAgregar.addActionListener(e -> new AgregarTareaFrame(gestor, this));

        filtroOrdenamiento.addActionListener(e -> {
            String criterio = (String) filtroOrdenamiento.getSelectedItem();
            switch (criterio) {
                case "Prioridad" -> gestor.setEstrategia(new OrdenarPorPrioridad());
                case "Fecha de Entrega" -> gestor.setEstrategia(new OrdenarPorFecha());
                case "Estado" -> gestor.setEstrategia(new OrdenarPorEstado());
            }
            gestor.ordenarTareas(); 
            actualizarTabla(gestor.getTareas()); 
        });

        gestor.getTareas().sort(Comparator.comparing(Tarea::getNombre));
        actualizarTabla(gestor.getTareas()); 

        setVisible(true); 
    }

    public void actualizarTabla(List<Tarea> tareas) {
        panelTabla.removeAll();

        JPanel encabezado = new JPanel(new GridLayout(1, 4));
        encabezado.add(new JLabel("Tarea", SwingConstants.CENTER));
        encabezado.add(new JLabel("Prioridad", SwingConstants.CENTER));
        encabezado.add(new JLabel("Estado", SwingConstants.CENTER));
        encabezado.add(new JLabel("Fecha de Entrega", SwingConstants.CENTER));
        panelTabla.add(encabezado);

        for (Tarea t : tareas) {
            JPanel fila = new JPanel(new GridLayout(1, 4));

            fila.add(new JLabel(t.getNombre(), SwingConstants.CENTER));

            fila.add(new JLabel(String.valueOf(t.getPrioridad()), SwingConstants.CENTER));

            JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Pendiente", "En progreso", "Completada"});
            comboEstado.setSelectedItem(t.getEstado());
            comboEstado.addActionListener(e -> {
                String nuevoEstado = (String) comboEstado.getSelectedItem();
                if (!nuevoEstado.equals(t.getEstado())) {
                    gestor.cambiarEstado(t.getId(), nuevoEstado);
                    actualizarTabla(gestor.getTareas());
                    JOptionPane.showMessageDialog(this,
                        "La tarea: " + t.getNombre() + ", se encuentra " + nuevoEstado,
                        "Notificación",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
            fila.add(comboEstado);

            fila.add(new JLabel(t.getFechaEntrega(), SwingConstants.CENTER));

            panelTabla.add(fila);
        }

        panelTabla.revalidate();
        panelTabla.repaint();
    }
}