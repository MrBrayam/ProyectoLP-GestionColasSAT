package com.colassat.GestionColas.Servicios;

import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;

public class C02_VentanaCRUDTickets extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtNumTicket, txtCodCliente, txtCodServicio, txtIdTrabajador;
    private JComboBox<String> cmbFiltroEstado, cmbFiltroServicio;
    private JSpinner spnFecha, spnHoraEmision, spnHoraAtencion;
    private JButton btnCrear, btnAtender, btnEliminar, btnLimpiar, btnVolver;
    private String ventanaOrigen; 
    private String usuarioActual; 
    
    public C02_VentanaCRUDTickets() {
        this("ADMINISTRADOR", "admin"); 
    }
    
    public C02_VentanaCRUDTickets(String origen, String usuario) {
        this.ventanaOrigen = origen;
        this.usuarioActual = usuario;
        initComponents();
        setupLayout();
        setupEvents();
        cargarDatos();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Gestion de Tickets");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(1200, 800);
        
        String[] columnas = {"Fecha", "Numero", "Estado", "Hora Emision", "Hora Atencion", "DNI Cliente", "Servicio", "ID Trabajador"};
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
        
        cmbFiltroEstado = new JComboBox<>();
        cmbFiltroEstado.addItem("Todos los Estados");
        cmbFiltroEstado.addItem("Pendiente");
        cmbFiltroEstado.addItem("Atendido");
        cmbFiltroEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbFiltroEstado.addActionListener(e -> aplicarFiltros());
        
        cmbFiltroServicio = new JComboBox<>();
        cmbFiltroServicio.addItem("Todos los Servicios");
        cmbFiltroServicio.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbFiltroServicio.addActionListener(e -> aplicarFiltros());
        
        cargarServiciosEnCombo();
        
        txtNumTicket = new JTextField(10);
        txtNumTicket.setEnabled(false);
        txtCodCliente = new JTextField(10);
        txtCodServicio = new JTextField(10);
        txtIdTrabajador = new JTextField(10);
        txtIdTrabajador.setEnabled(false); 
        
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEnabled(false);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(dateEditor);
        spnFecha.setPreferredSize(new Dimension(120, 25));
        
        SpinnerDateModel horaEmisionModel = new SpinnerDateModel();
        spnHoraEmision = new JSpinner(horaEmisionModel);
        spnHoraEmision.setEnabled(false);
        JSpinner.DateEditor horaEmisionEditor = new JSpinner.DateEditor(spnHoraEmision, "HH:mm:ss");
        spnHoraEmision.setEditor(horaEmisionEditor);
        spnHoraEmision.setPreferredSize(new Dimension(100, 25));
        
        SpinnerDateModel horaAtencionModel = new SpinnerDateModel();
        spnHoraAtencion = new JSpinner(horaAtencionModel);
        spnHoraAtencion.setEnabled(false);
        JSpinner.DateEditor horaAtencionEditor = new JSpinner.DateEditor(spnHoraAtencion, "HH:mm:ss");
        spnHoraAtencion.setEditor(horaAtencionEditor);
        spnHoraAtencion.setPreferredSize(new Dimension(100, 25));
        
        btnCrear = new JButton("CREAR TICKET");
        btnAtender = new JButton("ATENDER TICKET");
        btnEliminar = new JButton("ELIMINAR");
        btnLimpiar = new JButton("LIMPIAR");
        btnVolver = new JButton("VOLVER");
        
        configurarBoton(btnCrear, new Color(40, 167, 69));
        configurarBoton(btnAtender, new Color(0, 123, 255));
        configurarBoton(btnEliminar, new Color(220, 53, 69));
        configurarBoton(btnLimpiar, new Color(255, 193, 7));
        configurarBoton(btnVolver, new Color(108, 117, 125));
        
        cmbFiltroEstado.setSelectedItem("Pendiente");
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
        JLabel lblTitulo = new JLabel("GESTION DE TICKETS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(40, 167, 69));
        panelTitulo.add(lblTitulo);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(new Color(248, 249, 250));
        
        JLabel lblFiltroEstado = new JLabel("Estado:");
        lblFiltroEstado.setFont(new Font("Arial", Font.BOLD, 12));
        lblFiltroEstado.setForeground(new Color(52, 58, 64));
        panelBusqueda.add(lblFiltroEstado);
        panelBusqueda.add(cmbFiltroEstado);
        
        JLabel lblFiltroServicio = new JLabel("Servicio:");
        lblFiltroServicio.setFont(new Font("Arial", Font.BOLD, 12));
        lblFiltroServicio.setForeground(new Color(52, 58, 64));
        panelBusqueda.add(lblFiltroServicio);
        panelBusqueda.add(cmbFiltroServicio);
        
        panelSuperior.add(panelTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Tickets"));
        panelTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Ticket"));
        panelFormulario.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(spnFecha, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Numero:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtNumTicket, gbc);
        
        gbc.gridx = 4; gbc.gridy = 0;
        panelFormulario.add(new JLabel("H. Emision:"), gbc);
        gbc.gridx = 5;
        panelFormulario.add(spnHoraEmision, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("DNI Cliente:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCodCliente, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Servicio:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtCodServicio, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("ID Trabajador:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtIdTrabajador, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2;
        panelFormulario.add(new JLabel("H. Atencion:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(spnHoraAtencion, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnCrear);
        panelBotones.add(btnAtender);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 6;
        panelFormulario.add(panelBotones, gbc);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearTicket();
            }
        });
        
        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atenderTicket();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTicket();
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
                if ("TRABAJADOR".equals(ventanaOrigen)) {
                    new B03_PanelTrabajador(usuarioActual).setVisible(true);
                } else {
                    new D01_VentanaMenuAdministrador().setVisible(true);
                }
            }
        });
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
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
    
    private void aplicarFiltros() {
        String estadoSeleccionado = (String) cmbFiltroEstado.getSelectedItem();
        String servicioSeleccionado = (String) cmbFiltroServicio.getSelectedItem();
        
        java.util.List<RowFilter<Object, Object>> filters = new java.util.ArrayList<>();
        
        if (!"Todos los Estados".equals(estadoSeleccionado)) {
            filters.add(RowFilter.regexFilter("(?i)" + estadoSeleccionado, 2)); 
        }
        
        if (!"Todos los Servicios".equals(servicioSeleccionado)) {
            String codigoServicio = servicioSeleccionado.split(" - ")[0];
            filters.add(RowFilter.regexFilter("(?i)" + codigoServicio, 6));
        }
        
        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
            sorter.setRowFilter(combinedFilter);
        }
    }
    
    private void cargarServiciosEnCombo() {
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarServicio}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String codigoServicio = rs.getString("Codigo");
                String nombreServicio = rs.getString("Nombre Servicio");
                boolean estadoActivo = rs.getBoolean("Estado Activo");
                
                if (estadoActivo) {
                    cmbFiltroServicio.addItem(codigoServicio + " - " + nombreServicio);
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("Error al cargar servicios: " + e.getMessage());
        }
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0); 
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTicket}");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getDate("Fecha"),
                    rs.getString("Numero Ticket"),
                    rs.getString("Estado"),
                    rs.getTime("Hora Emision"),
                    rs.getTime("Hora Atencion"),
                    rs.getString("Dni Cliente"),
                    rs.getString("Codigo Servicio"),
                    rs.getString("Codigo Trabajador") 
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
    
    private void crearTicket() {
        if (validarCamposCreacion()) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_RegistrarTicket(?, ?, ?)}");
                
                stmt.setString(1, txtCodCliente.getText().trim());
                stmt.setString(2, "Cliente"); 
                stmt.setString(3, txtCodServicio.getText().trim());
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    String numTicket = rs.getString("NumeroTicket");
                    java.sql.Date fechaTicket = rs.getDate("FechaTicket");
                    
                    JOptionPane.showMessageDialog(this, 
                        "Ticket creado exitosamente\n" +
                        "Numero: " + numTicket + "\n" +
                        "Fecha: " + fechaTicket.toString(), 
                        "Ticket Creado", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                rs.close();
                stmt.close();
                conn.close();
                
                cargarDatos();
                limpiarCampos();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear ticket: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void atenderTicket() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket para atender");
            return;
        }
        
        int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
        
        String estado = modeloTabla.getValueAt(filaModelo, 2).toString();
        if (!"Pendiente".equals(estado)) {
            JOptionPane.showMessageDialog(this, 
                "Solo se pueden atender tickets pendientes", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String fechaStr = modeloTabla.getValueAt(filaModelo, 0).toString();
        String numTicket = modeloTabla.getValueAt(filaModelo, 1).toString();
        
        String trabajadorFinal = null;
        if ("admin".equals(usuarioActual) || "ADMINISTRADOR".equals(ventanaOrigen)) {
            trabajadorFinal = solicitarTrabajadorParaAdmin();
            if (trabajadorFinal == null) {
                return; 
            }
        } else {
            trabajadorFinal = obtenerIdTrabajadorDelUsuario(usuarioActual);
            if (trabajadorFinal == null) {
                JOptionPane.showMessageDialog(this, 
                    "Error: No se pudo encontrar el ID de trabajador para el usuario '" + usuarioActual + "'.\n" +
                    "Por favor, contacte al administrador.", 
                    "Error de Usuario", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("DEBUG - Trabajador identificado: '" + usuarioActual + "' -> ID: '" + trabajadorFinal + "'");
        }
        
        System.out.println("DEBUG - Verificando trabajador antes de atender ticket...");
        if (!verificarTrabajadorExiste(trabajadorFinal)) {
            JOptionPane.showMessageDialog(this, 
                "Error: El trabajador seleccionado '" + trabajadorFinal + "' no existe o no está activo.\n" +
                "Por favor, verifique la base de datos.", 
                "Error de Trabajador", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String mensajeConfirmacion;
        if ("admin".equals(usuarioActual) || "ADMINISTRADOR".equals(ventanaOrigen)) {
            mensajeConfirmacion = "¿Confirma que desea atender el ticket " + numTicket + " del " + fechaStr + "?\n" +
                                "Se asignará automáticamente como trabajador: " + trabajadorFinal;
        } else {
            mensajeConfirmacion = "¿Confirma que desea atender el ticket " + numTicket + " del " + fechaStr + "?\n" +
                                "Se registrará que usted (" + usuarioActual + ") atendió este ticket.";
        }
        
        int opcion = JOptionPane.showConfirmDialog(this, 
            mensajeConfirmacion, 
            "Confirmar Atencion", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_AtenderTicket(?, ?, ?)}");
                
                Date fechaSQL = Date.valueOf(fechaStr);
                stmt.setDate(1, fechaSQL);
                stmt.setString(2, numTicket);
                stmt.setString(3, trabajadorFinal); 
                
                System.out.println("DEBUG - Parámetros del procedimiento:");
                System.out.println("Fecha: " + fechaSQL);
                System.out.println("Número Ticket: " + numTicket);
                System.out.println("ID Trabajador: " + trabajadorFinal);
                System.out.println("Usuario Original: " + usuarioActual);
                System.out.println("Ventana Origen: " + ventanaOrigen);
                
                ResultSet rs = stmt.executeQuery();
                
                String mensajeResultado = "";
                if (rs.next()) {
                    mensajeResultado = rs.getString("Mensaje");
                    System.out.println("DEBUG - Mensaje del procedimiento: " + mensajeResultado);
                }
                
                rs.close();
                stmt.close();
                conn.close();
                
                if (mensajeResultado.toLowerCase().contains("error")) {
                    JOptionPane.showMessageDialog(this, 
                        mensajeResultado, 
                        "Error al Atender Ticket", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        mensajeResultado + "\n" +
                        "Trabajador asignado: " + trabajadorFinal + "\n" +
                        "Hora de atención: " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()), 
                        "Ticket Atendido", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                cargarDatos(); 
                limpiarCampos();
                
            } catch (Exception e) {
                System.out.println("DEBUG - Error en Java: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error al atender ticket: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarTicket() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket para eliminar");
            return;
        }
        
        int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
        
        String fecha = modeloTabla.getValueAt(filaModelo, 0).toString();
        String numero = modeloTabla.getValueAt(filaModelo, 1).toString();
        String estado = modeloTabla.getValueAt(filaModelo, 2).toString();
        
        if (!"Pendiente".equals(estado)) {
            JOptionPane.showMessageDialog(this, 
                "Solo se pueden eliminar tickets pendientes.\nEste ticket ya fue atendido.", 
                "Operación no permitida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar el ticket " + numero + " del " + fecha + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_CRUD_EliminarTicket(?, ?)}");
                
                Date fechaSQL = Date.valueOf(fecha);
                stmt.setDate(1, fechaSQL);
                stmt.setString(2, numero);
                
                stmt.execute();
                
                stmt.close();
                conn.close();
                
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Ticket eliminado exitosamente");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar ticket: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);
                
                String fechaStr = modeloTabla.getValueAt(filaModelo, 0).toString();
                Date fecha = Date.valueOf(fechaStr);
                spnFecha.setValue(new java.util.Date(fecha.getTime()));
                
                txtNumTicket.setText(modeloTabla.getValueAt(filaModelo, 1).toString());
                
                String horaEmisionStr = modeloTabla.getValueAt(filaModelo, 3).toString();
                if (horaEmisionStr != null && !horaEmisionStr.isEmpty()) {
                    Time horaEmision = Time.valueOf(horaEmisionStr);
                    spnHoraEmision.setValue(new java.util.Date(horaEmision.getTime()));
                }
                
                Object horaAtencionObj = modeloTabla.getValueAt(filaModelo, 4);
                if (horaAtencionObj != null && !horaAtencionObj.toString().isEmpty()) {
                    Time horaAtencion = Time.valueOf(horaAtencionObj.toString());
                    spnHoraAtencion.setValue(new java.util.Date(horaAtencion.getTime()));
                }
                
                txtCodCliente.setText(modeloTabla.getValueAt(filaModelo, 5).toString());
                txtCodServicio.setText(modeloTabla.getValueAt(filaModelo, 6).toString());
                
                Object trabajadorObj = modeloTabla.getValueAt(filaModelo, 7);
                txtIdTrabajador.setText(trabajadorObj != null ? trabajadorObj.toString() : "");
                
                String estado = modeloTabla.getValueAt(filaModelo, 2).toString();
                btnAtender.setEnabled("Pendiente".equals(estado));
                btnEliminar.setEnabled("Pendiente".equals(estado));
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar datos seleccionados: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String solicitarTrabajadorParaAdmin() {
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            ResultSet rs = stmt.executeQuery();
            
            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("DEBUG - Columnas disponibles en consulta directa Trabajador:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("  Columna " + i + ": " + metaData.getColumnName(i) + " (Tipo: " + metaData.getColumnTypeName(i) + ")");
            }
            
            java.util.List<String> trabajadores = new java.util.ArrayList<>();
            java.util.Map<String, String> mapTrabajadores = new java.util.HashMap<>();
            
            while (rs.next()) {
                System.out.println("DEBUG - Fila de trabajador:");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i);
                    System.out.println("  " + columnName + ": '" + value + "'");
                }
                
                String id = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                
                boolean estadoTrabajando = rs.getBoolean("Trabajando");
                if (estadoTrabajando) {
                    System.out.println("DEBUG - Valores extraídos:");
                    System.out.println("  ID (Codigo): '" + id + "'");
                    System.out.println("  Nombre: '" + nombre + "'");
                    
                    String displayText = nombre + " (" + id + ")";
                    trabajadores.add(displayText);
                    mapTrabajadores.put(displayText, id);
                    System.out.println("  Agregado a la lista de selección");
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            if (trabajadores.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay trabajadores activos disponibles", 
                    "Sin Trabajadores", 
                    JOptionPane.WARNING_MESSAGE);
                return null;
            }
            
            String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Como administrador, seleccione el trabajador que atenderá el ticket:",
                "Seleccionar Trabajador",
                JOptionPane.QUESTION_MESSAGE,
                null,
                trabajadores.toArray(),
                trabajadores.get(0)
            );
            
            if (seleccion != null) {
                String trabajadorSeleccionado = mapTrabajadores.get(seleccion);
                System.out.println("DEBUG - Trabajador seleccionado:");
                System.out.println("  Texto mostrado: '" + seleccion + "'");
                System.out.println("  ID a enviar: '" + trabajadorSeleccionado + "'");
                return trabajadorSeleccionado;
            }
            
        } catch (Exception e) {
            System.out.println("DEBUG - Error al cargar trabajadores: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar trabajadores: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }

    private boolean validarCamposCreacion() {
        if (txtCodCliente.getText().trim().isEmpty() || 
            txtCodServicio.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Los campos DNI Cliente y Servicio son obligatorios", 
                "Campos Vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String dni = txtCodCliente.getText().trim();
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe tener exactamente 8 dígitos numéricos", 
                "DNI Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        txtNumTicket.setText("");
        txtCodCliente.setText("");
        txtCodServicio.setText("");
        txtIdTrabajador.setText("");
        spnFecha.setValue(new java.util.Date());
        spnHoraEmision.setValue(new java.util.Date());
        spnHoraAtencion.setValue(new java.util.Date());
        
        btnAtender.setEnabled(true);
        btnEliminar.setEnabled(true);
        
        tabla.clearSelection();
    }
    
    private boolean verificarTrabajadorExiste(String idTrabajador) {
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            ResultSet rs = stmt.executeQuery();
            
            boolean esValido = false;
            while (rs.next()) {
                String id = rs.getString("Codigo");
                boolean estadoTrabajando = rs.getBoolean("Trabajando");
                
                if (idTrabajador.equals(id) && estadoTrabajando) {
                    String nombre = rs.getString("Nombre Trabajador");
                    System.out.println("DEBUG - Verificación de trabajador:");
                    System.out.println("  ID: '" + idTrabajador + "'");
                    System.out.println("  Existe y activo: true");
                    System.out.println("  Nombre: '" + nombre + "'");
                    esValido = true;
                    break;
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return esValido;
            
        } catch (Exception e) {
            System.out.println("DEBUG - Error al verificar trabajador: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private String obtenerIdTrabajadorDelUsuario(String usuarioLogin) {
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            ResultSet rs = stmt.executeQuery();
            
            String idTrabajador = null;
            while (rs.next()) {
                String id = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                boolean estadoTrabajando = rs.getBoolean("Trabajando");
                
                if (estadoTrabajando && (usuarioLogin.equals(nombre) || usuarioLogin.equals(id))) {
                    idTrabajador = id;
                    System.out.println("DEBUG - Trabajador encontrado para usuario '" + usuarioLogin + "':");
                    System.out.println("  ID Trabajador: '" + idTrabajador + "'");
                    System.out.println("  Nombre: '" + nombre + "'");
                    break;
                }
            }
            
            if (idTrabajador == null) {
                System.out.println("DEBUG - No se encontró trabajador para usuario: '" + usuarioLogin + "'");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return idTrabajador;
            
        } catch (Exception e) {
            System.out.println("DEBUG - Error al obtener ID de trabajador: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
