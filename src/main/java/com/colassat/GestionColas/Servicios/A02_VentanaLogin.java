package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class A02_VentanaLogin extends JFrame {
    private JTextField txtNombre;
    private JPasswordField txtPassword;
    private JButton btnIniciarSesion;
    private JButton btnVolver;
    private String tipoUsuario;
    
    public A02_VentanaLogin() {
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Login - Sistema de Gestión de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setSize(500, 400); 
        
        txtNombre = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        txtNombre.setEditable(true);
        txtPassword.setEditable(true);
        
        btnIniciarSesion = new JButton("INICIAR SESIÓN");
        btnVolver = new JButton("VOLVER");
        
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Font btnFont = new Font("Arial", Font.BOLD, 12);
        
        txtNombre.setFont(fieldFont);
        txtPassword.setFont(fieldFont);
        
        btnIniciarSesion.setFont(btnFont);
        btnIniciarSesion.setBackground(new Color(0, 123, 255));
        btnIniciarSesion.setForeground(Color.BLACK);
        btnIniciarSesion.setPreferredSize(new Dimension(150, 35));
        btnIniciarSesion.setFocusPainted(false);
        
        btnVolver.setFont(btnFont);
        btnVolver.setBackground(new Color(108, 117, 125));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setPreferredSize(new Dimension(100, 35));
        btnVolver.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtNombre, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnVolver);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
        
        txtNombre.addActionListener(e -> txtPassword.requestFocus());
        txtPassword.addActionListener(e -> iniciarSesion());
    }
    
    private void iniciarSesion() {
        String nombre = txtNombre.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (nombre.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese usuario y contraseña.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if ("Administrador".equals(tipoUsuario)) {
            if (A04_AutenticacionTrabajador.autenticacionSistema(nombre, password)) {
                if (A04_AutenticacionTrabajador.esAdministradorPorNombre(nombre) || "admin".equals(nombre)) {
                    dispose();
                    new D01_VentanaMenuAdministrador().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No tiene permisos de administrador.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if ("Trabajador".equals(tipoUsuario)) {
            if (A04_AutenticacionTrabajador.autenticar(nombre, password)) {
                String idTrabajador = A04_AutenticacionTrabajador.obtenerIdTrabajador(nombre);
                if (idTrabajador != null) {
                    dispose();
                    new B03_PanelTrabajador(nombre).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al obtener información del trabajador.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuario o contraseña incorrectos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void volver() {
        dispose();
        new A01_VentanaInicio().setVisible(true);
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        setTitle("Login " + tipoUsuario + " - Sistema de Gestión de Colas SAT");
    }
}
