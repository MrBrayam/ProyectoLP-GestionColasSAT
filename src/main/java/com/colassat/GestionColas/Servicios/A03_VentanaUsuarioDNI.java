
package com.colassat.GestionColas.Servicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class A03_VentanaUsuarioDNI extends JFrame {
    private JTextField txtNombre;
    private JTextField txtDNI;
    private JButton btnIngresar;
    private JButton btnVolver;
    
    public A03_VentanaUsuarioDNI() {
        initComponents();
        setupLayout();
        setupEvents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Acceso de Usuario - Sistema de Gestión de Colas SAT");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400); 
        setResizable(true); 
        
        txtNombre = new JTextField(20);
        txtDNI = new JTextField(20);
        
        txtNombre.setEditable(true);
        txtDNI.setEditable(true);
        
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDNI.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnIngresar = new JButton("INGRESAR");
        btnVolver = new JButton("VOLVER");
        
        Font btnFont = new Font("Arial", Font.BOLD, 12);
        
        btnIngresar.setFont(btnFont);
        btnIngresar.setBackground(new Color(40, 167, 69));
        btnIngresar.setForeground(Color.BLACK);
        btnIngresar.setPreferredSize(new Dimension(120, 35));
        btnIngresar.setFocusPainted(false);
        
        btnVolver.setFont(btnFont);
        btnVolver.setBackground(new Color(108, 117, 125));
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(248, 249, 250));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("ACCESO DE USUARIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(52, 58, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        JLabel lblSubtitulo = new JLabel("Ingrese sus datos personales");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10);
        panelPrincipal.add(lblSubtitulo, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtNombre, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setFont(new Font("Arial", Font.BOLD, 14));
        panelPrincipal.add(lblDNI, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtDNI, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 249, 250));
        panelBotones.add(btnIngresar);
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
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresar();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volver();
            }
        });
        
        txtNombre.addActionListener(e -> txtDNI.requestFocus());
        txtDNI.addActionListener(e -> ingresar());
    }
    
    private void ingresar() {
        String nombre = txtNombre.getText().trim();
        String dni = txtDNI.getText().trim();
        
        if (nombre.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (dni.length() != 8) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe tener 8 dígitos.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe contener solo números.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        dispose();
        new C01_VentanaCrearTicket(nombre, dni).setVisible(true);
    }
    
    private void volver() {
        dispose();
        new A01_VentanaInicio().setVisible(true);
    }
}
