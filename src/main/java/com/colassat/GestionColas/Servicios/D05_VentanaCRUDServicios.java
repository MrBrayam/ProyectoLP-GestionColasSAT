package com.colassat.GestionColas.Servicios;

import com.colassat.GestionColas.BaseDeDatos.ConexionBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class D05_VentanaCRUDServicios extends JFrame {
    private String usuarioAdministrador;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtCodigo, txtNombre;
    private JTextField txtBuscar;
    private JCheckBox chkEstado;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnVolver;
    
    public D05_VentanaCRUDServicios(String usuario) {
        this.usuarioAdministrador = usuario;
        initComponents();
        setupLayout();
        setupEvents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    public D05_VentanaCRUDServicios() {
        this("");
    }
    
    private void initComponents() {
        setTitle("Gestión de Servicios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 750);
        
        String[] columnas = {"Codigo", "Nombre", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        sorter = new TableRowSorter<>(modeloTabla);
        tabla.setRowSorter(sorter);
        
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        txtBuscar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabla();
            }
        });
        
        txtCodigo = new JTextField(15);
        txtNombre = new JTextField(15);
        chkEstado = new JCheckBox("Activo");
        chkEstado.setSelected(true);
        
        btnCrear = new JButton("CREAR");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR");
        btnLimpiar = new JButton("LIMPIAR");
        btnVolver = new JButton("VOLVER");
        
        configurarBoton(btnCrear, new Color(40, 167, 69));
        configurarBoton(btnActualizar, new Color(0, 123, 255));
        configurarBoton(btnEliminar, new Color(220, 53, 69));
        configurarBoton(btnLimpiar, new Color(255, 193, 7));
        configurarBoton(btnVolver, new Color(108, 117, 125));
    }
    
    private void configurarBoton(JButton boton, Color color) {
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(120, 35));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(248, 249, 250));
        
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(new Color(248, 249, 250));
        JLabel lblTitulo = new JLabel("GESTIÓN DE SERVICIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(40, 167, 69));
        panelTitulo.add(lblTitulo);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(248, 249, 250));
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        lblBuscar.setForeground(new Color(52, 58, 64));
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        
        panelSuperior.add(panelTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Servicios"));
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Servicio"));
        panelFormulario.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigo, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(chkEstado, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        panelFormulario.add(panelBotones, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearServicio();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarServicio();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarServicio();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D01_VentanaMenuAdministrador().setVisible(true);
            }
        });
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarServicio}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("Codigo"),
                    rs.getString("Nombre Servicio"),
                    rs.getBoolean("Estado Activo") ? "Activo" : "Inactivo"
                };
                modeloTabla.addRow(fila);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearServicio() {
        if (validarCampos()) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            boolean estado = chkEstado.isSelected();
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_InsertarServicio(?, ?, ?)}");
                
                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                stmt.setBoolean(3, estado);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos(); 
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Servicio creado exitosamente");
                
            } catch (Exception e) {
                String mensajeError = e.getMessage();
                if (mensajeError.contains("PRIMARY KEY")) {
                    JOptionPane.showMessageDialog(this, 
                        "Error: Ya existe un servicio con el código '" + codigo + "'.\n\n" +
                        "Por favor, ingrese un código diferente.", 
                        "Código duplicado", 
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al crear servicio: " + mensajeError, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void actualizarServicio() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para actualizar");
            return;
        }
        
        if (validarCampos()) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            boolean estado = chkEstado.isSelected();
            
            if (!estado) { 
                int ticketsPendientes = contarTicketsPendientes(codigo);
                if (ticketsPendientes > 0) {
                    JOptionPane.showMessageDialog(this, 
                        String.format("No se puede inactivar este servicio porque tiene %d ticket(s) pendiente(s).\n\n" +
                        "Para inactivar un servicio, todos sus tickets deben estar atendidos.", ticketsPendientes), 
                        "Operación no permitida", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ModificarServicio(?, ?, ?)}");
                
                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                stmt.setBoolean(3, estado);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos(); 
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente");
                
            } catch (Exception e) {
                String mensajeError = e.getMessage();
                System.out.println("DEBUG - Error completo: " + mensajeError); 
                
                if (mensajeError.contains("tickets pendientes") || mensajeError.contains("ticket(s) pendiente(s)")) {
                    JOptionPane.showMessageDialog(this, 
                        "No se puede inactivar este servicio porque tiene tickets pendientes.\n\n" +
                        "Para inactivar un servicio, todos sus tickets deben estar atendidos.", 
                        "Operación no permitida", 
                        JOptionPane.WARNING_MESSAGE);
                } else if (mensajeError.contains("tickets asociados")) {
                    JOptionPane.showMessageDialog(this, 
                        "No se puede inactivar este servicio porque tiene tickets asociados.\n\n" +
                        "Para inactivar un servicio, todos sus tickets deben estar atendidos.", 
                        "Operación no permitida", 
                        JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al actualizar servicio: " + mensajeError, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void eliminarServicio() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar");
            return;
        }
        
        int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
        String codigo = modeloTabla.getValueAt(filaModelo, 0).toString();
        
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar el servicio con código: " + codigo + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_EliminarServicio(?)}");
                
                stmt.setString(1, codigo);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos(); 
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Servicio eliminado exitosamente");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar servicio: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
            String codigo = modeloTabla.getValueAt(filaModelo, 0).toString();
            
            txtCodigo.setText(codigo);
            txtNombre.setText(modeloTabla.getValueAt(filaModelo, 1).toString());
            String estado = modeloTabla.getValueAt(filaModelo, 2).toString();
            chkEstado.setSelected("Activo".equals(estado));
            txtCodigo.setEnabled(false);
            txtCodigo.setBackground(new Color(240, 240, 240));
            
            verificarTicketsPendientes(codigo);
        }
    }
    
    private void verificarTicketsPendientes(String codigoServicio) {
        debugServicio(codigoServicio);
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall(
                "{call PA_CRUD_ContarTicketsPendientes(?)}"
            );
            stmt.setString(1, codigoServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int ticketsPendientes = rs.getInt(1);
                
                System.out.println("Tickets pendientes detectados: " + ticketsPendientes);
                
                if (ticketsPendientes > 0) {
                    chkEstado.setToolTipText(
                        "Este servicio tiene " + ticketsPendientes + 
                        " ticket(s) pendiente(s). No se puede inactivar."
                    );
                    chkEstado.setForeground(new Color(220, 53, 69)); // Color rojo
                } else {
                    chkEstado.setToolTipText("Este servicio no tiene tickets pendientes.");
                    chkEstado.setForeground(Color.BLACK);
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error al verificar tickets: " + e.getMessage());
            chkEstado.setToolTipText(null);
            chkEstado.setForeground(Color.BLACK);
        }
    }
    
    private void debugServicio(String codigoServicio) {
        System.out.println("=== DEBUG SERVICIO " + codigoServicio + " ===");
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_DEBUG_VerificarTicketsServicio(?)}");
            stmt.setString(1, codigoServicio);
            
            boolean hasResults = stmt.execute();
            
            if (hasResults) {
                ResultSet rs = stmt.getResultSet();
                
                System.out.println("Tickets encontrados:");
                while (rs.next()) {
                    System.out.println("- Ticket: " + rs.getString("NumTicket") + 
                                     ", Estado: " + rs.getString("Estado_Texto") + 
                                     ", Cliente: " + rs.getString("CodCliente"));
                }
                
                if (stmt.getMoreResults()) {
                    ResultSet rs2 = stmt.getResultSet();
                    if (rs2.next()) {
                        System.out.println("Resumen:");
                        System.out.println("- Total tickets: " + rs2.getInt("Total_Tickets"));
                        System.out.println("- Tickets pendientes: " + rs2.getInt("Tickets_Pendientes"));
                        System.out.println("- Tickets atendidos: " + rs2.getInt("Tickets_Atendidos"));
                    }
                }
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error en debug: " + e.getMessage());
        }
        System.out.println("=== FIN DEBUG ===");
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() || 
            txtNombre.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", 
                "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        chkEstado.setSelected(true); 
        txtCodigo.setEnabled(true);
        txtCodigo.setBackground(Color.WHITE);
        chkEstado.setToolTipText(null);
        chkEstado.setForeground(Color.BLACK);
        
        tabla.clearSelection();
    }
    
    private void filtrarTabla() {
        String texto = txtBuscar.getText().trim();
        
        if (texto.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            } catch (java.util.regex.PatternSyntaxException ex) {
                sorter.setRowFilter(null);
            }
        }
    }

    private int contarTicketsPendientes(String codigoServicio) {
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ContarTicketsPendientes(?)}");
            stmt.setString(1, codigoServicio);
            
            ResultSet rs = stmt.executeQuery();
            
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("DEBUG - Tickets pendientes para " + codigoServicio + ": " + count);
            return count;
            
        } catch (Exception e) {
            System.err.println("Error al contar tickets pendientes: " + e.getMessage());
            return 0;
        }
    }
}