package com.colassat.GestionColas.Servicios;

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
import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

public class D03_VentanaCRUDTrabajadores extends JFrame {
    private JTable tablaTrabajadores;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBuscar;
    private JButton btnInsertar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    private JButton btnVolver;
    
    public D03_VentanaCRUDTrabajadores() {
        initComponents();
        setupLayout();
        setupEvents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("CRUD Trabajadores - Sistema de Gestion de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(1000, 700); 
        
        String[] columnas = {"ID Trabajador", "Nombre", "Estado", "Administrador"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaTrabajadores = new JTable(modeloTabla);
        tablaTrabajadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        sorter = new TableRowSorter<>(modeloTabla);
        tablaTrabajadores.setRowSorter(sorter);
        
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
        
        btnInsertar = new JButton("INSERTAR");
        btnModificar = new JButton("MODIFICAR");
        btnEliminar = new JButton("ELIMINAR");
        btnRefrescar = new JButton("REFRESCAR");
        btnVolver = new JButton("VOLVER");
        
        Font btnFont = new Font("Arial", Font.BOLD, 12);
        Color btnColor = new Color(0, 123, 255);
        Color btnColorEliminar = new Color(220, 53, 69);
        Color btnColorRefrescar = new Color(40, 167, 69);
        Color btnColorVolver = new Color(108, 117, 125);
        
        configurarBoton(btnInsertar, btnFont, btnColor);
        configurarBoton(btnModificar, btnFont, btnColor);
        configurarBoton(btnEliminar, btnFont, btnColorEliminar);
        configurarBoton(btnRefrescar, btnFont, btnColorRefrescar);
        configurarBoton(btnVolver, btnFont, btnColorVolver);
    }
    
    private void configurarBoton(JButton boton, Font font, Color color) {
        boton.setFont(font);
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(248, 249, 250));
        
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBackground(new Color(248, 249, 250));
        JLabel lblTitulo = new JLabel("GESTION DE TRABAJADORES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 58, 64));
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
        
        JScrollPane scrollPane = new JScrollPane(tablaTrabajadores);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Trabajadores"));
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnInsertar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnVolver);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoInsertar();
            }
        });
        
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoModificar();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTrabajador();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new D01_VentanaMenuAdministrador().setVisible(true);
            }
        });
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("Codigo"),
                    rs.getString("Nombre Trabajador"),
                    rs.getBoolean("Trabajando") ? "Activo" : "Inactivo",
                    rs.getBoolean("Administrador") ? "Si" : "No"
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
    
    private void mostrarDialogoInsertar() {
        JDialog dialogo = new JDialog(this, "Insertar Trabajador", true);
        dialogo.setSize(500, 400); 
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtId = new JTextField(15);
        JTextField txtNombre = new JTextField(15);
        JCheckBox chkEstado = new JCheckBox("Activo");
        JCheckBox chkAdmin = new JCheckBox("Administrador");
        chkEstado.setSelected(true);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Trabajador:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(chkEstado, gbc);
        gbc.gridx = 1;
        panel.add(chkAdmin, gbc);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios");
                return;
            }
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_InsertarTrabajador(?, ?, ?, ?)}");
                
                stmt.setString(1, txtId.getText().trim());
                stmt.setString(2, txtNombre.getText().trim());
                stmt.setBoolean(3, chkEstado.isSelected());
                stmt.setBoolean(4, chkAdmin.isSelected());
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(dialogo, "Trabajador insertado correctamente");
                cargarDatos();
                dialogo.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, 
                    "Error al insertar trabajador: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void mostrarDialogoModificar() {
        int filaSeleccionada = tablaTrabajadores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un trabajador para modificar");
            return;
        }
        
        int filaModelo = tablaTrabajadores.convertRowIndexToModel(filaSeleccionada);
        
        String idTrabajador = (String) modeloTabla.getValueAt(filaModelo, 0);
        String nombre = (String) modeloTabla.getValueAt(filaModelo, 1);
        boolean estado = "Activo".equals(modeloTabla.getValueAt(filaModelo, 2));
        boolean admin = "Si".equals(modeloTabla.getValueAt(filaModelo, 3));
        
        JDialog dialogo = new JDialog(this, "Modificar Trabajador", true);
        dialogo.setSize(500, 400); 
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField txtId = new JTextField(idTrabajador, 15);
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        JTextField txtNombre = new JTextField(nombre, 15);
        JCheckBox chkEstado = new JCheckBox("Activo", estado);
        JCheckBox chkAdmin = new JCheckBox("Administrador", admin);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Trabajador:"), gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(chkEstado, gbc);
        gbc.gridx = 1;
        panel.add(chkAdmin, gbc);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre es obligatorio");
                return;
            }
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ModificarTrabajador(?, ?, ?, ?)}");
                
                stmt.setString(1, txtId.getText());
                stmt.setString(2, txtNombre.getText().trim());
                stmt.setBoolean(3, chkEstado.isSelected());
                stmt.setBoolean(4, chkAdmin.isSelected());
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(dialogo, "Trabajador modificado correctamente");
                cargarDatos();
                dialogo.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, 
                    "Error al modificar trabajador: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    private void eliminarTrabajador() {
        int filaSeleccionada = tablaTrabajadores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un trabajador para eliminar");
            return;
        }
        
        int filaModelo = tablaTrabajadores.convertRowIndexToModel(filaSeleccionada);
        
        String idTrabajador = (String) modeloTabla.getValueAt(filaModelo, 0);
        String nombre = (String) modeloTabla.getValueAt(filaModelo, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Esta seguro de eliminar al trabajador " + nombre + "?",
            "Confirmar eliminacion",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_EliminarTrabajador(?)}");
                
                stmt.setString(1, idTrabajador);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this, "Trabajador eliminado correctamente");
                cargarDatos();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar trabajador: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
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
}

