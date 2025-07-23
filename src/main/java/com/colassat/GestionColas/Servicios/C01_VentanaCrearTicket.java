package com.colassat.GestionColas.Servicios;

import com.colassat.GestionColas.BaseDeDatos.ConexionBD;
import com.colassat.GestionColas.Entidades.Servicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class C01_VentanaCrearTicket extends JFrame {
    private JLabel lblNombreUsuario;
    private JLabel lblDNIUsuario;
    private JComboBox<String> cmbServicios;
    private JButton btnGenerarTicket;
    private JButton btnVerHistorial;
    private JButton btnVolver;
    private String nombreUsuario;
    private String dniUsuario;
    private List<Servicio> serviciosDisponibles;
    
    public C01_VentanaCrearTicket(String nombre, String dni) {
        this.nombreUsuario = nombre;
        this.dniUsuario = dni;
        this.serviciosDisponibles = new ArrayList<>();
        initComponents();
        cargarServicios();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Generar Ticket - Sistema de Gestión de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800); 
        setResizable(true);
        
        lblNombreUsuario = new JLabel("Usuario: " + nombreUsuario);
        lblDNIUsuario = new JLabel("DNI: " + dniUsuario);
        
        lblNombreUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        lblDNIUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombreUsuario.setForeground(new Color(52, 58, 64));
        lblDNIUsuario.setForeground(new Color(52, 58, 64));
        
        cmbServicios = new JComboBox<>();
        cmbServicios.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbServicios.setPreferredSize(new Dimension(300, 35));
        cmbServicios.addItem("Seleccione un servicio...");
        
        btnGenerarTicket = new JButton("GENERAR TICKET");
        btnVerHistorial = new JButton("VER HISTORIAL");
        btnVolver = new JButton("VOLVER");
        
        Font btnFont = new Font("Arial", Font.BOLD, 14);
        
        btnGenerarTicket.setFont(btnFont);
        btnGenerarTicket.setBackground(new Color(40, 167, 69));
        btnGenerarTicket.setForeground(Color.WHITE);
        btnGenerarTicket.setPreferredSize(new Dimension(200, 45));
        btnGenerarTicket.setFocusPainted(false);
        btnGenerarTicket.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnVerHistorial.setFont(new Font("Arial", Font.BOLD, 12));
        btnVerHistorial.setBackground(new Color(0, 123, 255));
        btnVerHistorial.setForeground(Color.WHITE);
        btnVerHistorial.setPreferredSize(new Dimension(150, 40));
        btnVerHistorial.setFocusPainted(false);
        btnVerHistorial.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setBackground(new Color(108, 117, 125));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setPreferredSize(new Dimension(120, 40));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(new Color(248, 249, 250));
        JLabel lblTitulo = new JLabel("GENERAR NUEVO TICKET");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(40, 167, 69));
        panelTitulo.add(lblTitulo);
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        JPanel panelUsuario = new JPanel(new GridBagLayout());
        panelUsuario.setBackground(new Color(233, 236, 239));
        panelUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 58, 64)), 
                "Información del Usuario",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(52, 58, 64)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbcUsuario = new GridBagConstraints();
        gbcUsuario.insets = new Insets(8, 8, 8, 8);
        gbcUsuario.anchor = GridBagConstraints.WEST;
        
        gbcUsuario.gridx = 0; gbcUsuario.gridy = 0;
        panelUsuario.add(lblNombreUsuario, gbcUsuario);
        gbcUsuario.gridy = 1;
        panelUsuario.add(lblDNIUsuario, gbcUsuario);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(panelUsuario, gbc);
        
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(206, 212, 218));
        gbc.gridy = 1; gbc.insets = new Insets(20, 15, 20, 15);
        panelFormulario.add(separador, gbc);
        
        gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(15, 15, 15, 15);
        
        JLabel lblServicio = new JLabel("Tipo de Servicio:");
        lblServicio.setFont(new Font("Arial", Font.BOLD, 16));
        lblServicio.setForeground(new Color(52, 58, 64));
        panelFormulario.add(lblServicio, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(cmbServicios, gbc);
        
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(new Color(217, 237, 247));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(179, 229, 252)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblInfo = new JLabel("<html><b>Información:</b><br>" +
            "• El ticket se generará automáticamente con la fecha y hora actual<br>" +
            "• Recibirá un número único de ticket<br>" +
            "• El ticket quedará en estado 'Pendiente' hasta ser atendido</html>");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(52, 58, 64));
        panelInfo.add(lblInfo, BorderLayout.CENTER);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 15, 15, 15);
        panelFormulario.add(panelInfo, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnGenerarTicket);
        panelBotones.add(btnVerHistorial);
        panelBotones.add(btnVolver);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 15, 15, 15);
        panelFormulario.add(panelBotones, gbc);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnGenerarTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTicket();
            }
        });
        
        btnVerHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorialTickets();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
    }
    
    private void cargarServicios() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Connection conn = ConexionBD.getConexion();
                    CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarServicio}");
                    ResultSet rs = stmt.executeQuery();
                    
                    while (rs.next()) {
                        String codigo = rs.getString("Codigo");
                        String nombre = rs.getString("Nombre Servicio");
                        boolean estado = rs.getBoolean("Estado Activo");
                        
                        if (estado) {
                            Servicio servicio = new Servicio(codigo, nombre, estado);
                            serviciosDisponibles.add(servicio);
                        }
                    }
                    
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(C01_VentanaCrearTicket.this,
                            "Error al cargar servicios: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
                return null;
            }
            
            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    for (Servicio servicio : serviciosDisponibles) {
                        cmbServicios.addItem(servicio.getNomServ() + " (" + servicio.getCodServ() + ")");
                    }
                });
            }
        };
        
        worker.execute();
    }
    
    private String obtenerCodigoServicioSeleccionado() {
        String seleccion = (String) cmbServicios.getSelectedItem();
        if (seleccion == null || seleccion.equals("Seleccione un servicio...")) {
            return null;
        }
        
        int inicioParentesis = seleccion.lastIndexOf('(');
        int finParentesis = seleccion.lastIndexOf(')');
        
        if (inicioParentesis != -1 && finParentesis != -1) {
            return seleccion.substring(inicioParentesis + 1, finParentesis);
        }
        
        return null;
    }
    
    private void generarTicket() {
        if (!validarSeleccionServicio()) {
            return;
        }
        
        String servicioSeleccionado = (String) cmbServicios.getSelectedItem();
        String codigoServicio = obtenerCodigoServicioSeleccionado();
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Confirma que desea generar un ticket con los siguientes datos?\n\n" +
            "Usuario: " + nombreUsuario + "\n" +
            "DNI: " + dniUsuario + "\n" +
            "Servicio: " + servicioSeleccionado + "\n\n" +
            "El ticket se generará con fecha y hora actual.",
            "Confirmar Generación de Ticket",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        btnGenerarTicket.setEnabled(false);
        btnGenerarTicket.setText("Generando...");
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_RegistrarTicket(?, ?, ?)}");
            
            stmt.setString(1, dniUsuario);
            stmt.setString(2, nombreUsuario);
            stmt.setString(3, codigoServicio);
            //stmt.setString(4, idTrabajaor);
            
            ResultSet rs = stmt.executeQuery();
            
            String numeroTicketGenerado = "";
            String fechaTicket = "";
            
            if (rs.next()) {
                numeroTicketGenerado = rs.getString("NumeroTicket");
                fechaTicket = rs.getString("FechaTicket");
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            Date ahora = new Date();
            
            String mensaje = String.format(
                "✓ TICKET GENERADO EXITOSAMENTE\n\n" +
                "══════════════════════════════════\n" +
                "   NÚMERO DE TICKET: %s\n" +
                "══════════════════════════════════\n\n" +
                "Fecha: %s\n" +
                "Hora: %s\n" +
                "Usuario: %s\n" +
                "DNI: %s\n" +
                "Servicio: %s\n\n" +
                "IMPORTANTE:\n" +
                "• Conserve este número para ser atendido\n" +
                "• El ticket queda en estado 'Pendiente'\n" +
                "• Diríjase al área correspondiente",
                numeroTicketGenerado,
                fechaTicket,
                formatoHora.format(ahora),
                nombreUsuario,
                dniUsuario,
                servicioSeleccionado
            );
            
            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Ticket Generado Exitosamente", 
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new A03_VentanaUsuarioDNI().setVisible(true);
            
        } catch (SQLException ex) {
            btnGenerarTicket.setEnabled(true);
            btnGenerarTicket.setText("GENERAR TICKET");
            
            String mensajeError = ex.getMessage();
            if (mensajeError.contains("ya existe")) {
                JOptionPane.showMessageDialog(this, 
                    "Error: El número de ticket ya existe.\n" +
                    "Por favor, intente nuevamente en unos momentos.", 
                    "Error de Duplicación", 
                    JOptionPane.ERROR_MESSAGE);
            } else if (mensajeError.contains("Cliente")) {
                JOptionPane.showMessageDialog(this, 
                    "Error: El cliente con DNI " + dniUsuario + " no está registrado.\n" +
                    "Por favor, verifique su registro.", 
                    "Error de Cliente", 
                    JOptionPane.ERROR_MESSAGE);
            } else if (mensajeError.contains("Servicio")) {
                JOptionPane.showMessageDialog(this, 
                    "Error: El servicio seleccionado no está disponible.\n" +
                    "Por favor, seleccione otro servicio.", 
                    "Error de Servicio", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al generar el ticket:\n" + mensajeError + 
                    "\n\nPor favor, contacte al administrador si el problema persiste.", 
                    "Error del Sistema", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validarSeleccionServicio() {
        String servicioSeleccionado = (String) cmbServicios.getSelectedItem();
        
        if (servicioSeleccionado == null || servicioSeleccionado.equals("Seleccione un servicio...")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un servicio válido de la lista.", 
                "Servicio No Seleccionado", 
                JOptionPane.WARNING_MESSAGE);
            cmbServicios.requestFocus();
            return false;
        }
        
        String codigoServicio = obtenerCodigoServicioSeleccionado();
        if (codigoServicio == null || codigoServicio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar el servicio seleccionado.\n" +
                "Por favor, seleccione otro servicio.", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void volver() {
        dispose();
        new A01_VentanaInicio().setVisible(true);
    }
    
    private void verHistorialTickets() {
        JDialog dialogHistorial = new JDialog(this, "Historial de Tickets - " + nombreUsuario, true);
        dialogHistorial.setSize(900, 600);
        dialogHistorial.setLocationRelativeTo(this);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(new Color(248, 249, 250));
        JLabel lblTitulo = new JLabel("HISTORIAL DE TICKETS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 123, 255));
        panelTitulo.add(lblTitulo);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBackground(new Color(233, 236, 239));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel lblInfoUsuario = new JLabel("Usuario: " + nombreUsuario + " | DNI: " + dniUsuario);
        lblInfoUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfoUsuario.setForeground(new Color(52, 58, 64));
        panelInfo.add(lblInfoUsuario);
        
        String[] columnas = {"Número Ticket", "Fecha Creación", "Servicio", "Estado", "Trabajador", "Fecha Atención"};
        Object[][] datos = cargarHistorialTickets();
        
        JTable tablaHistorial = new JTable(datos, columnas);
        tablaHistorial.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaHistorial.setRowHeight(25);
        tablaHistorial.setSelectionBackground(new Color(179, 229, 252));
        tablaHistorial.setGridColor(new Color(206, 212, 218));
        tablaHistorial.setShowGrid(true);
        
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(120); 
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(140);
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(150); 
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(100); 
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(120); 
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(140); 
        
        tablaHistorial.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaHistorial.getTableHeader().setBackground(new Color(52, 58, 64));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218)));
        
        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEstadisticas.setBackground(new Color(248, 249, 250));
        
        int totalTickets = datos.length;
        int ticketsPendientes = 0;
        int ticketsAtendidos = 0;
        
        for (Object[] fila : datos) {
            String estado = (String) fila[3];
            if ("PENDIENTE".equals(estado)) {
                ticketsPendientes++;
            } else if ("ATENDIDO".equals(estado)) {
                ticketsAtendidos++;
            }
        }
        
        JLabel lblEstadisticas = new JLabel(String.format(
            "Total: %d tickets | Pendientes: %d | Atendidos: %d", 
            totalTickets, ticketsPendientes, ticketsAtendidos));
        lblEstadisticas.setFont(new Font("Arial", Font.BOLD, 12));
        lblEstadisticas.setForeground(new Color(52, 58, 64));
        panelEstadisticas.add(lblEstadisticas);
        
        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(108, 117, 125));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnCerrar.addActionListener(e -> dialogHistorial.dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(248, 249, 250));
        panelBoton.add(btnCerrar);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(new Color(248, 249, 250));
        panelCentro.add(panelInfo, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelCentro.add(panelEstadisticas, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        
        dialogHistorial.add(panelPrincipal);
        dialogHistorial.setVisible(true);
    }
    
    private Object[][] cargarHistorialTickets() {
        List<Object[]> tickets = new ArrayList<>();
        
        try {
            Connection conn = ConexionBD.getConexion();
            CallableStatement stmt = conn.prepareCall("{call PA_CRUD_ListarHistorialTicketCliente(?)}");
            stmt.setString(1, dniUsuario);
            
            System.out.println("DEBUG - Consultando historial para cliente: " + dniUsuario);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getString("Numero_Ticket");
                fila[1] = rs.getString("Fecha_Creacion");
                fila[2] = rs.getString("Nombre_Servicio");
                fila[3] = rs.getString("Estado");
                fila[4] = rs.getString("Nombre_Trabajador");
                fila[5] = rs.getString("Fecha_Atencion");
                
                tickets.add(fila);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("DEBUG - Tickets encontrados: " + tickets.size());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("ERROR - Al cargar historial de tickets: " + ex.getMessage());
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Error al cargar el historial de tickets:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
        
        if (tickets.isEmpty()) {
            return new Object[][]{
                {"Sin tickets", "No hay tickets registrados", "", "", "", ""}
            };
        }
        
        return tickets.toArray(new Object[0][]);
    }
}
