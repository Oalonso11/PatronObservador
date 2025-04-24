import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

/**
 * Esta clase representa una ventana gráfica que permite al usuario agregar una nueva tarea.
 
 *   Al hacer clic en el botón "Agregar Tarea".* :
 * - Se valida que el nombre no esté vacío.
 * - Se verifica que la fecha seleccionada sea válida.
 * - Si todo es correcto, se crea la tarea y se incorpora al sistema.
 * - Si hay errores, se muestra un mensaje en el formulario indicando el problema que se detectó.
 */


public class AgregarTareaFrame extends JFrame {

    private JTextField campoNombre;
    private JComboBox<Integer> comboPrioridad, comboDia, comboAño;
    private JComboBox<String> comboEstado, comboMes;
    private JLabel errorLabel;

    public AgregarTareaFrame(GestorDeTareas gestor, InterfazTareas principal) {
        setTitle("Agregar Nueva Tarea"); 
        setSize(500, 400);  
        setLayout(new GridBagLayout()); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel title = new JLabel("Registro de Nueva Tarea", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Nombre de la Tarea:"), gbc);
        gbc.gridx = 1;
        campoNombre = new JTextField(15);
        add(campoNombre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 1;
        comboPrioridad = new JComboBox<>(new Integer[]{1, 2, 3});
        add(comboPrioridad, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        comboEstado = new JComboBox<>(new String[]{"Pendiente", "En progreso", "Completada"});
        add(comboEstado, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Fecha de Entrega:"), gbc);

        comboMes = new JComboBox<>(new String[]{"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"});
        comboDia = new JComboBox<>();
        comboAño = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDia.addItem(i);
        for (int i = LocalDate.now().getYear(); i <= 2030; i++) comboAño.addItem(i);

        JPanel fechaPanel = new JPanel();
        fechaPanel.add(comboDia);
        fechaPanel.add(comboMes);
        fechaPanel.add(comboAño);
        gbc.gridx = 1;
        add(fechaPanel, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        add(errorLabel, gbc);

        gbc.gridy++;
        JButton btnAgregar = new JButton("Agregar Tarea");
        add(btnAgregar, gbc);        
        btnAgregar.addActionListener(e -> validarYAgregar(gestor, principal));

        setVisible(true);
    }

    private void validarYAgregar(GestorDeTareas gestor, InterfazTareas principal) {
        String nombre = campoNombre.getText().trim();
        int prioridad = (int) comboPrioridad.getSelectedItem();
        String estado = (String) comboEstado.getSelectedItem();
        int dia = (int) comboDia.getSelectedItem();
        int mes = comboMes.getSelectedIndex() + 1; 
        int año = (int) comboAño.getSelectedItem();

        if (nombre.isEmpty()) {
            errorLabel.setText("El nombre de la tarea no puede estar vacío.");
            return;
        }

        if (!validarFecha(dia, mes, año)) {
            errorLabel.setText("Fecha inválida");
            return;
        }

        int nuevoID = gestor.getTareas().size() + 1;
        Tarea nueva = new Tarea(nuevoID, nombre, prioridad, estado, dia, mes, año);
        gestor.agregarTarea(nueva); 

        gestor.setEstrategia(new OrdenarPorNombre());
        gestor.ordenarTareas();

        principal.actualizarTabla(gestor.getTareas());

        JOptionPane.showMessageDialog(this,
            "La tarea: " + nueva.getNombre() + ", está " + nueva.getEstado(),
            "Notificación",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private boolean validarFecha(int dia, int mes, int año) {
        try {
            LocalDate.of(año, mes, dia); 
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}