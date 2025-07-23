package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.colassat.GestionColas.BaseDeDatos.ConexionBD;

public class A01_VentanaInicio extends JFrame {
    private JButton btnAdministrador;
    private JButton btnTrabajador;
    private JButton btnUsuario;
    
    public A01_VentanaInicio() {
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión de Colas SAT - Seleccionar Rol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(700, 600); 
        
        btnAdministrador = new JButton("ADMINISTRADOR");
        btnTrabajador = new JButton("TRABAJADOR");
        btnUsuario = new JButton("USUARIO");
        
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        Color btnColorAdmin = new Color(220, 53, 69);
        Color btnColorTrabajador = new Color(0, 123, 255);
        Color btnColorUsuario = new Color(40, 167, 69);
        
        btnAdministrador.setFont(btnFont);
        btnAdministrador.setBackground(btnColorAdmin);
        btnAdministrador.setForeground(Color.BLACK);
        btnAdministrador.setPreferredSize(new Dimension(200, 60));
        btnAdministrador.setFocusPainted(false);
        
        btnTrabajador.setFont(btnFont);
        btnTrabajador.setBackground(btnColorTrabajador);
        btnTrabajador.setForeground(Color.BLACK);
        btnTrabajador.setPreferredSize(new Dimension(200, 60));
        btnTrabajador.setFocusPainted(false);
        
        btnUsuario.setFont(btnFont);
        btnUsuario.setBackground(btnColorUsuario);
        btnUsuario.setForeground(Color.BLACK);
        btnUsuario.setPreferredSize(new Dimension(200, 60));
        btnUsuario.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE COLAS SAT");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Seleccione su tipo de acceso");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        gbc.gridy = 1;
        panelPrincipal.add(lblSubtitulo, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelPrincipal.add(btnAdministrador, gbc);
        
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelPrincipal.add(btnTrabajador, gbc);
        
        gbc.gridy = 4;
        panelPrincipal.add(btnUsuario, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        btnAdministrador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaLogin("Administrador");
            }
        });
        
        btnTrabajador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaLogin("Trabajador");
            }
        });
        
        btnUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaUsuario();
            }
        });
    }
    
    private void abrirVentanaLogin(String tipoUsuario) {
        dispose();
        A02_VentanaLogin ventanaLogin = new A02_VentanaLogin();
        ventanaLogin.setTipoUsuario(tipoUsuario);
        ventanaLogin.setVisible(true);
    }
    
    private void abrirVentanaUsuario() {
        dispose();
        new A03_VentanaUsuarioDNI().setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
            }
            
            if (ConexionBD.verificarConexion()) {
                System.out.println("Conexión a la base de datos establecida correctamente");
                new A01_VentanaInicio();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error: No se pudo conectar a la base de datos.\nVerifique la conexión e intente nuevamente.",
                    "Error de Conexión", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}

