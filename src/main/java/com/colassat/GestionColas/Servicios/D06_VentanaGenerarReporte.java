package com.colassat.GestionColas.Servicios;

import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class D06_VentanaGenerarReporte extends JFrame {
    private JButton btnGenerarReporte;
    private JButton btnVolver;
    private JTextArea txtAreaInfo;
    private JProgressBar progressBar;
    private JLabel lblEstado;
    private JCheckBox chkClientes;
    private JCheckBox chkTrabajadores;
    private JCheckBox chkServicios;
    private JCheckBox chkTickets;
    private JSpinner spnFechaDesde;
    private JSpinner spnFechaHasta;
    private JLabel lblFechaDesde;
    private JLabel lblFechaHasta;
    
    public D06_VentanaGenerarReporte() {
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Generar Reporte - Sistema de Gestion de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(700, 650);
        
        btnGenerarReporte = new JButton("GENERAR REPORTE");
        btnVolver = new JButton("VOLVER");
        
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        Dimension btnSize = new Dimension(250, 50);
        
        Color btnColor = new Color(40, 167, 69);
        Color volverColor = new Color(108, 117, 125);
        
        btnGenerarReporte.setFont(btnFont);
        btnGenerarReporte.setBackground(btnColor);
        btnGenerarReporte.setForeground(Color.WHITE);
        btnGenerarReporte.setPreferredSize(btnSize);
        btnGenerarReporte.setFocusPainted(false);
        
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setBackground(volverColor);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setPreferredSize(new Dimension(100, 35));
        btnVolver.setFocusPainted(false);
        
        chkClientes = new JCheckBox("Incluir Clientes", true);
        chkTrabajadores = new JCheckBox("Incluir Trabajadores", true);
        chkServicios = new JCheckBox("Incluir Servicios", true);
        chkTickets = new JCheckBox("Incluir Tickets", true);
        
        Font checkBoxFont = new Font("Arial", Font.PLAIN, 12);
        chkClientes.setFont(checkBoxFont);
        chkTrabajadores.setFont(checkBoxFont);
        chkServicios.setFont(checkBoxFont);
        chkTickets.setFont(checkBoxFont);
        
        lblFechaDesde = new JLabel("Fecha Desde:");
        lblFechaHasta = new JLabel("Fecha Hasta:");
        lblFechaDesde.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFechaHasta.setFont(new Font("Arial", Font.PLAIN, 12));
        
        SpinnerDateModel modelDesde = new SpinnerDateModel();
        SpinnerDateModel modelHasta = new SpinnerDateModel();
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        modelHasta.setValue(cal.getTime()); 
        cal.add(java.util.Calendar.MONTH, -1);
        modelDesde.setValue(cal.getTime()); 
        
        spnFechaDesde = new JSpinner(modelDesde);
        spnFechaHasta = new JSpinner(modelHasta);
        
        JSpinner.DateEditor editorDesde = new JSpinner.DateEditor(spnFechaDesde, "dd/MM/yyyy");
        JSpinner.DateEditor editorHasta = new JSpinner.DateEditor(spnFechaHasta, "dd/MM/yyyy");
        spnFechaDesde.setEditor(editorDesde);
        spnFechaHasta.setEditor(editorHasta);
        
        txtAreaInfo = new JTextArea();
        txtAreaInfo.setEditable(false);
        txtAreaInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtAreaInfo.setBackground(new Color(248, 249, 250));
        txtAreaInfo.setText("Seleccione las secciones que desea incluir en el reporte:\n\n" +
                           "• Clientes: Lista completa de clientes registrados\n" +
                           "• Trabajadores: Lista de trabajadores con su estado\n" +
                           "• Servicios: Lista de servicios disponibles\n" +
                           "• Tickets: Lista detallada de tickets (filtrable por fechas)\n\n" +
                           "Para los tickets, puede especificar un rango de fechas para filtrar los resultados.\n\n" +
                           "El archivo se guardará en la carpeta del proyecto con la fecha y hora actual.");
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        
        lblEstado = new JLabel("Listo para generar reporte");
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEstado.setForeground(new Color(108, 117, 125));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("GENERAR REPORTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 10, 5));
        panelSeleccion.setBorder(BorderFactory.createTitledBorder("Seleccionar Secciones"));
        panelSeleccion.setBackground(new Color(248, 249, 250));
        panelSeleccion.add(chkClientes);
        panelSeleccion.add(chkTrabajadores);
        panelSeleccion.add(chkServicios);
        panelSeleccion.add(chkTickets);
        
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        panelPrincipal.add(panelSeleccion, gbc);
        
        JPanel panelFechas = new JPanel(new GridBagLayout());
        panelFechas.setBorder(BorderFactory.createTitledBorder("Filtro de Fechas para Tickets"));
        panelFechas.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbcFecha = new GridBagConstraints();
        gbcFecha.insets = new Insets(5, 5, 5, 5);
        
        gbcFecha.gridx = 0; gbcFecha.gridy = 0;
        gbcFecha.anchor = GridBagConstraints.WEST;
        panelFechas.add(lblFechaDesde, gbcFecha);
        
        gbcFecha.gridx = 1;
        gbcFecha.fill = GridBagConstraints.HORIZONTAL;
        gbcFecha.weightx = 1.0;
        panelFechas.add(spnFechaDesde, gbcFecha);
        
        gbcFecha.gridx = 2; gbcFecha.weightx = 0;
        gbcFecha.fill = GridBagConstraints.NONE;
        panelFechas.add(lblFechaHasta, gbcFecha);
        
        gbcFecha.gridx = 3;
        gbcFecha.fill = GridBagConstraints.HORIZONTAL;
        gbcFecha.weightx = 1.0;
        panelFechas.add(spnFechaHasta, gbcFecha);
        
        gbc.gridy = 2;
        panelPrincipal.add(panelFechas, gbc);
        
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane scrollPane = new JScrollPane(txtAreaInfo);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Información del Reporte"));
        scrollPane.setPreferredSize(new Dimension(500, 150));
        panelPrincipal.add(scrollPane, gbc);
        
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panelPrincipal.add(progressBar, gbc);
        
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblEstado, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnGenerarReporte);
        panelBotones.add(btnVolver);
        
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
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
    
    private void generarReporte() {
        if (!chkClientes.isSelected() && !chkTrabajadores.isSelected() && 
            !chkServicios.isSelected() && !chkTickets.isSelected()) {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar al menos una sección para generar el reporte.",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (chkTickets.isSelected()) {
            java.util.Date fechaDesde = (java.util.Date) spnFechaDesde.getValue();
            java.util.Date fechaHasta = (java.util.Date) spnFechaHasta.getValue();
            
            if (fechaDesde.after(fechaHasta)) {
                JOptionPane.showMessageDialog(this,
                    "La fecha 'Desde' no puede ser mayor que la fecha 'Hasta'.",
                    "Error en fechas",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    publish("Preparando generación de reporte...");
                    progressBar.setVisible(true);
                    progressBar.setValue(0);
                    btnGenerarReporte.setEnabled(false);
                    
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                    String fileName = "Reporte_GestionColas_" + timestamp + ".txt";
                    // Ruta del proyecto (ajustar según tu entorno), el file.separator es el backslash \
                    String projectPath = "C:" + File.separator + "Users" + File.separator + "?" + File.separator + 
                                        "OneDrive" + File.separator + "Escritorio" + File.separator + 
                                        "Proyecto LP - GestionColas SAT" + File.separator + fileName;
                    
                    PrintWriter writer = new PrintWriter(new FileWriter(projectPath));
                    
                    writer.println("===============================================================================");
                    writer.println("                REPORTE COMPLETO - SISTEMA DE GESTIÓN DE COLAS SAT");
                    writer.println("===============================================================================");
                    writer.println("Fecha de generación: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                    
                    writer.println("Secciones incluidas: " + 
                        (chkClientes.isSelected() ? "Clientes " : "") +
                        (chkTrabajadores.isSelected() ? "Trabajadores " : "") +
                        (chkServicios.isSelected() ? "Servicios " : "") +
                        (chkTickets.isSelected() ? "Tickets " : ""));
                    
                    if (chkTickets.isSelected()) {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        writer.println("Filtro de fechas para tickets: " + 
                            sdf.format(spnFechaDesde.getValue()) + " - " + 
                            sdf.format(spnFechaHasta.getValue()));
                    }
                    
                    writer.println("===============================================================================");
                    writer.println();
                    
                    int seccionesTotal = 0;
                    if (chkClientes.isSelected()) seccionesTotal++;
                    if (chkTrabajadores.isSelected()) seccionesTotal++;
                    if (chkServicios.isSelected()) seccionesTotal++;
                    if (chkTickets.isSelected()) seccionesTotal++;
                    
                    int seccionActual = 0;
                    
                    if (chkClientes.isSelected()) {
                        publish("Generando reporte de clientes...");
                        progressBar.setValue((++seccionActual * 100) / seccionesTotal / 2);
                        generarSeccionClientes(writer);
                    }
                    
                    if (chkTrabajadores.isSelected()) {
                        publish("Generando reporte de trabajadores...");
                        progressBar.setValue((++seccionActual * 100) / seccionesTotal / 2);
                        generarSeccionTrabajadores(writer);
                    }
                    
                    if (chkServicios.isSelected()) {
                        publish("Generando reporte de servicios...");
                        progressBar.setValue((++seccionActual * 100) / seccionesTotal / 2);
                        generarSeccionServicios(writer);
                    }
                    
                    if (chkTickets.isSelected()) {
                        publish("Generando reporte de tickets...");
                        progressBar.setValue((++seccionActual * 100) / seccionesTotal / 2);
                        generarSeccionTickets(writer);
                    }
                    
                    publish("Finalizando reporte...");
                    progressBar.setValue(100);
                    
                    writer.println();
                    writer.println("===============================================================================");
                    writer.println("                            FIN DEL REPORTE");
                    writer.println("===============================================================================");
                    
                    writer.close();
                    
                    publish("Reporte generado exitosamente en: " + projectPath);
                    
                    return true;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    publish("Error al generar reporte: " + e.getMessage());
                    return false;
                }
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String mensaje : chunks) {
                    lblEstado.setText(mensaje);
                }
            }
            
            @Override
            protected void done() {
                try {
                    Boolean exito = get();
                    btnGenerarReporte.setEnabled(true);
                    progressBar.setVisible(false);
                    
                    if (exito) {
                        JOptionPane.showMessageDialog(D06_VentanaGenerarReporte.this,
                            "Reporte generado exitosamente.\nEl archivo se ha guardado en la carpeta del proyecto.",
                            "Reporte Generado",
                            JOptionPane.INFORMATION_MESSAGE);
                        lblEstado.setText("Reporte generado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(D06_VentanaGenerarReporte.this,
                            "Error al generar el reporte.\nVerifique la consola para más detalles.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        lblEstado.setText("Error al generar reporte");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(D06_VentanaGenerarReporte.this,
                        "Error inesperado: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        worker.execute();
    }
    
    private void generarSeccionClientes(PrintWriter writer) throws Exception {
        writer.println("1. CLIENTES");
        writer.println("===============================================================================");
        writer.printf("%-15s %-50s%n", "CÓDIGO", "NOMBRE CLIENTE");
        writer.println("-------------------------------------------------------------------------------");
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarCliente}");
            ResultSet rs = stmt.executeQuery();
            
            int contador = 0;
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Cliente");
                
                writer.printf("%-15s %-50s%n", codigo, nombre);
                contador++;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            writer.println("-------------------------------------------------------------------------------");
            writer.println("Total de clientes: " + contador);
            writer.println();
            
        } catch (Exception e) {
            writer.println("Error al cargar datos de clientes: " + e.getMessage());
            writer.println();
        }
    }
    
    private void generarSeccionTrabajadores(PrintWriter writer) throws Exception {
        writer.println("2. TRABAJADORES");
        writer.println("===============================================================================");
        writer.printf("%-15s %-35s %-12s %-15s%n", "ID TRABAJADOR", "NOMBRE", "ESTADO", "ADMINISTRADOR");
        writer.println("-------------------------------------------------------------------------------");
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarTrabajador}");
            ResultSet rs = stmt.executeQuery();
            
            int contador = 0;
            while (rs.next()) {
                String id = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Trabajador");
                String estado = rs.getBoolean("Trabajando") ? "Activo" : "Inactivo";
                String admin = rs.getBoolean("Administrador") ? "Sí" : "No";
                
                writer.printf("%-15s %-35s %-12s %-15s%n", id, nombre, estado, admin);
                contador++;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            writer.println("-------------------------------------------------------------------------------");
            writer.println("Total de trabajadores: " + contador);
            writer.println();
            
        } catch (Exception e) {
            writer.println("Error al cargar datos de trabajadores: " + e.getMessage());
            writer.println();
        }
    }
    
    private void generarSeccionServicios(PrintWriter writer) throws Exception {
        writer.println("3. SERVICIOS");
        writer.println("===============================================================================");
        writer.printf("%-15s %-40s %-10s%n", "CÓDIGO", "NOMBRE SERVICIO", "ESTADO");
        writer.println("-------------------------------------------------------------------------------");
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarServicio}");
            ResultSet rs = stmt.executeQuery();
            
            int contador = 0;
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                String nombre = rs.getString("Nombre Servicio");
                String estado = rs.getBoolean("Estado Activo") ? "Activo" : "Inactivo";
                
                writer.printf("%-15s %-40s %-10s%n", codigo, nombre, estado);
                contador++;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            writer.println("-------------------------------------------------------------------------------");
            writer.println("Total de servicios: " + contador);
            writer.println();
            
        } catch (Exception e) {
            writer.println("Error al cargar datos de servicios: " + e.getMessage());
            writer.println();
        }
    }
    
    private void generarSeccionTickets(PrintWriter writer) throws Exception {
        writer.println("4. TICKETS");
        writer.println("===============================================================================");
        
        java.util.Date fechaDesde = (java.util.Date) spnFechaDesde.getValue();
        java.util.Date fechaHasta = (java.util.Date) spnFechaHasta.getValue();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        
        writer.println("Filtro aplicado: " + sdf.format(fechaDesde) + " - " + sdf.format(fechaHasta));
        writer.println("===============================================================================");
        writer.printf("%-12s %-6s %-12s %-8s %-8s %-10s %-15s %-15s %-15s %-10s %-15s%n", 
                     "FECHA", "NUM", "ESTADO", "H.EMIS", "H.ATEN", "DNI", "CLIENTE", "COD.SERV", "SERVICIO", "ID.TRAB", "TRABAJADOR");
        writer.println("-------------------------------------------------------------------------------");
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_DEBUG_ListarTicketsFiltrado(?, ?)}");
            stmt.setDate(1, new java.sql.Date(fechaDesde.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaHasta.getTime()));
            
            ResultSet rs = stmt.executeQuery();
            
            int contador = 0;
            int pendientes = 0;
            int atendidos = 0;
            
            while (rs.next()) {
                String fecha = rs.getDate("Fecha").toString();
                String numero = rs.getString("Numero");
                String estado = rs.getString("Estado");
                String horaEmision = rs.getTime("Hora Emision") != null ? rs.getTime("Hora Emision").toString().substring(0,5) : "";
                String horaAtencion = rs.getTime("Hora Atencion") != null ? rs.getTime("Hora Atencion").toString().substring(0,5) : "";
                String dniCliente = rs.getString("DNI Cliente");
                String nombreCliente = rs.getString("Nombre Cliente") != null ? rs.getString("Nombre Cliente") : "";
                String codServicio = rs.getString("Codigo Servicio");
                String nombreServicio = rs.getString("Nombre Servicio") != null ? rs.getString("Nombre Servicio") : "";
                String idTrabajador = rs.getString("ID Trabajador") != null ? rs.getString("ID Trabajador") : "";
                String nombreTrabajador = rs.getString("Nombre Trabajador") != null ? rs.getString("Nombre Trabajador") : "";
                
                if (nombreCliente.length() > 15) nombreCliente = nombreCliente.substring(0, 12) + "...";
                if (nombreServicio.length() > 15) nombreServicio = nombreServicio.substring(0, 12) + "...";
                if (nombreTrabajador.length() > 15) nombreTrabajador = nombreTrabajador.substring(0, 12) + "...";
                
                if ("Pendiente".equals(estado)) {
                    pendientes++;
                } else {
                    atendidos++;
                }
                
                writer.printf("%-12s %-6s %-12s %-8s %-8s %-10s %-15s %-15s %-15s %-10s %-15s%n", 
                             fecha, numero, estado, horaEmision, horaAtencion, dniCliente, 
                             nombreCliente, codServicio, nombreServicio, idTrabajador, nombreTrabajador);
                contador++;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            writer.println("-------------------------------------------------------------------------------");
            writer.println("Total de tickets en el rango: " + contador);
            writer.println("Tickets pendientes: " + pendientes);
            writer.println("Tickets atendidos: " + atendidos);
            writer.println();
            
        } catch (Exception e) {
            writer.println("Advertencia: No se pudo aplicar filtro de fechas, mostrando todos los tickets.");
            writer.println("Error: " + e.getMessage());
            writer.println();
            
            try {
                Connection conn = ConexionBD.getConexion();
                CallableStatement stmt = conn.prepareCall("{call PA_DEBUG_ListarTicketsDetallado}");
                ResultSet rs = stmt.executeQuery();
                
                int contador = 0;
                while (rs.next()) {
                    String fecha = rs.getDate("Fecha").toString();
                    String numero = rs.getString("Numero");
                    String estado = rs.getString("Estado");
                    
                    java.sql.Date fechaTicket = rs.getDate("Fecha");
                    if (fechaTicket.before(new java.sql.Date(fechaDesde.getTime())) || 
                        fechaTicket.after(new java.sql.Date(fechaHasta.getTime()))) {
                        continue;
                    }
                    
                    String horaEmision = rs.getTime("Hora Emision") != null ? rs.getTime("Hora Emision").toString().substring(0,5) : "";
                    String horaAtencion = rs.getTime("Hora Atencion") != null ? rs.getTime("Hora Atencion").toString().substring(0,5) : "";
                    String dniCliente = rs.getString("DNI Cliente");
                    String nombreCliente = rs.getString("Nombre Cliente") != null ? rs.getString("Nombre Cliente") : "";
                    String codServicio = rs.getString("Codigo Servicio");
                    String nombreServicio = rs.getString("Nombre Servicio") != null ? rs.getString("Nombre Servicio") : "";
                    String idTrabajador = rs.getString("ID Trabajador") != null ? rs.getString("ID Trabajador") : "";
                    String nombreTrabajador = rs.getString("Nombre Trabajador") != null ? rs.getString("Nombre Trabajador") : "";
                    
                    if (nombreCliente.length() > 15) nombreCliente = nombreCliente.substring(0, 12) + "...";
                    if (nombreServicio.length() > 15) nombreServicio = nombreServicio.substring(0, 12) + "...";
                    if (nombreTrabajador.length() > 15) nombreTrabajador = nombreTrabajador.substring(0, 12) + "...";
                    
                    writer.printf("%-12s %-6s %-12s %-8s %-8s %-10s %-15s %-15s %-15s %-10s %-15s%n", 
                                 fecha, numero, estado, horaEmision, horaAtencion, dniCliente, 
                                 nombreCliente, codServicio, nombreServicio, idTrabajador, nombreTrabajador);
                    contador++;
                }
                
                rs.close();
                stmt.close();
                conn.close();
                
                writer.println("-------------------------------------------------------------------------------");
                writer.println("Total de tickets filtrados: " + contador);
                writer.println();
                
            } catch (Exception e2) {
                writer.println("Error al cargar datos de tickets: " + e2.getMessage());
                writer.println();
            }
        }
    }
}
