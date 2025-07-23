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

public class D04_VentanaCRUDClientes extends JFrame {
    private String usuarioAdministrador;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtCodigo, txtNombre;
    private JTextField txtBuscar; 
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnVolver;
    
    public D04_VentanaCRUDClientes(String usuario) {
        this.usuarioAdministrador = usuario;
        initComponents();
        setupLayout();
        setupEvents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Gestion de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700); 
        
        String[] columnas = {"Codigo", "Nombre"};
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
        JLabel lblTitulo = new JLabel("GESTION DE CLIENTES");
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
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        panelFormulario.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Codigo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigo, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNombre, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        panelFormulario.add(panelBotones, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCliente();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
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
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    cargarDatosSeleccionados();
                }
            }
        });
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
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0); 
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarCliente}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("Codigo"),
                    rs.getString("Nombre Cliente")
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
    
    private void crearCliente() {
        if (validarCampos()) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_InsertarCliente(?, ?)}");
                
                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente creado exitosamente");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear cliente: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarCliente() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar");
            return;
        }
        
        if (validarCampos()) {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ModificarCliente(?, ?)}");
                
                stmt.setString(1, codigo);
                stmt.setString(2, nombre);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar cliente: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            return;
        }
        
        String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Esta seguro que desea eliminar el cliente con codigo: " + codigo + "?", 
            "Confirmar Eliminacion", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_EliminarCLiente(?, ?)}");
                
                stmt.setString(1, codigo);
                stmt.setString(2, modeloTabla.getValueAt(filaSeleccionada, 1).toString()); // Nombre
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar cliente: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
            txtCodigo.setText(modeloTabla.getValueAt(filaModelo, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(filaModelo, 1).toString());
            txtCodigo.setEnabled(false);
            txtCodigo.setBackground(new Color(240, 240, 240)); 
        }
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() || 
            txtNombre.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Campos Vacios", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCodigo.setEnabled(true);
        txtCodigo.setBackground(Color.WHITE);
        tabla.clearSelection();
    }
}

